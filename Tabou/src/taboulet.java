import java.util.*;
import general.Cities;

public class taboulet {

	private Cities city;
	private int[][] M;
	private int tailleM;
	// nombre max d'itération dans la boucle de calcul
	public int nbMax = 1000;
	// compteur du nombre d'itération
	public int nbIter = 0;
	// nombre d'itération ayant conduit à la meilleure solution jusque là
	public int meilIter = 0;
	// taille de la liste tabou, à déterminer expérimentalement (entre 7 et 20)
	// (doit être impair cf ligne 188)
	// strictement supérieur à 2
	public int tailleDeT = 10;
	// le nombre de voisin d'une solution s
	private int nombreDeVoisin;

	private class solution {
		// tableau contenant l'ordre des villes
		public int[] tab = new int[(tailleM - 1)];
		// distance totale du trajet
		public int dist;

		// génère une solution admissible aléatoire
		public void generation() {
			int i = 0;
			for (int t : city.get_index_list()) {
				if (t != city.get_start_city()) {
					tab[i] = t;
					i++;
				}
			}
			// on mélange complètement et aléatoirement la liste
			tab = shuffleTab(tab).clone();
			dist = distance(tab);
		}

		// créé une solution similaire à la solution s
		public void clone(solution s) {
			if (this.tab == null) {
				this.generation();
			}
			this.dist = s.dist;
			this.tab = s.tab.clone();
		}

		// déplacement en echangeant la ième ville et la jème ville
		public solution deplacement(int i, int j) {
			solution s = new solution();
			s.clone(this);
			int temp = s.tab[i];
			s.tab[i] = s.tab[j];
			s.tab[j] = temp;
			return s;
		}
	}

	// mélange un tableau d'entier
	private static int[] shuffleTab(int[] s) {
		/* shuffles a list */
		int n = s.length;
		Random random = new Random();
		random.nextInt();
		for (int i = 0; i < n; i++) {
			int change = i + random.nextInt(n - i);
			// on échange deux éléments
			int helper = s[i];
			s[i] = s[change];
			s[change] = helper;
		}

		return s;
	}

	// constructeur de la liste tabou
	private class T {
		private int tailleT;

		private T(int tailleT) {
			this.tailleT = tailleT;
			this.solutionBase = new solution[tailleT];
			this.deplacementInterdit = new int[tailleT][2];
		}

		// les solutions par lequelles on est passé
		public solution[] solutionBase = new solution[tailleT];
		// les déplacement qu'on a fait en partant de ces solutions
		public int[][] deplacementInterdit = new int[tailleT][2];

		// ajout d'un déplacement tabou
		public void addTabou(solution s, int[] deplacement) {
			// si la liste n'est pas encore pleine
			if (solutionBase[tailleT - 1] == null) {
				for (int i = 0; i < tailleT; i++) {
					if (solutionBase[i] == null) {
						solutionBase[i] = s;
						deplacementInterdit[i] = deplacement.clone();
						break;
					}
				}
			}
			// si elle est pleine on réautorise le plus ancien déplacement
			// et on ajoute le plus récents
			else {
				for (int i = 0; i < tailleT - 2; i++) {
					solutionBase[i].clone(solutionBase[(i + 1)]);
					deplacementInterdit[i] = deplacementInterdit[(i + 1)].clone();
				}
				solutionBase[tailleT - 1] = s;
				deplacementInterdit[tailleT - 1] = deplacement.clone();
			}

		}

		// On vérifie que ce déplacement est tabou
		public boolean isInterdit(solution s, int[] deplacement) {
			for (int i = 0; i < tailleT; i++) {
				if (solutionBase[i] != null) {
					if (solutionBase[i].equals(s) && deplacementInterdit[i].equals(deplacement)) {
						return true;
					}
				}
			}
			return false;
		}

		// On vérifie si on est déjà passé par cette solution
		public int nombreDePassage(solution s) {
			int compt = 0;
			for (int i = 0; i < tailleT; i++) {
				if (solutionBase[i] != null) {
					if (solutionBase[i].equals(s)) {
						compt++;
					}
				}
			}
			return compt;
		}

		public solution bestSolution() {
			int i = 0;
			solution sol = solutionBase[0];
			while (solutionBase[i] != null && i < tailleT) {
				if (solutionBase[i].dist < sol.dist) {
					sol.clone(solutionBase[i]);
				}
				i++;
			}
			return sol;
		}

	}

	// calcule la distance totale d'une solution s en partant de la ville de départ
	// définie dans Cities
	public int distance(int[] s) {
		int S = M[city.get_start_city()][s[0]];
		for (int i = 0; i < s.length - 1; i++) {
			S += M[s[i]][s[i + 1]];
		}
		S += M[s[s.length - 1]][city.get_start_city()];
		return S;
	}

	private void nbCombinaison(solution s) {
		int compt = 0;
		for (int i = 1; i < s.tab.length; i++) {
			for (int j = 0; j < i; j++) {
				compt++;
			}
		}
		nombreDeVoisin = compt;
	}

	private class voisin {
		private solution depart;
		public solution[] voisins = new solution[nombreDeVoisin];
		public int[][] deplacements = new int[nombreDeVoisin][2];

		public voisin(solution s) {
			this.depart = s;
			int compt = 0;
			for (int i = 1; i < depart.tab.length; i++) {
				for (int j = 0; j < i; j++) {
					voisins[compt] = depart.deplacement(i, j);
					deplacements[compt][0] = i;
					deplacements[compt][1] = j;
					compt++;
				}
			}
		}
	}

	private solution algo(solution s) {

		// Initialisation de la liste tabou
		T TabouTab = new T(tailleDeT);
		solution bestSol = new solution();
		bestSol.clone(s);
		solution tempSol = new solution();
		tempSol.clone(bestSol);

		while (nbIter < nbMax) {
			nbIter++;
			voisin Voisin = new voisin(tempSol);
			// recherche du minimum dans tabou, si tous les chemins sont dans tabou on
			// aspire => impossible taille de T trop faible
			// si on repasse trop de fois par le même chemin (longue boucle par rapport à
			// taille T on aspire) => minimum local
			for (int i = 0; i < nombreDeVoisin; i++) {
				// il existe au moins un voisin hors tabou ! Calcul du minimum initialisé
				if (!TabouTab.isInterdit(Voisin.voisins[i], Voisin.deplacements[i])) {
					solution vois = new solution();
					vois = Voisin.voisins[i];
					int ind = i;
					// on cherche le minimum hors tabou
					for (int j = 0; j < nombreDeVoisin; j++) {
						if (!TabouTab.isInterdit(Voisin.voisins[j], Voisin.deplacements[j])
								&& vois.dist > Voisin.voisins[j].dist) {
							vois.clone(Voisin.voisins[j]);
							ind = j;
						}
					}
					// mise à jour de la liste tabou
					TabouTab.addTabou(tempSol, Voisin.deplacements[ind]);
					tempSol.clone(vois);
					// cette solution est elle meilleure ?
					if (tempSol.dist < bestSol.dist) {
						bestSol.clone(tempSol);
					}
					if (TabouTab.nombreDePassage(tempSol) == (tailleDeT / 2)) {
						System.out.println("Aspiration");
						// on génère une nouvelle solution car on est passe trop de fois dans cette
						// solution (minimum local), on tourne en boucle
						// Pour améliorer l'algo on pourrait changer cette fonction d'aspiration,
						// prendre la meilleure dans tabou ?
						// tempSol.generation();
						tempSol.clone(TabouTab.bestSolution());
						if (tempSol.dist < bestSol.dist) {
							bestSol.clone(tempSol);
						}
					}
					break;
				}
			}

		}
		return bestSol;
	}

	public int[] tabou(Cities cities) {

		// initialisation des données du problème
		city = cities;
		M = city.get_dist();
		tailleM = city.get_num();

		// création d'une solution admissible
		solution s = new solution();
		// créé une solution aléatoire
		s.generation();
		// distance de s
		s.dist = distance(s.tab);
		// définit le nombre de voisin pour une solution S
		nbCombinaison(s);

		// Initialisation de la meilleure solution
		solution bestSol = algo(s);
		return bestSol.tab;

	}

	public int[] tabou(Cities cities, int[] sTab) {

		// initialisation des données du problème
		city = cities;
		M = city.get_dist();
		tailleM = city.get_num();

		// création d'une solution admissible
		solution s = new solution();
		// créé une solution aléatoire
		s.tab = sTab.clone();
		// distance de s
		s.dist = distance(s.tab);
		// définit le nombre de voisin pour une solution S
		nbCombinaison(s);

		// Initialisation de la meilleure solution
		solution bestSol = algo(s);
		return bestSol.tab;
	}
}