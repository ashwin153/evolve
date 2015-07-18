package com.ashwin.evolve.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.Operand;
import com.ashwin.evolve.exceptions.ExecutionException;
import com.ashwin.evolve.operands.DirectOperand;

@RunWith(MockitoJUnitRunner.class)
public class MovTest {

	@Mock
	private Operand _src;
	
	@Mock
	private DirectOperand _dest;
	
	@Test
	public void testExecute() throws ExecutionException {
		when(_src.get()).thenReturn(1);
		Mov instr = new Mov(_src, _dest);
		
		assertEquals(1, instr.execute());
		verify(_src, never()).set(anyInt());
		verify(_dest, never()).get();
		verify(_dest).set(1);
	}
	
}
