package tabou;
import java.util.ArrayList;

import java.util.Collections;
import general.Cities;

public class Solution {
	
	// Un parcours (ou un trajet) est une permutation de l'ensemble des villes qui sont visitées par le VdC 
	// sans prendre en compte la ville de départ, qui est fixée, et qui est aussi la ville d'arrivée
	
	ArrayList<Integer> parcours;
	int distance;
	
	public Solution() {
		this.parcours=null;
		this.distance = 0;
	}
	
	public Solution(ArrayList<Integer> trajet, int d) {
		this.parcours=trajet;
		this.distance = d;
	}

	public Solution (ArrayList<Integer> trajet) {
		this.parcours = trajet;
		this.distance = calculDistance(trajet);
	}
	public static Cities cities = new Cities();

	public static int[][] M = cities.get_dist(); //cities.dist
	
	public static int tailleM = cities.get_num() ; //cities.num
	
	public int calculDistance(ArrayList<Integer> trajet ) {
		int tailleT = trajet.size();
		int d=M[trajet.get (0)][cities.get_start_city()];
		for (int i = 0; i< (tailleT - 1); i++) {
			d += M[trajet.get(i)][trajet.get(i+1)];
		}
		d += M[trajet.get(tailleT -1)][cities.get_start_city()];		
		return d;
	}
	
	public static Solution nvlleSolAdmissible (){
		ArrayList<Integer> trajet = new ArrayList<Integer>();
		for(int i=1; i<tailleM;  i++) {
			trajet.add(i);
		}
		Collections.shuffle(trajet);
		return new Solution (trajet);
	}
	
}
