package com.ashwin.evolve.programs.operands;

import com.ashwin.evolve.programs.Memory;
import com.ashwin.evolve.programs.exceptions.ExecutionException;

public class IndirectOperand implements MutableOperand {
	
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
	public int read() throws ExecutionException {
		return _second.read(_first.read(_address));
	}

	@Override
	public void write(int value) throws ExecutionException {
		_second.write(_first.read(_address), value);
	}
	
	@Override
	public String toString() {
		return _second + "[" + _first + "[" + _address + "]]";
	}
}
