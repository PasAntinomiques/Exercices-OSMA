package recuit;
import java.util.concurrent.ThreadLocalRandom;

import general.Cities;

import java.lang.Math;
import java.util.*;
import general.Cities;

@SuppressWarnings("unused")
public class recuit {
	public static int k = 0;
	
	public static int[][] M;

	
	private static int[] toIntArray(List<Integer> list){
		
		  int[] ret = new int[list.size()];
		  for(int i = 0;i < ret.length;i++)
		    ret[i] = list.get(i);
		  return ret;
		}
	private static ArrayList<Integer> arrayToList(int[] sTab){
		ArrayList<Integer> sList = new ArrayList<Integer>();
		  for(int i = 0;i < sTab.length;i++) {
			   sList.add(sTab[i]);
		  }
		    
		return sList;
	}
	public static Object[] recuitMain(Cities cities) {
		
		M = cities.get_dist();
		ArrayList<Integer> chemin = genererChemin(M.length);
		double t = 10000;
		double a = 0.99;
		int nbIterMax = 400;
		ArrayList<Integer> sol = algo(chemin, t, a, nbIterMax);
		int [] solTab = toIntArray(sol);
		int distance = calculChemin(sol);
		Object[] tab = {solTab,distance};
		return tab;
	//	System.out.println(sol);
	//	System.out.println(k);
	//	System.out.println(calculChemin(sol));
		
	//	ArrayList<Integer> chemin2 = new ArrayList<Integer>(Arrays.asList(0,7,37,30,43,17,6,27,5,36,18,26,16,42,29,35,45,32,19,46,20,31,38,47,4,41,23,9,44,34,3,25,1,28,33,40,15,21,2,22,13,24,12,10,11,14,39,8));
	//	System.out.println(chemin2.size());
	//	System.out.println(calculChemin(chemin2));
				
	//	afficher(etudeStat(10000, 100, 0.99, 10, 45000));

	}
	
	public static Object[] recuitMain(Cities cities, int[] cheminTab) {
			
		M = cities.get_dist();
		ArrayList<Integer> chemin = arrayToList(cheminTab);
		double t = 10000;
		double a = 0.99;
		int nbIterMax = 400;
		ArrayList<Integer> sol = algo(chemin, t, a, nbIterMax);
		
		int [] solTab = toIntArray(sol);
		int distance = calculChemin(sol);
		Object[] tab = {solTab,distance};
		return tab;
//		System.out.println(sol);
//		System.out.println(k);
//		System.out.println(calculChemin(sol));
//		
//		ArrayList<Integer> chemin2 = new ArrayList<Integer>(Arrays.asList(0,7,37,30,43,17,6,27,5,36,18,26,16,42,29,35,45,32,19,46,20,31,38,47,4,41,23,9,44,34,3,25,1,28,33,40,15,21,2,22,13,24,12,10,11,14,39,8));
//		System.out.println(chemin2.size());
//		System.out.println(calculChemin(chemin2));
				
//		afficher(etudeStat(10000, 100, 0.99, 10, 45000));

	}
	
	public static void afficher(ArrayList<int[]> liste){
		System.out.println("Table start");
		for (int[] ar:liste){
			System.out.println(Arrays.toString(ar));
		}
		System.out.println("Table ends");
		
	}
	
	public static ArrayList<Integer> genererChemin(int taille){
		ArrayList<Integer> chemin = new ArrayList<Integer>();
		for (int i = 0; i<taille;i++){
			chemin.add(i);
		}
		return chemin;
	}
	
	public static ArrayList<int[]> etudeStat(int tmax, int nbiterMax, double a, int sampleSize, double tolerance){
		ArrayList<int[]> resultats = new ArrayList<int[]>();
		ArrayList<Integer> chemin = genererChemin(M.length);
		for (int t=1; t<=tmax;t++){
			int nbiter = 1;
			double moyenne;
			double kmoyen;
			do{
				moyenne = 0;
				kmoyen = 0;
				for (int j = 0; j<sampleSize; j++){
					Collections.shuffle(chemin);
					moyenne += ((double)calculChemin(algo(chemin, t, a, nbiter)))/((double)sampleSize);
					kmoyen +=((double) k)/sampleSize;
					//System.out.println("Pour t="+t+", nbiter ="+nbiter+",k ="+k);
				}				
				nbiter+=1;
				//System.out.println("Pour t="+t+", nbiter ="+nbiter+", moyenne ="+(int)moyenne);
			} while (moyenne>tolerance & nbiter<nbiterMax);
			if (nbiter == nbiterMax){
				int[] temp = {t,0,(int)kmoyen};
				resultats.add(temp);
			}
			else{
				int[] temp = {t,nbiter-1,(int) kmoyen};
				resultats.add(temp);
			}
		}
		return resultats;		
		
	}
	
	
	public static int calculChemin(ArrayList<Integer> chemin){
		int total = 0;
		for (int i=0;i<M.length-1;i++){
			total += M[chemin.get(i)][chemin.get(i+1)];
		}
		total += M[chemin.get(0)][chemin.get(M.length-1)];
		return total;
		
	}
	
	public static int calculChemin2(ArrayList<Integer> chemin){
		int total = 0;
		for (int i=0;i<5;i++){
			System.out.println("Going from "+chemin.get(i)+" to "+chemin.get(i+1)+ " takes "+M[chemin.get(i)][chemin.get(i+1)]);
			//System.out.println(chemin.get(i+1));
			//System.out.println(M[chemin.get(i)][chemin.get(i+1)]);
			total += M[chemin.get(i)][chemin.get(i+1)];
			System.out.println("Total is now "+total);
			
		}
		total += M[chemin.get(0)][chemin.get(M.length-1)];
		System.out.println("Going from "+chemin.get(M.length-1)+" to "+chemin.get(0)+" takes "+M[chemin.get(0)][chemin.get(M.length-1)]);
		return total;
		
	}
	public static ArrayList<Integer> algo(ArrayList<Integer> s, double t, double a, int nbIterMax){
		ArrayList<Integer> sStar = s;
		ArrayList<Integer> chemin = s;
		k = 0;
		boolean nouveau_cycle = true;
		while (nouveau_cycle){
			int nbiter = 0;
			nouveau_cycle = false;
			while (nbiter<nbIterMax){
				k += 1;
				nbiter += 1;
				int i = nbAleatoire(0,M.length-1);
				int j = i;
				while (j==i){
					j = nbAleatoire(0,M.length-1);
				}
				ArrayList<Integer> nouveauChemin = new ArrayList<Integer>();
				for (int m=0;m<M.length;m++){
					nouveauChemin.add(chemin.get(m));
				}
				nouveauChemin.set(i, chemin.get(j));
				nouveauChemin.set(j, chemin.get(i));
				int delta = calculChemin(nouveauChemin)-calculChemin(chemin);
				if (delta<0){
					chemin = nouveauChemin;
					nouveau_cycle = true;
				}
				else{
					if (Math.random() <Math.exp(-delta/(t))){
						chemin = nouveauChemin;
						nouveau_cycle = true;
					}
				}
				
				if (calculChemin(chemin)<calculChemin(sStar)){
					sStar  = chemin;
				}
			}
			t = a*t;
		}
	return sStar;
	}
	
	public static int nbAleatoire (int min, int max){
		return ThreadLocalRandom.current().nextInt(min, max + 1);
		
	}

}
