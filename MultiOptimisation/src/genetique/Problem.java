package genetique;

import general.Cities;
public class Problem { 
	// this class aims to define the problem which is to be solved in the Main class
	
	private Cities cities;
	private Population population;
	private int max_generations;
	private int comp_generations;
	private double proportion;
	private double proba_mut;
	private double proba_cross;
	
	/**
	 * Constructeur :
	 * 
	 * @param cities
	 *            Cities object that countains the list of cities we need to cross
	 * @param Population
	 *            Population object that contains the individuals that we optimize
	 * @param max_generations
	 * 			  maximum number of iterations of the algorithm
	 * @param comp_generations
	 * 			  the algo stops if the best cost does not improve after this number of iterations
	 * @param proportion
	 * 			  size of the selected set over the size of the population
	 * @param proba_mut
	 * 			  probability of a mutation
	 * @param proba_cross
	 * 			  probability of a crossing
	 */
	
	
	public Problem(Cities cities) {
		// This function defines the problem to solve
		
		// Definition of the population object
		int population_size = 100;
		Population population = new Population(population_size, cities);
		
		// Other parameters
		// Optimization result
		
		
		// arbitrary parameters
		int max_generations = 50000; // maximum number of ierations of the algorithm
		int comp_generations = 5000; // the algo stops if the best cost does not improve after this number of iterations
		double proportion = 0.6; // proportion of solutions kept after the selection phase
		double proba_mut = 0.02; // probability of a mutation on an individual
		double proba_cross = 0.1; // probability of a crossing over two individuals
		
		
		// Definition of the problem
		this.cities = cities;
		this.population = population;
		this.max_generations = max_generations;
		this.comp_generations = comp_generations;
		this.proportion = proportion;
		this.proba_mut = proba_mut;
		this.proba_cross = proba_cross;	
	}

	
	/* ------------  MAIN FUNCTION  ---------------------------------------------------------- */
	public Population getPopulation() {return this.population;}
	public static Individual principal(Cities cities) {
		// main function
		
		// Definition of the problem to solve
		Problem optimizer = new Problem(cities);
		
//		// Lists initialization
//		int[] worsts = new int[optimizer.max_generations + 1];
		int[] bests = new int[optimizer.max_generations + 1];
//		int[] means = new int[optimizer.max_generations + 1];
		
		// Start time
//		long startTime = System.currentTimeMillis();
		
		// Lists' first value
		int i = 0;
//		worsts[i] = optimizer.population.getWorst().get_cost();
		bests[i] = optimizer.population.getBest().get_cost();
//		means[i] = optimizer.population.costMean();		
		
		// Prints the properties of the first generation
//		optimizer.population.affichage_stats(optimizer.cities);
			
		// the program is run until the nb of generations generated reaches the max defined
		while (optimizer.population.get_nb_generations() < optimizer.max_generations) {		
			
			// Updates the population
			optimizer.population.toNextGeneration(
					optimizer.proportion, 
					optimizer.proba_mut, 
					optimizer.proba_cross, 
					optimizer.cities );
			
			// updates the nb of generations 
			optimizer.population.set_nb_generations( 
					optimizer.population.get_nb_generations() + 1 ) ;
			
			// Calculates stats over the new generation
			i++;
//			worsts[i] = optimizer.population.getWorst().get_cost();
//			bests[i] = optimizer.population.getBest().get_cost();
//			means[i] = optimizer.population.costMean();	
			
			// Tests whether the code remains in the loop or not
			if ( (optimizer.population.get_nb_generations() > optimizer.comp_generations) 
					&& (bests[i] == bests[ i - optimizer.comp_generations ] ) ) {
				break;			
			}
			
		}
		
		return optimizer.population.getBest();
		
		// Calculates the time taken by the algorithm
//		long estimatedTime = System.currentTimeMillis() - startTime;
		
//		System.out.format("Duration of the algorithm (in ms) : ");
//		System.out.println(estimatedTime);
//		
//		// Prints stats over the last generation
//		optimizer.population.affichage_stats( optimizer.cities );
		
		// Draws a graph with the best, worst and average costs for each generation
//		GraphingData.PlottingXYGraph(bests, worsts, means);
	}
	
	public Problem(Cities cities, int[] chromosome) {
		// This function defines the problem to solve
		
		// Definition of the population object
		int population_size = 100;
		Population population = new Population(population_size, cities, chromosome);
		
		// Other parameters
		// Optimization result
		
		
		// arbitrary parameters
		int max_generations = 50000; // maximum number of ierations of the algorithm
		int comp_generations = 5000; // the algo stops if the best cost does not improve after this number of iterations
		double proportion = 0.6; // proportion of solutions kept after the selection phase
		double proba_mut = 0.02; // probability of a mutation on an individual
		double proba_cross = 0.1; // probability of a crossing over two individuals
		
		
		// Definition of the problem
		this.cities = cities;
		this.population = population;
		this.max_generations = max_generations;
		this.comp_generations = comp_generations;
		this.proportion = proportion;
		this.proba_mut = proba_mut;
		this.proba_cross = proba_cross;	
	}
	
	
	public static Individual principal(Cities cities, int[] chromosome) {
		
		// Definition of the problem to solve
		Problem optimizer = new Problem(cities, chromosome);
		
		// Lists initialization
		int[] bests = new int[optimizer.max_generations + 1];
		
		// Start time
		long startTime = System.currentTimeMillis();
		
		// Lists' first value
		int i = 0;
		bests[i] = optimizer.population.getBest().get_cost();

			
		// the program is run until the nb of generations generated reaches the max defined
		while (optimizer.population.get_nb_generations() < optimizer.max_generations) {		
			
			// Updates the population
			optimizer.population.toNextGeneration(
					optimizer.proportion, 
					optimizer.proba_mut, 
					optimizer.proba_cross, 
					optimizer.cities );
			
			// updates the nb of generations 
			optimizer.population.set_nb_generations( 
					optimizer.population.get_nb_generations() + 1 ) ;
			
			// Calculates stats over the new generation
			i++;
			bests[i] = optimizer.population.getBest().get_cost();
			
			// Tests whether the code remains in the loop or not
			if ( (optimizer.population.get_nb_generations() > optimizer.comp_generations) 
					&& (bests[i] == bests[ i - optimizer.comp_generations ] ) ) {
				break;			
			}
			
		}
		
		return optimizer.population.getBest();
	}
	
	
}
