package com.ashwin.evolve.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents an interval notation. It is used to specify the domain
 * and ranges of Evaluables. Intervals are composed of a series of Ranges and
 * can be combined through intersection and union.
 * 
 * @author ashwin
 * @see Evaluable
 * @see Range
 */
public class Interval {
	
	public static final Interval ALL = new Interval(new Range(
			new Range.Endpoint(Double.NEGATIVE_INFINITY, false),
			new Range.Endpoint(Double.POSITIVE_INFINITY, false)));
	
	private List<Range> _ranges;
	
	public Interval(Range... ranges) {
		_ranges = new ArrayList<Range>(Arrays.asList(ranges));
	}
	
	public Interval(List<Range> ranges) {
		_ranges = ranges;
	}
	
	public List<Range> getRanges() {
		return _ranges;
	}
	
	/**
	 * Returns whether or not this interval completely contains the specified
	 * interval.
	 * 
	 * @param oth
	 * @return
	 */
	public boolean contains(Interval oth) {
		return oth.equals(intersection(oth));
	}
	
	/**
	 * Returns the union of this interval and the specified interval. The union
	 * of two intervals is the the largest interval contained in either this
	 * interval or the specified interval.
	 * 
	 * @param othr
	 * @return
	 */
	public Interval union(Interval oth) {
		List<Range> unions = new ArrayList<Range>();
		
		int counter = 0, len1 = _ranges.size(), len2 = oth.getRanges().size();
		while(counter < 2 * Math.min(len1, len2)) {
			int index = (int) (counter / 2.0);
			Range r1  = (counter % 2 == 0) ? _ranges.get(index) : oth.getRanges().get(index);
			Range r2  = (unions.isEmpty()) ? r1 : unions.get(unions.size() - 1);
			
			if(unions.isEmpty() || r1.union(r2) == null)
				unions.add(r1);
			else
				unions.set(unions.size() - 1, r1.union(r2));
			
			counter++;
		}
		
		// Add the remaining ranges to the list of unions
		unions.addAll(_ranges.subList(Math.min(counter, len1), len1));
		unions.addAll(oth.getRanges().subList(Math.min(counter, len2), len2));
		return new Interval(unions);
	}
	
	/**
	 * Returns the intersection of this interval and the specified interval. The
	 * intersection of two intervals is the largest interval contained within
	 * both this interval and the specified interval.
	 * 
	 * @param oth
	 * @return
	 */
	public Interval intersection(Interval oth) {
		List<Range> intersections = new ArrayList<Range>();
		
		for(int c1 = 0, c2 = 0; c1 < _ranges.size() && c2 < oth.getRanges().size();) {
			Range r1 = _ranges.get(c1);
			Range r2 = oth.getRanges().get(c2);
			
			// Add the range and increment the index with the smallest upper bound.
			Range range = r1.intersection(r2);
			if(range != null)
				intersections.add(range);
			
			if(r1.getUpper().compareTo(r2.getUpper()) <= 0)
				c1++;
			else
				c2++;
		}
		
		return new Interval(intersections);
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_ranges == null) ? 0 : _ranges.hashCode());
		return result;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Interval other = (Interval) obj;
		if (_ranges == null) {
			if (other._ranges != null)
				return false;
		} else if (!_ranges.equals(other._ranges))
			return false;
		return true;
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Range range : _ranges)
			sb.append(range.toString() + " ");
		return sb.toString().trim();
	}
}
