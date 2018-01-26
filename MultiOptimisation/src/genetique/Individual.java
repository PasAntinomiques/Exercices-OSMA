package genetique;

import general.Cities;
import java.util.Random ;

public class Individual {
	
	private int[] chromosome;
	private int cost;
		
	/**
	 * Constructeur :
	 * 
	 * @param chromosome
	 *            permutation of cities.index_list (without the start city)
	 *            that gives an order of traveling the cities
	 * @param cost 
	 * 			  integer, quantitative evaluation of the considered solution
	 */
	
	public Individual(int[] chromosome, Cities cities) {
		this.chromosome = chromosome;
		this.evaluateCost(cities);
	}
	
	public Individual(Cities cities) {
		int[] chromosome = new int[cities.get_num()-1];
		int i = 0;
				
		int start_city = cities.get_start_city();
		for (int t:cities.get_index_list()) {
			if (t != start_city ) { 
				chromosome[i] = t;
				i++;		
			}
		}
		
		shuffleArray( chromosome );
		this.chromosome = chromosome;	
		this.evaluateCost(cities);
	}
	
	public Individual copy(Cities cities) {
		/* copies the individual into a new and independant one */
		int[] new_chromosome = new int[this.chromosome.length];
		int i = 0;
		for (int t:this.chromosome) {
			new_chromosome[i] = t;
			i++;
		}
		return new Individual(new_chromosome, cities); 
	}
	
	/* Getter functions */

	public int[] get_chromosome() { return this.chromosome; }
	public int get_cost() { return this.cost; }
	
	
	/* ---------------------------------------------- */
	
	public void affichage_chromosome(Cities cities) {
		/* prints the individual's chromosome */
		String str = "";
		str += cities.get_name_list()[ cities.get_start_city() ] + " -> ";
		for (int i=0 ; i<this.chromosome.length ; i++ ) {
			str += cities.get_name_list()[ this.chromosome[i] ] + " -> ";
		}
		str += cities.get_name_list()[ cities.get_start_city() ];
		System.out.println(str);	
	}
	
	/* ---------------------------------------------------- */
	/* 					SHUFFLING A LIST					*/
	/* ---------------------------------------------------- */
	
	private static void swap(int[] a, int i, int change) {
		/* swaps two elements of a list */
        int helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }
	
	public static int[] shuffleArray(int[] a) {
		/* shuffles a list */
        int n = a.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
        return a;
	}
	
	/* ---------------------------------------------------- */
	
	
	private void swap(int i, int j) {
		/* helper function for mutation()
		 * swaps two elements of a list */
        int helper = this.chromosome[i];
        this.chromosome[i] = this.chromosome[j];
        this.chromosome[j] = helper;
    }
	
	private static void swap(int[] list_1, int[] list_2, int index) {
		/* helper function for crossing */
        int helper = list_1[index];
        list_1[index] = list_2[index];
        list_1[index] = helper;
    }
	
        
    /* ---------------------------------------------------- */

        
	public void evaluateCost(Cities cities) {
		/* Evaluates the cost of an individual's chromosome */
		int cost_value = cities.get_dist() [ cities.get_start_city() ] [ this.chromosome[0] ] ;
		
		for (int i=0 ; i<this.chromosome.length-1 ; i++) {
			int index_1 = this.chromosome[i];
			int index_2 = this.chromosome[i+1];

			cost_value += cities.get_dist()[index_1][index_2];
		}
		
		cost_value += cities.get_dist() 
				[this.chromosome[this.chromosome.length-1] ] [cities.get_start_city()] ;
		
		this.cost = cost_value ;
	}
	
	
	public void mutation(Cities cities) {
		/* applies a mutation to the individual
		 * ie it selects two indexes i,j between 0 and this.chromosome.lenght,
		 * and reverses the order of chromosome[i:j] */
		
		// first, it creates two indexes
		int i = (int)(Math.random() * (this.chromosome.length));	
		int j = (int)(Math.random() * (this.chromosome.length));
		
		// if j==i, there would be ne mutation
		while(j == i) { j = (int)(Math.random() * this.chromosome.length); }
		
		// then we actually reverse the order between chromosome[i] and chromosome[j]
		int min = Math.min(i,j);
		int max = Math.max(i,j);
		while(min<max) {
			swap(min, max);
			min += 1;
			max -= 1;
		}
		
		this.evaluateCost(cities);
	}
	
	
	public void crossing(Individual other, Cities cities) {
		/* apllies crossing to two individuals */
		
		// first, it creates two indexes
		int a = (int)(Math.random() * (this.chromosome.length));	
		int b = (int)(Math.random() * (this.chromosome.length));
		
		int min = Math.min(a,b);
		int max = Math.max(a,b);
		
		Individual son_1 = this.copy(cities);
		Individual son_2 = other.copy(cities);
			
		// on croise les deux parents pour obtenir les deux enfants
		for(int k=min ; k<=max ; k+=1) {
			swap(son_1.chromosome, son_2.chromosome, k);
		}
			
		// on corrige les doublons de chromosome_son_1
		for(int k_1=min ; k_1<=max ; k_1+=1) {
			int index = -1;
			boolean need_to_correct = false;
			for(int k_2=0 ; k_2<son_1.chromosome.length ; k_2+=1) {
				/* if true : there is a doublon, so we save the index of the int we need
				 * to correct  */
				if (son_1.chromosome[k_2] == son_1.chromosome[k_1] && k_2<min && k_2>max) { 
					index = k_2;
					need_to_correct = true;
					break;
				}
			}
			if (need_to_correct) {
				son_1.chromosome[index] = this.chromosome[k_1];
			}
		}
				
		// on corrige les doublons de chromosome_son_2
		for(int k_1=min ; k_1<=max ; k_1+=1) {
			int index = -1;
			boolean need_to_correct = false;
			for(int k_2=0 ; k_2<son_2.chromosome.length ; k_2+=1) {
				/* if true : there is a doublon, so we save the index of the int we need
				 * to correct  */
				if (son_2.chromosome[k_2] == son_2.chromosome[k_1] && k_2<min && k_2>max) { 
					index = k_2;
					need_to_correct = true;
					break;
				}
			}
			
			if (need_to_correct) {
				son_2.chromosome[index] = other.chromosome[k_1];
			}
		}
			
		// on remplace les chromosome des parents par ceux de leurs enfants
		this.chromosome = son_1.chromosome;
		this.evaluateCost(cities);
		other.chromosome = son_2.chromosome;
		other.evaluateCost(cities);
	}
}

