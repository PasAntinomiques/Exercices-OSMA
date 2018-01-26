import java.util.*;

public class Main {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		List<List<Integer>> sOpt = new ArrayList<List<Integer>>();
		int dOpt = 9000; //distance de s
		
		List<Integer> meilIterOpt = new ArrayList<Integer>();	// nombre d'itération ayant conduit à la meilleure solution jusque là
		
		for(int k = 0;k<2;k++){
			
			List<Integer> s = solAdm(); // création d'une solution admissible
			int d = distance(s); //distance de s
			
			List<Integer> sMin = s; // création d'une solution admissible
			int dMin = d; //distance de s
			
			sMin = s; //initialisation de la solution ayant la distance minimale finale
			dMin = d;
			
			int nbMax = 100000; // nombre max d'itération dans la boucle de calcul
			int nbIter = 0; // compteur du nombre d'itération
			int meilIter = 0;
			
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
			sOpt.add(sMin);
			meilIterOpt.add(meilIter);
		}	
		
		List<Integer> s = sOpt.get(0);
		int meilIter = 0;
		for(int i=1;i<sOpt.size();i++){
			if(distance(s)>distance(sOpt.get(i))){
				s=sOpt.get(i);
				meilIter = meilIterOpt.get(i);
			}
		}
		System.out.println("Solution finale");
		AfficherS(s);
		System.out.println("meil_iter : "+meilIter);
		
	}
	
	public static String [] nomVille = {"B","L","N","P","M","D"};
	
	public static int M[][] = {{0,780,320,580,480,660},
			 					{780,0,700,460,300,200},
			 					{320,700,0,380,820,630},
			 					{580,460,380,0,750,310},
			 					{480,300,820,750,0,500},
			 					{660,200,630,310,500,0}};
	
	//Création d'une solution admissible
	public static List<Integer> solAdm() {
		List<Integer> sol = new ArrayList<Integer>();
		sol.add(1);
		sol.add(2);
		sol.add(3);
		sol.add(4);
		sol.add(5);
		Collections.shuffle(sol);
		return sol;
	}

	
	//Attribue à un trajet la distance totale
	public static int distance(List<Integer> s ) {
		int d=M[s.get (0)][0];
		for (int i = 0; i<4; i++) {
			d += M[s.get(i)][s.get(i+1)];
		}
		d += M[s.get(4)][0];		
		return d;
	}
	
	//Fonction qui génère l'espace N qui contient les voisins de s ainsi que le déplacement nécéssaire pour y aller à partir de s
	public static List<List<List<Integer>>> voisinGenerator(List<Integer> s){
		// les voisins sont tous les solutions différentes où l'ont peut intervertir deux villes dans s
		List<List<List<Integer>>> N = new ArrayList<List<List<Integer>>>();
		List<List<Integer>> N0 = new ArrayList<List<Integer>>();
		List<List<Integer>> N1 = new ArrayList<List<Integer>>();
		
		for (int i=0;i<4;i++){
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
	
	//Fonction aspiration
	public static List<Integer> Aspiration(List<Integer> s){ // on mélange les trois éléments du milieu de s
		//List<Integer> threeS = new ArrayList<Integer>();
		//threeS.add(s.get(2));threeS.add(s.get(3));threeS.add(s.get(4));
		//java.util.Collections.shuffle(threeS);
		//s.set(2, threeS.get(0));
		//s.set(3, threeS.get(1));
		//s.set(4, threeS.get(2));
		Collections.shuffle(s);
		return s;
	}
	

}
