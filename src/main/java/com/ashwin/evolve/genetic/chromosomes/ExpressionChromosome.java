package com.ashwin.evolve.genetic.chromosomes;

import java.util.List;

import com.ashwin.evolve.expressions.Evaluable;
import com.ashwin.evolve.expressions.Expression;
import com.ashwin.evolve.expressions.Operation;
import com.ashwin.evolve.expressions.Variable;
import com.ashwin.evolve.genetic.GeneticChromosome;
import com.ashwin.evolve.genetic.GeneticFactory;

public class ExpressionChromosome extends Expression implements GeneticChromosome<ExpressionChromosome> {

	private GeneticFactory<ExpressionChromosome, Evaluable> _factory;
	
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
	public ExpressionChromosome(Evaluable root, List<Variable> variables, GeneticFactory<ExpressionChromosome, Evaluable> factory) {
		super(root, variables);
		_factory = factory;
	}
	
	@Override
	public ExpressionChromosome crossover(ExpressionChromosome mate, double rate) {		
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
		
		return new ExpressionChromosome(e1, getVariables(), _factory);
	}
	
	@Override
	public ExpressionChromosome mutate(double rate) {
		Evaluable copy = getRoot().copy();
		List<Operation> opers = Expression.get(copy, Operation.class);

		if(Math.random() < rate && !opers.isEmpty()) {
			Operation rand = opers.get((int) (Math.random() * opers.size()));
			int index = (int) (Math.random() * rand.arity());
			rand.set(index, _factory.getRandomGene());
		}
		
		return new ExpressionChromosome(copy, getVariables(), _factory);
	}
}
