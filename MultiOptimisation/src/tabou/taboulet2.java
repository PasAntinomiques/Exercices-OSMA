package tabou;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import general.Cities;
 

public class taboulet2 {
	//cette classe ne permet que de faire des tests sur l'algorithme tabou qui lui est codé dans taboulet 
	
	public int[] tabou(Cities cities) {
		// TODO Auto-generated method stub
//		System.out.println(taboulet.tabou());
//		List<List<Integer>> L =new ArrayList<List<Integer>>();
//		
//		// n correspond aux nombre de tests effectués
//		
//		int n = 1;
//		List<Integer> dmin = new ArrayList<Integer>();
//		//on lance l'algorithme tabou n fois et on stocke les résultas dans la liste dmin 
//		for(int i=0; i<n; i++ ) {
//			long debut = System.currentTimeMillis();
//			dmin.add(taboulet.tabou());
//			System.out.println(System.currentTimeMillis()-debut);
//		}
//		// le but de cette boucle est de compter le nombre d'occurence de chaque distance renvoyée par l'algorithme tabou
//		for (int j=0; j<n; j++) {
//			L.add(new ArrayList<Integer>());
//			L.get(j).add(dmin.get(j));
//			System.out.println(j);
//			L.get(j).add(Collections.frequency(dmin, dmin.get(j)));
//		}
//		enlever_doublons(L);
//		AfficherListeCouple(L);
		int[] p = taboulet.tabou();
		return p;
	}
	
	public int[] tabou(Cities cities, int[] s) {
		int[] p = taboulet.tabou(s);
		return p;
	}
	
		
		public static void AfficherCouple(List<Integer> c) {
			System.out.print(c.get(0));
			System.out.print("   ");
			System.out.println(c.get(1));
		}
		 public static void AfficherListeCouple(List<List<Integer>> L) {
			 for (int i=0; i<L.size(); i++) {
				 AfficherCouple(L.get(i));
			 }
		 }

		public static void enlever_doublons (List<List<Integer>> L){
			for (int i=0; i<L.size(); i++) {
				List<Integer> l = L.get(0);
				for (int j=1; j<L.size(); j++) {
					if (L.get(j).get(0)==l.get(0)) {
						L.remove(j);
					}
				}
			}
		}

}
