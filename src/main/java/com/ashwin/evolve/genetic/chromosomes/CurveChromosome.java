package com.ashwin.evolve.genetic.chromosomes;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Expression;
import com.ashwin.evolve.expressions.Interval;
import com.ashwin.evolve.expressions.Range;
import com.ashwin.evolve.expressions.functions.AbsoluteValueFunction;
import com.ashwin.evolve.expressions.functions.ConstantFunction;
import com.ashwin.evolve.expressions.functions.CosFunction;
import com.ashwin.evolve.expressions.functions.ExponentialFunction;
import com.ashwin.evolve.expressions.functions.LogarithmicFunction;
import com.ashwin.evolve.expressions.functions.LogisticFunction;
import com.ashwin.evolve.expressions.functions.PowerFunction;
import com.ashwin.evolve.expressions.functions.SinFunction;
import com.ashwin.evolve.expressions.operations.AdditionOperation;
import com.ashwin.evolve.expressions.operations.CompositionOperation;
import com.ashwin.evolve.expressions.operations.MultiplicationOperation;
import com.ashwin.evolve.genetic.GeneticChromosome;
import com.ashwin.evolve.genetic.GeneticPopulation;

public class CurveChromosome extends Expression implements GeneticChromosome<CurveChromosome> {
	
	private static final List<Evaluable> FUNCTIONS = Arrays.asList(
			new SinFunction(),
			new CosFunction(),
			new AbsoluteValueFunction(),
			new ExponentialFunction(),
			new LogarithmicFunction(),
			new LogisticFunction(),
			new ConstantFunction(2),
			new PowerFunction(2),
			new PowerFunction(1.5),
			new PowerFunction(0.75),
			new PowerFunction(0.90)
	);
	
	private List<Point2D> _data;
	private double _fitness;
	
	public CurveChromosome(Evaluable root, List<Point2D> data) {
		super(root);
		_data = data;
		
		// The fitness is equal to the total squared differences between actual
		// and observed values; less difference is better.
//		for(Point2D point : _data)
//			_fitness += Math.pow(eval(point.getX()) - point.getY(), 2);
	}
	
	@Override
	public double fitness() {
		return _fitness;
	}
	
	@Override
	public CurveChromosome crossover(CurveChromosome mate, double rate) {
//		GeneticExpression e1 = GeneticExpression.copy(this);
//		GeneticExpression e2 = GeneticExpression.copy(expr);
//		
//		if(Math.random() < rate) {
//			Operator o1 = e1.getRandom(Operator.class);
//			Operator o2 = e2.getRandom(Operator.class);
//			
//			double rand = Math.random();		
//			if(rand < 0.50) {
//				Evaluable tmp = o1.getLeftOperand();
//				o1.setLeftOperand(o2.getLeftOperand());
//				o2.setLeftOperand(tmp);
//			} else {
//				Evaluable tmp = o1.getRightOperand();
//				o1.setRightOperand(o2.getRightOperand());
//				o2.setRightOperand(tmp);
//			}
//		}
//		
//		return new GeneticExpression[] {e1, e2};
		return null;
	}

	@Override
	public CurveChromosome mutate(double rate) {
		return null;
	}
	
	/**
	 * Recursively generates a random evaluable with the specified domain with
	 * no more than target depth levels.
	 * 
	 * @param curDepth
	 * @param targetDepth
	 * @param domain
	 * @return
	 */
	private static Evaluable getRandomEvaluable(int curDepth, int targetDepth, Interval domain) {
		// Decrease the probability of selecting an operand as we get closer to
		// the target depth; the probability of selecting an operand at the
		// target depth should be zero.
		double prob = 1.0 - 1.0 / Math.pow(targetDepth, 2) * Math.pow(curDepth, 2);

		if(Math.random() < prob) {
			switch((int) (Math.random() * 3)) {
				case 0:
					return new AdditionOperation(
							getRandomEvaluable(curDepth + 1, targetDepth, domain), 
							getRandomEvaluable(curDepth + 1, targetDepth, domain));
				case 1:  
					return new MultiplicationOperation(
							getRandomEvaluable(curDepth + 1, targetDepth, domain), 
							getRandomEvaluable(curDepth + 1, targetDepth, domain));
				default: 
					Evaluable first  = getRandomEvaluable(curDepth + 1, targetDepth, domain);
					Evaluable second = getRandomEvaluable(curDepth + 1, targetDepth, first.getImage()); 
					return new CompositionOperation(first, second);
			}
		} else {
			// Returns a randomly chosen function whose domain contains the specified interval
			List<Evaluable> funcs = new ArrayList<Evaluable>();
			for(Evaluable func : CurveChromosome.FUNCTIONS)
				if(func.getDomain().contains(domain))
					funcs.add(func);
			
			return funcs.get((int) (Math.random() * funcs.size()));
		}
	}
	
	/**
	 * Returns a randomly generated curve chromosome with the specified target
	 * depth. This method does not guarantee that the resulting chromosome will
	 * be of the specified depth, but it does guarantee that it will not be
	 * larger than this depth.
	 * 
	 * @param targetDepth
	 * @return
	 */
	public static GeneticPopulation<CurveChromosome> getRandomPopulation(int size, List<Point2D> data) {
		Collections.sort(data, new Comparator<Point2D>() {
			@Override
			public int compare(Point2D o1, Point2D o2) {
				return Double.compare(o1.getX(), o2.getX());
			}
		});
		Interval domain = new Interval(new Range(
				new Range.Endpoint(data.get(0).getX(), true), 
				new Range.Endpoint(data.get(data.size() - 1).getX(), true)));
		
		List<CurveChromosome> chromosomes = new ArrayList<CurveChromosome>();
		for(int i = 0; i < size; i++)
			chromosomes.add(new CurveChromosome(getRandomEvaluable(0, 5, domain), data));
		
		return new GeneticPopulation<CurveChromosome>(chromosomes);
	}
}
