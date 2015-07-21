package com.ashwin.evolve.programs.operands;

import com.ashwin.evolve.programs.Operand;
import com.ashwin.evolve.programs.exceptions.ExecutionException;

public interface MutableOperand extends Operand {

	public void write(int value) throws ExecutionException;

}
