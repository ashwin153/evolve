package com.ashwin.evolve.genetic.chromosomes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.ashwin.evolve.expressions.Constant;
import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Expression;
import com.ashwin.evolve.expressions.Operation;
import com.ashwin.evolve.expressions.Variable;
import com.ashwin.evolve.expressions.operations.binary.Addition;
import com.ashwin.evolve.expressions.operations.binary.Multiplication;
import com.ashwin.evolve.expressions.operations.unary.AbsoluteValue;
import com.ashwin.evolve.expressions.operations.unary.Cos;
import com.ashwin.evolve.expressions.operations.unary.Negation;
import com.ashwin.evolve.expressions.operations.unary.Sin;
import com.ashwin.evolve.genetic.GeneticChromosome;
import com.ashwin.evolve.genetic.GeneticPopulation;

public class CurveChromosome extends Expression implements GeneticChromosome<CurveChromosome> {
	
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
	public CurveChromosome(BigDecimal[][] points, Evaluable root, Variable...variables) {
		super(root, variables);
		
		// The fitness is equal to the average squared differences between actual
		// and observed values; less average difference is better.
		BigDecimal l = BigDecimal.valueOf(points.length);
		for(BigDecimal[] point : points) {
			BigDecimal[] input = new BigDecimal[point.length - 1];
			BigDecimal output  = point[point.length - 1];
			System.arraycopy(point, 0, input, 0, input.length);
			_fitness += eval(input).subtract(output).pow(2).divide(l).doubleValue();
		}
	}
	
	@Override
	public double fitness() {
		return _fitness;
	}
	
	@Override
	public CurveChromosome crossover(CurveChromosome mate, double rate) {
		CurveChromosome copy = CurveChromosome.copy(this);
		
		Operation o1 = copy.getRandomOperation();
		if(Math.random() < rate && o1 != null) {
			Operation o2 = mate.getRandomOperation();
			Evaluable rand = (o2 == null) ? mate.getRoot() : o2.get((int) (Math.random() * o2.arity()));
			
			int index = (int) (Math.random() * o1.arity());
			o1.set(index, rand);
		}
		
		return copy;
	}
	
	@Override
	public CurveChromosome mutate(double rate) {
		CurveChromosome copy = CurveChromosome.copy(this);
		
		if(Math.random() < rate) {
			Operation oper = copy.getRandomOperation();
			int index = (int) (Math.random() * oper.arity());
			oper.set(index, getRandomEvaluable(getVariables()));
		}
		
		return copy;
	}
	
	/**
	 * Returns a randomly selected operation from this expression. This is used
	 * to select a point of crossover between two curve chromosomes.
	 * 
	 * @return
	 */
	private Operation getRandomOperation() {
		List<Operation> operations = new ArrayList<Operation>();
		Stack<Evaluable> stack = new Stack<Evaluable>();
		stack.push(getRoot());

		while (!stack.isEmpty()) {
			Evaluable element = stack.pop();
			if (element instanceof Operation) {
				Operation oper = (Operation) element;
				operations.add(oper);

				for (int i = 0; i < oper.arity(); i++)
					stack.push(oper.get(i));
			}
		}
		
		return (operations.isEmpty()) ? null : operations.get((int) (Math.random() * operations.size()));
	}
	
	/**
	 * Generates a randomized evaluable using the specified variables as
	 * components. This evaluable will be of random depth.
	 * 
	 * @param variables
	 * @return
	 */
	private static Evaluable getRandomEvaluable(Variable... variables) {
		// The greater the first number, the deeper the generated expression
		// will be. It's simple probability!
		switch((int) (Math.random() * 6)) {
			case 0: return variables[(int) (Math.random() * variables.length)];
			case 1: 
				// Generates a new random constant using a power low distribution
				int sign = (Math.random() < 0.50) ? 1 : -1;
				double value = Math.pow(Math.random(), 5.0) * Double.MAX_VALUE;
				return new Constant(sign * value);
				
			default: 
				switch((int) (Math.random() * 6)) {
					case 0: return new AbsoluteValue(getRandomEvaluable(variables));
					case 1: return new Sin(getRandomEvaluable(variables));
					case 2: return new Cos(getRandomEvaluable(variables));
					case 3: return new Negation(getRandomEvaluable(variables));
					case 4: return new Addition(getRandomEvaluable(variables), getRandomEvaluable(variables));
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
		Variable[] variables = new Variable[points[0].length - 1];
		for(int i = 0; i < variables.length; i++)
			variables[i] = new Variable("x" + i);
		
		// Construct the population of curve chromosomes
		List<CurveChromosome> chromosomes = new ArrayList<CurveChromosome>();
		for(int i = 0; i < size; i++)
			chromosomes.add(new CurveChromosome(points, getRandomEvaluable(variables), variables));
		return new GeneticPopulation<CurveChromosome>(chromosomes);
	}
	
	/**
	 * Returns a deep copy of the given expression. This is used to prevent
	 * destructively modifying an expression when performing operations on it.
	 * 
	 * @param expression
	 * @return
	 * @throws Exception
	 */
	public static CurveChromosome copy(CurveChromosome expression) {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(expression);
	        oos.flush();
			
	        ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
	        ois = new ObjectInputStream(bin);
	        CurveChromosome chromosome = (CurveChromosome) ois.readObject();
	 
	        oos.close();
	        ois.close();
	        
	        return chromosome;
		} catch(Exception e) {
			// If we cannot copy the chromosome, then we cannot continue with execution
			throw new RuntimeException("Unable to copy chromosome");
		}
	}
}
