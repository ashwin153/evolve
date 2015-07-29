package com.ashwin.evolve.expressions.operations.unary;

import java.math.BigDecimal;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Operation;

public class Negation extends Operation {

	private static final long serialVersionUID = 8897445137067931632L;

	public Negation(Evaluable arg) {
		super(arg);
	}
	
	@Override
	public BigDecimal eval() {
		return get(0).eval().negate(CONTEXT);
	}
	
	@Override
	public String toString() {
		return "neg(" + get(0) + ")";
	}
	
}
