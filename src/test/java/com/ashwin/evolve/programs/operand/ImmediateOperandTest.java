package com.ashwin.evolve.programs.operand;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ashwin.evolve.programs.exceptions.ExecutionException;
import com.ashwin.evolve.programs.operands.ImmediateOperand;

public class ImmediateOperandTest {

	@Test
	public void testRead() throws ExecutionException {
		ImmediateOperand o1 = new ImmediateOperand(1);
		assertEquals(1, o1.read());
	}
	
}
