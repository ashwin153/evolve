package com.ashwin.evolve.genetic.chromosomes;

import java.util.List;

import com.ashwin.evolve.genetic.GeneticChromosome;
import com.ashwin.evolve.programs.Instruction;
import com.ashwin.evolve.programs.Program;

public class ProgramChromosome extends Program implements GeneticChromosome<ProgramChromosome> {

	public ProgramChromosome(List<Instruction> instructions) {
		super(instructions);
	}

	@Override
	public ProgramChromosome crossover(ProgramChromosome mate, double rate) {
//		List<Instruction> i1 = new ArrayList<Instruction>(this.getInstructions());
//		List<Instruction> i2 = new ArrayList<Instruction>(mate.getInstructions());
//		
//		if(Math.random() < rate) {
//			// Generate two random numbers on [0, list.size()) swap the block of
//			// instructions between these random numbers in each of the programs.
//			int l = (int) (Math.random() * i1.size());
//			int h = (int) (Math.random() * (i1.size() - l));
//			
//			List<Instruction> s1 = new ArrayList<Instruction>(i1.subList(l, l + h));
//			List<Instruction> s2 = new ArrayList<Instruction>(i2.subList(l, l + h));
//			
//			i1.removeAll(s1);
//			i2.removeAll(s2);
//			
//			i1.addAll(l, s2);
//			i2.addAll(l, s1);
//		}
//		
//		return new GeneticProgram[] { 
//				new GeneticProgram(i1, _config), 
//				new GeneticProgram(i2, _config) };
		return null;
	}

	@Override
	public ProgramChromosome mutate(ProgramChromosome random, double rate) {
//		List<Instruction> instr = getInstructions();
//		for(int i = 0; i < instr.size(); i++)
//			if(Math.random() < rate)
//				instr.set(i, _config.getRandomInstruction(i, instr.size()));
		return null;
	}

}
