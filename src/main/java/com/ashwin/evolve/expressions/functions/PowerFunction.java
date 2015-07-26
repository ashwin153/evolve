package com.ashwin.evolve.expressions.functions;

import java.math.BigDecimal;

import com.ashwin.evolve.expressions.Calculator;
import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Interval;
import com.ashwin.evolve.expressions.Range;

public class PowerFunction implements Evaluable {
	
	private double _pow;
	
	public PowerFunction(double pow) {
		_pow = pow;
	}
	
	@Override
	public Interval getDomain() {
		return new Interval(new Range(
				new Range.Endpoint(0, false),
				new Range.Endpoint(Double.POSITIVE_INFINITY, false)));
	}
	
	@Override
	public Interval getImage() {
		return new Interval(new Range(
				new Range.Endpoint(0, false),
				new Range.Endpoint(Double.POSITIVE_INFINITY, false)));
	}

	@Override
	public BigDecimal eval(BigDecimal x) {
		return Calculator.exp(Calculator.ln(x, PRECISION)
				.multiply(BigDecimal.valueOf(_pow)), PRECISION);
	}
	
	@Override
	public String toString() {
		return String.format("pow(x, %.2f)", _pow);
	}
	
}
