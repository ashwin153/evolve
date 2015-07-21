package com.ashwin.evolve.expressions.functions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LogarithmicFunctionTest {

	@Test
	public void testEval() {
		LogarithmicFunction func = new LogarithmicFunction();
		assertEquals(0, func.eval(1), 0.00001);
		assertEquals(1, func.eval(Math.E), 0.00001);
		assertEquals(2, func.eval(Math.E * Math.E), 0.00001);
	}
	
}
