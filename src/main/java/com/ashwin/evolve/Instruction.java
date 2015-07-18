package com.ashwin.evolve;

import com.ashwin.evolve.exceptions.ExecutionException;


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
