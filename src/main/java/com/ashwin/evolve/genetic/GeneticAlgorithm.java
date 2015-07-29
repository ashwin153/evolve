package com.ashwin.evolve.genetic;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.ashwin.evolve.genetic.chromosomes.CurveChromosome;

public class GeneticAlgorithm {
	
	private static final Logger LOGGER = Logger.getLogger(GeneticAlgorithm.class);
	
	private GeneticPopulation<?> _initial;
	
//	public static void main(String[] args) {
//		GeneticAlgorithm alg = new GeneticAlgorithm(
//				SortingChromosome.getRandomPopulation(10000, 30));
//		
//		GeneticPopulation<?> pop = alg.run();
//		System.out.println(pop.getChromosomes().get(0));
//	}
	
	public static void main(String[] args) {
		BigDecimal[][] tests = new BigDecimal[100][2];
		for(int i = 0; i < tests.length; i++) {
			BigDecimal x = BigDecimal.valueOf(Math.random() * 4 - 2);
			BigDecimal y = x.pow(5).add(
					BigDecimal.valueOf(3).multiply(x.pow(4))).add(
							BigDecimal.valueOf(-2).multiply(x.pow(3))).add(
									x.subtract(BigDecimal.valueOf(2)));
			
			tests[i] = new BigDecimal[] { x, y };
		}
		
		GeneticAlgorithm alg = new GeneticAlgorithm(
				CurveChromosome.getRandomPopulation(1000, tests));
		
		GeneticPopulation<?> pop = alg.run();
		System.out.println(pop.getChromosomes().get(0));
	}
	
	public GeneticAlgorithm(GeneticPopulation<?> initial) {
		_initial = initial;
	}
	
	public GeneticPopulation<?> run() {
		GeneticPopulation<?> pop = _initial;
		LOGGER.info(String.format("%6s\t%15s\t%15s", "Gen", "Best", "Average"));
		LOGGER.info(String.format("%6d\t%15.8f\t%15.8f", 0, pop.getBestFitness(), pop.getAverageFitness()));
		
		for(int i = 1; i < 1000; i++) {
			pop = pop.evolve(0.95, 0.25, 0.05, 3);
			LOGGER.info(String.format("%6d\t%15.8f\t%15.8f", i, pop.getBestFitness(), pop.getAverageFitness()));
		}
		
		return pop;
	}
}
