package mesAgents;

import jade.core.AID;
import jade.core.Agent;


import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import tabou.taboulet;
import genetique.Problem;
import genetique.Individual;
import java.io.IOException;

import general.Cities;
import recuit.Recuit;

@SuppressWarnings("serial")
public class AgentOptimisation extends Agent{
	
	private int nombreDeCorrespondants;
	private Object[] args;
	private Cities cities = new Cities();
	private int[][] M = cities.get_dist();
	private int nbIt = 0;
	private int nbItMax;
	private int cout;
	private int [] instantSol=new int[cities.get_num()-1];
	private int [] meilleureSol=new int[cities.get_num()-1];
	
	
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
					taboulet t = new taboulet();
					instantSol = t.tabou(cities);
					cout = distance(instantSol);
					meilleureSol=instantSol.clone();			
					break;
				}
				case"recuit":{
//					Ici on exécute le main de recuit
					instantSol=Recuit.recuit(cities);
					cout = distance(instantSol);
					meilleureSol=instantSol.clone();		
					break;
				}
				case"genetique":{
//					Ici on exécute le main de genetique
					Individual best = Problem.principal(cities);
					instantSol=best.get_chromosome();
					cout = distance(instantSol);
					meilleureSol=instantSol.clone();		
					break;
				}
			}
			System.out.println("Agent "+getLocalName()+" : Processing done, sending result : "+cout);
			ACLMessage result = new ACLMessage(ACLMessage.INFORM);
			for(int i=2;i<args.length;i++) {
				result.addReceiver(new AID((String)args[i],AID.ISLOCALNAME));
			}
			try {
				result.setContentObject(instantSol);
			} catch (IOException e) {
				e.printStackTrace();
			}
			myAgent.send(result);
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
//					Ici on exécute le main de tabou en partant d'une solution admissible
					taboulet t = new taboulet();
					instantSol = t.tabou(cities, instantSol);
					cout = distance(instantSol);
					break;
				}
				case"recuit":{
//					Ici on exécute le main de recuit en partant d'une solution admissible
					instantSol=Recuit.recuit(cities, instantSol);
					cout = distance(instantSol);
					break;
				}
				case"genetique":{
//					Ici on exécute le main de genetique en partant d'une solution admissible
					Individual best = Problem.principal(cities, instantSol);
					instantSol=best.get_chromosome();
					cout = distance(instantSol);
					break;
				}
			}
			System.out.println("Agent "+getLocalName()+" : Processing done, sending result : "+cout);
			ACLMessage result = new ACLMessage(ACLMessage.INFORM);
			for(int i=2;i<args.length;i++) {
				result.addReceiver(new AID((String)args[i],AID.ISLOCALNAME));
			}
			try {
				result.setContentObject(instantSol);
			} catch (IOException e) {
				e.printStackTrace();
			}
			myAgent.send(result);
			nbIt++;
			
		}
		public int onEnd() {
			nbIt++;
			return 1;
		}
	}

	
	private class ecouteBehaviour extends OneShotBehaviour{
		private ecouteBehaviour(Agent a) {
			super(a);
		}
		public void action() {
			int nbMessages = 0;
			int[][] resultats = new int[(nombreDeCorrespondants+2)][];
			resultats[0]=instantSol;
			resultats[1]=meilleureSol;
			while(nbMessages<nombreDeCorrespondants) {
				ACLMessage msg = myAgent.receive();
				if(msg != null) {
					try {
						resultats[(nbMessages+2)]=(int[])msg.getContentObject();
//						System.out.println("Agent "+myAgent.getLocalName()+" : Receiving results from : "+msg.getSender().getLocalName());
					} catch (UnreadableException e) {
						e.printStackTrace();
					}
					nbMessages++;
				}else {
					block();
				}
			}
//			Fonction choix
			if(nbMessages == nombreDeCorrespondants) {
				int temp = distance(resultats[0]);
				int indTemp = 0;
				for(int i=1;i<resultats.length;i++) {
			 		if(temp>distance(resultats[i])) {
			 			temp = distance(resultats[i]);
			 			indTemp = i;
			 		}
			 	}
				System.out.println("Agent "+getLocalName()+" : Choosing : "+temp);
				instantSol=resultats[indTemp].clone();
				meilleureSol=instantSol.clone();
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
		int S = M[cities.get_start_city()][s[0]];
		for(int i=0;i<s.length-1;i++) {
			S += M[s[i]][s[i+1]];
		}
		S += M[s[s.length-1]][cities.get_start_city()];
		return S;
	}
	
	protected void setup() {
		args = this.getArguments();
		nbItMax = (int)args[1];
//		Convetit les arguments en chaîne de caractères
		nombreDeCorrespondants=args.length-2;
		System.out.println("Agent "+getLocalName()+" : Online ! Communicating with : "+nombreDeCorrespondants);		
		
		FSMBehaviour order = new FSMBehaviour(this);
		order.registerFirstState(new optimisationInitialisation(this), "initialisation");
		order.registerState(new ecouteBehaviour(this), "ecoute");
		order.registerState(new optimisation(this), "optimisation");
		order.registerLastState(new terminateBehaviour(this), "finish");
		
		order.registerTransition("initialisation", "ecoute", 1);
		order.registerTransition("ecoute", "finish", 0);
		order.registerTransition("ecoute", "optimisation", 1);
		order.registerTransition("optimisation", "ecoute", 1);
		addBehaviour(order);

	}
}