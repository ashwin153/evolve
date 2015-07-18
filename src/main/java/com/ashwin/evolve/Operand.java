package com.ashwin.evolve;

import com.ashwin.evolve.exceptions.ExecutionException;

public interface Operand {

	public int get() throws ExecutionException;
	
	public void set(int value) throws ExecutionException;
	
}
