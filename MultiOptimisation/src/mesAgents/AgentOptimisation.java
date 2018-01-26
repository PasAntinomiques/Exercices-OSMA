package mesAgents;

import jade.core.AID;

import jade.core.Agent;


import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import tabou.taboulet;
import genetique.Problem;
import genetique.Individual;
import general.Cities;
import recuit.recuit;

@SuppressWarnings("serial")
public class AgentOptimisation extends Agent{
	
	private static int nombreDeCorrespondants;
	private static Object[] args;	
	private static int cout;
	private static int [] meilleureSol;
	private static Cities cities = new Cities();
	private static int[][] M = cities.get_dist();
	private static int nbIt = 0;
	private static int nbItMax;
	
	
	private class optimisationInitialisation extends OneShotBehaviour{
		public optimisationInitialisation(Agent a) {
			super(a);
		}
		public void action() {
			
//			Argument 0 fournit à cet agent définit le type d'algorithme d'optimisation
			String type = (String)args[0];
			
			switch(type) {
				case"tabou":{
//					Ici on exécute le main de tabou
					Object[] best = taboulet.tabou(cities);
					meilleureSol =(int[])best[0];
					cout = (int)best[1];
					break;
				}
				case"recuit":{
//					Ici on exécute le main de recuit
					Object[] best = recuit.recuitMain(cities);
					meilleureSol =(int[])best[0];
					cout = (int)best[1];
					break;
				}
				case"genetique":{
//					Ici on exécute le main de genetique
					Individual best = Problem.principal(cities);
					meilleureSol = best.get_chromosome();
					cout = best.get_cost();
					break;
				}
			}
		}
		public int onEnd() {
			nbIt++;
			return 1;
		}
	}
	
	private class optimisation extends OneShotBehaviour{
		private optimisation(Agent a) {
			super(a);
		}
		public void action() {
			String type = (String)args[0];
			switch(type) {
				case"tabou":{
//					Ici on exécute le main de tabou
					Object[] best = taboulet.tabou(cities, meilleureSol);
					meilleureSol =(int[])best[0];
					cout = (int)best[1];
					break;
				}
				case"recuit":{
//					Ici on exécute le main de recuit
					Object[] best = recuit.recuitMain(cities, meilleureSol);
					meilleureSol =(int[])best[0];
					cout = (int)best[1];
					break;
				}
				case"genetique":{
//					Ici on exécute le main de genetique
					Individual best = Problem.principal(cities, meilleureSol);
					meilleureSol = best.get_chromosome();
					cout = best.get_cost();
					break;
				}
			}
			
		}
		public int onEnd() {
			nbIt++;
			return 1;
		}
	}
	
	private class envoieBehaviour extends OneShotBehaviour{
		private envoieBehaviour(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Agent "+getLocalName()+" : Processing done, sending result : "+cout);
			ACLMessage result = new ACLMessage(ACLMessage.INFORM);
			for(int i=2;i<args.length;i++) {
				result.addReceiver(new AID((String)args[i],AID.ISLOCALNAME));
			}
			String content = Integer.toString(meilleureSol[0]);
			for(int i=1;i<meilleureSol.length;i++) {
				content += " ";
				content += Integer.toString(meilleureSol[i]);
			}
			result.setContent(content);
			myAgent.send(result);
		}
		public int onEnd() {
			return 1;
		}
	}

	
	private class ecouteBehaviour extends OneShotBehaviour{
		private ecouteBehaviour(Agent a) {
			super(a);
		}
		public void action() {
			int nbMessages = 0;
			int[][] resultats = new int[(nombreDeCorrespondants+1)][];
			int[] coutResultats = new int[(nombreDeCorrespondants+1)];
			resultats[0] = meilleureSol;
			coutResultats[0] = distance(meilleureSol); 
			String contenu;
			while(nbMessages<nombreDeCorrespondants) {
				ACLMessage msg = myAgent.receive();
				if(msg != null) {
					if(msg.getSender().getName()!=myAgent.getName()) {
						System.out.println("Agent "+myAgent.getLocalName()+" : Receiving results from : "+msg.getSender().getLocalName());
						contenu = msg.getContent();
						
//						Processing content
						String subContent ="";
						int k=0;
						for(int i=0;i<contenu.length();i++) {
							if(String.valueOf(contenu.charAt(i))!=" ") {
								subContent +=String.valueOf(contenu.charAt(i));  
							}else {
								resultats[(nbMessages+1)][k]=Integer.parseInt(subContent);
								k=k+1;
								subContent="";
							}
						}
						nbMessages++;
						System.out.print(nbMessages+" et "+ nombreDeCorrespondants);
					}
					else {
						block();
					}
				}else {
					block();
				}
			}
			System.out.println("Balise 1");
			if(nbMessages == nombreDeCorrespondants) {
//				System.out.println(resultats[0].length);
//				System.out.println(resultats[1].length);
//				System.out.println(resultats[2].length);
				int temp = distance(resultats[0]);
				int indTemp = 0;
				for(int i=1;i<resultats.length;i++) {
			 		if(temp>distance(resultats[i])) {
			 			temp = distance(resultats[i]);
			 			indTemp = i;
			 		}
			 	}
				System.out.println("Agent "+getLocalName()+" : Choosing : "+temp);
				meilleureSol = resultats[indTemp];
				cout = temp;
			}
		}
		public int onEnd() {
			if(nbIt>nbItMax) {
				return 0;
			}else {
				return 1;
			}
		}
	}
	
	protected class terminateBehaviour extends OneShotBehaviour{
		public terminateBehaviour(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Agent "+getLocalName()+" : Solution finale : "+meilleureSol);
			System.out.println("Agent "+getLocalName()+" : Distance trouvée : "+cout);
		}
	}
	
	private int distance(int[] s) {
		int S = M[0][s[0]];
		for(int i=0;i<s.length-1;i++) {
			S += M[s[i]][s[i+1]];
		}
		S += M[s[s.length-1]][0];
		return S;
	}
	protected void setup() {
		
		args = getArguments();
		nbItMax = (int)args[1];
//		Convetit les arguments en chaîne de caractères
		nombreDeCorrespondants=args.length-2;
		System.out.println("Agent "+getLocalName()+" : Online ! Communicating with : "+nombreDeCorrespondants);

		
		
		FSMBehaviour order = new FSMBehaviour(this);
		order.registerFirstState(new optimisationInitialisation(this), "initialisation");
		order.registerState(new envoieBehaviour(this), "envoie");
		order.registerState(new ecouteBehaviour(this), "ecoute");
		order.registerState(new optimisation(this), "optimisation");
		order.registerLastState(new envoieBehaviour(this), "finish");
		
		order.registerTransition("initialisation", "envoie", 1);
		order.registerTransition("envoie", "ecoute", 1);
		order.registerTransition("ecoute", "optimisation", 1);
		order.registerTransition("optimisation", "envoie", 1);
		order.registerTransition("ecoute", "finish", 0);
		addBehaviour(order);

	}
}
