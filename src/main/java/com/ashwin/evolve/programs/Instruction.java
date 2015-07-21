package com.ashwin.evolve.programs;

import com.ashwin.evolve.programs.exceptions.ExecutionException;


/**
 * Represents an individual instruction. Instructions take some memory /
 * register / immediate values and perform some action. Instructions should be
 * atomic.
 * 
 * @author ashwin
 * 
 */
public interface Instruction {
	
	/**
	 * Executes the instruction and returns how much to increment the program
	 * counter.
	 * 
	 * @return
	 * @throws ExecutionException
	 */
	public int execute() throws ExecutionException;
	
}
