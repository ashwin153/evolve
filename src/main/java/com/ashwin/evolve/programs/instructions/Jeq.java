package com.ashwin.evolve.programs.instructions;

import com.ashwin.evolve.programs.Operand;
import com.ashwin.evolve.programs.exceptions.ExecutionException;
import com.ashwin.evolve.programs.operands.ImmediateOperand;

public class Jeq extends Jmp {
		
	private Operand _src, _dest;
	
	public Jeq(ImmediateOperand lines, Operand src, Operand dest) {
		super(lines);
		_src = src;
		_dest = dest;
	}

	@Override
	public int execute() throws ExecutionException {
		if(_src.read() == _dest.read())
			return super.execute();
		return 1;
	}
	
	@Override
	public String toString() {
		return "jeq " + getLines() + ", " + _src + ", " + _dest;
	}
}
