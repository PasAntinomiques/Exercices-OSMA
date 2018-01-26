import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Main {

	public static void main(String[] args) {
		
//		Création de l'environnement
		jade.core.Runtime rt = jade.core.Runtime.instance();
//		Création du main container nommé POSSAMAI (1 on paramètre le constructeur, 2 on construit)
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
		
//		----Exercice 1-a)----
//		AgentController P1;
//		AgentController P2;
//		AgentController I;
//		
//		try {
//			P1 = mc.createNewAgent("P1", "mesAgents.Participant", null);
//			P2 = mc.createNewAgent("P2", "mesAgents.Participant", null);
//			String[] arg = {"P1","P2"};
//			I = mc.createNewAgent("I", "mesAgents.Initiator", arg);
//			P1.start();
//			P2.start();
//			I.start();
//		} catch (StaleProxyException e) {
//			e.printStackTrace();
//		}
		AgentController P1;
		AgentController P2;
		AgentController B;
		AgentController I;
		
		try {
			P1 = mc.createNewAgent("P1", "mesAgents.Participant", null);
			P2 = mc.createNewAgent("P2", "mesAgents.Participant", null);
			String[] argB = {"P1","P2"};
			B = mc.createNewAgent("B", "mesAgents.BrokerAgent", argB);
			String[] argI = {"B"};
			I = mc.createNewAgent("I", "mesAgents.Initiator", argI);
			P1.start();
			P2.start();
			B.start();
			I.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
}