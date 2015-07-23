package com.ashwin.evolve.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GeneticPopulation<T extends GeneticChromosome<T>> {
	
	private List<T> _pop;

	/**
	 * Builds a randomized population of the specified size. This constructor is
	 * used to generate the initial population.
	 * 
	 * @param size
	 */
	public GeneticPopulation(List<T> pop) {
		_pop = pop;		
		
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		Collections.sort(_pop, new FitnessComparator());
	}
	
	public List<T> getChromosomes() {
		return _pop;
	}
	
	public double getBestFitness() {
		return _pop.get(0).fitness();
	}
	
	public double getAverageFitness() {
		double avg = 0.0;
		for(T chromosome : _pop)
			avg += chromosome.fitness();
		return avg / _pop.size();
	}
	
	/**
	 * Evolves the population. Repeatedly selects individuals, mates them, and
	 * mutates their children until a new population is generated.
	 * 
	 * @return next generation of population
	 */
	public GeneticPopulation<T> evolve(double crossoverRate,
			double mutationRate, double elitismRate, int tournamentSize) {
		List<T> next = new ArrayList<T>(_pop.size());
		
		// Elitism: Copy the best elements in the population into the next
		// genration. Because our population is always in sorted order, we
		// simply need to copy over the best elements. The mutation hack forces
		// the creation of new chromosomes.
		int index = (int) (_pop.size() * elitismRate);
		for(T chromosome : _pop.subList(0, index))
			next.add(chromosome.mutate(0));
		
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
		
		return new GeneticPopulation<T>(next);
	}
	
	/**
	 * Selects two individual from the population using tournament selection.
	 * Because the population is always guarenteed to be in ascending sorted
	 * fitness order, we can just randomly generate tournamentSize random
	 * indicies and select the largest one.
	 * 
	 * @return selected individual
	 */
	public T select(int tournamentSize) {
		int minIndex = Integer.MAX_VALUE;
		for(int i = 0; i < tournamentSize; i++) {
			int rand = (int) (Math.random() * _pop.size());
			if(rand < minIndex)
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
