package com.ashwin.evolve.expressions.operations.unary;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.expressions.Evaluable;

@RunWith(MockitoJUnitRunner.class)
public class NegationTest {

	@Mock
	private Evaluable _e1;
	
	@Test
	public void testEval() {
		when(_e1.eval()).thenReturn(BigDecimal.ONE);
		
		Negation oper = new Negation(_e1);
		assertEquals(BigDecimal.valueOf(-1), oper.eval());
		verify(_e1).eval();
	}
	
}
