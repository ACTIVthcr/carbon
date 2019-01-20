package org.carbon.adventure;

import java.io.File;

public class App {

	private static final String PATH = "src/main/resoucres/";

	public static void main(String[] args) {
		File fileInput = new File(PATH + "treasureMap.txt");
		File fileOutput = new File(PATH + "outputTreasureMap.txt");
		MapDescription mapDescription = new MapDescription();
		mapDescription.process(fileInput, fileOutput);
	}

}
