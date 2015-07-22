package com.ashwin.evolve.genetic.chromosomes;

import java.util.ArrayList;
import java.util.List;

import com.ashwin.evolve.genetic.GeneticChromosome;
import com.ashwin.evolve.programs.Instruction;
import com.ashwin.evolve.programs.Memory;
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
import com.ashwin.evolve.programs.operands.IndirectOperand;

public class SortingChromosome extends Program implements GeneticChromosome<SortingChromosome> {
	
	/** The number of tests used in fitness calculations. */
	private static final int NUM_TESTS = 20;
	
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

				// Calculate the total number of inversions before and after execution
				int before = SortingChromosome.getInversions(_in);
				execute();
				int after = SortingChromosome.getInversions(_in);
				_fitness -= (after - before) / before;
			} catch (ExecutionException e) {
				// Penalize sorting programs that do not terminate
				_fitness += _in.size();
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
			List<Instruction> i2 = new ArrayList<Instruction>(mate.getInstructions());
			int l = (int) (Math.random() * i1.size());
			int h = (int) (Math.random() * (i1.size() - l));
			
			// Swap the exiting instructions at [l, l + h) in i1 with those in i2
			i1.subList(l, l + h).clear();
			i1.addAll(l, i2.subList(l, l + h));
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
				if(Math.random() < 0.5)
					instr.set(i, getRandomInstruction());
				else
					instr.set(i, getRandomInstruction(instr.get(i).getClass()));
		return new SortingChromosome(instr, _in, _wk);
	}

	/**
	 * Returns a random instance of the instruction of the specified class. If
	 * the class is not a member of the instruction set for sorting programs,
	 * then throws an illegal argument exception.
	 * 
	 * @param clazz
	 * @return
	 */
	private Instruction getRandomInstruction(Class<? extends Instruction> clazz) {
		switch(clazz.getSimpleName()) {
			case "Dec":  return new Dec  (new DirectOperand(_wk));
			case "Inc":	 return new Inc  (new DirectOperand(_wk));
			case "Xchg": return new Xchg (new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in));
			case "Mov":	
			case "Jeq":  
			case "Jg":
			case "Jl":	 
			case "Jne":		
			case "Jmp":
			default: throw new IllegalArgumentException("Unrecognized instruction type");
		}
		
//		switch((int) (Math.random() * 7)) {
//		case  0: return new Dec  (new DirectOperand(_wk));
//		case  1: return new Inc  (new DirectOperand(_wk));
//		case  2: return new Mov  (new DirectOperand(_in), new DirectOperand(_wk));
//		case  3: return new Mov  (new DirectOperand(_wk), new DirectOperand(_wk));
//		case  4: return new Mov  (new ImmediateOperand(-5, 5), new DirectOperand(_wk));
//		case  5: return new Xchg (new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in));
//		default:
//			switch((int) (Math.random() * 9)) {
//				case  0: return new Jeq  (new ImmediateOperand(-index, length - index), new DirectOperand(_wk), new DirectOperand(_wk));
//				case  1: return new Jg   (new ImmediateOperand(-index, length - index), new DirectOperand(_wk), new DirectOperand(_wk));
//				case  2: return new Jl   (new ImmediateOperand(-index, length - index), new DirectOperand(_wk), new DirectOperand(_wk));
//				case  3: return new Jne  (new ImmediateOperand(-index, length - index), new DirectOperand(_wk), new DirectOperand(_wk));
//				case  4: return new Jeq  (new ImmediateOperand(-index, length - index), new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in));
//				case  5: return new Jg   (new ImmediateOperand(-index, length - index), new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in));
//				case  6: return new Jl   (new ImmediateOperand(-index, length - index), new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in));
//				case  7: return new Jne  (new ImmediateOperand(-index, length - index), new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in));
//				default: return new Jmp  (new ImmediateOperand(-index, length - index));
//			}
//	}
	}
	
	private Instruction getRandomInstruction() {
		switch((int) (Math.random() * 5)) {
			case 0: return getRandomInstruction(Dec.class);
			case 1: return getRandomInstruction(Inc.class);
			case 2: return getRandomInstruction(Mov.class);
			case 3: return getRandomInstruction(Xchg.class);
			
			// De-emphasize jump instructions; this reduces the probability that
			// any given jump instruction will be selected. Think about it.
			default:
				switch((int) (Math.random() * 5)) {
					case 0:  return getRandomInstruction(Jeq.class);
					case 1:  return getRandomInstruction(Jg.class);
					case 2:  return getRandomInstruction(Jl.class);
					case 3:  return getRandomInstruction(Jne.class);
					default: return getRandomInstruction(Jmp.class);
				}
		}
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
	 * @param memory
	 * @return
	 * @throws ExecutionException
	 */
	public static int getInversions(Memory memory) throws ExecutionException {
		int inversions = 0;
		for(int i = 0; i < memory.size(); i++)
			for(int j = i + 1; j < memory.size(); j++)
				if(memory.read(i) > memory.read(j))
					inversions++;
		return inversions;
	}
}
