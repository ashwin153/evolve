package com.ashwin.evolve.genetics.algorithms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Variable;
import com.ashwin.evolve.expressions.operations.binary.Addition;
import com.ashwin.evolve.expressions.operations.binary.Multiplication;
import com.ashwin.evolve.expressions.operations.unary.Cos;
import com.ashwin.evolve.expressions.operations.unary.Sin;
import com.ashwin.evolve.genetics.GeneticAlgorithm;
import com.ashwin.evolve.genetics.chromosomes.ExpressionChromosome;

public class CurveFittingAlgorithm extends GeneticAlgorithm<ExpressionChromosome, Evaluable> {

	private List<Variable> _variables;
	private BigDecimal[][] _tests;
	
	public CurveFittingAlgorithm(Properties props, BigDecimal[][] tests) {
		super(props);
		_tests = tests;
		
		// Construct the variables that will be used to generate the population
		_variables = new ArrayList<Variable>();
		for (int i = 0; i < _tests[0].length - 1; i++)
			_variables.add(new Variable("x" + i));
	}
	
	@Override
	public double getFitness(ExpressionChromosome chromosome) {
		// The fitness is equal to the average squared differences between actual
		// and observed values; less average difference is better.
		BigDecimal l = BigDecimal.valueOf(_tests.length);
		double fitness = 0.0;
		
		for(BigDecimal[] point : _tests) {
			BigDecimal[] input = new BigDecimal[point.length - 1];
			BigDecimal output  = point[point.length - 1];
			System.arraycopy(point, 0, input, 0, input.length);
			fitness += chromosome.eval(input).subtract(output).abs().divide(l).doubleValue();
		}
		
		return fitness;
	}
	
	@Override
	public Evaluable getRandomGene() {
		// The greater the first number, the deeper the generated expression
		// will be. It's simple probability!
		switch ((int) (Math.random() * 2)) {
			case 0: return _variables.get((int) (Math.random() * _variables.size()));
			
			default:
				switch ((int) (Math.random() * 4)) {
					case 0: return new Sin(getRandomGene());
					case 1: return new Cos(getRandomGene());
					case 2: return new Addition(getRandomGene(), getRandomGene());
					default: return new Multiplication(getRandomGene(), getRandomGene());
			}
		}
	}

	@Override
	public ExpressionChromosome getRandomChromosome() {
		return new ExpressionChromosome(getRandomGene(), _variables, this);
	}

}
