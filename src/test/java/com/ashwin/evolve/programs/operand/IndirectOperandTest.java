package com.ashwin.evolve.programs.operand;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.programs.Memory;
import com.ashwin.evolve.programs.exceptions.ExecutionException;
import com.ashwin.evolve.programs.operands.IndirectOperand;

@RunWith(MockitoJUnitRunner.class)
public class IndirectOperandTest {

	@Mock
	private Memory _first, _second;
	
	@Test
	public void testRead() throws ExecutionException {
		when(_first.read(0)).thenReturn(1);
		when(_second.read(1)).thenReturn(2);
		
		IndirectOperand o1 = new IndirectOperand(0, _first, _second);
		assertEquals(2, o1.read());
		verify(_first).read(0);
		verify(_second).read(1);
	}
	
	@Test
	public void testWrite() throws ExecutionException {
		when(_first.read(1)).thenReturn(2);
		IndirectOperand o1 = new IndirectOperand(1, _first, _second);
		o1.write(0);
		
		verify(_second).write(2, 0);
	}
}
