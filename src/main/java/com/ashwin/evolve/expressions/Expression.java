package com.ashwin.evolve.expressions;

public class Expression implements Evaluable {

	private Evaluable _root;

	public Expression(Evaluable root) {
		_root = root;
	}
	
	public Evaluable getRoot() {
		return _root;
	}
	
	@Override
	public Interval getDomain() {
		return _root.getDomain();
	}

	@Override
	public Interval getCodomain() {
		return null;
	}
	
	@Override
	public double eval(double x) {
		return _root.eval(x);
	}
	
}
