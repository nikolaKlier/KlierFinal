import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Simulator {
	Forest forest;
	public static ArrayList<Dataset> dataSet;
	
	
	public Simulator(Forest f){ //good way to test stuff
		forest = f;
		f.fill();
		for(int x = 0; x < 10; x++){
		f.setRandomFire();
		}
	}
	
	public void reset(double dense){
		forest = new Forest(dense);
		forest.fill();
		for(int x = 0; x < 10; x++){
		forest.setRandomFire();
		}
	}
	
	public void nextGen(){
		forest.nextGeneration();
	}
	
	
	public void run(){ //change the end condition
		boolean isAblaze = forest.isThereFire();
		while(isAblaze){
			forest.timeStep();
			isAblaze = forest.isThereFire();
		}
		Data thisForest = forest.assess(); //fix this in forest to return the data
		dataSet.add(thisForest);
		//have some sort of while loop that allows this to run unitl no more fire
		//have a status method that returns the number of trees dead and the number on fire, possible in a status object
	}
	
	public void displayInfo(){
		for(int x = 0; x < dataSet.size(); x++){
			System.out.println(dataSet.get(x));
		}
	}
	
	
	public Simulator(){
		dataSet = new ArrayList<Data>();
	}
	
	public void doOneStep(){
		//slowDown();
		update();
		forest.timeStep();
		update();
	}

	public Forest getForest() {
		return forest;
	}

	public void setForest(Forest forest) {
		this.forest = forest;
	}
	
	public void setFire(){
		for(int x = 0; x < 10; x++){
			forest.setRandomFire();
		}
	}
	public static void slowDown(){
		for(int x = 0; x < 100000; x ++){
			System.out.print("");
		}
	}
	
	public static String makeDensitiesString(){
		String builder = "";
		for(int x = 0; x < dataSet.size(); x++){
			builder += dataSet.get(x).getDensity();
			builder += " ";
			builder += "\n";
		}
		return builder;
	}
	
	public static String makePercentsString(){
		String builder = "";
		for(int x = 0; x < dataSet.size(); x++){
			builder += dataSet.get(x).getPercentBurned();
			builder += " ";
			builder += "\n";
		}
		return builder;
	}
	public static void writeData(){
		writeToFile(makeDensitiesString(), "densities");
		writeToFile(makePercentsString(), "percentages");
	}
	public void update(){
		forest.updateForest();
	}
	
	public static void writeToFile(String toWrite, String filename) {
		try {
			File file = new File("/CompSci/fireData/" + filename + ".txt");

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(toWrite);
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isBurning(){
		return forest.isThereFire();
	}

}
