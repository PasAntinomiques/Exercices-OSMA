package tabou;
import java.util.ArrayList;
import tabou.Solution;

public class taboulet {
	
	
	// il faut une version qui prend cities (initialisation) et une version qui prend cities et un type int[]
	// valeur de sortie int[]
	// int[][] M=cities.get_dist()
	
	public static int[] tabou () {
		//mettre "public static int[] tabou(){ " pour le dialogue entre agents
		
		//cr�ation d'une solution admissible
		Solution sol = Solution.nvlleSolAdmissible();
		
		// pour mesurer la dur�e d'execution du programme
		//long debut = System.currentTimeMillis(); 
		
		//initialisation de la solution ayant la distance minimale finale
		Solution solMinimale = Solution.nvlleSolAdmissible();
		
		// nombre max d'it�ration dans la boucle de calcul
		int nbMax = 500; 
		// compteur du nombre d'it�ration
		int nbIter = 0; 
		// nombre d'it�ration ayant conduit � la meilleure solution jusque l�
		int meilIter = 0;	
		
		ArrayList<Integer> listeT = new ArrayList<Integer>(); // liste taboue vide initialement, contient les d�placements tabous
		int tailleT=20; // la taille max de T 
		
		// espace N contenant les solutions voisines de s et leurs d�placement
		ArrayList<Voisin> N = new ArrayList<Voisin>(); 
		
		
		//MANQUE initialisation de la fonction d'aspiration A
		
		while (nbIter-meilIter < nbMax) {
			nbIter ++;
			
			N=Voisin.ensembleDesVoisins(sol);
			
//			List<Integer> sAspire = Aspiration(s);
//			int dAspire = distance(sAspire);
			
			Voisin voisinPrime = Voisin.meilleureSol(N, listeT);
			
			//1er cas : il existe une solution qui permet de diminuer la fonction objectif avec un d�placement qui ne soit pas dans tabou 
			if (voisinPrime.solVoisinne!=null) {
				
				// mise � jour de la solution minimale
				if (solMinimale.distance < voisinPrime.solVoisinne.distance) {
					solMinimale = voisinPrime.solVoisinne;
				}
					
				//mise � jour de la liste tabou
				if (listeT.size()>=tailleT) {
					listeT.remove(0);
					listeT.add(voisinPrime.d�placement);
					// tel qu'un d�placement est d�fini, effectuer deux fois le d�placement i c'est revenir � l'�tat initial (i=i^-1)
					// d'o� le fait qu'on stocke i dans listeT qui correspond au d�placement pour aller de la solution initiale � la solution suivante
					// et pas le d�placement pour aller de la solution nouvellement atteinte � la solution initiale
					// m�me si c'est ce d�placement qu'on veut �viter
				}
				
				else {
					listeT.add(voisinPrime.d�placement);
					//System.out.println("test");
				}
			}
			
			// 2�me cas, il n'existe pas de telle solution, il faut alors utiliser une fonction d'aspiration pour sortir du min local
			else {
				// on prend juste le meilleur des voisins sans prendre en compte la liste tabou
				// 
				voisinPrime = Voisin.meilleureSolAspiration(N);
				
				//dans ce cas pas de mise � jour de la solution minimale
				
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
		
		//cr�ation d'une solution admissible
		Solution sol = new Solution(sArray);
		
		// pour mesurer la dur�e d'execution du programme
		//long debut = System.currentTimeMillis(); 
		
		//initialisation de la solution ayant la distance minimale finale
		Solution solMinimale = new Solution(sArray);
		
		// nombre max d'it�ration dans la boucle de calcul
		int nbMax = 500; 
		// compteur du nombre d'it�ration
		int nbIter = 0; 
		// nombre d'it�ration ayant conduit � la meilleure solution jusque l�
		int meilIter = 0;	
		
		ArrayList<Integer> listeT = new ArrayList<Integer>(); // liste taboue vide initialement, contient les d�placements tabous
		int tailleT=20; // la taille max de T 
		
		// espace N contenant les solutions voisines de s et leurs d�placement
		ArrayList<Voisin> N = new ArrayList<Voisin>(); 
		
		
		//MANQUE initialisation de la fonction d'aspiration A
		
		while (nbIter-meilIter < nbMax) {
			nbIter ++;
			
			N=Voisin.ensembleDesVoisins(sol);
			
//			List<Integer> sAspire = Aspiration(s);
//			int dAspire = distance(sAspire);
			
			Voisin voisinPrime = Voisin.meilleureSol(N, listeT);
			
			//1er cas : il existe une solution qui permet de diminuer la fonction objectif avec un d�placement qui ne soit pas dans tabou 
			if (voisinPrime.solVoisinne!=null) {
				
				// mise � jour de la solution minimale
				if (solMinimale.distance < voisinPrime.solVoisinne.distance) {
					solMinimale = voisinPrime.solVoisinne;
				}
					
				//mise � jour de la liste tabou
				if (listeT.size()>=tailleT) {
					listeT.remove(0);
					listeT.add(voisinPrime.d�placement);
					// tel qu'un d�placement est d�fini, effectuer deux fois le d�placement i c'est revenir � l'�tat initial (i=i^-1)
					// d'o� le fait qu'on stocke i dans listeT qui correspond au d�placement pour aller de la solution initiale � la solution suivante
					// et pas le d�placement pour aller de la solution nouvellement atteinte � la solution initiale
					// m�me si c'est ce d�placement qu'on veut �viter
				}
				
				else {
					listeT.add(voisinPrime.d�placement);
					//System.out.println("test");
				}
			}
			
			// 2�me cas, il n'existe pas de telle solution, il faut alors utiliser une fonction d'aspiration pour sortir du min local
			else {
				// on prend juste le meilleur des voisins sans prendre en compte la liste tabou
				// 
				voisinPrime = Voisin.meilleureSolAspiration(N);
				
				//dans ce cas pas de mise � jour de la solution minimale
				
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
	public static List<Integer> Aspiration(List<Integer> s){ // on m�lange les trois �l�ments du milieu de s
		List<Integer> threeS = new ArrayList<Integer>();
		threeS.add(s.get(2));threeS.add(s.get(3));threeS.add(s.get(4));
		java.util.Collections.shuffle(threeS);
		s.set(2, threeS.get(0));
		s.set(3, threeS.get(1));
		s.set(4, threeS.get(2));
		return s;
	} */
	
//	public static List<Integer> Aspiration(List<Integer> s){ // on m�lange les trois �l�ments du milieu de s
//		Collections.shuffle(s);
//		return s;
//	}

	
}