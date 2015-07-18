package com.ashwin.evolve.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.exceptions.ExecutionException;
import com.ashwin.evolve.operands.DirectOperand;

@RunWith(MockitoJUnitRunner.class)
public class XchgTest {

	@Mock
	private DirectOperand _src, _dest;
	
	@Test
	public void testExecute() throws ExecutionException {
		when(_src.get()).thenReturn(1);
		when(_dest.get()).thenReturn(0);
		Xchg instr = new Xchg(_src, _dest);
		
		assertEquals(1, instr.execute());
		verify(_src).get();
		verify(_src).set(0);
		verify(_dest).get();
		verify(_dest).set(1);
	}
	
}
