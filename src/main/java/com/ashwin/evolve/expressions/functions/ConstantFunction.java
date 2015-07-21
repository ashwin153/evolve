package com.ashwin.evolve.expressions.functions;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Interval;
import com.ashwin.evolve.expressions.Range;

public class ConstantFunction implements Evaluable {

	private double _value;
	
	public ConstantFunction(double value) {
		_value = value;
	}
	
	@Override
	public Interval getDomain() {
		return Interval.ALL;
	}

	@Override
	public Interval getCodomain() {
		return new Interval(new Range(
				new Range.Endpoint(_value, true),
				new Range.Endpoint(_value, true)));
	}

	@Override
	public double eval(double x) {
		return _value;
	}

}
