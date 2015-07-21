package com.ashwin.evolve.programs;

import com.ashwin.evolve.programs.exceptions.ExecutionException;

public interface Operand {

	public int read() throws ExecutionException;
		
}
