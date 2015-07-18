package com.ashwin.evolve.instructions;

import com.ashwin.evolve.Instruction;
import com.ashwin.evolve.exceptions.ExecutionException;
import com.ashwin.evolve.operands.ImmediateOperand;

public class Jmp implements Instruction {
	
	private ImmediateOperand _lines;
	
	public Jmp(ImmediateOperand lines) {
		_lines = lines;
	}
	
	protected ImmediateOperand getLines() {
		return _lines;
	}
	
	@Override
	public int execute() throws ExecutionException {
		return _lines.get();
	}

	@Override
	public String toString() {
		return "jmp " + _lines;
	}
}
