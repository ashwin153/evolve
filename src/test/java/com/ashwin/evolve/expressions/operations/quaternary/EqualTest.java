package com.ashwin.evolve.expressions.operations.quaternary;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.expressions.Evaluable;

@RunWith(MockitoJUnitRunner.class)
public class EqualTest {

	@Mock
	private Evaluable _a, _b, _t, _f;
	
	@Test
	public void testEval() {
		when(_a.eval()).thenReturn(BigDecimal.ONE);
		when(_b.eval()).thenReturn(BigDecimal.ZERO);
		when(_t.eval()).thenReturn(BigDecimal.ONE);
		when(_f.eval()).thenReturn(BigDecimal.ZERO);
		
		Equal o1 = new Equal(_a, _a, _t, _f);
		assertEquals(BigDecimal.ONE, o1.eval());
		
		Equal o2 = new Equal(_a, _b, _t, _f);
		assertEquals(BigDecimal.ZERO, o2.eval());
	}
}
