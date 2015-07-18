package com.ashwin.evolve.genetics;

import java.util.Arrays;
import java.util.Comparator;

public class GeneticPopulation {

	private GeneticProgram[] _pop;
	
	public GeneticPopulation(GeneticProgram[] pop) {
		_pop = pop;
		Arrays.sort(_pop, new FitnessComparator());
	}
	
	public GeneticPopulation evolve() {
		GeneticProgram[] next = new GeneticProgram[_pop.length];
		
		// Population size MUST be even, because we do everything in multiples of 2
		int index = (int) (_pop.length * GeneticAlgorithm.ELITISM_RATE);
		if(index % 2 != 0) index++;
				
		// Elitism: Copy the best elements in the population into the next generation.
		// Because the population is sorted, take elements between [0, index)
		System.arraycopy(_pop, 0, next, 0, index);
		
		// While the next generation is not yet full, continue natural selection
		while (index < next.length) {
			try {
				// Select two parents using tournament selection
				GeneticProgram p1 = select();
				GeneticProgram p2 = select();

				GeneticProgram[] off = p1.crossover(p2, GeneticAlgorithm.CROSSOVER_RATE);
				off[0].mutate(GeneticAlgorithm.MUTATION_RATE);
				off[1].mutate(GeneticAlgorithm.MUTATION_RATE);

				// Put the offspring into the next generation and increment the
				// counter
				System.arraycopy(off, 0, next, index, 2);
				index += 2;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Return a new generation of the population
		return new GeneticPopulation(next);
	}
	
	/**
	 * This method performs tournament selection. Tournament selection involves
	 * selecting a group of chromosomes and returning the chromosome with the
	 * lowest fitness value.
	 * 
	 * @return selected chromosome
	 */
	private GeneticProgram select() {
		GeneticProgram winner = null;
		double min = Double.MAX_VALUE;
		
		for(int i = 0; i < GeneticAlgorithm.TOURNAMENT_SIZE; i++) {
			int rand = (int) (Math.random() * _pop.length);
			double fitness = _pop[rand].getFitness();
			
			if(fitness < min) {
				winner = _pop[rand];
				min = fitness;
			}
		}
		
		return winner;
	}
	
	public GeneticProgram getFittest() {
		return _pop[0];
	}
	
	/** Returns the lowest fitness in the population. */
	public double getMinimumFitness() {
		return _pop[0].getFitness();
	}
	
	/** Returns the average fitness of the population. */
	public double getAverageFitness() {
		double avg = 0.0;
		for(int i = 0; i < _pop.length; i++)
			avg += _pop[i].getFitness();
		return avg / _pop.length;
	}
	
	/**
	 * This class is responsible for comparing two chromosomes. It is used
	 * by the evolve function to sort the population by their fitness values.
	 * 
	 * @author ashwin
	 */
	private class FitnessComparator implements Comparator<GeneticProgram> {
		public int compare(GeneticProgram o1, GeneticProgram o2) {
			return Double.compare(o1.getFitness(), o2.getFitness());
		}
	}
}
