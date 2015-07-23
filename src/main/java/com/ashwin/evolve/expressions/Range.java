package com.ashwin.evolve.expressions;

/**
 * This class represents a range over the real numbers. Ranges start and end
 * with an Endpoint.
 * 
 * @author ashwin
 * @see Endpoint
 */
public class Range {
	
	private Endpoint _lower, _upper;
	
	public Range(Endpoint lower, Endpoint upper) {
		_lower = lower;
		_upper = upper;
	}
	
	public Endpoint getLower() {
		return _lower;
	}
	
	public Endpoint getUpper() {
		return _upper;
	}
	
	/**
	 * Returns whether or not the specified range is fully contained within this
	 * range.
	 * 
	 * @param oth
	 * @return true if contains; false otherwise
	 */
	public boolean contains(Range oth) {
		return oth.equals(intersection(oth));
	}
	
	/**
	 * Returns whether or not the specified endpoint is contained within this
	 * range. For example, the point 2 is contained in the range [2, 3), but not
	 * in the range (2, 3).
	 * 
	 * @param point
	 * @return true if contains; false otherwise
	 */
	public boolean contains(Endpoint point) {
		if(point.equals(_lower) || point.equals(_upper))
			return true;
		
		// Open endpoints can occur at or outside the range; closed endpoints
		// can occur at any point within the range
		return !(point.compareTo(_lower) > 0 && point.compareTo(_upper) < 0);
	}
	
	/**
	 * Returns the intersection of the specified ranges. The intersection is the
	 * largest lower and the smallest upper. If these two ranges do not overlap,
	 * then their intersection is null.
	 * 
	 * @param oth other range
	 * @return intersection
	 */
	public Range intersection(Range oth) {
		if(_lower.compareTo(oth.getUpper()) <= 0 && _upper.compareTo(oth.getLower()) >= 0)
			return new Range(
					(_lower.compareTo(oth.getLower()) >= 0) ? _lower : oth.getLower(),
					(_upper.compareTo(oth.getUpper()) <= 0) ? _upper : oth.getUpper());
		return null;
	}
	
	/**
	 * Returns the union of the specified ranges. If the ranges do not intersect
	 * then their union is null.
	 * 
	 * @param oth other range
	 * @return union
	 */
	public Range union(Range oth) {
		if(intersection(oth) != null)
			return new Range(
					(_lower.compareTo(oth.getLower()) <= 0) ? _lower : oth.getLower(),
					(_upper.compareTo(oth.getUpper()) >= 0) ? _upper : oth.getUpper());
		return null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_lower == null) ? 0 : _lower.hashCode());
		result = prime * result + ((_upper == null) ? 0 : _upper.hashCode());
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
		
		Range other = (Range) obj;
		if (_lower == null) {
			if (other._lower != null)
				return false;
		} else if (!_lower.equals(other._lower))
			return false;
		if (_upper == null) {
			if (other._upper != null)
				return false;
		} else if (!_upper.equals(other._upper))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ((_lower.isClosed()) ? "[" : "(") + _lower.getValue() + ", "
				+ _upper.getValue() + ((_upper.isClosed()) ? "]" : ")");
	}
	
	/**
	 * This class represents an endpoint of a range. Endpoints can be either
	 * open or closed and contain a value. Endpoints are also comparable; an
	 * open endpoint at 4 is greater than a closed endpoint at 4.
	 * 
	 * @author ashwin
	 * @see Range
	 */
	public static class Endpoint implements Comparable<Endpoint> {
		
		private double _value;
		private boolean _isClosed;
		
		public Endpoint(double value, boolean isClosed) {
			_value = value;
			_isClosed = isClosed;
		}
		
		public double getValue() {
			return _value;
		}
		
		public boolean isClosed() {
			return _isClosed;
		}

		@Override
		public int compareTo(Endpoint oth) {
			if(_value < oth.getValue())
				return -1;
			else if(_value > oth.getValue())
				return 1;
			else if(_isClosed && !oth.isClosed())
				return -1;
			else if(!_isClosed && oth.isClosed())
				return 1;
			else
				return 0;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (_isClosed ? 1231 : 1237);
			long temp;
			temp = Double.doubleToLongBits(_value);
			result = prime * result + (int) (temp ^ (temp >>> 32));
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
			
			Endpoint other = (Endpoint) obj;
			if (_isClosed != other._isClosed)
				return false;
			if (Double.doubleToLongBits(_value) != Double
					.doubleToLongBits(other._value))
				return false;
			return true;
		}
	}
}
