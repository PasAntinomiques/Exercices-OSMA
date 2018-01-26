import java.util.concurrent.ThreadLocalRandom;
import java.util.Collections;
import java.lang.Math;
import java.util.*;

public class AlgoRecuit {
	public static int k = 0;
	
	/*public static int M[][] = {{0,780,320,580,480,660},
			{780,0,700,460,300,200},
			{320,700,0,380,820,630},
			{580,460,380,0,750,310},
			{480,300,820,750,0,500},
			{660,200,630,310,500,0}};
	*/
	public static int[][] coord = {{6734,1453},
			{2233,10},
			{5530,1424},
			{401,841},
			{3082,1644},
			{7608,4458},
			{7573,3716},
			{7265,1268},
			{6898,1885},
			{1112,2049},
			{5468,2606},
			{5989,2873},
			{4706,2674},
			{4612,2035},
			{6347,2683},
			{6107,669},
			{7611,5184},
			{7462,3590},
			{7732,4723},
			{5900,3561},
			{4483,3369},
			{6101,1110},
			{5199,2182},
			{1633,2809},
			{4307,2322},
			{675,1006},
			{7555,4819},
			{7541,3981},
			{3177,756},
			{7352,4506},
			{7545,2801},
			{3245,3305},
			{6426,3173},
			{4608,1198},
			{23,2216},
			{7248,3779},
			{7762,4595},
			{7392,2244},
			{3484,2829},
			{6271,2135},
			{4985,140},
			{1916,1569},
			{7280,4899},
			{7509,3239},
			{10,2676},
			{6807,2993},
			{5185,3258},
			{3023,1942}};
	
	public static int[][] M = coordToDistance(coord);

	public static int[][] coordToDistance(int[][] coord){
		int[][] distances = new int[coord.length][coord.length];
		for (int i=0;i<coord.length;i++){
			for (int j=0;j<coord.length;j++){
				distances[i][j] =(int) Math.sqrt((Math.pow(coord[i][0]-coord[j][0],2)+(Math.pow(coord[i][1]-coord[j][1],2))));
			}
		}
		return distances;
	}
	

	public static void main(String[] args) {
		
		
	ArrayList<Integer> chemin = genererChemin(M.length);
	double t = 10000;
	double a = 0.99;
	int nbIterMax = 400;
	ArrayList<Integer> sol = algo(chemin, t, a, nbIterMax);
	System.out.println(sol);
	System.out.println(k);
	System.out.println(calculChemin(sol));
	
	ArrayList<Integer> chemin2 = new ArrayList<Integer>(Arrays.asList(0,7,37,30,43,17,6,27,5,36,18,26,16,42,29,35,45,32,19,46,20,31,38,47,4,41,23,9,44,34,3,25,1,28,33,40,15,21,2,22,13,24,12,10,11,14,39,8));
	System.out.println(chemin2.size());
	System.out.println(calculChemin(chemin2));
			
	afficher(etudeStat(10000, 100, 0.99, 10, 45000));

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
