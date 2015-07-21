package com.ashwin.evolve.expressions.operators;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Interval;

public class ExponentiationOperation implements Evaluable {

	private Evaluable _base, _power;
	
	public ExponentiationOperation(Evaluable base, Evaluable power) {
		_base = base;
		_power = power;
	}
	
	@Override
	public Interval getDomain() {
		return null;
	}

	@Override
	public Interval getCodomain() {
		return null;
	}

	@Override
	public double eval(double x) {
		return Math.pow(_base.eval(x), _power.eval(x));
	}

}
