package com.ashwin.evolve.genetics.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.ashwin.evolve.genetics.GeneticAlgorithm;
import com.ashwin.evolve.genetics.chromosomes.ProgramChromosome;
import com.ashwin.evolve.programs.Instruction;
import com.ashwin.evolve.programs.Memory;
import com.ashwin.evolve.programs.Operand;
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

public class SortingAlgorithm extends GeneticAlgorithm<ProgramChromosome, Instruction> {

	private Memory _in, _wk;
	private int[][] _tests;
	private int _length;
	
	public SortingAlgorithm(Properties props, int length) {
		super(props);
		
		_in = new Memory("in", 20, 0);
		_wk = new Memory("wk", 5, 0);
		_length = length;
		
		_tests = new int[60][];
		for(int i = 0; i < _tests.length; i++) {
			_tests[i] = new int[(int) (Math.random() * 20)];
			for(int j = 0; j < _tests[i].length; j++)
				_tests[i][j] = (int) (Math.random() * Integer.MAX_VALUE);
		}
	}
	
	@Override
	public double getFitness(ProgramChromosome chromosome) {
		// Calculate the fitness of this chromosome using the number of
		// inversions it is able to "fix" in a set of randomly generated numbers
		double fitness = 0.0;
		for (int i = 0; i < _tests.length; i++) {
			try {
				_in.reset(_tests[i].length, 0);
				_wk.reset(5, 0);
				_in.write(0, _tests[i]);
				_wk.write(0, _tests[i].length);
				
				// Calculate the total number of inversions before and after execution
				int before = getInversions(_in);
				chromosome.execute(0);
				int after = getInversions(_in);
				fitness += (double) (after - before) / before;
			} catch (ExecutionException e) {
				// Penalize sorting programs that do not terminate
				fitness += 1;
			}
		}
		
		return fitness;
	}

	@Override
	public Instruction getRandomGene() {
		switch((int) (Math.random() * 5)) {
			case 0: return new Dec  (new DirectOperand(_wk));
			case 1: return new Inc  (new DirectOperand(_wk));
			case 2: return new Xchg (new IndirectOperand(_wk, _in), new IndirectOperand(_wk, _in));
			case 3:
				switch((int) (Math.random() * 2)) {
					case 0:  return new Mov  (new DirectOperand(_wk), new DirectOperand(_wk));
					default: return new Mov  (new ImmediateOperand(-5, 5), new DirectOperand(_wk));
				}
			
			// De-emphasize jump instructions; this reduces the probability that
			// any given jump instruction will be selected. Think about it.
			default:
				ImmediateOperand line = new ImmediateOperand(0, _length + 1);
				List<Operand> operands = Arrays.asList(new DirectOperand(_wk),
						new IndirectOperand(_wk, _in), new ImmediateOperand(0, _length + 1));
			
				Operand o1 = operands.get((int) (Math.random() * operands.size()));
				Operand o2 = operands.get((int) (Math.random() * operands.size()));
				
				switch((int) (Math.random() * 5)) {
					case 0:  return new Jeq (line, o1, o2);
					case 1:  return new Jg  (line, o1, o2);
					case 2:  return new Jl  (line, o1, o2);
					case 3:  return new Jne (line, o1, o2);
					default: return new Jmp (line);
				}
		}
	}

	@Override
	public ProgramChromosome getRandomChromosome() {
		List<Instruction> instructions = new ArrayList<Instruction>();
		for(int i = 0; i < _length; i++)
			instructions.add(getRandomGene());
		return new ProgramChromosome(instructions, this);
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
}
