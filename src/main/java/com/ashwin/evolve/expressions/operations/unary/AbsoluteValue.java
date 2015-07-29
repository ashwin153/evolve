package com.ashwin.evolve.expressions.operations.unary;

import java.math.BigDecimal;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Operation;

public class AbsoluteValue extends Operation {
		
	public AbsoluteValue(Evaluable arg) {
		super(arg);
	}

	@Override
	public BigDecimal eval() {
		return get(0).eval().abs(CONTEXT);
	}
	
	@Override
	public AbsoluteValue copy() {
		return new AbsoluteValue(get(0).copy());
	}
	
	@Override
	public String toString() {
		return "abs(" + get(0) + ")";
	}
	
}
