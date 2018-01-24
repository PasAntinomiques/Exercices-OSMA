package mesAgents;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
//import jade.util.Logger;

//CF les commentaires de l'agent B

@SuppressWarnings("serial")
public class AgentA extends Agent{

//	private Logger myLogger = Logger.getMyLogger(getClass().getName());
	
	public void setup() {
		addBehaviour(new CyclicBehaviour(this) {
			public void action() {
				ACLMessage msg = myAgent.receive();
				if(msg != null) {
//					myLogger.log(Logger.INFO,"Agent "+getLocalName()+" -Receivve "+msg.getContent()+" from "+msg.getSender().getName());
					System.out.println("Agent "+getLocalName()+" -Receivve "+msg.getContent()+" from "+msg.getSender().getName());
					ACLMessage reply = msg.createReply();
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("Okkkkkay");
					myAgent.send(reply);
				}else {
					
//					Permet de bloquer l'agent, l'ajoute dans la liste d'agent bloqué
					block();
				}
			}
		});
	}
	
}
