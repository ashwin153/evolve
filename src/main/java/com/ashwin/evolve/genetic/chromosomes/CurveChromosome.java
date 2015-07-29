package com.ashwin.evolve.genetic.chromosomes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ashwin.evolve.expressions.Constant;
import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Expression;
import com.ashwin.evolve.expressions.Operation;
import com.ashwin.evolve.expressions.Variable;
import com.ashwin.evolve.expressions.operations.binary.Addition;
import com.ashwin.evolve.expressions.operations.binary.Multiplication;
import com.ashwin.evolve.expressions.operations.unary.Cos;
import com.ashwin.evolve.expressions.operations.unary.Sin;
import com.ashwin.evolve.genetic.GeneticChromosome;
import com.ashwin.evolve.genetic.GeneticPopulation;

public class CurveChromosome extends Expression implements GeneticChromosome<CurveChromosome> {

	private BigDecimal[][] _points;
	private double _fitness;
	
	/**
	 * Constructs a new curve chromosome. The input matrix contains tuples of
	 * the form (x_0, ... , x_n, f(x_0, ... , x_n)). This input matrix is used
	 * to calculate the fitness of the function (least squares difference).
	 * Note: Each row of in points MUST have length variables.length + 1.
	 * 
	 * @param points
	 * @param root
	 * @param variables
	 */
	public CurveChromosome(BigDecimal[][] points, Evaluable root, List<Variable> variables) {
		super(root, variables);
		_points = points;
		
		// The fitness is equal to the average squared differences between actual
		// and observed values; less average difference is better.
		BigDecimal l = BigDecimal.valueOf(points.length);
		for(BigDecimal[] point : points) {
			BigDecimal[] input = new BigDecimal[point.length - 1];
			BigDecimal output  = point[point.length - 1];
			System.arraycopy(point, 0, input, 0, input.length);
			
			_fitness += eval(input).subtract(output).abs().divide(l).doubleValue();
		}
	}
	
	@Override
	public double fitness() {
		return _fitness;
	}
	
	@Override
	public CurveChromosome crossover(CurveChromosome mate, double rate) {		
		Evaluable e1 = this.getRoot().copy();
		Evaluable e2 = mate.getRoot().copy();
		
		List<Operation> o1 = Expression.get(e1, Operation.class);
		List<Operation> o2 = Expression.get(e2, Operation.class);
			
		if(Math.random() < rate && !o1.isEmpty() && !o2.isEmpty()) {
			Operation r1 = o1.get((int) (Math.random() * o1.size()));
			Operation r2 = o2.get((int) (Math.random() * o2.size()));
			
			r1.set((int) (Math.random() * r1.arity()),
					r2.get((int) (Math.random() * r2.arity())));
		}
		
		return new CurveChromosome(_points, e1, getVariables());
	}
	
	@Override
	public CurveChromosome mutate(double rate) {
		Evaluable copy = getRoot().copy();
		List<Operation> opers = Expression.get(copy, Operation.class);

		if(Math.random() < rate && !opers.isEmpty()) {
			Operation rand = opers.get((int) (Math.random() * opers.size()));
			int index = (int) (Math.random() * rand.arity());
			rand.set(index, getRandomEvaluable(getVariables()));
		}
		
		return new CurveChromosome(_points, copy, getVariables());
	}
	
	/**
	 * Generates a randomized evaluable using the specified variables as
	 * components. This evaluable will be of random depth.
	 * 
	 * @param variables
	 * @return
	 */
	private static Evaluable getRandomEvaluable(List<Variable> variables) {
		// The greater the first number, the deeper the generated expression
		// will be. It's simple probability!
		switch((int) (Math.random() * 3)) {
			case 0: return variables.get((int) (Math.random() * variables.size()));
			case 1: 
				// Generates a new random constant using a power low distribution
				int sign = (Math.random() < 0.50) ? 1 : -1;
				double value = Math.pow(Math.random(), 10.0) * Double.MAX_VALUE;
				return new Constant(sign * value);
				
			default: 
				switch((int) (Math.random() * 4)) {
					case 0: return new Sin(getRandomEvaluable(variables));
					case 1: return new Cos(getRandomEvaluable(variables));
					case 2: return new Addition(getRandomEvaluable(variables), getRandomEvaluable(variables));
					default: return new Multiplication(getRandomEvaluable(variables), getRandomEvaluable(variables));
				}
		}
	}
	
	/**
	 * Generates a randomized population of curve chromosomes of the specified
	 * size that utilizes the specified points to calculate its fitness.
	 * 
	 * @param size
	 * @param points
	 * @return
	 */
	public static GeneticPopulation<CurveChromosome> getRandomPopulation(int size, BigDecimal[][] points) {
		// Construct the variables that will be used to generate the population
		List<Variable> variables = new ArrayList<Variable>();
		for(int i = 0; i < points[0].length - 1; i++)
			variables.add(new Variable("x" + i));
		
		// Construct the population of curve chromosomes
		List<CurveChromosome> chromosomes = new ArrayList<CurveChromosome>();
		for(int i = 0; i < size; i++) {
			Evaluable root = getRandomEvaluable(variables);
			chromosomes.add(new CurveChromosome(points, root, variables));
		}
		
		return new GeneticPopulation<CurveChromosome>(chromosomes);
	}
}
