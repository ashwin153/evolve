package com.ashwin.evolve.programs.operands;

import com.ashwin.evolve.programs.Operand;
import com.ashwin.evolve.programs.exceptions.ExecutionException;

public class ImmediateOperand implements Operand {
	
	private int _value;
	
	public ImmediateOperand(int value) {
		_value = value;
	}
	
	public ImmediateOperand(int lower, int upper) {
		_value = (int) (Math.random() * (upper - lower) + lower);
	}
	
	@Override
	public int read() throws ExecutionException {
		return _value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(_value);
	}
}
