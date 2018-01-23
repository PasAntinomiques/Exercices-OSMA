package mesAgents;
import jade.core.*;
import jade.core.behaviours.*;


@SuppressWarnings("serial")
public class TimeAgent extends Agent{
	
	
	
		protected void setup(){
			
		addBehaviour(new TickerBehaviour(this, 1000) {
			protected void onTick() {
				count = this.getTickCount();
				System.out.println("Je suis "+myAgent.getLocalName()+" et le num�ro du cycle est "+this.getTickCount());
			}
		});
		
		addBehaviour(new Behaviour(this) {
			boolean fait;
			public void action() {
				if(count == 10) {
					System.out.println("Ex�cution de t�che");
					fait=true;
				}
			}
			public boolean done() {
				return fait;
			}
		});
	}
	int count;
}