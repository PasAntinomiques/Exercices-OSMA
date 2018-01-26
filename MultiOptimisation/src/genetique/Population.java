package genetique;

import general.Cities;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Population {
	
	final static int INFINI = Integer.MAX_VALUE ;
	
	private int size;
	private Individual[] group;
	private int nb_generations;
	
	/**
	 * Constructeur :
	 * 
	 * @param size
	 *            size of the population
	 * @param group
	 *            set of the individual that compose the population
	 */
	public Population(int size, Individual[] group) {
		/* generates a Population from a group and its size */
		this.size = size;
		this.group = group;
		this.nb_generations = 0;
	}
	
	public Population(int size, Cities cities) {
		/* generates a population from the wanted size */
		this.size = size;
		this.group = new Individual[size];
		for (int i=0 ; i<size ; i++) {
			this.group[i] = new Individual(cities);
		}	
		this.nb_generations = 0;
	}
	
	public Population(int size, Cities cities, int[] chromosome) {
		/* generates a population from the wanted size */
		this.size = size;
		this.group = new Individual[size];
		
		// le premier élément correspond à la solution retenue du précédent algo
		this.group[0] = new Individual(chromosome, cities);
		for (int i=1 ; i<size ; i++) {
			// pour tous les autres individus de la population, on leur donne la solution retenue, et on la fait muter
			this.group[i] = new Individual(chromosome, cities);
			this.group[i].mutation(cities);
		}	
		this.nb_generations = 0;
	}
	
	/* Getter & setter functions */
	
	public int get_nb_generations() { return this.nb_generations; }
	public int get_size() { return this.size; }
	public Individual[] get_group() { return this.group; }
	public int get_actual_size() { return this.group.length; }
	
	public void set_nb_generations(int new_val) { this.nb_generations = new_val; }

	
	/* ---------------------------------------------------- */
	
	private static void swap(Individual[] a, int i, int change) {
		/* helper function for crossing and shuffleGroup
		 * swaps two elements of a list */
        Individual helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }
	
	public static void shuffleGroup(Individual[] a) {
		/* helper function for recombination
		 * shuffles a list */
        int n = a.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
	}
	
	/* ----------------Statistics over the population------------------------------------ */
	
	
	public Individual getBest(List<Individual> list, Cities cities) {
		//returns the individual of the population with the lowest cost 
		Individual[] group = new Individual[list.size()];
		for(int i=0; i<list.size(); i++) {
            group[i] = list.get(i);
		}
		
		int lowest_cost = group[0].get_cost() ;
		Individual best = group[0] ;
		for (int i=1 ; i<group.length ; i++) {
			if (lowest_cost > group[i].get_cost() ) {
				best = group[i];
				lowest_cost = group[i].get_cost() ;
			}
		}
		return best;
	}
	
	
	
	public Individual getBest() {
		/* returns the individual of the population with the lowest cost */
		int lowest_cost = this.group[0].get_cost() ;
		Individual best = this.group[0] ;
		for (int i=1 ; i<this.group.length ; i++) {
			if (lowest_cost > this.group[i].get_cost() ) {
				best = this.group[i];
				lowest_cost = this.group[i].get_cost() ;
			}
		}
		return best;
	}
	
	public Individual getWorst() {
		/* returns the individual of the population with the lowest cost */
		
		int highest_cost = this.group[0].get_cost() ;
		Individual worst = this.group[0] ;
		for (int i=1 ; i<this.group.length ; i++) {
			if (highest_cost < this.group[i].get_cost() ) {
				worst = this.group[i];
				highest_cost = this.group[i].get_cost() ;
			}
		}
		return worst;
	}
	
	public int costMean() {
		/* returns the mean cost of the population */
		int total = 0;
		for (int i=0 ; i<this.size ; i++) {
			total += this.group[i].get_cost();
		}
		int mean = total / this.size;
		return mean;
	}
	
	public void affichage_stats(Cities cities) {
		/* prints statistics over the considered population */
		
		System.out.format("\nAfter %d iterations :\n", this.get_nb_generations() );	
		System.out.format("Average cost of the population : %d \n", 
				(int)this.costMean() );
		
		Individual best_individual = this.getBest();
		System.out.format("Best identified Cost : %d km\n", best_individual.get_cost());
		System.out.format("Associated Path : ");
		best_individual.affichage_chromosome(cities);
		
		Individual worst_individual = this.getWorst();
		System.out.format("Worst identified Cost : %d km\n", worst_individual.get_cost());
		System.out.format("Associated Path : ");
		worst_individual.affichage_chromosome(cities);
		
		System.out.format("\n");
			
	}
	
	/* ---------------------------------------------------- */
	
	
	private List<Individual> inter_group_builder(int size, Cities cities) {
		/* helper function for selection
		 * returns a list containing the n best individuals of this,
		 * with n = size
		 */
		
		// First, builds a list identical to this.group (we need a list to modify the contents)
		List<Individual> inter_group_2 = new LinkedList<Individual>();
		for (int k=0 ; k<this.size ; k++) {
			inter_group_2.add(this.group[k]);
		}
		
		// Then, builds the a list containing the n best individuals of this
		List<Individual> inter_group = new LinkedList<Individual>();
		for (int k=0 ; k<size ; k++) {
			Individual best = getBest(inter_group_2, cities);
			inter_group.add(best.copy(cities));
			inter_group_2.remove(best);

		}
		return inter_group;
	}
	
	public Individual[] selection(double proportion, Cities cities) {
		/* select and returns the best part of the population, with a given proportion */
		
		// Vérification sur la validité de proportion. Si invalide, 0.8 par défaut
		if (proportion > 1  || proportion <= 0) { 
			proportion = 0.8 ;
		}
		
		int size_selected = (int)(this.size * proportion);
		if (size_selected == 0) { size_selected = 1; }
		List<Individual> inter_group = inter_group_builder(size_selected, cities);
		Individual[] selected_group = new Individual[this.size];
		
		int i=0;
		while (i < this.size) { 
			Individual best = getBest(inter_group, cities);
			selected_group[i] = best;		
			inter_group.remove(best); 
			
			i += 1;
			if (inter_group.size() == 0 && i < this.size) {
				inter_group = inter_group_builder(size_selected, cities);
			}
			
		}
		return selected_group; // best_group.length == this.size
	}
	
	
	
	public static void populationCrossing(Individual[] survivors, 
			double proba_cross, Cities cities) {
		
		/* crosses the ith and jth Individuals of the population */
		for (int k=0 ; k<survivors.length-1 ; k+=2) {
			double x = Math.random();
			if (x <= proba_cross) {
				survivors[k].crossing( survivors[k+1], cities );
			}
		}
	}
	

	public static void populationMutation(Individual[] survivors, double proba_mut, Cities cities) {
		/* applies the mutation process to the generation */
		for (int i=0 ; i<survivors.length ; i++) {
			double a = Math.random();
			if (a <= proba_mut) {
				survivors[i].mutation(cities);
			}
		}			
	}
	
	
	public void recombination(Individual[] survivors, double proba_mut, 
			double proba_cross, Cities cities) {
		/* applies a recombination (shuffling, crossing and mutation) */
		
		shuffleGroup(survivors); // survivors is shuffled
			
		populationCrossing(survivors, proba_cross, cities); // crossing
		populationMutation(survivors, proba_mut, cities); // mutation
		
		this.group = survivors;	
	}
		
	
	public void toNextGeneration(double proportion, double proba_mut, 
			double proba_cross, Cities cities) {
		/* applies selection and then recombination */
		
		Individual[] survivors = this.selection(proportion, cities); // Selection
		this.recombination(survivors, proba_mut, proba_cross, cities); // Recombination
	}

}
