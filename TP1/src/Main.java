import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Main {

	public static void main(String[] args) {
//		ouioui
		//Création d'une instance de l'environnement Jade
		jade.core.Runtime rt = jade.core.Runtime.instance();
		
		//Création d'un profil de Container par défaut pour lancer la plateforme
		//pour le main container
		ProfileImpl pMain = new ProfileImpl(null, 2000, "BIAU");//les conteneurs créés seront auront comme paramètres
		//les arguments de cette fonction
		
		ProfileImpl pHome = new ProfileImpl();//créateur de conteneur
		
		
		
		//Création du main container
		AgentContainer mc = rt.createMainContainer(pMain);
		
		AgentController rma; 
		// création du type de variable comme int i
		
		AgentController HelloIAmAtHome; 
		//Agent qui contiendra l'adresse d'un autre agent
		
		int N = 3;
//		int N=Integer.parseInt(args[0]);
		//parsInt convertit les String en Integer
		//elle peut générer une exception 'soft' donc pas de try catch
		//n désigne le nombre d'agent de type HelloWorld à exécuter
		
		for(int i=0;i<N;i++){
			pHome.setParameter("container-name", "Home"+i+1);
			AgentContainer Home = rt.createAgentContainer(pHome);
		}
		try {//on place les createNewAgent et start dans un try catch car ils peuvent générer des exeptions 'hard'
			//Création de l'agent rma
			rma = mc.createNewAgent("rma", "jade.tools.rma.rma", null);//on ajoute des paramètres à notre agent
			//Lancer l'agent rma
			rma.start();
		} catch (StaleProxyException e1) {
			e1.printStackTrace();
		}
	}

}
//La plateforme ne voit pas les classes Agents dans le package par défaut donc ont l'a bougé dans mesClasses