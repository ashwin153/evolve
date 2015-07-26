package com.ashwin.evolve.genetic.chromosomes;

import java.util.ArrayList;
import java.util.List;

import com.ashwin.evolve.genetic.GeneticChromosome;
import com.ashwin.evolve.genetic.GeneticPopulation;
import com.ashwin.evolve.programs.Instruction;
import com.ashwin.evolve.programs.Memory;
import com.ashwin.evolve.programs.Operand;
import com.ashwin.evolve.programs.Program;
import com.ashwin.evolve.programs.exceptions.ExecutionException;
import com.ashwin.evolve.programs.instructions.Dec;
import com.ashwin.evolve.programs.instructions.Inc;
import com.ashwin.evolve.programs.instructions.Jeq;
import com.ashwin.evolve.programs.instructions.Jg;
import com.ashwin.evolve.programs.instructions.Jl;
import com.ashwin.evolve.programs.instructions.Jmp;
import com.ashwin.evolve.programs.instructions.Jne;
import com.ashwin.evolve.programs.instructions.Mov;
import com.ashwin.evolve.programs.instructions.Xchg;
import com.ashwin.evolve.programs.operands.DirectOperand;
import com.ashwin.evolve.programs.operands.ImmediateOperand;
import com.ashwin.evolve.programs.operands.IndirectOperand;

public class SortingChromosome extends Program implements GeneticChromosome<SortingChromosome> {
	
	/** The number of tests used in fitness calculations. */
	private static final int NUM_TESTS = 60;
	
	/** The number of inputs used in each test. */
	private static final int NUM_INPUTS = 30;
		
	private Memory _in, _wk;
	private double _fitness;
	
	/**
	 * Constructs a new SortingChromosome that uses the specified input and
	 * working memorys for their computation.
	 * 
	 * @param instructions program instructions
	 * @param in input memory
	 * @param wk working memory
	 * @throws ExecutionException
	 */
	public SortingChromosome(List<Instruction> instructions, Memory in, Memory wk) {
		super(instructions);
		_in = in;
		_wk = wk;
		
		// Calculate the fitness of this chromosome using the number of
		// inversions it is able to "fix" in a set of randomly generated numbers
		for (int i = 0; i < SortingChromosome.NUM_TESTS; i++) {
			try {
				for (int j = 0; j < in.size(); j++)
					_in.write(j, (int) (Math.random() * Integer.MAX_VALUE));
				
				int[] zeroes = new int[_wk.size()];
				zeroes[0] = in.size();
				_wk.write(0, zeroes);
				
				// Calculate the total number of inversions before and after execution
				int before = SortingChromosome.getInversions(_in);
				execute();
				int after = SortingChromosome.getInversions(_in);
				_fitness += (double) (after - before) / before;
			} catch (ExecutionException e) {
				// Penalize sorting programs that do not terminate
				_fitness += 5;
			}
		}
	}
	
	@Override
	public double fitness() {
		return _fitness;
	}
	
	@Override
	public SortingChromosome crossover(SortingChromosome mate, double rate) {
		List<Instruction> i1 = new ArrayList<Instruction>(this.getInstructions());
		
		if(Math.random() < rate) {
			// Generate two random numbers on [0, list.size()) swap the block of
			// instructions between these random numbers in each of the programs.
			int l = (int) (Math.random() * i1.size());
			int h = (int) (Math.random() * (i1.size() - l));

			// Swap the exiting instructions at [l, l + h) in i1 with those in i2
			List<Instruction> i2 = mate.getInstructions().subList(l, l + h);
			i1.subList(l, l + h).clear();
			i1.addAll(l, new ArrayList<Instruction>(i2));
		}
		
		return new SortingChromosome(i1, _in, _wk);
	}

	@Override
	public SortingChromosome mutate(double rate) {
		List<Instruction> instr = new ArrayList<Instruction>(getInstructions());
		for(int i = 0; i < instr.size(); i++)
			// Randomly decide whether or not to mutate this instruction
			if(Math.random() < rate)
				// There are two ways to mutate an instruction: (1) randomly
				// generate a completely new instruction or (2) randomly
				// generate new operands for the existing intstruction.
				if(Math.random() < 0.75)
					instr.set(i, getRandomInstruction(i, size(), _in, _wk));
				else
					instr.set(i, getRandomInstruction(i, size(), _in, _wk, instr.get(i).getClass()));
		return new SortingChromosome(instr, _in, _wk);
	}
	
	/**
	 * A simple O(n^2) algorithm for calculating the number of inversions in a
	 * permutation. A better algorithm would be to use a modified merge sort to
	 * count the number of inversions O(n * log n).
	 * 
	 * The maximum number of inversions of a permutation is n(n-1)/2; therefore,
	 * the average number of permutations is n(n-1)/4. Therefore the x = (inputs
	 * * (inputs - 1) / 4; (x - (total delta inversions)/population_size) / x
	 * tells us on average, what percentage of the inversions we are fixing.
	 * 
	 * @param memory memory space
	 * @return number of inversions
	 * @throws ExecutionException
	 */
	private static int getInversions(Memory memory) throws ExecutionException {
		int inversions = 0;
		for(int i = 0; i < memory.size(); i++)
			for(int j = i + 1; j < memory.size(); j++)
				if(memory.read(i) > memory.read(j))
					inversions++;
		return inversions;
	}
	
	/**
	 * Returns a random operand to use for jump instructions.
	 * 
	 * @param in
	 * @param wk
	 * @return
	 */
	private static Operand getRandomJumpOperand(Memory in, Memory wk) {
		switch((int) (Math.random() * 3)) {
			case 0:  return new DirectOperand(wk);
			case 1:  return new IndirectOperand(wk, in);
			default: return new ImmediateOperand(-in.size(), in.size());
		}
	}

	/**
	 * Returns a random instance of the instruction of the specified class. If
	 * the class is not a member of the instruction set for sorting programs,
	 * then throws an illegal argument exception.
	 * 
	 * @param index instruction index inside of program (used for jumps)
	 * @param length length of program
	 * @param in input memory
	 * @param wk working memory
	 * @param clazz instruction type
	 * @return random instruction
	 */
	private static Instruction getRandomInstruction(int index, int length, Memory in, Memory wk, Class<? extends Instruction> clazz) {
		switch(clazz.getSimpleName()) {
			case "Dec":  return new Dec  (new DirectOperand(wk));
			case "Inc":	 return new Inc  (new DirectOperand(wk));
			case "Xchg": return new Xchg (new IndirectOperand(wk, in), new IndirectOperand(wk, in));
			case "Mov":	 
				switch((int) (Math.random() * 2)) {
					case 0:  return new Mov  (new DirectOperand(wk), new DirectOperand(wk));
					default: return new Mov  (new ImmediateOperand(-5, 5), new DirectOperand(wk));
				}
				
			// Handle jump instructions separately so that we can extract out reused code.
			default:
				ImmediateOperand line = new ImmediateOperand(- index - 1, length - index + 1);
				Operand o1 = getRandomJumpOperand(in, wk);
				Operand o2 = getRandomJumpOperand(in, wk);

				switch(clazz.getSimpleName()) {
					case "Jeq": return new Jeq (line, o1, o2);
					case "Jg":  return new Jg  (line, o1, o2);
					case "Jl":	return new Jl  (line, o1, o2);
					case "Jne": return new Jne (line, o1, o2);
					case "Jmp": return new Jmp (line);
					default: throw new IllegalArgumentException("Unrecognized instruction type");
				}
		}
	}
	
	/**
	 * Returns a random instance of an arbitrary instruction.
	 * 
	 * @param index instruction index inside of program (used for jumps)
	 * @param length length of program
	 * @param in input memory
	 * @param wk working memory
	 * @return random instruction
	 */
	private static Instruction getRandomInstruction(int index, int length, Memory in, Memory wk) {
		switch((int) (Math.random() * 5)) {
			case 0: return getRandomInstruction(index, length, in, wk, Dec.class);
			case 1: return getRandomInstruction(index, length, in, wk, Inc.class);
			case 2: return getRandomInstruction(index, length, in, wk, Mov.class);
			case 3: return getRandomInstruction(index, length, in, wk, Xchg.class);
			
			// De-emphasize jump instructions; this reduces the probability that
			// any given jump instruction will be selected. Think about it.
			default:
				switch((int) (Math.random() * 5)) {
					case 0:  return getRandomInstruction(index, length, in, wk, Jeq.class);
					case 1:  return getRandomInstruction(index, length, in, wk, Jg.class);
					case 2:  return getRandomInstruction(index, length, in, wk, Jl.class);
					case 3:  return getRandomInstruction(index, length, in, wk, Jne.class);
					default: return getRandomInstruction(index, length, in, wk, Jmp.class);
				}
		}
	}
	
	/**
	 * Builds a new randomized population of sorting chromosomes of the specified length.
	 * 
	 * @param size number of programs
	 * @param length length of program
	 * @return random chromosome
	 */
	public static GeneticPopulation<SortingChromosome> getRandomPopulation(int size, int length) {
		List<SortingChromosome> chromosomes = new ArrayList<SortingChromosome>();
		Memory in = new Memory("in", NUM_INPUTS, 0);
		Memory wk = new Memory("wk", 5, 0);
		
		for(int i = 0; i < size; i++) {
			List<Instruction> instrs = new ArrayList<Instruction>();
			for(int j = 0; j < length; j++)
				instrs.add(getRandomInstruction(j, length, in, wk));
			chromosomes.add(new SortingChromosome(instrs, in, wk));
		}
		
		return new GeneticPopulation<SortingChromosome>(chromosomes);
	}

}
