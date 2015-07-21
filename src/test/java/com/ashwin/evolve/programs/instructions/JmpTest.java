package com.ashwin.evolve.programs.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.programs.exceptions.ExecutionException;
import com.ashwin.evolve.programs.instructions.Jmp;
import com.ashwin.evolve.programs.operands.ImmediateOperand;

@RunWith(MockitoJUnitRunner.class)
public class JmpTest {

	@Mock
	private ImmediateOperand _lines;
	
	@Test
	public void testExecute() throws ExecutionException {
		when(_lines.read()).thenReturn(0);
		
		Jmp instr = new Jmp(_lines);
		assertEquals(0, instr.execute());
		verify(_lines).read();
	}
	
}
