package mesAgents;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;

@SuppressWarnings("serial")
public class CustomTemplateAgent extends Agent{
	protected void setup(){
		addBehaviour(new CyclicBehaviour(this) {
			public void action() {
				System.out.println(myAgent.getName());
				ACLMessage msg = myAgent.receive();
				if(msg != null) {
					String ontologyFirstCharacter = msg.getOntology().substring(0, 1);
					System.out.println(ontologyFirstCharacter);
					
//					Le == permet de comparé les référence des objets, la méthode équals vérifie leurs valeurs, pour les types
//					primitifs == fonctionne car ils ne sont pas dissociés de leur référence
					if(ontologyFirstCharacter.equals("X")) {
						System.out.println("Message reçu ! Son ontology est "+msg.getOntology());
					}else {
//						Permet de bloquer le message entrant ?
						block();
					}
				}else {
					block();
				}
				
			}		
		});
	}
}
