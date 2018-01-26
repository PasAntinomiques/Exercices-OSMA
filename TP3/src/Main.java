import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Main {

	public static void main(String[] args) {
		
//		Cr�ation de l'environnement
		jade.core.Runtime rt = jade.core.Runtime.instance();
//		Cr�ation du main container nomm� POSSAMAI (1 on param�tre le constructeur, 2 on construit)
		ProfileImpl pMain = new ProfileImpl(null, 2000, "POSSAMAI");
		AgentContainer mc = rt.createMainContainer(pMain);
		
//		D�claration de l'agent rma qui permet de cr�er une interface graphique (ICP)
		AgentController rma;
		try {
//			On cr�� l'agent dans le container main
//			Ces deux lignes sont entour�es par un try/catch car elles peuvent g�n�rer une erreur
//			1- nom de l'agent, 2- chemin de l'agent, 3-param�tre pour cet agent (String[])
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
