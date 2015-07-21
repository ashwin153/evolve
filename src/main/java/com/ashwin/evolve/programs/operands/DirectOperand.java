package com.ashwin.evolve.programs.operands;

import com.ashwin.evolve.programs.Memory;
import com.ashwin.evolve.programs.exceptions.ExecutionException;

public class DirectOperand implements MutableOperand {
	
	private Memory _memory;
	private int _address;

	public DirectOperand(Memory memory) {
		this((int) (Math.random() * memory.size()), memory);
	}
	
	public DirectOperand(int address, Memory memory) {
		_address = address;
		_memory = memory;
	}
	
	@Override
	public int read() throws ExecutionException {
		return _memory.read(_address);
	}
	
	@Override
	public void write(int value) throws ExecutionException {
		_memory.write(_address, value);
	}
	
	@Override
	public String toString() {
		return _memory + "[" + _address + "]";
	}
	
}
