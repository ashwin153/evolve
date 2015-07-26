package com.ashwin.evolve.expressions.functions;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Interval;
import com.ashwin.evolve.expressions.Range;
import com.ashwin.evolve.expressions.calculator.Calculator;

public class LogisticFunction implements Evaluable {

	@Override
	public Interval getDomain() {
		return Interval.ALL;
	}

	@Override
	public Interval getImage() {
		return new Interval(new Range(
				new Range.Endpoint(0, false),
				new Range.Endpoint(1, false)));
	}

	@Override
	public BigDecimal eval(BigDecimal x) {
		return BigDecimal.ONE.divide(BigDecimal.ONE.add(Calculator.exp(x.negate(), 
				Evaluable.PRECISION)), Evaluable.PRECISION, RoundingMode.HALF_UP);
	}

	@Override
	public String toString() {
		return "logistic(x)";
	}
}
