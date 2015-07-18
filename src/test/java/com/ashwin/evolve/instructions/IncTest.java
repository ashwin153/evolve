package com.ashwin.evolve.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.exceptions.ExecutionException;
import com.ashwin.evolve.operands.DirectOperand;

@RunWith(MockitoJUnitRunner.class)
public class IncTest {

	@Mock
	private DirectOperand _oper;
	
	@Test
	public void testExecute() throws ExecutionException {
		when(_oper.get()).thenReturn(0);
		Inc instr = new Inc(_oper);
		
		assertEquals(1, instr.execute());
		verify(_oper).set(1);
	}
	
}
