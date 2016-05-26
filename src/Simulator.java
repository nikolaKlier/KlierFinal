import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Simulator {
	Forest forest;
	public static ArrayList<Data> dataSet;

	public Simulator(Forest f) { // good way to test stuff
		forest = f;
		f.fill();
		for (int x = 0; x < 10; x++) {
			f.setRandomFire();
		}
	}

	public void reset() {
		forest = new Forest();
		forest.fill();
		for (int x = 0; x < 10; x++) {
			forest.setRandomFire();
		}
	}

	public void nextGen() {
		forest.nextGeneration();
	}

	public void run() { // change the end condition
		int gen = 0;
		while (gen < 100) {
			doOneStep();
			if (!isBurning()) {
				gen++;
				Data temp = new Data(gen, forest);
				dataSet.add(temp);
				nextGen();
				setFire();
				System.out.println(gen + " , " + temp.getPercentBurned());
			}
			//System.out.println("end of while loop, perfect boolean is " + perfect);
		}
	}

	public void displayInfo() {
		for (int x = 0; x < dataSet.size(); x++) {
			System.out.println(dataSet.get(x));
		}
	}

	public Simulator() {
		dataSet = new ArrayList<Data>();
	}

	public void doOneStep() {
		// slowDown();
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

	public void setFire() {
		for (int x = 0; x < 20; x++) {
			forest.setRandomFire();
		}
	}

	public static void slowDown() {
		for (int x = 0; x < 100000; x++) {
			System.out.print("");
		}
	}

	public static String makeDataString() {
		String builder = ("generation, percentBurned, avverage starting fuel, average seed drop rate, average mutation rate, average catching probability, average heat degredation");
		for (int x = 0; x < dataSet.size(); x++) {
			builder += "\n";
			builder += dataSet.get(x);
		}
		return builder;
	}

	public static void writeData() {
		writeToFile(makeDataString(), "fireData");
	}

	public void update() {
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

	public boolean isBurning() {
		return forest.isThereFire();
	}

}
