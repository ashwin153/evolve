package com.ashwin.evolve.programs.instructions;

import com.ashwin.evolve.programs.Instruction;
import com.ashwin.evolve.programs.exceptions.ExecutionException;
import com.ashwin.evolve.programs.operands.MutableOperand;

public class Inc implements Instruction {
	
	private MutableOperand _address;
	
	public Inc(MutableOperand address) {
		_address = address;
	}

	@Override
	public int execute() throws ExecutionException {
		_address.write(_address.read() + 1);
		return 1;
	}
	
	@Override
	public String toString() {
		return "inc " + _address;
	}
}
