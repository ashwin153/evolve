package com.ashwin.evolve.genetic;

public interface GeneticChromosome<T extends GeneticChromosome<T>> {

	/**
	 * Non-destructively performs crossover with this chromosome and the
	 * specified mate and returns their child.
	 * 
	 * @param mate
	 * @param rate
	 * @return child
	 */
	public T crossover(T mate, double rate);
	
	/**
	 * Non-destructively mutates this genetic chromosome, using genes from the
	 * randomly generated chromosome specified in the parameters.
	 * 
	 * @param random
	 * @param rate
	 * @return mutated chromosome
	 */
	public T mutate(double rate);
	
}
