package com.ashwin.evolve.expressions.operations.unary;

import java.math.BigDecimal;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Operation;

public class Negation extends Operation {

	public Negation(Evaluable arg) {
		super(arg);
	}
	
	@Override
	public BigDecimal eval() {
		return get(0).eval().negate(CONTEXT);
	}
	
	@Override
	public Negation copy() {
		return new Negation(get(0).copy());
	}
	
	@Override
	public String toString() {
		return "neg(" + get(0) + ")";
	}
	
}
