package tabou;
import java.util.ArrayList;


public class Voisin {
	
	Solution solVoisinne;
	int déplacement;
	
	public Voisin (){
		this.solVoisinne=null;
		this.déplacement=-1;
	}
	
	// On passe d'une solution à une solution voisinne par une transposition de deux villes adjacentes t=(i i+1) 
	// Pour mémoriser le déplacement on mémorise i
	// cela permet d'avoir seulement n voisins pour un trajet de longueur n
	
	public static ArrayList<Voisin> ensembleDesVoisins (Solution sol) {
		ArrayList<Voisin> ensVoisins = new ArrayList<Voisin> ();
		for (int i=0; i<sol.parcours.size()-1;i++ ) {
			Voisin voisinSol = new Voisin();
			ArrayList<Integer> trajetVoisin = sol.parcours;
			
			int temp=sol.parcours.get(i+1) ;
			trajetVoisin.set(i+1, trajetVoisin.get(i));
			trajetVoisin.set(i, temp);
			
			Solution solVoisinne = new Solution(trajetVoisin);
			voisinSol.solVoisinne = solVoisinne;
			voisinSol.déplacement = i;
			ensVoisins.add(voisinSol);
		}
		
		// Cas où il faut intervertir le premier élément de la liste avec le dernier
		Voisin voisinSol = new Voisin();
		ArrayList<Integer> trajetVoisin = sol.parcours;
		
		int temp=sol.parcours.get(0) ;
		trajetVoisin.set(0, trajetVoisin.get(trajetVoisin.size()-1));
		trajetVoisin.set(trajetVoisin.size()-1, temp);
		
		Solution solVoisinne = new Solution(trajetVoisin);
		voisinSol.solVoisinne = solVoisinne;
		voisinSol.déplacement = sol.parcours.size()-1;
		ensVoisins.add(voisinSol);
		
		return ensVoisins;
	}
		//cette fonction va renvoyer la solution qui minimise la fonction distance et qui n'est pas dans la liste tabou
		public static Voisin meilleureSol (ArrayList<Voisin> ensVois, ArrayList<Integer> listeT) {
			Voisin meilVoisin = new Voisin();
			int dmin = ensVois.get(0).solVoisinne.distance;
			for(int i=0; i<ensVois.size(); i++) {
				if (dmin < ensVois.get(i).solVoisinne.distance && !(listeT.contains(ensVois.get(i).déplacement))) {
					meilVoisin.solVoisinne =ensVois.get(i).solVoisinne;
					dmin=meilVoisin.solVoisinne.distance;
				}
			}
			return meilVoisin;
		}
	
		// cette fonction renvoie la solution qui minimise la fonction aspiration
		public static Voisin meilleureSolAspiration (ArrayList<Voisin> ensVois) {
			Voisin meilVoisin = new Voisin();
			int dmin = ensVois.get(0).solVoisinne.distance;
			for(int i=0; i<ensVois.size(); i++) {
				if (dmin <= ensVois.get(i).solVoisinne.distance ) {
					meilVoisin.solVoisinne =ensVois.get(i).solVoisinne;
					dmin=meilVoisin.solVoisinne.distance;
				}
			}
			return meilVoisin;
		}
}
