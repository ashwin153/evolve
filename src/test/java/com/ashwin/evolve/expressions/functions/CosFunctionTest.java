package com.ashwin.evolve.expressions.functions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CosFunctionTest {

	@Test
	public void testEval() {
		CosFunction func = new CosFunction();
		assertEquals(1, func.eval(0), 0.00001);
		assertEquals(0, func.eval(Math.PI / 2), 0.00001);
		assertEquals(-1, func.eval(Math.PI), 0.00001);
		assertEquals(0, func.eval(3 * Math.PI / 2), 0.00001);
	}
	
}
