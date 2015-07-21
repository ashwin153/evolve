package com.ashwin.evolve.expressions.functions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LogisticFunctionTest {

	@Test
	public void testEval() {
		LogisticFunction func = new LogisticFunction();
		assertEquals(0.5, func.eval(0), 0.00001);
		assertEquals(1, func.eval(+1000), 0.00001);
		assertEquals(0, func.eval(-1000), 0.00001);
	}
	
}
