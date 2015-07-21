package com.ashwin.evolve.genetic.populations;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ashwin.evolve.genetic.GeneticPopulation;
import com.ashwin.evolve.genetic.chromosomes.ProgramChromosome;
import com.ashwin.evolve.programs.Instruction;
import com.ashwin.evolve.programs.Memory;
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

public class SortingPopulation extends GeneticPopulation<ProgramChromosome> {

	private Memory _in, _wk;
	private int[][] _tests;
	private int _numInstrs, _numTests, _numInputs;

	public SortingPopulation(Properties props) throws ExecutionException {
		super(props);
		
		_numInstrs = Integer.valueOf(props.getProperty("ga.sorting.instrs"));
		_numTests  = Integer.valueOf(props.getProperty("ga.sorting.tests"));
		_numInputs = Integer.valueOf(props.getProperty("ga.sorting.inputs"));
		
		_in = new Memory("in", _numInputs, 0);
		_wk = new Memory("wk", 5, 0);
		_wk.write(0, _numInputs);
	}
	
	@Override
	public void evolve() {
		// Regenerate the test cases before evolving the population. This
		// ensures that the optimal algorithm can handle arbitrary inputs.
		_tests = new int[_numTests][_numInputs];
		for(int i = 0; i < _tests.length; i++)
			for(int j = 0; j < _tests[i].length; j++)
				_tests[i][j] = (int) (Math.random() * Integer.MAX_VALUE);
	
		super.evolve();
	}
	
	@Override
	public double fitness(ProgramChromosome chromosome) {
		double fitness = 0;
		for (int[] test : _tests) {
			try {
				_in.write(0, test);
				int before = getInversions(_in);
				chromosome.execute();
				int after = getInversions(_in);
				fitness += after - before;
				
				// Reward programs that successfully sort inputs
				if(after == 0)
					fitness -= _numTests * 20;
			} catch (ExecutionException e) {
				// Penalize programs that do not terminate
				fitness += _numTests * 20;
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
	public ProgramChromosome random() {
		List<Instruction> instrs = new ArrayList<Instruction>();
		
		for(int i = 0; i < _numInstrs; i++) {
			switch((int) (Math.random() * 7)) {
				case  0: instrs.add(new Dec  (new DirectOperand(_wk))); break;
				case  1: instrs.add(new Inc  (new DirectOperand(_wk))); break;
				case  2: instrs.add(new Mov  (new DirectOperand(_in), new DirectOperand(_wk))); break;
				case  3: instrs.add(new Mov  (new DirectOperand(_wk), new DirectOperand(_wk))); break;
				case  4: instrs.add(new Mov  (new ImmediateOperand(-5, 5), new DirectOperand(_wk))); break;
				case  5: instrs.add(new Xchg (new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in))); break;
				default:
					switch((int) (Math.random() * 9)) {
						case  0: instrs.add(new Jeq  (new ImmediateOperand(-i, _numInstrs - i), new DirectOperand(_wk), new DirectOperand(_wk)));
						case  1: instrs.add(new Jg   (new ImmediateOperand(-i, _numInstrs - i), new DirectOperand(_wk), new DirectOperand(_wk))); break;
						case  2: instrs.add(new Jl   (new ImmediateOperand(-i, _numInstrs - i), new DirectOperand(_wk), new DirectOperand(_wk))); break;
						case  3: instrs.add(new Jne  (new ImmediateOperand(-i, _numInstrs - i), new DirectOperand(_wk), new DirectOperand(_wk))); break;
						case  4: instrs.add(new Jeq  (new ImmediateOperand(-i, _numInstrs - i), new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in))); break;
						case  5: instrs.add(new Jg   (new ImmediateOperand(-i, _numInstrs - i), new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in))); break;
						case  6: instrs.add(new Jl   (new ImmediateOperand(-i, _numInstrs - i), new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in))); break;
						case  7: instrs.add(new Jne  (new ImmediateOperand(-i, _numInstrs - i), new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in))); break;
						default: instrs.add(new Jmp  (new ImmediateOperand(-i, _numInstrs - i)));
					}
			}
		}
		
		return new ProgramChromosome(instrs);
	}
}
