package com.ashwin.evolve.expressions;

public class Expression implements Evaluable {

	private Evaluable _root;
	
	public Expression(Evaluable root) {
		_root = root;
	}
	
	@Override
	public Interval getDomain() {
		return _root.getDomain();
	}

	@Override
	public Interval getCodomain() {
		return _root.getCodomain();
	}

	@Override
	public double eval(double x) {
		return _root.eval(x);
	}
}
