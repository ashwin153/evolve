package com.ashwin.evolve.genetics;

import java.util.ArrayList;
import java.util.List;

import com.ashwin.evolve.Instruction;

public abstract class GeneticConfiguration {
	
	private int _length;
	
	public GeneticConfiguration(int length) {
		_length = length;
	}
	
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
	abstract public Instruction getRandomInstruction(int length);
	
	/**
	 * Builds a new genetic program using this configuration. Generated programs
	 * are guarenteed to be valid; therefore, they must have a fitness less than
	 * Double.MAX_VALUE;
	 * 
	 * @param length
	 * @return
	 */
	public GeneticProgram build() {
		GeneticProgram program = null;
		do {
			List<Instruction> instr = new ArrayList<Instruction>();
			for(int i = 0; i < _length; i++)
				instr.add(getRandomInstruction(_length));
			program = new GeneticProgram(instr, this);
		} while(getFitness(program) >= Integer.MAX_VALUE);
	
		return program;
	}
}
