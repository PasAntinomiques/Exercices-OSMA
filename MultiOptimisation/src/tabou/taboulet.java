package tabou;

import java.util.ArrayList;
import java.util.*;
import general.Cities;

public class taboulet {
	public static int[][] M;
	public static int tailleM;
	
	public static Object[] tabou (Cities cities) {
		M = cities.get_dist();
		tailleM = cities.get_num();
		
		//long debut = System.currentTimeMillis(); // pour mesurer la durée d'execution du programme
		
		List<Integer> s = solAdm(); // création d'une solution admissible
		int d = distance(s); //distance de s
		
		List<Integer> sMin = s; //initialisation de la solution ayant la distance minimale finale
		int dMin = d;
		
		int nbMax = 100000; // nombre max d'itération dans la boucle de calcul
		int nbIter = 0; // compteur du nombre d'itération
		int meilIter = 0;	// nombre d'itération ayant conduit à la meilleure solution jusque là
		
		List<List<Integer>> T = new ArrayList<List<Integer>>(); // liste taboue vide initialement, contient les déplacements tabous
		int tailleT=20; // la taille max de T est de 20
		
		List<List<List<Integer>>> N = new ArrayList<List<List<Integer>>>(); // espace N contenant les solutions voisines de s et leurs déplacement
		
		//MANQUE initialisation de la fonction d'aspiration A
		
		while (d>=dMin && nbIter-meilIter < nbMax) {
			nbIter ++;
			
			N=voisinGenerator(s);
			
			List<Integer> sAspire = Aspiration(s);
			int dAspire = distance(sAspire);
			
			List<Integer> sPrime1 = N.get(0).get(0);
			int dPrime1 = distance(sPrime1);
			List<Integer> mouvement1 = N.get(1).get(0);
			boolean aspired1 = false;
			for(int i=0;i<N.size();i++){ //On choisit la solution la plus courte de N' et qui est plus courte que la solution aspirée
				if(distance(N.get(0).get(i))<=dAspire && dPrime1<d){
					sPrime1=N.get(0).get(i);
					dPrime1=distance(sPrime1);
					mouvement1 = N.get(1).get(i);
				}
				else{ // si elle n'existe pas on aspire
					sPrime1=sAspire;
					dPrime1=dAspire;
					aspired1=true;
				}
			}
			
			List<Integer> sPrime2 = sAspire; //sPrime2 meilleure solution hors tabou
			int dPrime2 = dAspire;			//si toutes les solutions sont dans tabou on aspire
			List<Integer> mouvement2 = new ArrayList<Integer>();
			boolean aspired2 = true;
			boolean breaked = false;
			List<Integer> couple = new ArrayList<Integer>();
			for(int i=0;i<N.size();i++){
				couple = N.get(1).get(i);
				for(int j=0;j<T.size();j++){//initialisation de sPrime2 la meilleure solution hors tabou
					if(couple!=T.get(j) && breaked==false) {//si on est hors tabou et que l'initialisation n'est pas terminée
						sPrime2 = N.get(0).get(i);
						dPrime2 = distance(sPrime2);
						mouvement2 = N.get(1).get(i);
						aspired2 = false;
						breaked = true;//initialisation terminée
						break;
					}
				}
				if(breaked){//quand l'initialisation est terminée
					for(int j=0;j<T.size();j++){ //recherche de la solution minimale hors tabou
						if(couple!=T.get(j) && distance(N.get(0).get(i))<dPrime2){
							sPrime2 = N.get(0).get(i);
							dPrime2 = distance(N.get(0).get(i));
							mouvement2 = N.get(1).get(i);
						}
					}
				}
			}
			
			//Comparaison entre les deux solutions
						
			if(dPrime2<dPrime1){
				s=sPrime2;
				d=dPrime2;
				if(aspired2==false){
					T.add(mouvement2);
				}
			}
			else{
				s=sPrime1;
				d=dPrime1;
				if(aspired1==false){
					T.add(mouvement1);
				}
			}
			
			if(T.size()>tailleT){
				T.remove(0);
			}
			
			if(d<dMin){
				sMin=s;
				dMin=d;
				meilIter=nbIter;
				nbIter=0;
			}
		}
		//System.out.println("Solution finale");
		//AfficherS(sMin);
		//System.out.println("meil_iter : "+meilIter);
		
		//System.out.println(System.currentTimeMillis()-debut); // renvoie la durée du programme
		
		
		int[] sMinTab = toIntArray(sMin);
		Object[] solFinale = {sMinTab,dMin};
		return solFinale;
	}
	
	public static Object[] tabou (Cities cities, int[] sTab) {
	
		M = cities.get_dist();
		tailleM = cities.get_num();
		
		//long debut = System.currentTimeMillis(); // pour mesurer la durée d'execution du programme
		
		List<Integer> s = arrayToList(sTab); // création d'une solution admissible
		int d = distance(s); //distance de s
		
		List<Integer> sMin = s; //initialisation de la solution ayant la distance minimale finale
		int dMin = d;
		
		int nbMax = 100000; // nombre max d'itération dans la boucle de calcul
		int nbIter = 0; // compteur du nombre d'itération
		int meilIter = 0;	// nombre d'itération ayant conduit à la meilleure solution jusque là
		
		List<List<Integer>> T = new ArrayList<List<Integer>>(); // liste taboue vide initialement, contient les déplacements tabous
		int tailleT=20; // la taille max de T est de 20
		
		List<List<List<Integer>>> N = new ArrayList<List<List<Integer>>>(); // espace N contenant les solutions voisines de s et leurs déplacement
		
		//MANQUE initialisation de la fonction d'aspiration A
		
		while (d>=dMin && nbIter-meilIter < nbMax) {
			nbIter ++;
			
			N=voisinGenerator(s);
			
			List<Integer> sAspire = Aspiration(s);
			int dAspire = distance(sAspire);
			
			List<Integer> sPrime1 = N.get(0).get(0);
			int dPrime1 = distance(sPrime1);
			List<Integer> mouvement1 = N.get(1).get(0);
			boolean aspired1 = false;
			for(int i=0;i<N.size();i++){ //On choisit la solution la plus courte de N' et qui est plus courte que la solution aspirée
				if(distance(N.get(0).get(i))<=dAspire && dPrime1<d){
					sPrime1=N.get(0).get(i);
					dPrime1=distance(sPrime1);
					mouvement1 = N.get(1).get(i);
				}
				else{ // si elle n'existe pas on aspire
					sPrime1=sAspire;
					dPrime1=dAspire;
					aspired1=true;
				}
			}
			
			List<Integer> sPrime2 = sAspire; //sPrime2 meilleure solution hors tabou
			int dPrime2 = dAspire;			//si toutes les solutions sont dans tabou on aspire
			List<Integer> mouvement2 = new ArrayList<Integer>();
			boolean aspired2 = true;
			boolean breaked = false;
			List<Integer> couple = new ArrayList<Integer>();
			for(int i=0;i<N.size();i++){
				couple = N.get(1).get(i);
				for(int j=0;j<T.size();j++){//initialisation de sPrime2 la meilleure solution hors tabou
					if(couple!=T.get(j) && breaked==false) {//si on est hors tabou et que l'initialisation n'est pas terminée
						sPrime2 = N.get(0).get(i);
						dPrime2 = distance(sPrime2);
						mouvement2 = N.get(1).get(i);
						aspired2 = false;
						breaked = true;//initialisation terminée
						break;
					}
				}
				if(breaked){//quand l'initialisation est terminée
					for(int j=0;j<T.size();j++){ //recherche de la solution minimale hors tabou
						if(couple!=T.get(j) && distance(N.get(0).get(i))<dPrime2){
							sPrime2 = N.get(0).get(i);
							dPrime2 = distance(N.get(0).get(i));
							mouvement2 = N.get(1).get(i);
						}
					}
				}
			}
			
			//Comparaison entre les deux solutions
						
			if(dPrime2<dPrime1){
				s=sPrime2;
				d=dPrime2;
				if(aspired2==false){
					T.add(mouvement2);
				}
			}
			else{
				s=sPrime1;
				d=dPrime1;
				if(aspired1==false){
					T.add(mouvement1);
				}
			}
			
			if(T.size()>tailleT){
				T.remove(0);
			}
			
			if(d<dMin){
				sMin=s;
				dMin=d;
				meilIter=nbIter;
				nbIter=0;
			}
		}
		//System.out.println("Solution finale");
		//AfficherS(sMin);
		//System.out.println("meil_iter : "+meilIter);
		
		//System.out.println(System.currentTimeMillis()-debut); // renvoie la durée du programme
		
		
		int[] sMinTab = toIntArray(sMin);
		Object[] solFinale = {sMinTab,dMin};
		return solFinale;
	}
	
	
	
	private static List<Integer> arrayToList(int[] sTab){
		List<Integer> sList = new ArrayList<Integer>();
		  for(int i = 0;i < sTab.length;i++) {
			   sList.add(sTab[i]);
		  }
		    
		return sList;
	}
	
	private static int[] toIntArray(List<Integer> list){
		
		  int[] ret = new int[list.size()];
		  for(int i = 0;i < ret.length;i++)
		    ret[i] = list.get(i);
		  return ret;
		}
	
	
	
	
//	public static int M [][] = { 
//			   {0, 300 ,352 ,466, 217, 238, 431, 336, 451 , 47, 415 ,515},    //Matrice des distances
//			   {300  , 0, 638, 180, 595 ,190, 138, 271, 229, 236, 214 ,393},  //pour aller d'une ville
//			   {352, 638,   0 ,251,  88, 401 ,189, 386, 565, 206, 292, 349},  //à une autre
//			   {466, 180, 251 ,  0, 139, 371, 169, 316, 180, 284, 206, 198},
//			   {217, 595 , 88 ,139  , 0 ,310, 211, 295, 474, 130 ,133, 165},
//			   {238, 190, 401, 371, 310 ,  0 ,202, 122, 378, 157 ,362, 542},
//			   {431 ,138 ,189, 169 ,211, 202 ,  0 ,183,  67, 268, 117, 369},
//			   {336, 271 ,386, 316, 295 ,122, 183 ,  0, 483 ,155, 448, 108},
//			   {451, 229, 565 ,180, 474 ,378 , 67, 483,   0 ,299, 246, 418},
//			   {47 ,236, 206 ,284 ,130, 157 ,268, 155 ,299 ,  0, 202, 327},
//			   {415, 214, 292 ,206, 133, 362 ,117 ,448, 246, 202,   0, 394},
//			   {515, 393, 349, 198, 165, 542, 368, 108, 418, 327, 394,   0}};
	
//	public static int M[][] = {{0,780,320,580,480,660},
//			 					{780,0,700,460,300,200},
//			 					{320,700,0,380,820,630},
//			 					{580,460,380,0,750,310},
//			 					{480,300,820,750,0,500},
//			 					{660,200,630,310,500,0}};
	

	
	
	//Création d'une solution admissible
		public static List<Integer> solAdm() {
			List<Integer> sol = new ArrayList<Integer>();
			for(int i=1; i<tailleM;  i++) {
				sol.add(i);
			}
			Collections.shuffle(sol);
			return sol;
		}

	
	//Attribue à un trajet la distance totale
	public static int distance(List<Integer> s ) {
		int tailleS = s.size();
		int d=M[s.get (0)][0];
		for (int i = 0; i< (tailleS - 1); i++) {
			d += M[s.get(i)][s.get(i+1)];
		}
		d += M[s.get(tailleS -1)][0];		
		return d;
	}
	
	//Fonction qui génère l'espace N qui contient les voisins de s ainsi que le déplacement nécéssaire pour y aller à partir de s
	public static List<List<List<Integer>>> voisinGenerator(List<Integer> s){
		// les voisins sont tous les solutions différentes où l'ont peut intervertir deux villes dans s
		List<List<List<Integer>>> N = new ArrayList<List<List<Integer>>>();
		List<List<Integer>> N0 = new ArrayList<List<Integer>>();
		List<List<Integer>> N1 = new ArrayList<List<Integer>>();
		int tailleS = s.size();
		
		for (int i=0;i< tailleS ;i++){
			for(int j=0;j<i;j++){
				if(i!=j){
					List<Integer> couple = new ArrayList<Integer>();
					couple.add(i); couple.add(j); //on enregistre les coordonées de chaque terme à échanger
					List<Integer> s2 = new ArrayList<Integer>();
					s2=s;
					int temp = s.get(i);
					s2.set(i, s.get(j));
					s2.set(j,temp);
					N0.add(s2); //N(0) est l'ensemble N' du cours N(1) est l'ensemble des déplacements de s à N(0)
					N1.add(couple);
				}
			}
		}
		N.add(N0);N.add(N1);
		return N;
	}

	public static void AfficherS(List<Integer> s){
		for (int i=0;i<s.size();i++){
			System.out.println(s.get(i));
		}		
		System.out.println(distance(s));
	}
	

	

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
	
	public static List<Integer> Aspiration(List<Integer> s){ // on mélange les trois éléments du milieu de s
		Collections.shuffle(s);
		return s;
	}

	
}