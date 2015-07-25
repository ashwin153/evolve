package com.ashwin.evolve.expressions.functions;

import java.math.BigDecimal;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Expression;
import com.ashwin.evolve.expressions.Interval;
import com.ashwin.evolve.expressions.Range;
import com.ashwin.evolve.expressions.calculator.BigFunctions;

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
		return BigFunctions.exp(BigFunctions.ln(x, Expression.PRECISION)
				.multiply(BigDecimal.valueOf(_pow)), Expression.PRECISION);
	}
	
	@Override
	public String toString() {
		return String.format("pow(x, %.2f)", _pow);
	}
	
}
