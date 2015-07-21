package com.ashwin.evolve.programs.instructions;

import com.ashwin.evolve.programs.Instruction;
import com.ashwin.evolve.programs.Operand;
import com.ashwin.evolve.programs.exceptions.ExecutionException;
import com.ashwin.evolve.programs.operands.MutableOperand;

public class Mov implements Instruction {

	private Operand _src;
	private MutableOperand _dest;
	
	public Mov(Operand src, MutableOperand dest) {
		_src = src;
		_dest = dest;
	}

	@Override
	public int execute() throws ExecutionException {
		_dest.write(_src.read());
		return 1;
	}
	
	@Override
	public String toString() {
		return "mov " + _src + ", " + _dest;
	}
}
