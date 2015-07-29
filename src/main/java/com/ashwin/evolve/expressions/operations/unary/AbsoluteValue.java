package com.ashwin.evolve.expressions.operations.unary;

import java.math.BigDecimal;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Operation;

public class AbsoluteValue extends Operation {
		
	private static final long serialVersionUID = 3370867631356541732L;

	public AbsoluteValue(Evaluable arg) {
		super(arg);
	}

	@Override
	public BigDecimal eval() {
		return get(0).eval().abs(CONTEXT);
	}
	
	@Override
	public String toString() {
		return "abs(" + get(0) + ")";
	}
	
}
