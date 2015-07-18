package com.ashwin.evolve.operand;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ashwin.evolve.exceptions.ExecutionException;
import com.ashwin.evolve.operands.ImmediateOperand;

public class ImmediateOperandTest {

	@Test
	public void testGet() throws ExecutionException {
		ImmediateOperand o1 = new ImmediateOperand(1);
		assertEquals(1, o1.get());
	}
	
	@Test(expected = ExecutionException.class)
	public void testSet() throws ExecutionException {
		ImmediateOperand o1 = new ImmediateOperand(1);
		o1.set(0);
	}
	
}
