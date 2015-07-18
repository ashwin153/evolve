package com.ashwin.evolve.genetics.configs;

import com.ashwin.evolve.Instruction;
import com.ashwin.evolve.Memory;
import com.ashwin.evolve.exceptions.ExecutionException;
import com.ashwin.evolve.genetics.GeneticConfiguration;
import com.ashwin.evolve.genetics.GeneticProgram;
import com.ashwin.evolve.instructions.Dec;
import com.ashwin.evolve.instructions.Inc;
import com.ashwin.evolve.instructions.Jeq;
import com.ashwin.evolve.instructions.Jg;
import com.ashwin.evolve.instructions.Jl;
import com.ashwin.evolve.instructions.Jmp;
import com.ashwin.evolve.instructions.Jne;
import com.ashwin.evolve.instructions.Mov;
import com.ashwin.evolve.instructions.Xchg;
import com.ashwin.evolve.operands.DirectOperand;
import com.ashwin.evolve.operands.ImmediateOperand;

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
		_wk = new Memory("wk", inputs, 0);
		_wk.write(0, inputs);
		
		for(int i = 0; i < _tests.length; i++)
			for(int j = 0; j < _tests[i].length; j++)
				_tests[i][j] = (int) (Math.random() * Integer.MAX_VALUE);
	}
	
	@Override
	public double getFitness(GeneticProgram program) {
		double fitness = 0;
		for (int[] test : _tests) {
			try {
				_in.write(0, test);
				int before = getInversions(_in);
				program.execute();
				fitness += getInversions(_in) - before;
			} catch (ExecutionException e) {
				// If a program does not terminate or encounters any errors in
				// execution (e.g., memory address out of range), then assign
				// the worst possible fitness to this individual.
				fitness += Integer.MAX_VALUE;
			}
		}
		
		return fitness;
	}
	
	/**
	 * A simple O(n^2) algorithm for calculating the number of inversions in a
	 * permutation. A better algorithm would be to use a modified merge sort to
	 * count the number of inversions O(n * log n).
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
	public Instruction getRandomInstruction(int length) {
		switch((int) (Math.random() * 12)) {
			case  0: return new Dec  (new DirectOperand(_wk));
			case  1: return new Inc  (new DirectOperand(_wk));
			case  2: return new Mov  (new DirectOperand(_in), new DirectOperand(_wk));
			case  3: return new Mov  (new DirectOperand(_wk), new DirectOperand(_wk));
			case  4: return new Mov  (new ImmediateOperand(), new DirectOperand(_wk));
			case  5: return new Xchg (new DirectOperand(_wk), new DirectOperand(_wk));
			case  6: return new Xchg (new DirectOperand(_in), new DirectOperand(_in));
			case  7: return new Jeq  (new ImmediateOperand(0, length), new DirectOperand(_wk), new DirectOperand(_wk));
			case  8: return new Jg   (new ImmediateOperand(0, length), new DirectOperand(_wk), new DirectOperand(_wk));
			case  9: return new Jl   (new ImmediateOperand(0, length), new DirectOperand(_wk), new DirectOperand(_wk));
			case 10: return new Jne  (new ImmediateOperand(0, length), new DirectOperand(_wk), new DirectOperand(_wk));
			default: return new Jmp  (new ImmediateOperand(0, length));
		}
	}
}
