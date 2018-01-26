package mesAgents;

import jade.core.Agent;

import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import tabou.taboulet;
import genetique.Problem;
import genetique.Individual;
import general.Cities;

@SuppressWarnings("serial")
public class AgentOptimisation extends Agent{
	
	public static String[] args;
	public static int cout;
	public static int [] meilleureSol;
	public static Cities cities = new Cities();
	
	public class optimisationInitialisation extends OneShotBehaviour{
		public optimisationInitialisation(Agent a) {
			super(a);
		}
		public void action() {

//			Argument 0 fournit � cet agent d�finit le type d'algorithme d'optimisation
			String type = args[0];
			
			switch(type) {
				case"tabou":{
//					Ici on ex�cute le main de tabou
					Object[] best = taboulet.tabou(cities);
					meilleureSol =(int[])best[0];
					cout = (int)best[1];
					break;
				}
				case"recuit":{
//					Ici on ex�cute le main de recuit
					
					break;
				}
				case"genetique":{
//					Ici on ex�cute le main de genetique
					Individual best = Problem.principal(cities);
					meilleureSol = best.get_chromosome();
					cout = best.get_cost();
					break;
				}
			}
			
		}
		public int onEnd() {
			return 1;
		}
	}
	
	public class optimisation extends OneShotBehaviour{
		public optimisation(Agent a) {
			super(a);
		}
		public void action() {

//			Argument 0 fournit � cet agent d�finit le type d'algorithme d'optimisation
			String type = args[0];
			
			switch(type) {
				case"tabou":{
//					Ici on ex�cute le main de tabou
					Object[] best = taboulet.tabou(cities, meilleureSol);
					meilleureSol =(int[])best[0];
					cout = (int)best[1];
					break;
				}
				case"recuit":{
//					Ici on ex�cute le main de recuit
					break;
				}
				case"genetique":{
//					Ici on ex�cute le main de genetique
					Individual best = Problem.principal(cities, meilleureSol);
					meilleureSol = best.get_chromosome();
					cout = best.get_cost();
					break;
				}
			}
			
		}
		public int onEnd() {
			return 1;
		}
	}
	
	public class ecouteBehaviour extends OneShotBehaviour{
		public ecouteBehaviour(Agent a) {
			super(a);
		}
		public void action() {
			int nbMessages = 0;
			String[] contenu = new String[2];
			while(nbMessages<2) {
				ACLMessage msg = myAgent.receive();
				if(msg != null) {
					contenu[nbMessages] = msg.getContent();
					nbMessages++;
					
				}else {
					block();
				}
			}
		}
	}

	protected void setup() {
		Object[] argsTemp = getArguments();
//		Cr�� un cha�ne vide de la taille du nombre d'arguments
		for(int i=0;i<argsTemp.length;i++) {
			args[i]=(String) argsTemp[i];
		}

		optimisationInitialisation algorithmeDOptimisation = new optimisationInitialisation(this);
		ecouteBehaviour ecoute = new ecouteBehaviour(this);
		
		FSMBehaviour order = new FSMBehaviour(this);
//		Argument 1 fournit � cet agent d�finit s'il doit s'ex�cuter en premier
		String first = args[1];
		if(first.equals("first")) {
			order.registerFirstState(algorithmeDOptimisation, "optimisation");
		}
	}
}
