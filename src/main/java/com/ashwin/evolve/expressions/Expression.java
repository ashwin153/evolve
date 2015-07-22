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
	public Interval getImage() {
		return _root.getImage();
	}

	@Override
	public double eval(double x) {
		return _root.eval(x);
	}
	
	@Override
	public String toString() {
		return _root.toString();
	}
}
