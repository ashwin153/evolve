package com.ashwin.evolve.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

public class GeneticPopulation<T extends GeneticChromosome<T>> {
	
	private List<T> _pop;
	
	private double _crossoverRate, _mutationRate, _elitismRate;
	private int _tournamentSize;

	/**
	 * Builds a randomized population of the specified size. This constructor is
	 * used to generate the initial population.
	 * 
	 * @param size
	 */
	public GeneticPopulation(List<T> pop, Properties props) {
		_crossoverRate  = Double.valueOf(props.getProperty("ga.crossover"));
		_mutationRate   = Double.valueOf(props.getProperty("ga.mutation"));
		_elitismRate    = Double.valueOf(props.getProperty("ga.elitism"));
		_tournamentSize = Integer.valueOf(props.getProperty("ga.tournament"));
		
		_pop = pop;
		Collections.sort(_pop, new FitnessComparator());
	}
	
	/**
	 * Evolves the population. Repeatedly selects individuals, mates them, and
	 * mutates their children until a new population is generated.
	 * 
	 * @return
	 */
	public void evolve() {
		List<T> next = new ArrayList<T>(_pop.size());
		
		// Elitism: Copy the best elements in the population into the next
		// genration. Because our population is always in sorted order, we
		// simply need to copy over the best elements.
		int index = (int) (_pop.size() * _elitismRate);
		if(index % 2 != 0) index++;
		next.addAll(_pop.subList(_pop.size() - index, _pop.size()));
		
		while(index < next.size()) {
			// Select two parents using tournament selection.
			T p1 = select(), p2 = select();
			
			// Perform crossover and mutation
			T child = p1.crossover(p2, _crossoverRate);
			child.mutate(_mutationRate);
			next.add(child);
		}
		
		_pop = next;
		Collections.sort(_pop, new FitnessComparator());
	}
	
	/**
	 * Selects two individual from the population using tournament selection.
	 * Because the population is always guarenteed to be in ascending sorted
	 * fitness order, we can just randomly generate tournamentSize random
	 * indicies and select the largest one.
	 * 
	 * @return
	 */
	public T select() {
		int maxIndex = Integer.MIN_VALUE;
		for(int i = 0; i < _tournamentSize; i++) {
			int rand = (int) (Math.random() * _pop.size());
			if(rand > maxIndex)
				maxIndex = rand;
		}
		return _pop.get(maxIndex);
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
