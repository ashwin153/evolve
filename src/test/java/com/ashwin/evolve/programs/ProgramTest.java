package com.ashwin.evolve.programs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ashwin.evolve.programs.exceptions.ExecutionException;
import com.ashwin.evolve.programs.instructions.Dec;
import com.ashwin.evolve.programs.instructions.Inc;
import com.ashwin.evolve.programs.instructions.Jg;
import com.ashwin.evolve.programs.instructions.Jl;
import com.ashwin.evolve.programs.instructions.Jmp;
import com.ashwin.evolve.programs.instructions.Mov;
import com.ashwin.evolve.programs.instructions.Xchg;
import com.ashwin.evolve.programs.operands.DirectOperand;
import com.ashwin.evolve.programs.operands.ImmediateOperand;
import com.ashwin.evolve.programs.operands.IndirectOperand;

@RunWith(MockitoJUnitRunner.class)
public class ProgramTest {
	
	@Mock
	private Instruction _i1, _i2, _i3;
	
	@Test
	public void testSortingProgram() throws Exception {
		Memory in = new Memory("in", 10, 0);
		Memory wk = new Memory("wk", 5, 0);
		wk.write(0, in.size());
		
		List<Instruction> instrs = new ArrayList<Instruction>(Arrays.asList(new Instruction[] {
				// wk[0] = length; wk[1] = outer loop counter, wk[2] = inner loop counter, wk[3] = tmp variable
				// Decrement length; we don't have greater than or equal to instruction (jge)
				new Dec (new DirectOperand(0, wk)),	
				// Increment the first element, because a 1 length list is trivially guarenteed to be sorted
				new Mov (new ImmediateOperand(1), new DirectOperand(1, wk)),
				// If outer loop counter has reached the end of the list, then exit END_OUTER
				new Jg  (new ImmediateOperand(11), new DirectOperand(1, wk), new DirectOperand(0, wk)),	
				// Reset the inner loop counter
				new Mov (new DirectOperand(1, wk), new DirectOperand(2, wk)),
				// If the inner loop counter has reached the end of the list, then exit END_INNER
				new Jl  (new ImmediateOperand(7), new DirectOperand(2, wk), new ImmediateOperand(1)),
				// Move the inner loop counter into a temporary variable
				new Mov (new DirectOperand(2, wk), new DirectOperand(3, wk)),
				// Decrement the temporary variable to get the next element
				new Dec (new DirectOperand(3, wk)),
				// If the current element is smaller than the next element, then stop END_INNER
				new Jg  (new ImmediateOperand(4), new IndirectOperand(2, wk, in), new IndirectOperand(3, wk, in)),
				// Otherwise, swap the current and next elements
				new Xchg (new IndirectOperand(2, wk, in), new IndirectOperand(3, wk, in)),
				// Decrement the inner loop counter
				new Dec  (new DirectOperand(2, wk)),
				// Jump back to the beginning of the inner loop BEG INNER
				new Jmp  (new ImmediateOperand(-6)),
				// Increment the outer loop counter
				new Inc  (new DirectOperand(1, wk)),
				// Jump back to the comparison
				new Jmp  (new ImmediateOperand(-10))
		}));
		
		Program sort = new Program(instrs);
		int[] values = new int[] {4, 6, 3, 1, 7, 8, 9, 0, 2, 5};
		in.write(0, values);
		sort.execute();
		
		for(int i = 0; i < in.size(); i++)
			assertEquals(i, in.read(i));
	}
	
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
