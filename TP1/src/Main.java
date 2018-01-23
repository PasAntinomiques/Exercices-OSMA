import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Main {

	public static void main(String[] args) {
//		ouioui
		//Cr�ation d'une instance de l'environnement Jade
		jade.core.Runtime rt = jade.core.Runtime.instance();
		
		//Cr�ation d'un profil de Container par d�faut pour lancer la plateforme
		//pour le main container
		ProfileImpl pMain = new ProfileImpl(null, 2000, "BIAU");//les conteneurs cr��s seront auront comme param�tres
		//les arguments de cette fonction
		
		ProfileImpl pHome = new ProfileImpl();//cr�ateur de conteneur
		
		
		
		//Cr�ation du main container
		AgentContainer mc = rt.createMainContainer(pMain);
		
		AgentController rma; 
		// cr�ation du type de variable comme int i
		
		AgentController HelloIAmAtHome; 
		//Agent qui contiendra l'adresse d'un autre agent
		
		int N = 3;
//		int N=Integer.parseInt(args[0]);
		//parsInt convertit les String en Integer
		//elle peut g�n�rer une exception 'soft' donc pas de try catch
		//n d�signe le nombre d'agent de type HelloWorld � ex�cuter
		
		for(int i=0;i<N;i++){
			pHome.setParameter("container-name", "Home"+i+1);
			AgentContainer Home = rt.createAgentContainer(pHome);
		}
		try {//on place les createNewAgent et start dans un try catch car ils peuvent g�n�rer des exeptions 'hard'
			//Cr�ation de l'agent rma
			rma = mc.createNewAgent("rma", "jade.tools.rma.rma", null);//on ajoute des param�tres � notre agent
			//Lancer l'agent rma
			rma.start();
		} catch (StaleProxyException e1) {
			e1.printStackTrace();
		}
	}

}
//La plateforme ne voit pas les classes Agents dans le package par d�faut donc ont l'a boug� dans mesClasses