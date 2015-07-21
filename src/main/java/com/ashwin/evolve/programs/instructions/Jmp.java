package com.ashwin.evolve.programs.instructions;

import com.ashwin.evolve.programs.Instruction;
import com.ashwin.evolve.programs.exceptions.ExecutionException;
import com.ashwin.evolve.programs.operands.ImmediateOperand;

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
		return _lines.read();
	}

	@Override
	public String toString() {
		return "jmp " + _lines;
	}
}
