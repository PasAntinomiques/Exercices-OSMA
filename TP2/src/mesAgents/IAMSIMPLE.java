package mesAgents;
import jade.core.Agent;
import jade.core.behaviours.*;
import java.time.*;//Bibliothèque de java 8
import java.time.format.*;


public class IAMSIMPLE extends Agent{
	
	//Fonction initialisant l'agent, elle est obligatoire
	protected void setup() {
		
		System.out.println("Hello I am simple !");
		
		//Comportement n°1
		addBehaviour(new CyclicBehaviour(this) {
			 public void action(){
				 
				 //Permet d'obtenir le temps actuel
				 LocalDateTime time = LocalDateTime.now();
				 //Permet de formater la date
				 String date = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
				 System.out.println(date);
			 }
		});
		
		//Comportement n°2
		addBehaviour(new Behaviour(this) {
			public void action() {
				for(int i=0;i<4;i++) {
				System.out.println("Opération n°"+i+1);
					if(i==2) {
						addBehaviour(new OneShotBehaviour() {
							public void action() {
								
							};
						});
					}
				}
			}
			public boolean done() {
				
				return true;
			}
		});
	}
}