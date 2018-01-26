package mesAgents;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import java.util.Iterator;

@SuppressWarnings("serial")
public class AliasAgent extends Agent{
	protected void setup() {
		addBehaviour(new CyclicBehaviour(this) {
			public void action() {
				ACLMessage msg = myAgent.receive();
				if(msg != null) {
					Iterator itReceivers = msg.getAllReceiver();
					while(itReceivers.hasNext()) {
						String receiver = (String)itReceivers.next();
						String myName = myAgent.getName();
//						String myAlias
						ACLMessage reply = msg.createReply();
//						On cherche l'ancien nom dans la liste des destinataires
						if(myName.equals(receiver) && !myName.substring(myName.length()-4).equals("AAA")) {
							reply.setPerformative(ACLMessage.REQUEST);
							reply.setContent("Please call me "+myName+"AAA");
//							MANQUE Doit s'ajouter un alias
						}
//						if(myAlias.equals(receiver){
//							reply.setPerfomative(ACLMessage.INFORM);
//							reply.setContent("Good name chose");
//						}
					myAgent.send(reply);
					}
				}else {
					block();
				}
			}
			
		});
	}
}
