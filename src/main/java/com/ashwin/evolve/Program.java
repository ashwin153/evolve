package com.ashwin.evolve;

import java.util.List;

import com.ashwin.evolve.exceptions.ExecutionException;

/**
 * Think of a program as a series of instructions executed in sequential order. Programs 
 * 
 * @author ashwin
 * 
 */
public class Program implements Instruction {
	
	public static final int MAX_INSTRUCTIONS = 5000;
		
	private List<Instruction> _instructions;
	
	public Program(List<Instruction> instructions) {
		_instructions = instructions;
	}
	
	public int size() {
		return _instructions.size();
	}
	
	public List<Instruction> getInstructions() {
		return _instructions;
	}
	
	@Override
	public final int execute() throws ExecutionException {
		int pc = 0, counter = 0;
		
		while(pc >= 0 && pc < _instructions.size() && counter < Program.MAX_INSTRUCTIONS) {
			pc += _instructions.get(pc).execute();
			counter++;
		}
		
		if(counter >= Program.MAX_INSTRUCTIONS)
			throw new ExecutionException("Program did not terminate in " + Program.MAX_INSTRUCTIONS + " steps.");
		
		return 1;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Instruction instr : _instructions)
			sb.append(instr + "\n");
		return sb.toString();
	}
}
