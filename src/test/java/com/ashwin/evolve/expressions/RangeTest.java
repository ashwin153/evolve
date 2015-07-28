package com.ashwin.evolve.expressions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ashwin.evolve.expressions.intervals.Range;

public class RangeTest {

	@Test
	public void testContains() {
		Range r1 = new Range(
				new Range.Endpoint(0, true),  
				new Range.Endpoint(Double.POSITIVE_INFINITY, false));
		
		Range r2 = new Range(
				new Range.Endpoint(0, false), 
				new Range.Endpoint(Double.POSITIVE_INFINITY, false));
		
		assertTrue(r1.contains(r2));
		assertFalse(r2.contains(r1));
	}
	
	@Test
	public void testUnion() {
		Range r1 = new Range(
				new Range.Endpoint(0, true),  
				new Range.Endpoint(2, false));
		
		Range r2 = new Range(
				new Range.Endpoint(-1, true),  
				new Range.Endpoint(1, false));
		
		Range r3 = new Range(
				new Range.Endpoint(-1, true),
				new Range.Endpoint(2, false));
		
		assertEquals(r3, r1.union(r2));
	}
	
	@Test
	public void testIntersection() {
		Range r1 = new Range(
				new Range.Endpoint(0, true),  
				new Range.Endpoint(2, false));
		
		Range r2 = new Range(
				new Range.Endpoint(-1, true),  
				new Range.Endpoint(1, false));
		
		Range r3 = new Range(
				new Range.Endpoint(0, true),
				new Range.Endpoint(1, false));
		
		assertEquals(r3, r1.intersection(r2));
	}
}
