package com.ashwin.evolve.expressions.functions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AbsoluteValueFunctionTest {
	
	@Test
	public void testEval() {
		AbsoluteValueFunction func = new AbsoluteValueFunction();
		assertEquals(1, func.eval(-1), 0.00001);
		assertEquals(0, func.eval(0), 0.00001);
		assertEquals(1, func.eval(1), 0.00001);
	}
	
}
