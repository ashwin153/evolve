package com.ashwin.evolve.expressions.operations.unary;

import java.math.BigDecimal;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Operation;

public class Cos extends Operation {

	private static final long serialVersionUID = -3203547965092806989L;

	public Cos(Evaluable arg) {
		super(arg);
	}
	
	@Override
	public BigDecimal eval() {
		BigDecimal mod = get(0).eval().remainder(BigDecimal.valueOf(2 * Math.PI), CONTEXT);
		return BigDecimal.valueOf(Math.cos(mod.doubleValue()));
	}
	
	@Override
	public String toString() {
		return "cos(" + get(0) + ")";
	}
	
}
