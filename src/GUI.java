import processing.core.*;

public class GUI extends PApplet {
	int c;
	Simulator sim;
	Display display;

	public void setup() {
		size(640, 550); // set the size of the screen.
		Forest f = new Forest(Forest.DEFAULT_FOREST_DENSE);
		// Create a simulator
		sim = new Simulator(f);
		sim.getForest();
		// Create the display
		// parameters: (10,10) is upper left of display
		// (620, 530) is the width and height
		display = new Display(this, 10, 10, 620, 530);

		// Set different grid values to different colors
		display.setColor(Tree.ON_FIRE, color(255, 0, 0)); 
		display.setColor(Tree.ALIVE, color(0, 255, 0));
		display.setColor(Tree.DEAD, color(255, 255, 255));
		// You can use classes instead, for example:
		// display.setColor(Tree.class, color(0, 255, 0));
		// display.setColor(Ash.class, color(0, 255, 0));
		
		display.setNumCols(100);
		display.setNumRows(100);
	}

	@Override
	public void draw() {
		background(200);
		sim.doOneStep(); //add the checking for generation in here as well
		display.drawGrid(sim.getForest());
		if(!sim.getForest().isThereFire()){
			sim.nextGen();
		}
	}
	
	public void keyReleased(){
		sim.doOneStep();
	}
}