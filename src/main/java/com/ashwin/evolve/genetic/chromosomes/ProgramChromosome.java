package com.ashwin.evolve.genetic.chromosomes;

import java.util.ArrayList;
import java.util.List;

import com.ashwin.evolve.genetic.GeneticChromosome;
import com.ashwin.evolve.genetic.GeneticFactory;
import com.ashwin.evolve.programs.Instruction;
import com.ashwin.evolve.programs.Program;
import com.ashwin.evolve.programs.exceptions.ExecutionException;

public class ProgramChromosome extends Program implements GeneticChromosome<ProgramChromosome> {
	
	private GeneticFactory<ProgramChromosome, Instruction> _factory;
	
	/**
	 * Constructs a new SortingChromosome that uses the specified input and
	 * working memorys for their computation.
	 * 
	 * @param instructions program instructions
	 * @param in input memory
	 * @param wk working memory
	 * @throws ExecutionException
	 */
	public ProgramChromosome(List<Instruction> instructions, GeneticFactory<ProgramChromosome, Instruction> factory) {
		super(instructions);
		_factory = factory;
	}
	
	@Override
	public ProgramChromosome crossover(ProgramChromosome mate, double rate) {
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
		
		return new ProgramChromosome(i1, _factory);
	}

	@Override
	public ProgramChromosome mutate(double rate) {
		List<Instruction> instr = new ArrayList<Instruction>(getInstructions());
		for(int i = 0; i < instr.size(); i++)
			// Randomly decide whether or not to mutate this instruction
			if(Math.random() < rate)
				// There are two ways to mutate an instruction: (1) randomly
				// generate a completely new instruction or (2) randomly
				// generate new operands for the existing intstruction.
				instr.set(i, _factory.getRandomGene());
		return new ProgramChromosome(instr, _factory);
	}

}
