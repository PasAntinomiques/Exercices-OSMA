package mesAgents;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Date;
import java.util.Vector;

import jade.core.AID;
import jade.core.behaviours.*;
import jade.proto.AchieveREInitiator;

@SuppressWarnings("serial")
public class BrokerAgent extends Agent{
	
	public static int nbInform =0;
	public static int nbRefuse =0;
	public static int nbFailure =0;
	public static int nbNonExistingAgent=0;
	public static int nbLateAgent =0;
	public static int nbResponders;
	public static Object[] args;
	public static ACLMessage relay=new ACLMessage(ACLMessage.REQUEST);
	
	public class receivingRequest extends OneShotBehaviour{
		public receivingRequest(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Agent "+getLocalName()+ " : Waiting for requests...");
			args = getArguments();
			nbResponders = args.length;
			if(args !=null && nbResponders>0) {
				
				MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
				
				ACLMessage request = myAgent.blockingReceive(template);
				System.out.println("Relaying dummy-action to "+nbResponders+" responders.");
				
				System.out.println("Agent "+getLocalName()+" : REQUEST received from "+request.getSender().getName()+". Action is "+request.getContent());
				System.out.println("Agent "+getLocalName()+" : Agree");
				
//				On lui renvoit un agree pour lui dire qu'on va relaye
				ACLMessage agree = request.createReply();
				agree.setPerformative(ACLMessage.AGREE);
				System.out.println("Agent "+getLocalName()+" : Relaying");
				myAgent.send(agree);
				
				
//				On relait la requête			
//				relay.setPerformative(request.getPerformative());
				for(int i=0;i<nbResponders;i++) {
					relay.addReceiver(new AID((String) args[i],AID.ISLOCALNAME));
				}
				relay.setProtocol(request.getProtocol());
				relay.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
				relay.setContent(request.getContent());
			}
			else {
				System.out.println("Agent "+getLocalName()+" : No responder specified.");
			}
		}
		public int onEnd() {
			return 1;
		}
	}
	
	public class sendingRequest extends AchieveREInitiator{
		public sendingRequest(Agent a, ACLMessage msg){
			super(a, msg);
		}
		protected void handleInform(ACLMessage inform){
			System.out.println("Agent "+getLocalName()+" : Agent "+inform.getSender().getName()+" successfully performed the requested action");
			nbInform++;
		}
		protected void handleRefuse(ACLMessage refuse){
			System.out.println("Agent "+getLocalName()+" : Agent "+refuse.getSender().getName()+" refused to perform the requested action");
			nbRefuse++;
		}
		protected void handleFailure(ACLMessage failure){
			if (failure.getSender().equals(myAgent.getAMS())) {
				// FAILURE notification from the JADE runtime: the receiver
				// does not exist
				System.out.println("Agent "+getLocalName()+" : Responder does not exist");
				nbNonExistingAgent++;
			}
			else {
				System.out.println("Agent "+getLocalName()+" : Agent "+failure.getSender().getName()+" failed to perform the requested action");
			nbFailure++;
			}
		}
		protected void handleAllResultNotifications(Vector notifications){
			if (notifications.size() < nbResponders) {
				// Some responder didn't reply within the specified timeout
				System.out.println("Agent "+getLocalName()+" : Timeout expired: missing "+(nbResponders - notifications.size())+" responses");
				nbLateAgent++;
			}
		}
//		public final void action() {
//			RESTE A FAIRE
//			le comportement est bloqué, il faudrait soit arriver à éditer action pour dire quand arreter de recevoir les messages
//			il faudrait sinon apprendre à detecter les throw refuse exeptions du participant afin de se passer d'un achieveREInitiator
//		}
		public int onEnd() {
			if(nbNonExistingAgent+nbRefuse+nbInform+nbFailure+nbLateAgent==nbResponders) {
				return 1;
			}else {
				return 0;
			}
		}
	}
	
	public class feedback extends OneShotBehaviour {
		public feedback(Agent a) {
			super(a);
		}
		public void action() {
			ACLMessage feed = new ACLMessage(ACLMessage.INFORM);
			feed.setProtocol(relay.getProtocol());
			feed.setContent("Agent "+getLocalName()+" : Nb of success : "+nbInform+"Nb of failure : "+nbFailure+"Nb of refuse : "+nbRefuse+"Nb of late agent : "+nbLateAgent+"Nb of non existing agent : "+nbNonExistingAgent);
			nbInform =0;
			nbRefuse =0;
			nbFailure =0;
			nbNonExistingAgent=0;
			nbLateAgent =0;
		}
		public int onEnd() {
			return 1;
		}
	}
	
	protected void setup() {
		
		FSMBehaviour order = new FSMBehaviour(this);
		order.registerFirstState(new receivingRequest(this), "receiving");
		order.registerState(new sendingRequest(this, relay), "sending");
		order.registerLastState(new feedback(this), "feedback");
		order.registerTransition("receiving", "sending", 1);
		order.registerTransition("sending", "feedback", 1);
		order.registerTransition("feedback", "receiving", 1);
		
		addBehaviour(order);
	}
}
