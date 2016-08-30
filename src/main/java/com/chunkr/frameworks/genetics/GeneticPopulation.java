package com.chunkr.frameworks.genetics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class GeneticPopulation<T extends GeneticChromosome<T>> {

	private GeneticAlgorithm<T, ?> _algorithm;
	private List<T> _chromosomes;
	
	/**
	 * Constructs a new genetic population. Genetic populations utilize an
	 * executor service to parallelize fitness calculations; which, in turn,
	 * massively increases runtime performance.
	 * 
	 * @param algorithm
	 * @param chromosomes
	 */
	public GeneticPopulation(List<T> chromosomes, GeneticAlgorithm<T, ?> algorithm) { 
		_algorithm = algorithm;
	
		// Sort the list of chromosomes by their fitness; utilizes an executor
		// service to greatly increase runtime performance by parallelizing
		// fitness calculations.
		int threads = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(threads);
		
		List<Pair<T, Future<BigDecimal>>> sorted = new ArrayList<Pair<T, Future<BigDecimal>>>();
		for(T chromosome : chromosomes) {
			Future<BigDecimal> fitness = executor.submit(_algorithm.getFitness(chromosome));
			sorted.add(new MutablePair<T, Future<BigDecimal>>(chromosome, fitness));
		}
		
		Collections.sort(sorted, new Comparator<Pair<T, Future<BigDecimal>>>() {
			@Override
			public int compare(Pair<T, Future<BigDecimal>> o1, Pair<T, Future<BigDecimal>> o2) {
				try {
					BigDecimal f1 = o1.getValue().get();
					BigDecimal f2 = o2.getValue().get();
					return f1.compareTo(f2);
				} catch(Exception e) {
					// If we cannot retrieve the fitness, then we cannot evolve
					// this population; therefore, we throw a runtime error.
					throw new RuntimeException(e);
				}
			}
		});
		
		executor.shutdownNow();
		
		// Put the sorted chromosomes into the population list
		_chromosomes = new ArrayList<T>();
		for(Pair<T, Future<BigDecimal>> entry : sorted)
			_chromosomes.add(entry.getKey());
		
		try {
			System.out.println("Best Fitness: " + sorted.get(0).getValue().get());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<T> getChromosomes() {
		return _chromosomes;
	}
	
	/**
	 * Evolves the population. Repeatedly selects individuals, mates them, and
	 * mutates their children until a new population is generated.
	 * 
	 * @return next generation of population
	 */
	public GeneticPopulation<T> evolve(double crossoverRate,
			double mutationRate, double elitismRate, int tournamentSize) {
		List<T> next = new ArrayList<T>(_chromosomes.size());
		
		// Elitism: Copy the best elements in the population into the next gen.
		// Because our population is always in sorted order, we simply need to
		// copy over the best elements.
		int index = (int) (_chromosomes.size() * elitismRate);
		for(T chromosome : _chromosomes.subList(0, index))
			next.add(chromosome);
		
		while(next.size() < _chromosomes.size()) {
			// Select two parents using tournament selection, perform crossover
			// and mutation, and add the resulting child into the next gen.
			T p1 = select(tournamentSize), p2 = select(tournamentSize);
			T child = p1.crossover(p2, crossoverRate);
			child.mutate(mutationRate);
			next.add(child);
		}
		
		return new GeneticPopulation<T>(next, _algorithm);
	}
	
	/**
	 * Selects two individual from the population using tournament selection.
	 * Because the population is always guaranteed to be in ascending sorted
	 * fitness order, we can just randomly generate tournamentSize random
	 * indices and select the largest one.
	 * 
	 * @return selected individual
	 */
	public T select(int tournamentSize) {
		int minIndex = Integer.MAX_VALUE;
		for(int i = 0; i < tournamentSize; i++) {
			int rand = (int) (Math.random() * _chromosomes.size());
			if(rand < minIndex)
				minIndex = rand;
		}
		
		return _chromosomes.get(minIndex);
	}
	
}
