package com.ashwin.evolve.expressions.functions;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Interval;
import com.ashwin.evolve.expressions.Range;

public class AbsoluteValueFunction implements Evaluable {

	@Override
	public Interval getDomain() {
		return Interval.ALL;
	}

	@Override
	public Interval getImage() {
		return new Interval(new Range(
				new Range.Endpoint(0, true),
				new Range.Endpoint(Double.POSITIVE_INFINITY, false)));
	}

	@Override
	public double eval(double x) {
		return Math.abs(x);
	}

	@Override
	public String toString() {
		return "abs(x)";
	}
	
}
