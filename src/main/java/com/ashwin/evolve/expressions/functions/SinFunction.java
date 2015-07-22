package com.ashwin.evolve.expressions.functions;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Interval;
import com.ashwin.evolve.expressions.Range;

public class SinFunction implements Evaluable {

	@Override
	public Interval getDomain() {
		return Interval.ALL;
	}

	@Override
	public Interval getImage() {
		return new Interval(new Range(
				new Range.Endpoint(-1, true),
				new Range.Endpoint(1, true)));
	}

	@Override
	public double eval(double x) {
		return Math.sin(x);
	}

	@Override
	public String toString() {
		return "sin(x)";
	}
	
}
