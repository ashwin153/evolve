package com.ashwin.evolve.genetic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ashwin.evolve.genetic.factories.CurveFittingFactory;

public class GeneticAlgorithm {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		BigDecimal[][] tests = new BigDecimal[100][2];
		for(int i = 0; i < tests.length; i++) {
			BigDecimal x = BigDecimal.valueOf(Math.random() * 4 - 2);
			BigDecimal y = x.pow(5).add(
					BigDecimal.valueOf(3).multiply(x.pow(4))).add(
							BigDecimal.valueOf(-2).multiply(x.pow(3))).add(
									x.subtract(BigDecimal.valueOf(2)));
			tests[i] = new BigDecimal[] { x, y };
		}
		
		Properties props = new Properties();
		props.load(new FileInputStream(new File("./src/main/resources/ga.properties")));
		
		GeneticFactory<?, ?> factory = new CurveFittingFactory(tests);
		GeneticAlgorithm algorithm	 = new GeneticAlgorithm(factory);
		GeneticPopulation<?> evolved = algorithm.run(props);
		System.out.println(evolved.getChromosomes().get(0));
	}
	
	
	private static final Logger LOGGER = Logger.getLogger(GeneticAlgorithm.class);
	
	private GeneticFactory<?, ?> _factory;
	
	public GeneticAlgorithm(GeneticFactory<?, ?> factory) {
		_factory = factory;
	}
	
	public GeneticPopulation<?> run(Properties props) {
		// Load the Genetic Algorithm Properties
		int endGen			= Integer.valueOf(props.getProperty("ga.end.gen"));
		double endFitness	= Double.valueOf(props.getProperty("ga.end.fitness"));
		int size 		 	= Integer.valueOf(props.getProperty("ga.pop.size"));
		double crossover 	= Double.valueOf(props.getProperty("ga.pop.crossover"));
		double mutation  	= Double.valueOf(props.getProperty("ga.pop.mutation"));
		double elitism   	= Double.valueOf(props.getProperty("ga.pop.elitism"));
		int tournament 	 	= Integer.valueOf(props.getProperty("ga.pop.tournament"));

		GeneticPopulation<?> pop = _factory.getRandomPopulation(size);
		LOGGER.info(String.format("%6s\t%15s", "Gen", "Best"));
		LOGGER.info(String.format("%6d\t%15.8f", 0, pop.getBestFitness()));
		
		for(int i = 1; i <= endGen && pop.getBestFitness() > endFitness; i++) {
			pop = pop.evolve(crossover, mutation, elitism, tournament);
			LOGGER.info(String.format("%6d\t%15.8f", i, pop.getBestFitness()));
		}
		
		return pop;
	}
}
