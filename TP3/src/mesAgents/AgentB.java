package mesAgents;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
//import jade.util.Logger;

@SuppressWarnings("serial")
public class AgentB extends Agent{
	
//	Permet d'afficher des messages de types log dans la console
//	Plus utile pour débugger car spécifié la date et de quel programme le message vient
//	private Logger myLogger = Logger.getMyLogger(getClass().getName());
	
	protected void setup() {
		addBehaviour(new TickerBehaviour(this,1000) {
			protected void onTick() {
//				myLogger.log(Logger.WARNING,"Agent "+myAgent.getLocalName()+" tick : "+getTickCount());
				System.out.println("Agent "+myAgent.getLocalName()+" tick : "+getTickCount());
				
//				Permet de créer un message de Performative(type?) AGREE
				ACLMessage msg = new ACLMessage(ACLMessage.AGREE);
				
//				Permet d'ajouter un destinataire car c'est l'agent qui va initialiser le message
//				(impossibilité de faire reply)
				msg.addReceiver(new AID("A@POSSAMAI",AID.ISGUID));
				msg.setContent("OKAIT");
//				Permet d'envoyer le message
				myAgent.send(msg);
			}
		});
	}
}
