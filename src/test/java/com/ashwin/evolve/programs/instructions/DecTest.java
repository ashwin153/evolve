package com.ashwin.evolve.programs.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.programs.exceptions.ExecutionException;
import com.ashwin.evolve.programs.instructions.Dec;
import com.ashwin.evolve.programs.operands.DirectOperand;

@RunWith(MockitoJUnitRunner.class)
public class DecTest {

	@Mock
	private DirectOperand _oper;
	
	@Test
	public void testExecute() throws ExecutionException {
		when(_oper.read()).thenReturn(1);
		
		Dec instr = new Dec(_oper);
		assertEquals(1, instr.execute());
		verify(_oper).write(0);
	}
}
