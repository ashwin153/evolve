package com.ashwin.evolve.expressions.operations.binary;

import java.math.BigDecimal;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Operation;

public class Addition extends Operation {
	
	public Addition(Evaluable left, Evaluable right) {
		super(left, right);
	}
	
	@Override
	public BigDecimal eval() {
		return get(0).eval().add(get(1).eval(), CONTEXT);
	}
	
	@Override
	public Addition copy() {
		return new Addition(get(0).copy(), get(1).copy());
	}
	
	@Override
	public String toString() {
		return "add(" + get(0) + ", " + get(1) + ")";
	}
	
}
