package com.ashwin.evolve.expressions.functions;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Interval;
import com.ashwin.evolve.expressions.Range;

public class PowerFunction implements Evaluable {
	
	private int _p, _q;
	
	/**
	 * Constructs a new power function whose degree is equal to p/q. We do it
	 * this way, because it simplifies calculation of the domain and codomain of
	 * the power function. Note: q cannot be negative.
	 * 
	 * @param p non-zero integer
	 * @param q non-negative integer
	 */
	public PowerFunction(int p, int q) {
		if(p == 0 || q <= 0)
			throw new IllegalArgumentException("p must be non-zero and q must be positive");
		
		_p = p;
		_q = q;
	}
	
	@Override
	public Interval getDomain() {
		// http://www.biology.arizona.edu/biomath/tutorials/power/Powerbasics.html
		if(_q % 2 == 0)
			if(_p > 0)
				return new Interval(
						new Range(new Range.Endpoint(0, true),
								  new Range.Endpoint(Double.POSITIVE_INFINITY, false)));
			else
				return new Interval(
						new Range(new Range.Endpoint(0, false),
								  new Range.Endpoint(Double.POSITIVE_INFINITY, false)));
		else
			if(_p > 0)
				return Interval.ALL;
			else
				return new Interval(
						new Range(new Range.Endpoint(Double.NEGATIVE_INFINITY, false),
								  new Range.Endpoint(0, false)),
						new Range(new Range.Endpoint(0, false),
								  new Range.Endpoint(Double.POSITIVE_INFINITY, false)));
			
	}

	@Override
	public Interval getImage() {
		// https://diversity.umn.edu/multicultural/sites/diversity.umn.edu.multicultural/files/M1271-elemfuncprop_000.pdf
		if(_p % 2 != 0 && _q % 2 != 0)
			if(_p > 0)
				return Interval.ALL;
			else
				return new Interval(
						new Range(new Range.Endpoint(Double.NEGATIVE_INFINITY, false),
								  new Range.Endpoint(0, false)),
						new Range(new Range.Endpoint(0, false),
								  new Range.Endpoint(Double.POSITIVE_INFINITY, false)));
		else
			if(_p > 0)
				return new Interval(
						new Range(new Range.Endpoint(0, true),
								  new Range.Endpoint(Double.POSITIVE_INFINITY, false)));
			else
				return new Interval(
						new Range(new Range.Endpoint(0, false),
								  new Range.Endpoint(Double.POSITIVE_INFINITY, false)));
	}

	@Override
	public double eval(double x) {
		return Math.pow(x, (double) _p / _q);
	}
	
	@Override
	public String toString() {
		return String.format("pow(x, %.2f)", (double) _p / _q);
	}
	
}
