package com.ashwin.evolve.programs;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ashwin.evolve.programs.Memory;
import com.ashwin.evolve.programs.exceptions.ExecutionException;

public class MemoryTest {
	
	@Test
	public void testSize() throws ExecutionException {
		Memory m1 = new Memory("test", 1, 0);
		assertEquals(1, m1.size());
		
		// Verify that all addresses less than its size are accessible
		for(int i = 0; i < m1.size(); i++)
			m1.read(i);
	}

	@Test(expected = ExecutionException.class)
	public void testReadAddressOutOfBounds() throws ExecutionException {
		Memory m1 = new Memory("test", 1, 0);
		m1.read(1);
	}
	
	@Test(expected = ExecutionException.class)
	public void testReadAddressNegative() throws ExecutionException {
		Memory m1 = new Memory("test", 1, 0);
		m1.read(-1);
	}
	
	@Test
	public void testReadAddress() throws ExecutionException {
		Memory m1 = new Memory("test", 1, 1);
		assertEquals(1, m1.read(0));
	}
	
	@Test(expected = ExecutionException.class)
	public void testWriteAddressOutOfBounds() throws ExecutionException {
		Memory m1 = new Memory("test", 1, 0);
		m1.write(1, 1);
	}
	
	@Test(expected = ExecutionException.class)
	public void testWriteAddressNegative() throws ExecutionException {
		Memory m1 = new Memory("test", 1, 0);
		m1.write(-1, 1);
	}
	
	@Test
	public void testWriteAddress() throws ExecutionException {
		Memory m1 = new Memory("test", 1, 0);
		assertEquals(0, m1.read(0));
		m1.write(0, 1);
		assertEquals(1, m1.read(0));
	}
}
