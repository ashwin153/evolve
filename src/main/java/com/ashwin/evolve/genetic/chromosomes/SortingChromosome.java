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
import com.ashwin.evolve.programs.operands.ImmediateOperand;
import com.ashwin.evolve.programs.operands.IndirectOperand;

public class SortingChromosome extends Program implements GeneticChromosome<SortingChromosome> {

	private Memory _in, _wk;
	private double _fitness;
	
	public SortingChromosome(int length, Memory in, Memory wk) throws ExecutionException {
		super(new ArrayList<Instruction>());
		
		_in = in;
		_wk = wk;
		
		// Generate a new set of random instructions
		for(int i = 0; i < length; i++)
			getInstructions().add(random(i, length));
		
		// Calculate the fitness of this chromosome using the number of inversions it is 
		// able to "fix" in a set of randomly generated numbers
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < in.size(); j++)
				_in.write(j, (int) (Math.random() * Integer.MAX_VALUE));
			
			try {
				// Calculate the total number of inversions before and after execution
				int before = inversions(_in);
				execute();
				int after  = inversions(_in);
				_fitness  += (before - after) / before;
			} catch (ExecutionException e) {
				// Penalize sorting programs that do not terminate
				_fitness -= _in.size() * 20;
			}
		}
	}
	
	public SortingChromosome(List<Instruction> instructions, Memory in, Memory wk) {
		super(instructions);
		
		_in = in;
		_wk = wk;
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
	 * @param permutation
	 * @return
	 * @throws ExecutionException
	 */
	private int inversions(Memory memory) throws ExecutionException {
		int inversions = 0;
		for(int i = 0; i < memory.size(); i++)
			for(int j = i + 1; j < memory.size(); j++)
				if(memory.read(i) > memory.read(j))
					inversions++;
		return inversions;
	}
	
	public Instruction random(int index, int length) {
		switch((int) (Math.random() * 7)) {
			case  0: return new Dec  (new DirectOperand(_wk));
			case  1: return new Inc  (new DirectOperand(_wk));
			case  2: return new Mov  (new DirectOperand(_in), new DirectOperand(_wk));
			case  3: return new Mov  (new DirectOperand(_wk), new DirectOperand(_wk));
			case  4: return new Mov  (new ImmediateOperand(-5, 5), new DirectOperand(_wk));
			case  5: return new Xchg (new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in));
			default:
				switch((int) (Math.random() * 9)) {
					case  0: return new Jeq  (new ImmediateOperand(-index, length - index), new DirectOperand(_wk), new DirectOperand(_wk));
					case  1: return new Jg   (new ImmediateOperand(-index, length - index), new DirectOperand(_wk), new DirectOperand(_wk));
					case  2: return new Jl   (new ImmediateOperand(-index, length - index), new DirectOperand(_wk), new DirectOperand(_wk));
					case  3: return new Jne  (new ImmediateOperand(-index, length - index), new DirectOperand(_wk), new DirectOperand(_wk));
					case  4: return new Jeq  (new ImmediateOperand(-index, length - index), new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in));
					case  5: return new Jg   (new ImmediateOperand(-index, length - index), new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in));
					case  6: return new Jl   (new ImmediateOperand(-index, length - index), new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in));
					case  7: return new Jne  (new ImmediateOperand(-index, length - index), new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in));
					default: return new Jmp  (new ImmediateOperand(-index, length - index));
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
//		List<Instruction> instr = getInstructions();
//		for(int i = 0; i < instr.size(); i++)
//			if(Math.random() < rate)
//				instr.set(i, _config.getRandomInstruction(i, instr.size()));
		return null;
	}

}
