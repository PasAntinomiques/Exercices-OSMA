package mesAgents;
import jade.core.Agent;
import jade.core.behaviours.*;
import java.time.*;//Bibliothèque de java 8
import java.time.format.*;


@SuppressWarnings("serial")
public class IAMSIMPLE extends Agent{
	
//	Fonction initialisant l'agent, elle est obligatoire
	protected void setup() {
		
		System.out.println("Hello I am simple !");
		
//		Comportement n°1
		addBehaviour(new CyclicBehaviour(this) {
			public void action(){
				 
				 //Permet d'obtenir le temps actuel
				 LocalDateTime time = LocalDateTime.now();
				 //Permet de formater la date
				 String date = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
				 System.out.println(date);
				 
//				 Permet de faire patienter le programme, l'affichage chaque cycle étant trop rapide
				 try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			 }
		});
		
//		Comportement n°2
		class OneShot extends OneShotBehaviour{
			public OneShot(Agent a) {
				super(a);
			}
			public void action() {
//				Affchage de l'heure actuelle complète cette fois si
				System.out.println(LocalDateTime.now());
			}
		}
		
		addBehaviour(new Behaviour(this) {			
			public void action() {				
				for(int i=0;i<4;i++) {					
//					Les parenthèses sont nécessaires dans le println car sinon il affiche "i1"
					System.out.println("Opération n°"+(i+1));
					if(i==1) {
						OneShot a = new OneShot(myAgent);
						a.action();
					}
				}
			}
			public boolean done() {
				
				return true;
			}
		});
	}
}