package com.ashwin.evolve.expressions.operations.unary;

import java.math.BigDecimal;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Operation;

public class Cos extends Operation {

	public Cos(Evaluable arg) {
		super(arg);
	}
	
	@Override
	public BigDecimal eval() {
		BigDecimal mod = get(0).eval().remainder(BigDecimal.valueOf(2 * Math.PI));
		return BigDecimal.valueOf(Math.cos(mod.doubleValue()));
	}
	
	@Override
	public Cos copy() {
		return new Cos(get(0).copy());
	}
	
	@Override
	public String toString() {
		return "cos(" + get(0) + ")";
	}
	
}
