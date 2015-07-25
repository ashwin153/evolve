package com.ashwin.evolve.expressions.functions;

import java.math.BigDecimal;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Expression;
import com.ashwin.evolve.expressions.Interval;
import com.ashwin.evolve.expressions.Range;
import com.ashwin.evolve.expressions.calculator.BigFunctions;

public class LogarithmicFunction implements Evaluable {

	@Override
	public Interval getDomain() {
		return new Interval(new Range(
				new Range.Endpoint(0, false),
				new Range.Endpoint(Double.POSITIVE_INFINITY, false)));
	}

	@Override
	public Interval getImage() {
		return new Interval(new Range(
				new Range.Endpoint(Double.NEGATIVE_INFINITY, false),
				new Range.Endpoint(Math.log(Double.POSITIVE_INFINITY), false)));
	}

	@Override
	public BigDecimal eval(BigDecimal x) {
		return BigFunctions.ln(x, Expression.PRECISION);
	}
	
	@Override
	public String toString() {
		return "ln(x)";
	}
	
}
