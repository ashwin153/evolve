package com.ashwin.evolve.programs.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.programs.exceptions.ExecutionException;
import com.ashwin.evolve.programs.instructions.Xchg;
import com.ashwin.evolve.programs.operands.DirectOperand;

@RunWith(MockitoJUnitRunner.class)
public class XchgTest {

	@Mock
	private DirectOperand _src, _dest;
	
	@Test
	public void testExecute() throws ExecutionException {
		when(_src.read()).thenReturn(1);
		when(_dest.read()).thenReturn(0);
		Xchg instr = new Xchg(_src, _dest);
		
		assertEquals(1, instr.execute());
		verify(_src).read();
		verify(_src).write(0);
		verify(_dest).read();
		verify(_dest).write(1);
	}
	
}
