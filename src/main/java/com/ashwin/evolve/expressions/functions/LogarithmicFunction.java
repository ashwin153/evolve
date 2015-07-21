package com.ashwin.evolve.expressions.functions;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Interval;
import com.ashwin.evolve.expressions.Range;

public class LogarithmicFunction implements Evaluable {

	@Override
	public Interval getDomain() {
		return new Interval(new Range(
				new Range.Endpoint(0, false),
				new Range.Endpoint(Double.POSITIVE_INFINITY, false)));
	}

	@Override
	public Interval getCodomain() {
		return Interval.ALL;
	}

	@Override
	public double eval(double x) {
		return Math.log(x);
	}
}