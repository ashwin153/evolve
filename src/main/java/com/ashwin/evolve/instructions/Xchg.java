package com.ashwin.evolve.instructions;

import com.ashwin.evolve.Instruction;
import com.ashwin.evolve.Operand;
import com.ashwin.evolve.exceptions.ExecutionException;
import com.ashwin.evolve.operands.DirectOperand;

public class Xchg implements Instruction {
	
	private Operand _src, _dest;
	
	public Xchg(DirectOperand src, DirectOperand dest) {
		_src = src;
		_dest = dest;
	}
	
	@Override
	public int execute() throws ExecutionException {
		int tmp = _src.get();
		_src.set(_dest.get());
		_dest.set(tmp);
		return 1;
	}

	@Override
	public String toString() {
		return "xchg " + _src + ", " + _dest;
	}
}
