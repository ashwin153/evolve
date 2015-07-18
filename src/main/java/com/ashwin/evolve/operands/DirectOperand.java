package com.ashwin.evolve.operands;

import com.ashwin.evolve.Memory;
import com.ashwin.evolve.Operand;
import com.ashwin.evolve.exceptions.ExecutionException;

public class DirectOperand implements Operand {
	
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
	public int get() throws ExecutionException {
		return _memory.read(_address);
	}
	
	@Override
	public void set(int value) throws ExecutionException {
		_memory.write(_address, value);
	}
	
	@Override
	public String toString() {
		return _memory + "[" + _address + "]";
	}
	
}
