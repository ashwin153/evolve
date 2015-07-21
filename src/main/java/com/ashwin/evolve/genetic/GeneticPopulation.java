package com.ashwin.evolve.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class GeneticPopulation<T extends GeneticChromosome> {
	
	private List<T> _pop;

	/**
	 * Builds a randomized population of the specified size. This constructor is
	 * used to generate the initial population.
	 * 
	 * @param size
	 */
	public GeneticPopulation(int size) {
		_pop = new ArrayList<T>();
		for(int i = 0; i < _pop.size(); i++)
			_pop.add(random());
		Collections.sort(_pop, new FitnessComparator());
	}
	
	/**
	 * Creates a population from an existing array of chromosomes. This
	 * constructor is used when populations evolve.
	 * 
	 * @param pop
	 */
	public GeneticPopulation(List<T> pop) {
		_pop = pop;
		Collections.sort(_pop, new FitnessComparator());
	}
	
	/**
	 * Returns a randomized genetic chromosome. This is used to build the
	 * initial population of the genetic population. Because each population
	 * builds chromosomes differently, we leave the definition of this method to
	 * subclasses.
	 * 
	 * @return
	 */
	abstract public T random();

	/**
	 * Returns the fitness of the specified chromosome. Because each population
	 * will have a different cost function, we leave the definition of this
	 * methods to subclasses.
	 * 
	 * @param chromosome
	 * @return
	 */
	abstract public double fitness(T chromosome);
	
	/**
	 * Evolves the population. Repeatedly selects individuals, mates them, and
	 * mutates their children until a new population is generated.
	 * 
	 * @return
	 */
	public GeneticPopulation<T> evolve() {
//		GeneticProgram[] next = new GeneticProgram[_pop.length];
//		
//		// Population size MUST be even, because we do everything in multiples of 2
//		int index = (int) (_pop.length * GeneticAlgorithm.ELITISM_RATE);
//		if(index % 2 != 0) index++;
//				
//		// Elitism: Copy the best elements in the population into the next generation.
//		// Because the population is sorted, take elements between [0, index)
//		System.arraycopy(_pop, 0, next, 0, index);
//		
//		// While the next generation is not yet full, continue natural selection
//		while (index < next.length) {
//			try {
//				// Select two parents using tournament selection
//				GeneticProgram p1 = select();
//				GeneticProgram p2 = select();
//
//				GeneticProgram[] off = p1.crossover(p2, GeneticAlgorithm.CROSSOVER_RATE);
//				off[0].mutate(GeneticAlgorithm.MUTATION_RATE);
//				off[1].mutate(GeneticAlgorithm.MUTATION_RATE);
//
//				// Put the offspring into the next generation and increment the
//				// counter
//				System.arraycopy(off, 0, next, index, 2);
//				index += 2;
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		// Return a new generation of the population
//		return new GeneticPopulation(next);
		return null;
	}
	
	/**
	 * Selects two individual from the population using tournament selection.
	 * 
	 * @return
	 */
	public GeneticChromosome select() {
//		GeneticProgram winner = null;
//		double min = Double.MAX_VALUE;
//		
//		for(int i = 0; i < GeneticAlgorithm.TOURNAMENT_SIZE; i++) {
//			int rand = (int) (Math.random() * _pop.length);
//			double fitness = _pop[rand].getFitness();
//			
//			if(fitness < min) {
//				winner = _pop[rand];
//				min = fitness;
//			}
//		}
//		
//		return winner;
		return null;
	}
	
	/**
	 * This class is responsible for comparing two chromosomes. It is used
	 * by the evolve function to sort the population by their fitness values.
	 * 
	 * @author ashwin
	 */
	private class FitnessComparator implements Comparator<T> {
		public int compare(T o1, T o2) {
			return Double.compare(fitness(o1), fitness(o2));
		}
	}
}
