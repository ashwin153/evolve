package com.ashwin.evolve.expressions.operations.quaternary;

import java.math.BigDecimal;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Operation;

public class Less extends Operation {
	
	public Less(Evaluable a, Evaluable b, Evaluable t, Evaluable f) {
		super(a, b, t, f);
	}
	
	@Override
	public BigDecimal eval() {
		return (get(0).eval().compareTo(get(1).eval()) < 0) ? get(2).eval() : get(3).eval();
	}
	
	@Override
	public Less copy() {
		return new Less(get(0).copy(), get(1).copy(), get(2).copy(), get(3).copy());
	}
	
	@Override
	public String toString() {
		return get(0) + " < " + get(1) + " ? " + get(2) + " : " + get(3);
	}
	
}
