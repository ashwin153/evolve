package com.ashwin.evolve.expressions.operations.binary;

import java.math.BigDecimal;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Operation;

public class Multiplication extends Operation {
	
	private static final long serialVersionUID = 4059798853631891280L;

	public Multiplication(Evaluable left, Evaluable right) {
		super(left, right);
	}
	
	@Override
	public BigDecimal eval() {
		return get(0).eval().multiply(get(1).eval(), CONTEXT);
	}
	
	@Override
	public String toString() {
		return "multiply(" + get(0) + ", " + get(1) + ")";
	}
	
}
