package com.ashwin.evolve.programs.operand;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.programs.Memory;
import com.ashwin.evolve.programs.exceptions.ExecutionException;
import com.ashwin.evolve.programs.operands.DirectOperand;

@RunWith(MockitoJUnitRunner.class)
public class DirectOperandTest {

	@Mock
	private Memory _memory;
	
	@Test
	public void testRead() throws ExecutionException {
		when(_memory.read(0)).thenReturn(1);
		
		DirectOperand o1 = new DirectOperand(0, _memory);
		assertEquals(1, o1.read());
		verify(_memory).read(0);
		verify(_memory, never()).write(anyInt(), anyInt());
	}
	
	@Test
	public void testWrite() throws ExecutionException {
		DirectOperand o1 = new DirectOperand(0, _memory);
		o1.write(0);
		
		verify(_memory).write(0, 0);
		verify(_memory, never()).read(0);
	}
}
