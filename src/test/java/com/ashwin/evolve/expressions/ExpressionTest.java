package com.ashwin.evolve.expressions;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;

import com.ashwin.evolve.expressions.operations.binary.Addition;
import com.ashwin.evolve.expressions.operations.binary.Multiplication;
import com.ashwin.evolve.expressions.operations.unary.Negation;

public class ExpressionTest {
	
	/**
	 * Verify that operations chained together are implicitly performing
	 * composition; this is a really important aspect of the architecture so it
	 * is imperative that the program does this properly.
	 * 
	 * Verify that the evaluation of an expression changes when the values of
	 * its variables change.
	 */
	@Test
	public void testEvalSampleExpression() {
		Variable x = new Variable("x");
		Variable y = new Variable("y");
		
		Evaluable root = new Addition(
				new Multiplication(
						new Negation(new Constant(3)),
						x),
				y);
		
		Expression expr = new Expression(root, Arrays.asList(x, y));
		assertEquals(BigDecimal.valueOf(-2), expr.eval(BigDecimal.ONE, BigDecimal.ONE));
		assertEquals(BigDecimal.valueOf(-3), expr.eval(BigDecimal.ONE, BigDecimal.ZERO));
		assertEquals(BigDecimal.valueOf(+1), expr.eval(BigDecimal.ZERO, BigDecimal.ONE));
	}
	
}
