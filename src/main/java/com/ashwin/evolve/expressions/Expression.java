package com.ashwin.evolve.expressions;

import java.math.BigDecimal;

public class Expression implements Evaluable {

	private static final long serialVersionUID = -5243010604049058716L;
	
	private Evaluable _root;
	
	public Expression(Evaluable root) {
		_root = root;
	}
	
	@Override
	public BigDecimal eval() {
		return _root.eval();
	}

	@Override
	public String toString() {
		return _root.toString();
	}
	
}
