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
import com.ashwin.evolve.programs.instructions.Jl;
import com.ashwin.evolve.programs.operands.ImmediateOperand;

@RunWith(MockitoJUnitRunner.class)
public class JlTest {

	@Mock
	private ImmediateOperand _lines;
	
	@Mock
	private Operand _src, _dest;
	
	@Test
	public void testExecuteLess() throws ExecutionException {
		when(_lines.read()).thenReturn(0);
		when(_src.read()).thenReturn(0);
		when(_dest.read()).thenReturn(1);
		
		Jl instr = new Jl(_lines, _src, _dest);
		assertEquals(0, instr.execute());
		
		verify(_lines).read();
		verify(_src).read();
		verify(_dest).read();
	}
	
	@Test
	public void testExecuteGreaterThanEqualTo() throws ExecutionException {
		when(_lines.read()).thenReturn(0);
		when(_src.read()).thenReturn(0);
		when(_dest.read()).thenReturn(0);
		
		Jl instr = new Jl(_lines, _src, _dest);
		assertEquals(1, instr.execute());
		
		verify(_lines, never()).read();
		verify(_src).read();
		verify(_dest).read();
	}
}
