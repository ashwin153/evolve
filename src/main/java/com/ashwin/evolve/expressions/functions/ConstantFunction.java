package com.ashwin.evolve.expressions.functions;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Interval;
import com.ashwin.evolve.expressions.Range;

public class ConstantFunction implements Evaluable {

	private double _value;
	
//	/**
//	 * Creates a new constant function using a power function with the specified
//	 * bias. Negative bias will result in higher values and positive bias will
//	 * result in lower values.
//	 * 
//	 * @param bias
//	 */
//	public ConstantFunction(int bias) {
//		// Uses a power function to bias the random number toward lower
//		// values. This allows constants to range over the entire range
//		// of values, while still ranging over all the integers.
//		this((((Math.random() < 0.50) ? 1 : -1) * 
//				(Math.pow(Math.random(), bias) * Double.MAX_VALUE)));
//	}
	
	/**
	 * Creates a new constant function with the specified value.
	 * 
	 * @param value
	 */
	public ConstantFunction(double value) {
		_value = value;
	}
	
	@Override
	public Interval getDomain() {
		return Interval.ALL;
	}

	@Override
	public Interval getImage() {
		return new Interval(new Range(
				new Range.Endpoint(_value, true),
				new Range.Endpoint(_value, true)));
	}

	@Override
	public double eval(double x) {
		return _value;
	}

	@Override
	public String toString() {
		return String.format("%.4f", _value);
	}
}
