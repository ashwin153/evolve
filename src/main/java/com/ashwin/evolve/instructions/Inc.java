package com.ashwin.evolve.instructions;

import com.ashwin.evolve.Instruction;
import com.ashwin.evolve.Operand;
import com.ashwin.evolve.exceptions.ExecutionException;
import com.ashwin.evolve.operands.DirectOperand;

public class Inc implements Instruction {
	
	private Operand _address;
	
	public Inc(DirectOperand address) {
		_address = address;
	}

	@Override
	public int execute() throws ExecutionException {
		_address.set(_address.get() + 1);
		return 1;
	}
	
	@Override
	public String toString() {
		return "inc " + _address;
	}
}
