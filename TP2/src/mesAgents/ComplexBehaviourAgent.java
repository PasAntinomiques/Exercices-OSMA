package mesAgents;
import jade.core.Agent;
import jade.core.behaviours.*;

@SuppressWarnings("serial")
public class ComplexBehaviourAgent extends Agent{
//	Comportement 1.1
public class OneShot11 extends OneShotBehaviour{
	public OneShot11(Agent a) {
		super(a);
	}
	public void action() {
		System.out.println("Action 1.1");
	}
}
//	Comportement 1.2
public class OneShot12 extends OneShotBehaviour{
	public OneShot12(Agent a) {
		super(a);
	}
	public void action() {
		System.out.println("Action 1.2");
	}
}
//	Comportement 1.3
public class OneShot13 extends OneShotBehaviour{
	public OneShot13(Agent a) {
		super(a);
	}
	public void action() {
		System.out.println("Action 1.3");
	}
}
//	Comportement 2.1.1
	public class OneShot211 extends OneShotBehaviour{
		public OneShot211(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action 2.1.1");
		}
	}
//	Comportement 2.1.2
	public class OneShot212 extends OneShotBehaviour{
		public OneShot212(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action 2.1.2");
		}
	}
//	Comportement 2.1.3
	public class OneShot213 extends OneShotBehaviour{
		public OneShot213(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action 2.1.3");
		}
	}
//	Comportement 2.2.1
	public class OneShot221 extends OneShotBehaviour{
		public OneShot221(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action 2.2.1");
		}
	}
//	Comportement 2.2.2
	public class OneShot222 extends OneShotBehaviour{
		public OneShot222(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action 2.2.2");
		}
	}
//	Comportement 2.2.3
	public class OneShot223 extends OneShotBehaviour{
		public OneShot223(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action 2.2.3");
		}
	}
//	Comportement 2.3
	public class OneShot23 extends OneShotBehaviour{
		public OneShot23(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action 2.3");
		}
	}
//	Comportement 2.4
	public class OneShot24 extends OneShotBehaviour{
		public OneShot24(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action 2.4");
		}
	}
//	Comportement 2.5
	public class OneShot25 extends OneShotBehaviour{
		public OneShot25(Agent a) {
			super(a);
		}
		public void action() {
			System.out.println("Action 2.5");
		}
	}
	
	protected void setup(){
		
		SequentialBehaviour Seq21 = new SequentialBehaviour(this);
		Seq21.addSubBehaviour(new OneShot211(this));
		Seq21.addSubBehaviour(new OneShot212(this));
		Seq21.addSubBehaviour(new OneShot213(this));
		
		SequentialBehaviour Seq22 = new SequentialBehaviour(this);
		Seq22.addSubBehaviour(new OneShot221(this));
		Seq22.addSubBehaviour(new OneShot222(this));
		Seq22.addSubBehaviour(new OneShot223(this));
		
		SequentialBehaviour Seq1 = new SequentialBehaviour(this);
		Seq1.addSubBehaviour(new OneShot11(this));
		Seq1.addSubBehaviour(new OneShot12(this));
		Seq1.addSubBehaviour(new OneShot13(this));
		
		SequentialBehaviour Seq2 = new SequentialBehaviour(this);
		Seq2.addSubBehaviour(Seq21);
		Seq2.addSubBehaviour(Seq22);
		Seq2.addSubBehaviour(new OneShot23(this));
		Seq2.addSubBehaviour(new OneShot24(this));
		Seq2.addSubBehaviour(new OneShot25(this));
		
		addBehaviour(Seq1);
		addBehaviour(Seq2);
		
	}
}
