
package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.guiClasses.Facade;

import roboRallyPackage.*;


/**
 * @author Jonas
 *
 */
public class BoardTest {

	
	private Facade facade;
	private Board boardOne;
	private Board boardTwo;
	
	private Robot robotOne;
	private Robot robotTwo;
	private Wall wallOne;
	private Wall wallTwo;
	private Battery batteryOne;
	private Battery batteryTwo;
	private Position positionOne;

	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Position positionOne = new Position(1, 5);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		facade = new Facade();
		boardOne = facade.createBoard(20, 30);
		robotOne = facade.createRobot(3, 4000);
		robotTwo = facade.createRobot(2, 3000);
		batteryOne = facade.createBattery(4500, 50);
		batteryTwo = facade.createBattery(300, 5);
		wallOne = facade.createWall();
		wallTwo =facade.createWall();
		
		
	}

	
	/**
	 * Test method for {@link roboRallyPackage.Board#Board(long, long)}.
	 */
	@Test
	public void createBoard() {
		boardTwo = facade.createBoard(50, 100);
		assertTrue(boardTwo != null);
		assertFalse(boardTwo.isTerminated());
		assertTrue(facade.getBatteries(boardTwo).isEmpty());
		assertTrue(facade.getRobots(boardTwo).isEmpty());
		assertTrue(facade.getWalls(boardTwo).isEmpty());
		assertEquals(50, boardTwo.getWidth());
		assertEquals(100, boardTwo.getHeight());
	}
	
	/**
	 * Test method for {@link roboRallyPackage.Board#Board(long, long)}.
	 */
	@Test
	public void createBoard_invalidWidth() {
		boardTwo = facade.createBoard(-50, 100);
		assertTrue(boardTwo != null);
		assertFalse(boardTwo.isTerminated());
		assertTrue(facade.getBatteries(boardTwo).isEmpty());
		assertTrue(facade.getRobots(boardTwo).isEmpty());
		assertTrue(facade.getWalls(boardTwo).isEmpty());
		assertEquals(10, boardTwo.getWidth());
		assertEquals(100, boardTwo.getHeight());
	}
	/**
	 * Test method for {@link roboRallyPackage.Board#Board(long, long)}.
	 */
	@Test
	public void createBoard_invalidHeight() {
		boardTwo = facade.createBoard(50, -100);
		assertTrue(boardTwo != null);
		assertFalse(boardTwo.isTerminated());
		assertTrue(facade.getBatteries(boardTwo).isEmpty());
		assertTrue(facade.getRobots(boardTwo).isEmpty());
		assertTrue(facade.getWalls(boardTwo).isEmpty());
		assertEquals(50, boardTwo.getWidth());
		assertEquals(10, boardTwo.getHeight());
	}
	
	

	/**
	 * Test method for {@link roboRallyPackage.Board#terminate()}.
	 */
	@Test
	public void testTerminate_noElements() {
		boardOne.terminate();
		assertTrue(boardOne.isTerminated());
	}
	
	/**
	 * Test method for {@link roboRallyPackage.Board#terminate()}.
	 */
	@Test
	public void testTerminate_withElements() {
		facade.putBattery(boardOne, 1, 2, batteryOne);
		facade.putBattery(boardOne, 2, 3, batteryTwo);
		facade.putRobot(boardOne, 5, 6, robotOne);
		facade.putRobot(boardOne, 7, 8, robotTwo);
		facade.putWall(boardOne, 9, 10, wallOne);
		facade.putWall(boardOne, 11, 12, wallTwo);
			
		boardOne.terminate();
		
		assertTrue(boardOne.isTerminated());
		assertTrue(batteryOne.isTerminated());
		assertTrue(batteryTwo.isTerminated());
		assertTrue(robotOne.isTerminated());
		assertTrue(robotTwo.isTerminated());
		assertTrue(wallOne.isTerminated());
		assertTrue(wallTwo.isTerminated());	
	}
	
	
	
	
	
	@Test 
	public void putElements_robots(){
	
		facade.putRobot(boardOne, 1, 2, robotOne);
		facade.putRobot(boardOne, 5, 6, robotTwo);
		assertFalse(facade.getRobots(boardOne).isEmpty());
		assertTrue(facade.getRobots(boardOne).contains(robotOne));
		assertTrue(facade.getRobots(boardOne).contains(robotTwo));
		assertFalse(boardOne.isTerminated());
		assertFalse(robotOne.isTerminated());
		assertFalse(robotTwo.isTerminated());
		assertTrue(robotOne.getBoard() == boardOne);		
	}
	
	

	/**
	 * Test method for {@link roboRallyPackage.Board#getWidth()}.
	 */
	@Test
	public void testGetWidth() {
		assertEquals(20, boardOne.getWidth());
	}

	/**
	 * Test method for {@link roboRallyPackage.Board#canHaveAsWidth(long)}.
	 */
	@Test
	public void testCanHaveAsWidth_validWidth() {
		assertTrue(boardOne.canHaveAsWidth(100));
		
	}
	/**
	 * Test method for {@link roboRallyPackage.Board#canHaveAsWidth(long)}.
	 */
	@Test
	public void testCanHaveAsWidth_negativeWidth() {
		assertFalse(boardOne.canHaveAsWidth(-100));
		
	}
	/**
	 * Test method for {@link roboRallyPackage.Board#canHaveAsWidth(long)}.
	 */
	@Test
	public void testCanHaveAsWidth_maxWidth() {
		assertTrue(boardOne.canHaveAsWidth(Board.getMaxWidth()));
		
	}


	/**
	 * Test method for {@link roboRallyPackage.Board#getMaxWidth()}.
	 */
	@Test
	public void testGetMaxWidth() {
		assertEquals(Long.MAX_VALUE,Board.getMaxWidth());
	}

	/**
	 * Test method for {@link roboRallyPackage.Board#getHeight()}.
	 */
	@Test
	public void testGetHeight() {
		assertEquals(30, boardOne.getHeight());
	}

	/**
	 * Test method for {@link roboRallyPackage.Board#canHaveAsHeight(long)}.
	 */
	@Test
	public void testCanHaveAsHeight_validHeight() {
		assertTrue(boardOne.canHaveAsHeight(100));
	}

	/**
	 * Test method for {@link roboRallyPackage.Board#canHaveAsHeight(long)}.
	 */
	@Test
	public void testCanHaveAsHeight_negativeHeight() {
		assertFalse(boardOne.canHaveAsHeight(-100));
	}
	
	/**
	 * Test method for {@link roboRallyPackage.Board#canHaveAsHeight(long)}.
	 */
	@Test
	public void testCanHaveAsHeight_maxHeight() {
		assertTrue(boardOne.canHaveAsHeight(Board.getMaxHeight()));
	}
	
	
	
	/**
	 * Test method for {@link roboRallyPackage.Board#getMaxHeight()}.
	 */
	@Test
	public void testGetMaxHeight() {
		assertEquals(Long.MAX_VALUE,Board.getMaxHeight());
	}

	/**
	 * Test method for {@link roboRallyPackage.Board#isValidPositionOnBoard(roboRallyPackage.Position)}.
	 */
	@Test
	public void testIsValidPositionOnBoard_positionNull() {
		assertFalse(boardOne.isValidPositionOnBoard(null));
	}

	/**
	 * Test method for {@link roboRallyPackage.Board#isValidPositionOnBoard(roboRallyPackage.Position)}.
	 */
	@Test
	public void testIsValidPositionOnBoard_positionWithXCoordANDYCoordZero() {
		Position position= new Position(0,0);
		assertTrue(boardOne.isValidPositionOnBoard(position));
	}
	

	@Test
	public void testGetElements_noElementsAtGivenPosition(){
		facade.putRobot(boardOne, 1, 2, robotOne);
		facade.putRobot(boardOne, 5, 6, robotTwo);
		Position position = new Position(5, 5);
		assertEquals(null, boardOne.getElements(position));
		
	}



	/**
	 * Test method for {@link roboRallyPackage.Board#getFirstElementStartingAt(roboRallyPackage.Position, roboRallyPackage.Orientation)}.
	 */
	@Test
	public void testGetFirstElementStartingAt_validCase_oneElement() {
		facade.putBattery(boardOne, 1, 4, batteryOne);
		Position positonThree = new Position(4,4);
		assertTrue(boardOne.getFirstElementStartingAt(positonThree, Orientation.LEFT) == batteryOne);
	}


	
	/**
	 * Test method for {@link roboRallyPackage.Board#getFirstElementStartingAt(roboRallyPackage.Position, roboRallyPackage.Orientation)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testGetFirstElementStartingAt_positionNull() {
		facade.putBattery(boardOne, 1, 4, batteryOne);
		facade.putBattery(boardOne,1, 4, batteryTwo);
		facade.putRobot(boardOne, 1, 4, robotOne);
		boardOne.getFirstElementStartingAt(null, Orientation.LEFT);
	}
	/**
	 * Test method for {@link roboRallyPackage.Board#getFirstElementStartingAt(roboRallyPackage.Position, roboRallyPackage.Orientation)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testGetFirstElementStartingAt_orientationNull() {
		facade.putBattery(boardOne, 1, 4, batteryOne);
		facade.putBattery(boardOne,1, 4, batteryTwo);
		facade.putRobot(boardOne, 1, 4, robotOne);
		Position positonThree = new Position(4,4);
		boardOne.getFirstElementStartingAt(positonThree, null);
	}

	
	/**
	 * Test method for {@link roboRallyPackage.Board#removeElement(roboRallyPackage.gameElementClasses.Element)}.
	 */
	@Test
	public void testRemoveElement_elementOnThisBoard() {
		facade.putBattery(boardOne, 1, 1, batteryOne);
		boardOne.removeElement(batteryOne);
		assertFalse(facade.getBatteries(boardOne).contains(batteryOne));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testRemoveElement_elementOnOtherBoard() {
		boardTwo = facade.createBoard(25, 30);
		facade.putBattery(boardTwo, 1, 1, batteryOne);
		boardOne.removeElement(batteryOne);
	
	}
	
	
	
	

	/**
	 * Test method for {@link roboRallyPackage.Board#putElement(roboRallyPackage.Position, roboRallyPackage.gameElementClasses.Element)}.
	 */
	@Test
	public void testPutElement_TerminatedElement() {
		robotTwo.terminate();
		facade.putRobot(boardOne, 1, 1, robotTwo);
		assertTrue(robotTwo.isTerminated());
		assertFalse(facade.getRobots(boardOne).contains(robotTwo));
		assertFalse(boardOne.isTerminated());
	}

	/**
	 * Test method for {@link roboRallyPackage.Board#putElement(roboRallyPackage.Position, roboRallyPackage.gameElementClasses.Element)}.
	 */
	@Test
	public void testPutElement_ElementCannotBePlacedAtposition() {
		facade.putWall(boardOne, 1, 1, wallOne);
		facade.putRobot(boardOne, 1, 1, robotOne);
		
		assertFalse(facade.getRobots(boardOne).contains(robotOne));
		assertTrue(robotOne.isTerminated());
		assertTrue(robotOne.getBoard()==null);
		
	}

	@Test(expected = IllegalStateException.class)
	public void testPutElement_terminatedElement(){
		batteryOne.terminate();
		boardOne.putElement(positionOne, batteryOne);
	}

	
	

	/**
	 * Test method for {@link roboRallyPackage.Board#merge(roboRallyPackage.Board)}.
	 */
	@Test
	public void testMerge() {
		facade.putBattery(boardOne, 1, 1, batteryOne);
		boardTwo = facade.createBoard(50, 45);
		facade.putBattery(boardTwo, 4, 5, batteryTwo);
		facade.merge(boardOne, boardTwo);
		assertTrue(boardTwo.isTerminated());
		assertFalse(boardOne.isTerminated());
		assertTrue(facade.getBatteries(boardOne).contains(batteryOne));
		assertTrue(facade.getBatteries(boardOne).contains(batteryTwo));
		assertFalse(batteryOne.isTerminated());
		assertFalse(batteryTwo.isTerminated());
		assertEquals(boardOne, batteryOne.getBoard());
		assertEquals(boardOne, batteryTwo.getBoard());
		assertEquals(1, batteryOne.getPosition().getCoordX());
		assertEquals(1, batteryOne.getPosition().getCoordY());
		assertEquals(4, batteryTwo.getPosition().getCoordX());
		assertEquals(5, batteryTwo.getPosition().getCoordY());
	}
	
	/**
	 * Test method for {@link roboRallyPackage.Board#merge(roboRallyPackage.Board)}.
	 */
	@Test
	public void testMerge_boardTerminated() {
		
		boardTwo = facade.createBoard(50, 45);
		boardTwo.terminate();
		facade.merge(boardOne, boardTwo);
		assertTrue(boardTwo.isTerminated());
		assertFalse(boardOne.isTerminated());
	}
	
	
	
	/**
	 * Test method for {@link roboRallyPackage.Board#toString()}.
	 */
	@Test
	public void testToString() {
		assertFalse(boardOne.toString().isEmpty());
		
	}


}
