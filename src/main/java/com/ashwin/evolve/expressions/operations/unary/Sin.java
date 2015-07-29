package com.ashwin.evolve.expressions.operations.unary;

import java.math.BigDecimal;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Operation;

public class Sin extends Operation {

	public Sin(Evaluable arg) {
		super(arg);
	}
	
	@Override
	public BigDecimal eval() {
		BigDecimal mod = get(0).eval().remainder(BigDecimal.valueOf(2 * Math.PI));
		return BigDecimal.valueOf(Math.sin(mod.doubleValue()));
	}
	
	@Override
	public Sin copy() {
		return new Sin(get(0).copy());
	}
	
	@Override
	public String toString() {
		return "sin(" + get(0) + ")";
	}
	
}
