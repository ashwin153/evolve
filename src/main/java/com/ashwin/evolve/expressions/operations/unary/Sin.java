package com.ashwin.evolve.expressions.operations.unary;

import java.math.BigDecimal;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Operation;

public class Sin extends Operation {

	private static final long serialVersionUID = 5712951576216933072L;

	public Sin(Evaluable arg) {
		super(arg);
	}
	
	@Override
	public BigDecimal eval() {
		BigDecimal mod = get(0).eval().remainder(BigDecimal.valueOf(2 * Math.PI), CONTEXT);
		return BigDecimal.valueOf(Math.sin(mod.doubleValue()));
	}
	
	@Override
	public String toString() {
		return "sin(" + get(0) + ")";
	}
	
}
