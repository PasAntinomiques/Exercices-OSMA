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


//		---Exercice I)----
//		AgentController Agent1;
//		try {
//			Agent1 = mc.createNewAgent("PingAgent", "mesAgents.PingAgent", null);
//			Agent1.start();
//		} catch (StaleProxyException e) {
//			e.printStackTrace();
//		}
//		----Exercice II)----
//		AgentController A;
//		AgentController B;
//		try {
//			A = mc.createNewAgent("A", "mesAgents.AgentA", null);
//			A.start();
//			B = mc.createNewAgent("B", "mesAgents.AgentB", null);
//			B.start();
//		} catch (StaleProxyException e) {
//			e.printStackTrace();
//		}
//		----Exercice III-1)----
//		AgentController BlockingRecieveAgentInstance;
//		try {
//			BlockingRecieveAgentInstance = mc.createNewAgent("BlockingReceive", "mesAgents.BlockingReceiveAgent", null);
//			BlockingRecieveAgentInstance.start();
//		} catch (StaleProxyException e) {
//			e.printStackTrace();
//		}
//		----Exercice III-2)----
		AgentController CustomTemplateAgent;
		try {
			CustomTemplateAgent = mc.createNewAgent("CustomTemplateAgent", "mesAgents.CustomTemplateAgent", null);
			CustomTemplateAgent.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
						
	}
	
}
