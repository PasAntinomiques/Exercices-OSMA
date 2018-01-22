package mesAgents;

import java.text.DateFormat;
import java.util.Date;
import jade.core.*;
import jade.core.behaviours.*;

public class TIME extends Agent{
	
	//l'agent TIME doit afficher la date et l'heure courante chaque seconde
	protected void setup(){
		
		addBehaviour(new TickerBehaviour(this, 1000){
			
			protected void onTick(){
				//Permet de définir les paramètres de formatage, formamt petite date ?
				DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
				//Objet date construit avec la classe Date (instant actuel)
				Date time = new Date();
				//Permet de formater l'objet date
				shortDateFormat.format(time);
				System.out.println(time+" Tick ="+getTickCount());
							
					
			}
		});
	}
}
