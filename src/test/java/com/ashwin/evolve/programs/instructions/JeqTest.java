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
import com.ashwin.evolve.programs.instructions.Jeq;
import com.ashwin.evolve.programs.operands.ImmediateOperand;

@RunWith(MockitoJUnitRunner.class)
public class JeqTest {

	@Mock
	private ImmediateOperand _lines;
	
	@Mock
	private Operand _src, _dest;
	
	@Test
	public void testExecuteEqual() throws ExecutionException {
		when(_lines.read()).thenReturn(0);
		when(_src.read()).thenReturn(0);
		when(_dest.read()).thenReturn(0);
		
		Jeq instr = new Jeq(_lines, _src, _dest);
		assertEquals(0, instr.execute());
		
		verify(_lines).read();
		verify(_src).read();
		verify(_dest).read();
	}
	
	@Test
	public void testExecuteNotEqual() throws ExecutionException {
		when(_lines.read()).thenReturn(0);
		when(_src.read()).thenReturn(0);
		when(_dest.read()).thenReturn(1);
		
		Jeq instr = new Jeq(_lines, _src, _dest);
		assertEquals(1, instr.execute());
		
		verify(_lines, never()).read();
		verify(_src).read();
		verify(_dest).read();
	}
}
