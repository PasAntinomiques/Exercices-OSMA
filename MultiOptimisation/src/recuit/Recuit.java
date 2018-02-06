package recuit;

import java.util.Random;
import general.Cities;

public class Recuit {
	
	public static int[] recuit(Cities cities) {
		// applique l'algorithme du recuit � partir de rien
		
		int k = 0;
		boolean nouveau_cycle = true;
		
		final int nb_iter_cycle = 400;
		final double t0 = 100;
		final double a = 0.999995;
		
		// on initialise l'algo en g�n�rant un chemin al�atoire admissible
		int[] s1 = genererCheminAleatoire(cities);
		
		int[]s_star = copy(s1);
		double t = t0;
		
		while(nouveau_cycle) {
			int nb_iter = 0;
			nouveau_cycle = false;
			while(nb_iter < nb_iter_cycle) {
				k++;
				nb_iter++;
				
				// On g�n�re un voisin proche de la solution actuelle (en �changeant deux villes)
				int[] s2 = genererCheminVoisin(s1);
				
				// On calcule la diff�rence des co�ts pour comparer la qualit� des deux solutions admissibles
				double delta_E = cost(s2, cities) - cost(s1, cities);
				
				// Si delta_E est n�gatif, on garde la solution
				if(delta_E < 0) {
					s1 = copy(s2);
					nouveau_cycle = true;
				}
				
				// si delta_E est positif, on garde la solution selon une probabilit�
				else {
					double proba = Math.exp( - delta_E / t);
					double q = Math.random();
					if (q < proba) {
						s1 = copy(s2);
						nouveau_cycle = true;
					}
				}
				
				// Si la solution consid�r�e est meilleure que la meilleure solution consid�r�e jusqu'ici, on la garde
				if(cost(s1, cities) < cost(s_star, cities)) { s_star = s1; }
			}
			
			// on abaisse la temp�rature
			t*=a;
		}
		
		return s_star;
	}
	
	
	
	public static int[] recuit(Cities cities, int[] solution_intermediaire) {
		// applique l'algorithme du recuit � partir d'une solution interm�diaire
		
		int k = 0;
		boolean nouveau_cycle = true;
		
		final int nb_iter_cycle = 400;
		final double t0 = 100;
		final double a = 0.99;
		
		// on initialise l'algo en g�n�rant un chemin al�atoire admissible
		int[] s1 = copy(solution_intermediaire);
		
		int[] s_star = copy(s1);
		double t = t0;
		
		while(nouveau_cycle) {
			int nb_iter = 0;
			nouveau_cycle = false;
			while(nb_iter < nb_iter_cycle) {
				k++;
				nb_iter++;
				int[] s2 = genererCheminVoisin(s1);
				double delta_E = cost(s2, cities) - cost(s1, cities);
				if(delta_E < 0) {
					s1 = copy(s2);
					nouveau_cycle = true;
				}
				else {
					double proba = Math.exp( - delta_E / t);
					double q = Math.random();
					if (q < proba) {
						s1 = copy(s2);
						nouveau_cycle = true;
					}
				}
				if(cost(s1, cities) < cost(s_star, cities)) { s_star = s1; }
			}
			t*=a;
		}
		
		return s_star;
	}
	
// ----------------------------------------------------------//
	
	private static int[] genererCheminAleatoire(Cities cities) {
		// g�n�re un chemin al�atoire en m�langeant les indices de cities.index_list
		int[] s = new int[ cities.get_num()  - 1 ];
		
		// on r�cup�re tous les indices, sauf celui de la ville de d�part
		int i = 0;
		for (int t:cities.get_index_list()) {
			if (t != cities.get_start_city() ) { 
				s[i] = t;
				i++;		
			}
		}
		// on m�lange compl�tement et al�atoirement la liste
		s = shuffleTab(s);
		return s;	
	}
	
	private static int[] genererCheminVoisin(int[] s1) {
		// g�n�re une solution voisine de la solution consid�r�e en �changeant deux indices
		
		int[] s2 = copy(s1);
		Random random = new Random();
		int i = random.nextInt(s2.length);
		int j = random.nextInt(s2.length);
		while(i==j) { j = random.nextInt(s2.length); }
        
        // on �change deux �l�ments pour g�n�rer une solution voisine
        int helper = s2[i];
        s2[i] = s2[j];
        s2[j] = helper;
        
		return s2;	
	}
	
	private static int[] shuffleTab(int[] s) {
		/* shuffles a list */
        int n = s.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            
            // on �change deux �l�ments
            int helper = s[i];
            s[i] = s[change];
            s[change] = helper;
        }
        
        return s;
	}
	
// --------------------------------------------------------- //
	
	private static int[] copy(int[] s) {
		// copie une liste pour obtenir deux listes ind�pendantes l'une de l'autre
		int[] s_copy = new int[s.length];
		for(int i=0; i<s.length ; i++) {
			s_copy[i] = s[i];
		}
		return s_copy;
	}
	

// --------------------------------------------------------- //
	
	private static int cost(int[] s, Cities cities) {
		// calcule le co�t d'une solution
		int cost = cities.get_dist()[ cities.get_start_city() ] [s[0] ];
		for(int i=0; i<s.length - 1 ; i++) {
			cost += cities.get_dist()[ s[i] ][ s[i+1] ];
		}
		cost += cities.get_dist()[ s[s.length - 1] ][ cities.get_start_city() ];
		return cost;
	}
}
