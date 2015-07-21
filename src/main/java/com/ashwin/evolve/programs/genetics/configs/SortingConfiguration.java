package com.ashwin.evolve.programs.genetics.configs;

import com.ashwin.evolve.programs.Instruction;
import com.ashwin.evolve.programs.Memory;
import com.ashwin.evolve.programs.exceptions.ExecutionException;
import com.ashwin.evolve.programs.genetics.GeneticConfiguration;
import com.ashwin.evolve.programs.genetics.GeneticProgram;
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

public class SortingConfiguration extends GeneticConfiguration {
	
	private Memory _in, _wk;
	private int[][] _tests;
	
	/**
	 * Constructs a new sorting configuration.
	 * 
	 * @param length number of instructions in programs
	 * @param tests number of tests to perform
	 * @param inputs number of inputs in each test
	 */
	public SortingConfiguration(int length, int tests, int inputs) throws ExecutionException {
		super(length);
		
		_in = new Memory("in", inputs, 0);
		_tests = new int[tests][inputs];
		_wk = new Memory("wk", 6, 0);
		_wk.write(0, inputs);
	}
	
	@Override
	public void reset() {
		for(int i = 0; i < _tests.length; i++)
			for(int j = 0; j < _tests[i].length; j++)
				_tests[i][j] = (int) (Math.random() * Integer.MAX_VALUE);
	}
	
	@Override
	public double getFitness(GeneticProgram program) {
		double fitness = 0;
		for (int[] test : _tests) {
			int bonus = test.length * (test.length - 1) / 2;
			
			try {
				_in.write(0, test);
				int before = getInversions(_in);
				program.execute();
				int after = getInversions(_in);
				fitness += after - before;
				
				// Give a huge bonus if the algorithm is able to perfectly sort
				if(after == 0)
					fitness -= bonus;
				
			} catch (ExecutionException e) {
				// If a program does not terminate or encounters any errors in
				// execution (e.g., memory address out of range), then assign
				// the worst possible fitness to this individual.
				fitness += bonus;
			}
		}
		
		return fitness;
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
	private int getInversions(Memory memory) throws ExecutionException {
		int inversions = 0;
		for(int i = 0; i < memory.size(); i++)
			for(int j = i + 1; j < memory.size(); j++)
				if(memory.read(i) > memory.read(j))
					inversions++;
		return inversions;
	}
	
	@Override
	public Instruction getRandomInstruction(int index, int length) {
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
}
