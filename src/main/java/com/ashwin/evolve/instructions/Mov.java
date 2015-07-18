package com.ashwin.evolve.instructions;

import com.ashwin.evolve.Instruction;
import com.ashwin.evolve.Operand;
import com.ashwin.evolve.exceptions.ExecutionException;
import com.ashwin.evolve.operands.DirectOperand;

public class Mov implements Instruction {

	private Operand _src, _dest;
	
	public Mov(Operand src, DirectOperand dest) {
		_src = src;
		_dest = dest;
	}

	@Override
	public int execute() throws ExecutionException {
		_dest.set(_src.get());
		return 1;
	}
	
	@Override
	public String toString() {
		return "mov " + _src + ", " + _dest;
	}
}
