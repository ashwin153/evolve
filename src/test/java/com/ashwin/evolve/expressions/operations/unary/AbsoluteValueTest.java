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
public class AbsoluteValueTest {

	@Mock
	private Evaluable _e1;
	
	@Test
	public void testEval() {
		when(_e1.eval()).thenReturn(BigDecimal.valueOf(-1));
		
		AbsoluteValue oper = new AbsoluteValue(_e1);
		assertEquals(BigDecimal.ONE, oper.eval());
		verify(_e1).eval();
	}
	
}
