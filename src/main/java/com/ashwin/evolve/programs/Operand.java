package com.ashwin.evolve.programs;

import com.ashwin.evolve.programs.exceptions.ExecutionException;

public interface Operand {

	/**
	 * Reads the value of this operand.
	 * 
	 * @return value of the operand
	 * @throws ExecutionException
	 */
	public int read() throws ExecutionException;
		
}
