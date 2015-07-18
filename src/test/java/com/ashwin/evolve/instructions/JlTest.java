package com.ashwin.evolve.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.Operand;
import com.ashwin.evolve.exceptions.ExecutionException;
import com.ashwin.evolve.operands.ImmediateOperand;

@RunWith(MockitoJUnitRunner.class)
public class JlTest {

	@Mock
	private ImmediateOperand _lines;
	
	@Mock
	private Operand _src, _dest;
	
	@Test
	public void testExecuteLess() throws ExecutionException {
		when(_lines.get()).thenReturn(0);
		when(_src.get()).thenReturn(0);
		when(_dest.get()).thenReturn(1);
		
		Jl instr = new Jl(_lines, _src, _dest);
		assertEquals(0, instr.execute());
		
		verify(_lines).get();
		verify(_src).get();
		verify(_dest).get();
	}
	
	@Test
	public void testExecuteGreaterThanEqualTo() throws ExecutionException {
		when(_lines.get()).thenReturn(0);
		when(_src.get()).thenReturn(0);
		when(_dest.get()).thenReturn(0);
		
		Jl instr = new Jl(_lines, _src, _dest);
		assertEquals(1, instr.execute());
		
		verify(_lines, never()).get();
		verify(_src).get();
		verify(_dest).get();
	}
}
