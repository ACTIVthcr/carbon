package org.carbon.adventure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.util.List;

import org.carbon.adventure.tile.Adventurer;
import org.carbon.adventure.tile.Mountain;
import org.carbon.adventure.tile.Position;
import org.carbon.adventure.tile.Tile;
import org.carbon.adventure.tile.Treasure;
import org.junit.jupiter.api.Test;;

class AppTest {

	private static final String PATH = "src/test/resources/";

	@Test
	void should_process_the_map() {
		MapDescription mapDescription = new MapDescription();
		mapDescription.process(new File(PATH + "test.txt"), new File("target/outputTest.txt"));
		assertFalse(mapDescription.getAdventurers().isEmpty());
		assertFalse(mapDescription.getTreasures().isEmpty());
		assertFalse(mapDescription.getMoutains().isEmpty());
		assertNotNull(mapDescription.getSize());
		assertEquals(2, mapDescription.getAdventurers().size());
		assertEquals(2, mapDescription.getTreasures().size());
		assertEquals(2, mapDescription.getMoutains().size());
		Adventurer adventurer = mapDescription.getAdventurers().get(0);
		assertEquals("Lara", adventurer.getName());
		assertEquals(0, adventurer.getTreasureFound());
		assertEquals("S", adventurer.getOrientation().getName());
		assertEquals(new Position(0, 2), adventurer.getPosition());
		Treasure treasure = mapDescription.getTreasures().get(1);
		assertEquals(new Position(1, 3), treasure.getPosition());
		assertEquals(2, treasure.getTreasureNumber());
		Mountain moutain = mapDescription.getMoutains().get(0);
		assertEquals(new Position(1, 0), moutain.getPosition());
		MapDescription mapDecriptionFileOutput = UtilsTest.parseOutputFile(new File("target/outputTest.txt"));
		compareFile(mapDescription, mapDecriptionFileOutput);
	}

	@Test
	void should_process_the_map_2() {
		MapDescription mapDescription = new MapDescription();
		mapDescription.process(new File(PATH + "test2.txt"), new File("target/outputTest2.txt"));
		assertFalse(mapDescription.getAdventurers().isEmpty());
		assertFalse(mapDescription.getTreasures().isEmpty());
		assertFalse(mapDescription.getMoutains().isEmpty());
		assertNotNull(mapDescription.getSize());
		assertEquals(2, mapDescription.getAdventurers().size());
		assertEquals(4, mapDescription.getTreasures().size());
		assertEquals(15, mapDescription.getMoutains().size());
		Adventurer adventurer = mapDescription.getAdventurers().get(0);
		assertEquals("Lara", adventurer.getName());
		assertEquals(3, adventurer.getTreasureFound());
		assertEquals("S", adventurer.getOrientation().getName());
		assertEquals(new Position(0, 3), adventurer.getPosition());
		Treasure treasure = mapDescription.getTreasures().get(3);
		assertEquals(new Position(3, 7), treasure.getPosition());
		assertEquals(0, treasure.getTreasureNumber());
		Mountain moutain = mapDescription.getMoutains().get(0);
		assertEquals(new Position(1, 0), moutain.getPosition());
		MapDescription mapDecriptionFileOutput = UtilsTest.parseOutputFile(new File("target/outputTest2.txt"));
		compareFile(mapDescription, mapDecriptionFileOutput);
	}

	@Test
	void should_not_place_out_of_bound_element() {
		MapDescription mapDescription = new MapDescription();
		mapDescription.process(new File(PATH + "testOutOfMap.txt"), new File("target/outputTest3.txt"));
		assertFalse(mapDescription.getAdventurers().isEmpty());
		assertFalse(mapDescription.getTreasures().isEmpty());
		assertFalse(mapDescription.getMoutains().isEmpty());
		assertEquals(1, mapDescription.getAdventurers().size());
		assertEquals(1, mapDescription.getTreasures().size());
		assertEquals(1, mapDescription.getMoutains().size());
		Adventurer adventurer = mapDescription.getAdventurers().get(0);
		assertEquals("Lara", adventurer.getName());
		assertEquals(0, adventurer.getTreasureFound());
		assertEquals("S", adventurer.getOrientation().getName());
		assertEquals(new Position(1, 3), adventurer.getPosition());
		MapDescription mapDecriptionFileOutput = UtilsTest.parseOutputFile(new File("target/outputTest3.txt"));
		compareFile(mapDescription, mapDecriptionFileOutput);
	}

	private void compareFile(MapDescription mapDescription, MapDescription mapDecriptionFileOutput) {
		assertEquals(mapDescription.getSize(), mapDecriptionFileOutput.getSize());
		compareList(mapDescription.getAdventurers(), mapDecriptionFileOutput.getAdventurers());
		compareList(mapDescription.getMoutains(), mapDecriptionFileOutput.getMoutains());
		compareList(mapDescription.getTreasures(), mapDecriptionFileOutput.getTreasures());
	}

	private void compareList(List<? extends Tile> genericInputList, List<? extends Tile> genericOutputList) {
		int size = genericInputList.size();
		assertEquals(size, genericOutputList.size());
		for (int i = 0; i < genericInputList.size(); i++) {
			assertEquals(genericInputList.get(i), genericOutputList.get(i));
		}
	}

}
