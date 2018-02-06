package tabou;
import java.util.ArrayList;
import tabou.Solution;

public class taboulet {
	
	
	// il faut une version qui prend cities (initialisation) et une version qui prend cities et un type int[]
	// valeur de sortie int[]
	// int[][] M=cities.get_dist()
	
	public static int[] tabou () {
		//mettre "public static int[] tabou(){ " pour le dialogue entre agents
		
		//création d'une solution admissible
		Solution sol = Solution.nvlleSolAdmissible();
		
		// pour mesurer la durée d'execution du programme
		//long debut = System.currentTimeMillis(); 
		
		//initialisation de la solution ayant la distance minimale finale
		Solution solMinimale = Solution.nvlleSolAdmissible();
		
		// nombre max d'itération dans la boucle de calcul
		int nbMax = 500; 
		// compteur du nombre d'itération
		int nbIter = 0; 
		// nombre d'itération ayant conduit à la meilleure solution jusque là
		int meilIter = 0;	
		
		ArrayList<Integer> listeT = new ArrayList<Integer>(); // liste taboue vide initialement, contient les déplacements tabous
		int tailleT=20; // la taille max de T 
		
		// espace N contenant les solutions voisines de s et leurs déplacement
		ArrayList<Voisin> N = new ArrayList<Voisin>(); 
		
		
		//MANQUE initialisation de la fonction d'aspiration A
		
		while (nbIter-meilIter < nbMax) {
			nbIter ++;
			
			N=Voisin.ensembleDesVoisins(sol);
			
//			List<Integer> sAspire = Aspiration(s);
//			int dAspire = distance(sAspire);
			
			Voisin voisinPrime = Voisin.meilleureSol(N, listeT);
			
			//1er cas : il existe une solution qui permet de diminuer la fonction objectif avec un déplacement qui ne soit pas dans tabou 
			if (voisinPrime.solVoisinne!=null) {
				
				// mise à jour de la solution minimale
				if (solMinimale.distance < voisinPrime.solVoisinne.distance) {
					solMinimale = voisinPrime.solVoisinne;
				}
					
				//mise à jour de la liste tabou
				if (listeT.size()>=tailleT) {
					listeT.remove(0);
					listeT.add(voisinPrime.déplacement);
					// tel qu'un déplacement est défini, effectuer deux fois le déplacement i c'est revenir à l'état initial (i=i^-1)
					// d'où le fait qu'on stocke i dans listeT qui correspond au déplacement pour aller de la solution initiale à la solution suivante
					// et pas le déplacement pour aller de la solution nouvellement atteinte à la solution initiale
					// même si c'est ce déplacement qu'on veut éviter
				}
				
				else {
					listeT.add(voisinPrime.déplacement);
					//System.out.println("test");
				}
			}
			
			// 2ème cas, il n'existe pas de telle solution, il faut alors utiliser une fonction d'aspiration pour sortir du min local
			else {
				// on prend juste le meilleur des voisins sans prendre en compte la liste tabou
				// 
				voisinPrime = Voisin.meilleureSolAspiration(N);
				
				//dans ce cas pas de mise à jour de la solution minimale
				
				//listeT.remove(listeT.size()-1);
			}
			if (voisinPrime.solVoisinne==null) {
				System.out.println("test");
			}
			sol=voisinPrime.solVoisinne;
			//System.out.println("test");
		}	
		
		return arrayListToIntArray(solMinimale.parcours);
		
		// Pour le dialogue inter agents :
		// return arrayListToIntArray(solMinimale.parcours);
		}
	
	public static ArrayList<Integer> conversion(int[]s){
		ArrayList<Integer> p= new ArrayList<Integer>();
		for(int i =0;i<s.length;i++) {
			p.add(s[i]);
		}
		return p;
	}
	
	
	public static int[] tabou (int[] s) {
		//mettre "public static int[] tabou(){ " pour le dialogue entre agents
		
		ArrayList<Integer> sArray=conversion(s);
		
		//création d'une solution admissible
		Solution sol = new Solution(sArray);
		
		// pour mesurer la durée d'execution du programme
		//long debut = System.currentTimeMillis(); 
		
		//initialisation de la solution ayant la distance minimale finale
		Solution solMinimale = new Solution(sArray);
		
		// nombre max d'itération dans la boucle de calcul
		int nbMax = 500; 
		// compteur du nombre d'itération
		int nbIter = 0; 
		// nombre d'itération ayant conduit à la meilleure solution jusque là
		int meilIter = 0;	
		
		ArrayList<Integer> listeT = new ArrayList<Integer>(); // liste taboue vide initialement, contient les déplacements tabous
		int tailleT=20; // la taille max de T 
		
		// espace N contenant les solutions voisines de s et leurs déplacement
		ArrayList<Voisin> N = new ArrayList<Voisin>(); 
		
		
		//MANQUE initialisation de la fonction d'aspiration A
		
		while (nbIter-meilIter < nbMax) {
			nbIter ++;
			
			N=Voisin.ensembleDesVoisins(sol);
			
//			List<Integer> sAspire = Aspiration(s);
//			int dAspire = distance(sAspire);
			
			Voisin voisinPrime = Voisin.meilleureSol(N, listeT);
			
			//1er cas : il existe une solution qui permet de diminuer la fonction objectif avec un déplacement qui ne soit pas dans tabou 
			if (voisinPrime.solVoisinne!=null) {
				
				// mise à jour de la solution minimale
				if (solMinimale.distance < voisinPrime.solVoisinne.distance) {
					solMinimale = voisinPrime.solVoisinne;
				}
					
				//mise à jour de la liste tabou
				if (listeT.size()>=tailleT) {
					listeT.remove(0);
					listeT.add(voisinPrime.déplacement);
					// tel qu'un déplacement est défini, effectuer deux fois le déplacement i c'est revenir à l'état initial (i=i^-1)
					// d'où le fait qu'on stocke i dans listeT qui correspond au déplacement pour aller de la solution initiale à la solution suivante
					// et pas le déplacement pour aller de la solution nouvellement atteinte à la solution initiale
					// même si c'est ce déplacement qu'on veut éviter
				}
				
				else {
					listeT.add(voisinPrime.déplacement);
					//System.out.println("test");
				}
			}
			
			// 2ème cas, il n'existe pas de telle solution, il faut alors utiliser une fonction d'aspiration pour sortir du min local
			else {
				// on prend juste le meilleur des voisins sans prendre en compte la liste tabou
				// 
				voisinPrime = Voisin.meilleureSolAspiration(N);
				
				//dans ce cas pas de mise à jour de la solution minimale
				
				//listeT.remove(listeT.size()-1);
			}
			if (voisinPrime.solVoisinne==null) {
				System.out.println("test");
			}
			sol=voisinPrime.solVoisinne;
			//System.out.println("test");
		}	
		
		return arrayListToIntArray(solMinimale.parcours);
		
		// Pour le dialogue inter agents :
		// return arrayListToIntArray(solMinimale.parcours);
		}

	
	public static int[] arrayListToIntArray (ArrayList<Integer> liste) {
		int[] ret = new int[liste.size()];
		for(int i = 0;i < ret.length;i++)
			ret[i] = liste.get(i);
		return ret;
		}

	
	
//	public static int M[][] = ville.M; //cities.dist
//	
//	public static int tailleM = M.length ; //cities.num
//

//	public static void AfficherS(List<Integer> s){
//		for (int i=0;i<s.size();i++){
//			System.out.println(s.get(i));
//		}		
//		System.out.println(distance(s));
//	}
	

	

/*	//Fonction aspiration
	public static List<Integer> Aspiration(List<Integer> s){ // on mélange les trois éléments du milieu de s
		List<Integer> threeS = new ArrayList<Integer>();
		threeS.add(s.get(2));threeS.add(s.get(3));threeS.add(s.get(4));
		java.util.Collections.shuffle(threeS);
		s.set(2, threeS.get(0));
		s.set(3, threeS.get(1));
		s.set(4, threeS.get(2));
		return s;
	} */
	
//	public static List<Integer> Aspiration(List<Integer> s){ // on mélange les trois éléments du milieu de s
//		Collections.shuffle(s);
//		return s;
//	}

	
}