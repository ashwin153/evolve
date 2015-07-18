package com.ashwin.evolve.operands;

import com.ashwin.evolve.Operand;
import com.ashwin.evolve.exceptions.ExecutionException;

public class ImmediateOperand implements Operand {
	
	private int _value;
	
	public ImmediateOperand() {
		// Uses a power function to bias the random number toward lower
		// values. This allows constants to range over the entire range
		// of values, while still ranging over all the integers.
		this((int) (((Math.random() < 0.50) ? 1 : -1) * 
				(Math.pow(Math.random(), 15) * Integer.MAX_VALUE)));
	}
	
	public ImmediateOperand(int value) {
		_value = value;
	}
	
	public ImmediateOperand(int lower, int upper) {
		_value = (int) (Math.random() * (upper - lower) + lower);
	}
	
	@Override
	public int get() throws ExecutionException {
		return _value;
	}

	@Override
	public void set(int value) throws ExecutionException {
		throw new ExecutionException("Cannot mutate an immediate operand");
	}
	
	@Override
	public String toString() {
		return String.valueOf(_value);
	}
}
