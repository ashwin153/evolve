package com.ashwin.evolve.programs.instructions;

import com.ashwin.evolve.programs.Instruction;
import com.ashwin.evolve.programs.exceptions.ExecutionException;
import com.ashwin.evolve.programs.operands.MutableOperand;

public class Xchg implements Instruction {
	
	private MutableOperand _src, _dest;
	
	public Xchg(MutableOperand src, MutableOperand dest) {
		_src = src;
		_dest = dest;
	}
	
	@Override
	public int execute() throws ExecutionException {
		int tmp = _src.read();
		_src.write(_dest.read());
		_dest.write(tmp);
		return 1;
	}

	@Override
	public String toString() {
		return "xchg " + _src + ", " + _dest;
	}
}
