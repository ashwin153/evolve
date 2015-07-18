package com.ashwin.evolve.operands;

import com.ashwin.evolve.Memory;
import com.ashwin.evolve.Operand;
import com.ashwin.evolve.exceptions.ExecutionException;

public class IndirectOperand implements Operand {
	
	private Memory _first, _second;
	private int _address;
	
	public IndirectOperand(Memory first, Memory second) {
		this((int) (Math.random() * first.size()), first, second);
	}
	
	public IndirectOperand(int address, Memory first, Memory second) {
		_address = address;
		_first = first;
		_second = second;
	}

	@Override
	public int get() throws ExecutionException {
		return _second.read(_first.read(_address));
	}

	@Override
	public void set(int value) throws ExecutionException {
		throw new ExecutionException("Cannot mutate an indirect operand");
	}
	
	@Override
	public String toString() {
		return _second + "[" + _first + "[" + _address + "]]";
	}
}
