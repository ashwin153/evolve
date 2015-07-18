package com.ashwin.evolve;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.exceptions.ExecutionException;

@RunWith(MockitoJUnitRunner.class)
public class ProgramTest {
	
	@Mock
	private Instruction _i1, _i2, _i3;
	
	@Test
	public void testExecute() throws ExecutionException {
		when(_i1.execute()).thenReturn(2);
		when(_i2.execute()).thenReturn(1);
		when(_i3.execute()).thenReturn(1);
		
		Program program = new Program(Arrays.asList(_i1, _i2, _i3));
		assertEquals(1, program.execute());
		verify(_i1).execute();
		verify(_i2, never()).execute();
		verify(_i3).execute();
	}

	@Test(expected = ExecutionException.class, timeout=10000)
	public void testExecuteTerminateOnInfiniteLoop() throws ExecutionException {
		when(_i1.execute()).thenReturn(0);
		
		Program program = new Program(Arrays.asList(_i1));
		program.execute();
	}
	
}
