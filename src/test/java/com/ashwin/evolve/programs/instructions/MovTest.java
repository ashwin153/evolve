package com.ashwin.evolve.programs.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.programs.Operand;
import com.ashwin.evolve.programs.exceptions.ExecutionException;
import com.ashwin.evolve.programs.instructions.Mov;
import com.ashwin.evolve.programs.operands.DirectOperand;

@RunWith(MockitoJUnitRunner.class)
public class MovTest {

	@Mock
	private Operand _src;
	
	@Mock
	private DirectOperand _dest;
	
	@Test
	public void testExecute() throws ExecutionException {
		when(_src.read()).thenReturn(1);
		Mov instr = new Mov(_src, _dest);
		
		assertEquals(1, instr.execute());
		verify(_dest, never()).read();
		verify(_dest).write(1);
	}
	
}
