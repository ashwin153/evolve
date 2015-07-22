package com.ashwin.evolve.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

public class GeneticPopulation<T extends GeneticChromosome<T>> {
	
	private List<T> _pop;
	private Properties _props;

	/**
	 * Builds a randomized population of the specified size. This constructor is
	 * used to generate the initial population.
	 * 
	 * @param size
	 */
	public GeneticPopulation(List<T> pop, Properties props) {
		_pop = pop;
		_props = props;
		
		Collections.sort(_pop, new FitnessComparator());
	}
	
	/**
	 * Evolves the population. Repeatedly selects individuals, mates them, and
	 * mutates their children until a new population is generated.
	 * 
	 * @return
	 */
	public GeneticPopulation<T> evolve() {
		List<T> next = new ArrayList<T>(_pop.size());
		double crossoverRate  = Double.valueOf(_props.getProperty("ga.crossover"));
		double mutationRate   = Double.valueOf(_props.getProperty("ga.mutation"));
		double elitismRate    = Double.valueOf(_props.getProperty("ga.elitism"));
		int tournamentSize    = Integer.valueOf(_props.getProperty("ga.tournament"));
		
		// Elitism: Copy the best elements in the population into the next
		// genration. Because our population is always in sorted order, we
		// simply need to copy over the best elements.
		int index = (int) (_pop.size() * elitismRate);
		next.addAll(_pop.subList(0, index));
		
		while(next.size() < _pop.size()) {
			// Select two parents using tournament selection, perform crossover
			// and mutation, and add the resulting child into the next
			// population.
			T p1 = select(tournamentSize), p2 = select(tournamentSize);
			
			// Perform crossover and mutation
			T child = p1.crossover(p2, crossoverRate);
			child.mutate(mutationRate);
			next.add(child);
		}
		
		return new GeneticPopulation<T>(next, _props);
	}
	
	/**
	 * Selects two individual from the population using tournament selection.
	 * Because the population is always guarenteed to be in ascending sorted
	 * fitness order, we can just randomly generate tournamentSize random
	 * indicies and select the largest one.
	 * 
	 * @return
	 */
	public T select(int tournamentSize) {
		int minIndex = Integer.MIN_VALUE;
		for(int i = 0; i < tournamentSize; i++) {
			int rand = (int) (Math.random() * _pop.size());
			if(rand > minIndex)
				minIndex = rand;
		}
		return _pop.get(minIndex);
	}
	
	/**
	 * This class is responsible for comparing two chromosomes. It is used
	 * by the evolve function to sort the population by their fitness values.
	 * 
	 * @author ashwin
	 */
	private class FitnessComparator implements Comparator<T> {
		public int compare(T o1, T o2) {
			return Double.compare(o1.fitness(), o2.fitness());
		}
	}
}
