package com.ashwin.evolve.programs.genetics;

import java.util.ArrayList;
import java.util.List;

import com.ashwin.evolve.programs.Instruction;

public abstract class GeneticConfiguration {
	
	private int _length;
	
	public GeneticConfiguration(int length) {
		_length = length;
	}
	
	/**
	 * Resets the configuration to its original state. This is used as a general
	 * purpose clean-up method after a population has finished evolving a
	 * generation. This isn't pretty, but it's easier than fixing everything.
	 */
	abstract public void reset();
	
	/**
	 * Calculates the fitness of a given genetic program.
	 * 
	 * @param program
	 * @return
	 */
	abstract public double getFitness(GeneticProgram program);
	
	/**
	 * Generates a random instruction, given the current length of the program.
	 * 
	 * @return
	 */
	abstract public Instruction getRandomInstruction(int index, int length);
	
	/**
	 * Builds a new genetic program using this configuration. Generated programs
	 * are guarenteed to be valid; therefore, they must have a fitness less than
	 * Double.MAX_VALUE;
	 * 
	 * @param length
	 * @return
	 */
	public GeneticProgram build() {
		List<Instruction> instr = new ArrayList<Instruction>();
			for(int i = 0; i < _length; i++)
				instr.add(getRandomInstruction(i, _length));
		return new GeneticProgram(instr, this);
	}
}
