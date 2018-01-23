package mesAgents;
import jade.core.Agent;
import jade.core.behaviours.*;
import java.util.Random;


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
	}
//	Comportement B
	public class OneShotB extends OneShotBehaviour{
		public OneShotB(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action B");
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
	}
//	Comportement D
	public class OneShotD extends OneShotBehaviour{
		public OneShotD(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action D");
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
//	 définit ici une fonction afin d'utiliser les pouvoirs de la récursirvité
	public void execution(String boucle) {
//		Ce que doit exécuter l'algorithme après avoir fait C (deux boucles)
		if(boucle == "c") {
			while(decision == 0);{
				Seq.addSubBehaviour(new OneShotC(this));
				decision = randomiser(3);
			}
		}
		if(boucle == "abc") {
			while(decision == 1){
				Seq.addSubBehaviour(new OneShotA(this));
				Seq.addSubBehaviour(new OneShotB(this));
				Seq.addSubBehaviour(new OneShotC(this));
				decision = randomiser(3);
				execution("c");
			};
		}
//		Ce que doit exécuter l'algorithme après avoir fait E (une boucle)
		if(boucle == "bcde") {
			while(decision!=3) {
				Seq.addSubBehaviour(new OneShotB(this));
				Seq.addSubBehaviour(new OneShotC(this));
				decision = randomiser(3);
				execution("c");
				execution("abc");
				Seq.addSubBehaviour(new OneShotD(this));
				Seq.addSubBehaviour(new OneShotE(this));
				decision = randomiser(4);
				execution("bcde");
			}
		}
	}
	
//	prend un entier a et renvoie un entier aléatoire entre [0; a-1]
	public int randomiser(int a) {
		int b = (int) (Math.random()*a);
//		System.out.println("Décision vaut : " + b);
		return b;
	}
	
	SequentialBehaviour Seq = new SequentialBehaviour(this);
	int decision;
	public void setup() {
		Seq.addSubBehaviour(new OneShotA(this));
		Seq.addSubBehaviour(new OneShotB(this));
		Seq.addSubBehaviour(new OneShotC(this));
		decision = randomiser(3);
		execution("c");
		execution("abc");
		Seq.addSubBehaviour(new OneShotD(this));
		Seq.addSubBehaviour(new OneShotE(this));
		decision = randomiser(4);
		execution("bcde");
		Seq.addSubBehaviour(new OneShotF(this));
			
		addBehaviour(Seq);
	}
	
}
