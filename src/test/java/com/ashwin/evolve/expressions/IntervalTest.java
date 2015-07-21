package com.ashwin.evolve.expressions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class IntervalTest {
	
	@Test
	public void testContains() {
		Interval i1 = new Interval(Arrays.asList(
				new Range(new Range.Endpoint(-1, false),
						  new Range.Endpoint(1, true)),
				new Range(new Range.Endpoint(3, true),
					      new Range.Endpoint(8, false))));
		
		Interval i2 = new Interval(Arrays.asList(
				new Range(new Range.Endpoint(-1, false),
						  new Range.Endpoint(0, true)),
				new Range(new Range.Endpoint(4, false),
						  new Range.Endpoint(5, true))));
		
		assertTrue(i1.contains(i2));
		assertFalse(i2.contains(i1));
	}
	
	@Test
	public void testUnion() {
		Interval i1 = new Interval(Arrays.asList(
				new Range(new Range.Endpoint(-1, false),
						  new Range.Endpoint(+1, true)),
				new Range(new Range.Endpoint(2, true),
						  new Range.Endpoint(4, false))));
		
		Interval i2 = new Interval(Arrays.asList(
				new Range(new Range.Endpoint(0, false),
						  new Range.Endpoint(3, true)),
				new Range(new Range.Endpoint(5, true),
						  new Range.Endpoint(6, false))));
		
		Interval i3 = new Interval(Arrays.asList(
				new Range(new Range.Endpoint(-1, false),
						  new Range.Endpoint(4, false)),
				new Range(new Range.Endpoint(5, true),
						  new Range.Endpoint(6, false))));
				
		assertEquals(i3, i1.union(i2));
	}

	@Test
	public void testIntersect() {
		Interval i1 = new Interval(Arrays.asList(
				new Range(new Range.Endpoint(-1, false),
						  new Range.Endpoint(+1, true)),
				new Range(new Range.Endpoint(2, true),
						  new Range.Endpoint(4, false))));
		
		Interval i2 = new Interval(Arrays.asList(
				new Range(new Range.Endpoint(0, false),
						  new Range.Endpoint(3, true)),
				new Range(new Range.Endpoint(5, true),
						  new Range.Endpoint(6, false))));
		
		Interval i3 = new Interval(Arrays.asList(
				new Range(new Range.Endpoint(0, false),
						  new Range.Endpoint(1, true)),
				new Range(new Range.Endpoint(2, true),
						  new Range.Endpoint(3, true))));
		
		assertEquals(i3, i1.intersection(i2));
	}
}
