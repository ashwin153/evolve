package com.ashwin.evolve.expressions.functions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExponentialFunctionTest {

	@Test
	public void testEval() {
		ExponentialFunction func = new ExponentialFunction();
		assertEquals(1, func.eval(0), 0.00001);
		assertEquals(Math.E, func.eval(1), 0.00001);
	}
	
}
