package com.ashwin.evolve.programs.genetics;

import java.util.ArrayList;
import java.util.List;

import com.ashwin.evolve.programs.Instruction;
import com.ashwin.evolve.programs.Program;

public class GeneticProgram extends Program {
			
	private GeneticConfiguration _config;
	private double _fitness;
	
	public GeneticProgram(List<Instruction> instructions, GeneticConfiguration config) {
		super(instructions);
		_config = config;
		_fitness = config.getFitness(this);
	}
	
	/**
	 * Returns the fitness of this genetic program.
	 * 
	 * @return
	 */
	public double getFitness() {
		return _fitness;
	}
	
	/**
	 * Performs crossover between this genetic program and the specified mate.
	 * Crossover works by exchanging random blocks of instructions between the
	 * two programs. This method is not destructive; the parent programs are not
	 * effected in any way by the execution of this method.
	 * 
	 * @param mate
	 * @return
	 * @throws Exception
	 */
	public final GeneticProgram[] crossover(GeneticProgram mate, double rate) throws Exception {
		List<Instruction> i1 = new ArrayList<Instruction>(this.getInstructions());
		List<Instruction> i2 = new ArrayList<Instruction>(mate.getInstructions());
		
		if(Math.random() < rate) {
			// Generate two random numbers on [0, list.size()) swap the block of
			// instructions between these random numbers in each of the programs.
			int l = (int) (Math.random() * i1.size());
			int h = (int) (Math.random() * (i1.size() - l));
			
			List<Instruction> s1 = new ArrayList<Instruction>(i1.subList(l, l + h));
			List<Instruction> s2 = new ArrayList<Instruction>(i2.subList(l, l + h));
			
			i1.removeAll(s1);
			i2.removeAll(s2);
			
			i1.addAll(l, s2);
			i2.addAll(l, s1);
		}
		
		return new GeneticProgram[] { 
				new GeneticProgram(i1, _config), 
				new GeneticProgram(i2, _config) };
	}
	
	/**
	 * Performs mutation on this genetic program. Mutation works by randomly
	 * changing instructions in the program. This method is not destructive; the
	 * parent program is not effected in any way by the execution of this
	 * method.
	 * 
	 * @param rate
	 * @return
	 * @throws Exception
	 */
	public final void mutate(double rate) throws Exception {
		List<Instruction> instr = getInstructions();
		for(int i = 0; i < instr.size(); i++)
			if(Math.random() < rate)
				instr.set(i, _config.getRandomInstruction(i, instr.size()));
	}
	
}
