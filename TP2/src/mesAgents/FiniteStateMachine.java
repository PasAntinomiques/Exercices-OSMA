package mesAgents;
import jade.core.Agent;
import jade.core.behaviours.*;


@SuppressWarnings("serial")
public class FiniteStateMachine extends Agent{
	
//	Comportement A
	public class OneShotA extends OneShotBehaviour{
		public OneShotA(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action A");
		}
//		Fonction qui permet de définir par la suite des transition, lorsqu'on retourne 9
//		on prend comme convention de passer au comportement suivant directement
		public int onEnd() {
			return 9;
		}
	}
//	Comportement B
	public class OneShotB extends OneShotBehaviour{
		public OneShotB(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action B");
		}
		public int onEnd() {
			return 9;
		}
	}	
//	Comportement C
	public class OneShotC extends OneShotBehaviour{
		public OneShotC(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action C");
		}
		public int onEnd() {
//			permet de retouner un nombre entier entre 0 et 2
			return (int) (Math.random()*3);
		}
	}
//	Comportement D
	public class OneShotD extends OneShotBehaviour{
		public OneShotD(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action D");
		}
		public int onEnd() {
			return 9;
		}
	}
//	Comportement E
	public class OneShotE extends OneShotBehaviour{
		public OneShotE(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action E");
		}
		public int onEnd() {
			return (int) (Math.random()*4);
		}
	}
//	Comportement F
	public class OneShotF extends OneShotBehaviour{
		public OneShotF(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action F");
		}
	}
	
	public void setup() {
//		On instancie un comportement assez puissant qui exécute d'autre comportement avec un ordre qui peut être complexe qu'on peut définir facilement
		FSMBehaviour FiniteStateMachineBehaviour = new FSMBehaviour(this);
		FiniteStateMachineBehaviour.registerFirstState(new OneShotA(this), "A");
		FiniteStateMachineBehaviour.registerState(new OneShotB(this), "B");
		FiniteStateMachineBehaviour.registerState(new OneShotC(this), "C");
		FiniteStateMachineBehaviour.registerState(new OneShotD(this), "D");
		FiniteStateMachineBehaviour.registerState(new OneShotE(this), "E");
		FiniteStateMachineBehaviour.registerLastState(new OneShotF(this), "F");
		
//		On définit les conditions pour passer d'un comportement à un autre
//		On pass de A à B seulement si onEnd de A retourn 9
		FiniteStateMachineBehaviour.registerTransition("A", "B", 9);
		FiniteStateMachineBehaviour.registerTransition("B", "C", 9);
		FiniteStateMachineBehaviour.registerTransition("C", "C", 0);
		FiniteStateMachineBehaviour.registerTransition("C", "A", 1);
		FiniteStateMachineBehaviour.registerTransition("C", "D", 2);
		FiniteStateMachineBehaviour.registerTransition("D", "E", 9);
		FiniteStateMachineBehaviour.registerTransition("E", "A", 0);
		FiniteStateMachineBehaviour.registerTransition("E", "A", 1);
		FiniteStateMachineBehaviour.registerTransition("E", "A", 2);
		FiniteStateMachineBehaviour.registerTransition("E", "F", 3);
		
		addBehaviour(FiniteStateMachineBehaviour);
		
	}
	
}
