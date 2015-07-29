package com.ashwin.evolve.expressions.operations.binary;

import java.math.BigDecimal;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Operation;

public class Multiplication extends Operation {
	
	public Multiplication(Evaluable left, Evaluable right) {
		super(left, right);
	}
	
	@Override
	public BigDecimal eval() {
		return get(0).eval().multiply(get(1).eval(), CONTEXT);
	}
	
	@Override
	public Multiplication copy() {
		return new Multiplication(get(0).copy(), get(1).copy());
	}
	
	@Override
	public String toString() {
		return "multiply(" + get(0) + ", " + get(1) + ")";
	}
	
}
