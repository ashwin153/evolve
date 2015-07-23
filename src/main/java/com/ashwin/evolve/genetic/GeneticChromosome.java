package com.ashwin.evolve.genetic;

public interface GeneticChromosome<T extends GeneticChromosome<T>> {

	/**
	 * Returns the fitness of the specified chromosome. Because each population
	 * will have a different cost function, we leave the definition of this
	 * methods to subclasses.
	 * 
	 * @param chromosome
	 * @return fitness
	 */
	abstract public double fitness();
	
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
