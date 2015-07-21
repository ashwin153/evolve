package com.ashwin.evolve.expressions.operations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.expressions.Evaluable;

@RunWith(MockitoJUnitRunner.class)
public class AdditionOperationTest {

	@Mock
	private Evaluable _e1, _e2;
	
	@Test
	public void testEval() {
		when(_e1.eval(0)).thenReturn(1.0);
		when(_e2.eval(0)).thenReturn(2.0);
		
		AdditionOperation oper = new AdditionOperation(_e1, _e2);
		assertEquals(3, oper.eval(0), 0.0);
		verify(_e1).eval(0);
		verify(_e2).eval(0);
	}
	
}
