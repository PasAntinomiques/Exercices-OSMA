package mesClasses;

import jade.core.*;

//extends permet de définir la classe HelloWorld comme une classe de type
//Agent (possible grâce à l'import, Agent € jade.core)
public class HelloWorld extends Agent{

	//protected : en dehors du package on ne peut pas y accéder
	//setup est la fonction initialisation d'un agent, setup € Agent
	protected void setup(){
		
		System.out.println("HelloWorld!My name is "+getLocalName());
		//doDelete();//Détruit l'agent
	}
	

}
