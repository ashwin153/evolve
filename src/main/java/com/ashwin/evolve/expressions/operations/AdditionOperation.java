package com.ashwin.evolve.expressions.operations;

import java.util.ArrayList;
import java.util.List;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Interval;
import com.ashwin.evolve.expressions.Range;

public class AdditionOperation implements Evaluable {

	private Evaluable _left, _right;
	
	public AdditionOperation(Evaluable left, Evaluable right) {
		_left = left;
		_right = right;
	}
	
	@Override
	public Interval getDomain() {
		return _left.getDomain().intersection(_right.getDomain());
	}

	@Override
	public Interval getImage() {
		Interval image = new Interval();
		
		// Returns an approximation of the image of the sums of two functions.
		// This method works by adding the lower bounds and upper bounds of each
		// of the ranges in both intervals and taking the union over the
		// results. While this method is far from perfect, it enables us to
		// quickly estimate the image. The returned approximation should ALWAYS
		// be bigger than the actual image.
		for(Range r1 : _left.getImage().getRanges()) {
			List<Range> sum = new ArrayList<Range>();
			Range.Endpoint l1 = r1.getLower();
			Range.Endpoint u1 = r1.getUpper();
			
			for(Range r2 : _right.getImage().getRanges()) {
				Range.Endpoint l2 = r2.getLower();
				Range.Endpoint u2 = r2.getUpper();
				
				sum.add(new Range(
						new Range.Endpoint(l2.getValue() + l1.getValue(), l2.isClosed() && l1.isClosed()),
						new Range.Endpoint(u2.getValue() + u1.getValue(), u2.isClosed() && u2.isClosed())));
			}
			
			image = image.union(new Interval(sum));
		}
				
		return image;
	}

	@Override
	public double eval(double x) {
		return _left.eval(x) + _right.eval(x);
	}

	@Override
	public String toString() {
		return "(" + _left + ") + (" + _right + ")";
	}
}
