package mesClasses;

import jade.core.*;

//extends permet de d�finir la classe HelloWorld comme une classe de type
//Agent (possible gr�ce � l'import, Agent � jade.core)
public class HelloWorld extends Agent{

	//protected : en dehors du package on ne peut pas y acc�der
	//setup est la fonction initialisation d'un agent, setup � Agent
	protected void setup(){
		
		System.out.println("HelloWorld!My name is "+getLocalName());
		//doDelete();//D�truit l'agent
	}
	

}
