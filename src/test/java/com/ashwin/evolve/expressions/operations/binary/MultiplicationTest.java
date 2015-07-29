package com.ashwin.evolve.expressions.operations.binary;

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
public class MultiplicationTest {

	@Mock
	private Evaluable _e1, _e2;
	
	@Test
	public void testEval() {
		when(_e1.eval()).thenReturn(BigDecimal.valueOf(3));
		when(_e2.eval()).thenReturn(BigDecimal.valueOf(2));
		
		Multiplication oper = new Multiplication(_e1, _e2);
		assertEquals(BigDecimal.valueOf(6), oper.eval());
		verify(_e1).eval();
		verify(_e2).eval();
	}
}