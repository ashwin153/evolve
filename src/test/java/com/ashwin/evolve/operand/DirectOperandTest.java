package com.ashwin.evolve.operand;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.Memory;
import com.ashwin.evolve.exceptions.ExecutionException;
import com.ashwin.evolve.operands.DirectOperand;

@RunWith(MockitoJUnitRunner.class)
public class DirectOperandTest {

	@Mock
	private Memory _memory;
	
	@Test
	public void testGet() throws ExecutionException {
		when(_memory.read(0)).thenReturn(1);
		
		DirectOperand o1 = new DirectOperand(0, _memory);
		assertEquals(1, o1.get());
		verify(_memory).read(0);
		verify(_memory, never()).write(anyInt(), anyInt());
	}
	
	@Test
	public void testSet() throws ExecutionException {
		DirectOperand o1 = new DirectOperand(0, _memory);
		o1.set(0);
		
		verify(_memory).write(0, 0);
		verify(_memory, never()).read(0);
	}
}
