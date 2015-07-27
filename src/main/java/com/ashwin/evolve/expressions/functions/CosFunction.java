package com.ashwin.evolve.expressions.functions;

import java.math.BigDecimal;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Interval;
import com.ashwin.evolve.expressions.Range;

public class CosFunction implements Evaluable {

	@Override
	public Interval getDomain() {
		return Interval.ALL;
	}

	@Override
	public Interval getCodomain() {
		return new Interval(new Range(
				new Range.Endpoint(-1, true),
				new Range.Endpoint(1, true)));
	}

	@Override
	public BigDecimal eval(BigDecimal x) {
		BigDecimal mod = x.remainder(BigDecimal.valueOf(2 * Math.PI));
		return BigDecimal.valueOf(Math.cos(mod.doubleValue()));
	}
	
	@Override
	public String toString() {
		return "cos(x)";
	}
}
