package tabou;

import java.util.ArrayList;
import java.util.*;
import general.Cities;

public class taboulet {
	
	public taboulet(Cities cities) {
		super(cities);
	}
	
	
	
	public int[][] M;
	public int tailleM;
	
	public class solution{
		public int[] tab = new int[(tailleM-1)];
		public int dist=distance(tab);
	}
	
	public Object[] tabou (Cities cities) {
		M = cities.get_dist();
		tailleM = cities.get_num();
		
		List<Integer> s = solAdm(); // cr�ation d'une solution admissible
		int d = distance(s); //distance de s
		
		List<Integer> sMin = s; //initialisation de la solution ayant la distance minimale finale
		int dMin = d;
		
		int nbMax = 100000; // nombre max d'it�ration dans la boucle de calcul
		int nbIter = 0; // compteur du nombre d'it�ration
		int meilIter = 0;	// nombre d'it�ration ayant conduit � la meilleure solution jusque l�
		
		List<List<Integer>> T = new ArrayList<List<Integer>>(); // liste taboue vide initialement, contient les d�placements tabous
		int tailleT=20; // la taille max de T est de 20
		
		List<List<List<Integer>>> N = new ArrayList<List<List<Integer>>>(); // espace N contenant les solutions voisines de s et leurs d�placement
		
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
			for(int i=0;i<N.size();i++){ //On choisit la solution la plus courte de N' et qui est plus courte que la solution aspir�e
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
					if(couple!=T.get(j) && breaked==false) {//si on est hors tabou et que l'initialisation n'est pas termin�e
						sPrime2 = N.get(0).get(i);
						dPrime2 = distance(sPrime2);
						mouvement2 = N.get(1).get(i);
						aspired2 = false;
						breaked = true;//initialisation termin�e
						break;
					}
				}
				if(breaked){//quand l'initialisation est termin�e
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
		
		//System.out.println(System.currentTimeMillis()-debut); // renvoie la dur�e du programme
		
		
		int[] sMinTab = toIntArray(sMin);
		Object[] solFinale = {sMinTab,dMin};
		return solFinale;
	}
	
	public Object[] tabou (Cities cities, int[] sTab) {
	
		M = cities.get_dist();
		tailleM = cities.get_num();
		
		//long debut = System.currentTimeMillis(); // pour mesurer la dur�e d'execution du programme
		
		List<Integer> s = arrayToList(sTab); // cr�ation d'une solution admissible
		int d = distance(s); //distance de s
		
		List<Integer> sMin = s; //initialisation de la solution ayant la distance minimale finale
		int dMin = d;
		
		int nbMax = 100000; // nombre max d'it�ration dans la boucle de calcul
		int nbIter = 0; // compteur du nombre d'it�ration
		int meilIter = 0;	// nombre d'it�ration ayant conduit � la meilleure solution jusque l�
		
		List<List<Integer>> T = new ArrayList<List<Integer>>(); // liste taboue vide initialement, contient les d�placements tabous
		int tailleT=20; // la taille max de T est de 20
		
		List<List<List<Integer>>> N = new ArrayList<List<List<Integer>>>(); // espace N contenant les solutions voisines de s et leurs d�placement
		
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
			for(int i=0;i<N.size();i++){ //On choisit la solution la plus courte de N' et qui est plus courte que la solution aspir�e
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
					if(couple!=T.get(j) && breaked==false) {//si on est hors tabou et que l'initialisation n'est pas termin�e
						sPrime2 = N.get(0).get(i);
						dPrime2 = distance(sPrime2);
						mouvement2 = N.get(1).get(i);
						aspired2 = false;
						breaked = true;//initialisation termin�e
						break;
					}
				}
				if(breaked){//quand l'initialisation est termin�e
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
		
		//System.out.println(System.currentTimeMillis()-debut); // renvoie la dur�e du programme
		
		
		int[] sMinTab = toIntArray(sMin);
		Object[] solFinale = {sMinTab,dMin};
		return solFinale;
	}
	
	
	
	private List<Integer> arrayToList(int[] sTab){
		List<Integer> sList = new ArrayList<Integer>();
		  for(int i = 0;i < sTab.length;i++) {
			   sList.add(sTab[i]);
		  }
		    
		return sList;
	}
	
	private int[] toIntArray(List<Integer> list){
		
		  int[] ret = new int[list.size()];
		  for(int i = 0;i < ret.length;i++)
		    ret[i] = list.get(i);
		  return ret;
		}
	
	//Cr�ation d'une solution admissible
		public List<Integer> solAdm() {
			List<Integer> sol = new ArrayList<Integer>();
			for(int i=1; i<tailleM;  i++) {
				sol.add(i);
			}
			Collections.shuffle(sol);
			return sol;
		}

	
	//Attribue � un trajet la distance totale
	public int distance(int[] s) {
		int tailleS = s.length;
		int d=M[s.get (0)][M.];
		for (int i = 0; i< (tailleS - 1); i++) {
			d += M[s.get(i)][s.get(i+1)];
		}
		d += M[s.get(tailleS -1)][0];		
		return d;
	}
	
	//Fonction qui g�n�re l'espace N qui contient les voisins de s ainsi que le d�placement n�c�ssaire pour y aller � partir de s
	public List<List<List<Integer>>> voisinGenerator(List<Integer> s){
		// les voisins sont tous les solutions diff�rentes o� l'ont peut intervertir deux villes dans s
		List<List<List<Integer>>> N = new ArrayList<List<List<Integer>>>();
		List<List<Integer>> N0 = new ArrayList<List<Integer>>();
		List<List<Integer>> N1 = new ArrayList<List<Integer>>();
		int tailleS = s.size();
		
		for (int i=0;i< tailleS ;i++){
			for(int j=0;j<i;j++){
				if(i!=j){
					List<Integer> couple = new ArrayList<Integer>();
					couple.add(i); couple.add(j); //on enregistre les coordon�es de chaque terme � �changer
					List<Integer> s2 = new ArrayList<Integer>();
					s2=s;
					int temp = s.get(i);
					s2.set(i, s.get(j));
					s2.set(j,temp);
					N0.add(s2); //N(0) est l'ensemble N' du cours N(1) est l'ensemble des d�placements de s � N(0)
					N1.add(couple);
				}
			}
		}
		N.add(N0);N.add(N1);
		return N;
	}

	public void AfficherS(List<Integer> s){
		for (int i=0;i<s.size();i++){
			System.out.println(s.get(i));
		}		
		System.out.println(distance(s));
	}
	

	

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
	
	public List<Integer> Aspiration(List<Integer> s){ // on m�lange les trois �l�ments du milieu de s
		Collections.shuffle(s);
		return s;
	}

	
}