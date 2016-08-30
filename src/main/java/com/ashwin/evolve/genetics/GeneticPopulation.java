package com.ashwin.evolve.genetics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class GeneticPopulation<T extends GeneticChromosome<T>> {
	
	private List<T> _pop;
	private GeneticAlgorithm<T, ?> _algorithm;

	/**
	 * Builds a randomized population of the specified size. This constructor is
	 * used to generate the initial population.
	 * 
	 * @param size
	 */
	public GeneticPopulation(List<T> pop, GeneticAlgorithm<T, ?> algorithm) {
		_algorithm = algorithm;

		// Cache fitness of chromosomes to speed up sorting process
		List<Pair<T, Double>> sort = new ArrayList<Pair<T, Double>>();
		for(T member : pop)
			sort.add(new ImmutablePair<T, Double>(
					member, _algorithm.getFitness(member)));

		Collections.sort(sort, new Comparator<Pair<T, Double>>() {
			@Override
			public int compare(Pair<T, Double> o1, Pair<T, Double> o2) {
				return Double.compare(o1.getValue(), o2.getValue());
			}
		});
		
		_pop = new ArrayList<T>();
		for(Pair<T, Double> pair : sort)
			_pop.add(pair.getKey());
	}
	
	public List<T> getChromosomes() {
		return _pop;
	}
	
	public double getBestFitness() {
		return _algorithm.getFitness(_pop.get(0));
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
		
		return new GeneticPopulation<T>(next, _algorithm);
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
}
