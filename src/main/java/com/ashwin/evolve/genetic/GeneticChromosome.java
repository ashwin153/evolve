package com.ashwin.evolve.genetic;

public interface GeneticChromosome<T extends GeneticChromosome<T>> {

	/**
	 * Returns the fitness of the specified chromosome. Because each population
	 * will have a different cost function, we leave the definition of this
	 * methods to subclasses.
	 * 
	 * @param chromosome
	 * @return
	 */
	abstract public double fitness();
	
	/**
	 * Non-destructively performs crossover with this chromosome and the
	 * specified mate and returns their child.
	 * 
	 * @param mate
	 * @param rate
	 * @return
	 */
	public T crossover(T mate, double rate);
	
	/**
	 * Non-destructively mutates this genetic chromosome, using genes from the
	 * randomly generated chromosome specified in the parameters.
	 * 
	 * @param random
	 * @param rate
	 * @return
	 */
	public T mutate(double rate);
	
}
