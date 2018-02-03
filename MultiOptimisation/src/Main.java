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
		AgentController genetique;
		AgentController recuit;
		AgentController tabou;
		
		int MaxIteration = 1000;
		
		try {
			Object[] argsGenetique = {"genetique",MaxIteration,"genetique2","genetique3"};
			genetique = mc.createNewAgent("genetique1", "mesAgents.AgentOptimisation", argsGenetique);
			
			Object[] argsRecuit = {"genetique",MaxIteration,"genetique1","genetique3"};
			recuit = mc.createNewAgent("genetique2", "mesAgents.AgentOptimisation", argsRecuit);
			
			Object[] argsTabou = {"genetique",MaxIteration,"genetique1","genetique2"};
			tabou = mc.createNewAgent("genetique3", "mesAgents.AgentOptimisation", argsTabou);
			
			genetique.start();
			recuit.start();
			tabou.start();
			
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
		

	}

}
