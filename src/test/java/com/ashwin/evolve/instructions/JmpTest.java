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

import com.ashwin.evolve.exceptions.ExecutionException;
import com.ashwin.evolve.operands.ImmediateOperand;

@RunWith(MockitoJUnitRunner.class)
public class JmpTest {

	@Mock
	private ImmediateOperand _lines;
	
	@Test
	public void testExecute() throws ExecutionException {
		when(_lines.get()).thenReturn(0);
		
		Jmp instr = new Jmp(_lines);
		assertEquals(0, instr.execute());
		verify(_lines).get();
		verify(_lines, never()).set(anyInt());
	}
	
}
