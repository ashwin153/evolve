package com.ashwin.evolve.expressions.operations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.intervals.CompositionOperation;

@RunWith(MockitoJUnitRunner.class)
public class CompositionOperationTest {

	@Mock
	private Evaluable _e1, _e2;
	
	@Test
	public void testEval() {
		when(_e1.eval(BigDecimal.ZERO)).thenReturn(BigDecimal.ONE);
		when(_e2.eval(BigDecimal.ONE)).thenReturn(BigDecimal.valueOf(2));
		
		CompositionOperation oper = new CompositionOperation(_e1, _e2);
		assertEquals(BigDecimal.valueOf(2), oper.eval(BigDecimal.ZERO));
		verify(_e1).eval(BigDecimal.ZERO);
		verify(_e2).eval(BigDecimal.ONE);
	}
	
}
