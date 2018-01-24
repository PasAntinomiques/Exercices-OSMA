package mesAgents;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;

@SuppressWarnings("serial")
public class BlockingReceiveAgent extends Agent{
	protected void setup() {
		addBehaviour(new CyclicBehaviour(){
			public void action() {
				
//				Si on veut rajouter des conditions il faut utiliser la fonction and ou or(cf api)
				MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
				ACLMessage msg = myAgent.blockingReceive(template);
				System.out.print("Agent "+getLocalName()+" -Receivve "+msg.getContent()+" from "+msg.getSender().getName());
				ACLMessage reply = msg.createReply();
				reply.setPerformative(ACLMessage.INFORM);
				myAgent.doDelete();
			}
			
		});
	}
	
}
