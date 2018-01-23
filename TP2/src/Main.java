import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Main {

	public static void main(String[] args) {
	
//		Création de l'environnement
		jade.core.Runtime rt = jade.core.Runtime.instance();
		//Création du main container nommé POSSAMAI (1 on paramètre le constructeur, 2 on construit)
		ProfileImpl pMain = new ProfileImpl(null, 2000, "POSSAMAI");
		AgentContainer mc = rt.createMainContainer(pMain);
		
//		Déclaration de l'agent rma qui permet de créer une interface graphique (ICP)
		AgentController rma;
		try {
//			On créé l'agent dans le container main
//			Ces deux lignes sont entourées par un try/catch car elles peuvent générer une erreur
//			1- nom de l'agent, 2- chemin de l'agent, 3-paramètre pour cet agent (String[])
			rma = mc.createNewAgent("rma", "jade.tools.rma.rma", null);
			rma.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
		
//		----Exercice I-1)----
//		AgentController Agent1;
//		try {
//			Agent1 = mc.createNewAgent("TIME", "mesAgents.TIME", null);
//			Agent1.start();
//		} catch (StaleProxyException e) {
//			e.printStackTrace();
//		}
		
//		----Exercice I-2)----
//		AgentController Agent2;
//		try {
//			Agent2 = mc.createNewAgent("IAMSIMPLE", "mesAgents.IAMSIMPLE", null);
//			Agent2.start();
//		} catch (StaleProxyException e) {
//			e.printStackTrace();
//		}
		
//		----Exercice II)----
//		AgentController Agent3;
//		try {
//			Agent3 = mc.createNewAgent("TimeAgent", "mesAgents.TimeAgent", null);
//			Agent3.start();
//		} catch (StaleProxyException e) {
//			e.printStackTrace();
//		}
		
//		----Exercice III)----
//		AgentController Agent4;
//		try {
//			Agent4 = mc.createNewAgent("ComplexBehaviourAgent", "mesAgents.ComplexBehaviourAgent", null);
//			Agent4.start();
//		} catch (StaleProxyException e) {
//			e.printStackTrace();
//		}
		
//		---Exercice IV)---
		AgentController Agent5;
		try {
			Agent5 = mc.createNewAgent("FiniteStateMachine", "mesAgents.FiniteStateMachine", null);
			Agent5.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}

}