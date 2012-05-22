package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.guiClasses.*;

public class FacadeTest {

	private Facade facade;
	private Board board;
	private static Robot immutableRobotOne;
	private Robot mutableRobotOne;
	private Robot mutableRobotTwo;
	
	@org.junit.Before
	public void setUp() {
		facade = new Facade();
		board = facade.createBoard(1000, 2000);
	}
	
	@Before
	public void setUpMuttableFixture(){
		mutableRobotOne = facade.createRobot(1, 20000);
		facade.putRobot(board, 1, 1, mutableRobotOne);
		
		mutableRobotTwo = facade.createRobot(2, 5000);
		facade.putRobot(board, 5, 4, mutableRobotTwo);
	}
	@BeforeClass
	public static void setUpImmutableFixture(){
		immutableRobotOne = new Robot(new Position(2,3), new Board(1000,2000), Orientation.RIGHT, (double) 20000, (double) 20000);
	}
	
	
	@Test
	public void createRobotTest_Normal() {
		Robot robot = facade.createRobot(0, 5000);
		facade.putRobot(board, 5, 7, robot);
		assertNotNull(robot);
		assertEquals(5, facade.getRobotX(robot));
		assertEquals(7, facade.getRobotY(robot));
		assertEquals(0, facade.getOrientation(robot));
		assertEquals(5000, facade.getEnergy(robot), 0);
	}
	
	@Test
	public void createRobotTest_UnvalidCoordX(){
		Robot robot = facade.createRobot(0, 5000);
		facade.putRobot(board, -1, 7, robot);
		assertNotNull(robot);
		assertEquals(0, facade.getRobotX(robot));
		assertEquals(7, facade.getRobotY(robot));
		assertEquals(0, facade.getOrientation(robot));
		assertEquals(5000, facade.getEnergy(robot), 0);
	}
	
	@Test
	public void createRobotTest_UnvalidCoordY(){
		Robot robot = facade.createRobot(0, 5000);
		facade.putRobot(board, 5, -1, robot);
		assertNotNull(robot);
		assertEquals(5, facade.getRobotX(robot));
		assertEquals(0, facade.getRobotY(robot));
		assertEquals(0, facade.getOrientation(robot));
		assertEquals(5000, facade.getEnergy(robot), 0);
	}
	
	@Test
	public void getCoordXTest(){
		assertTrue(immutableRobotOne.getPosition().getCoordX() == 2);
	}
	
	@Test
	public void getCoordYTest(){
		assertTrue(immutableRobotOne.getPosition().getCoordY() == 3);
	}
	
	
	@Test
	public void turnClockwiseTest(){
		Robot robot = facade.createRobot(2, 2000);
		facade.putRobot(board, 1, 1, robot);
		facade.turn(robot);
		assertTrue(facade.getOrientation(robot) == 2);
		assertTrue(facade.getEnergy(robot) == 19900);
		
	}
	
	@Test
	public void getOrientationTest(){
		Robot robot = facade.createRobot(0, 20000);
		facade.putRobot(board, 4, 4, robot);
		assertTrue(robot.getOrientation().ordinal() == 0);
	}
	
	
	
	@Test
	public void testMove_Up() {
		Robot robot = facade.createRobot(0, 5000);
		facade.putRobot(board, 5, 7, robot);
		facade.move(robot);
		assertEquals(5, facade.getRobotX(robot));
		assertEquals(6, facade.getRobotY(robot));
		assertEquals(0, facade.getOrientation(robot));
		assertEquals(5000 - 500, facade.getEnergy(robot), 0.1);
	}

	
	
	
	@Test
	public void testMove_Right() {
		Robot robot = facade.createRobot(1, 5000);
		facade.putRobot(board, 5, 7, robot);
		facade.move(robot);
		assertEquals(6, facade.getRobotX(robot));
		assertEquals(7, facade.getRobotY(robot));
		assertEquals(1, facade.getOrientation(robot));
		assertEquals(5000 - 500, facade.getEnergy(robot), 0.1);
	}
	
	
	@Test
	public void testMove_Down() {
		Robot robot = facade.createRobot(2, 5000);
		facade.putRobot(board, 5, 7, robot);
		facade.move(robot);
		assertEquals(5, facade.getRobotX(robot));
		assertEquals(8, facade.getRobotY(robot));
		assertEquals(2, facade.getOrientation(robot));
		assertEquals(5000 - 500, facade.getEnergy(robot), 0.1);
	}

	
	@Test
	public void testMove_Left() {
		Robot robot = facade.createRobot(3, 5000);
		facade.putRobot(board, 5, 7, robot);
		facade.move(robot);
		assertEquals(4, facade.getRobotX(robot));
		assertEquals(7, facade.getRobotY(robot));
		assertEquals(3, facade.getOrientation(robot));
		assertEquals(5000 - 500, facade.getEnergy(robot), 0.1);
	}
	
	@Test
	public void testMoveInsufficientEnergy() {
		Robot robot = facade.createRobot(1, 250);
		facade.putRobot(board, 0, 0, robot);
		facade.move(robot);
		assertEquals(0, facade.getRobotX(robot));
		assertEquals(0, facade.getRobotY(robot));
		assertEquals(1, facade.getOrientation(robot));
		assertEquals(250, facade.getEnergy(robot), 0.1);
	}
	
	@Test
	public void testMoveNextTo_enoughEnergy() {
		Robot robot1 = facade.createRobot(1, 20000);
		facade.putRobot(board, 1, 1, robot1);
		Robot robot2 = facade.createRobot(1, 20000);
		facade.putRobot(board, 3, 3, robot2);
		facade.moveNextTo(robot2, robot1);
		assertTrue((facade.getRobotX(robot1) == 3 && facade.getRobotY(robot1) == 2 && facade.getRobotX(robot2) == 3 && facade.getRobotY(robot2) == 3)
				|| (facade.getRobotX(robot1) == 3 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 3 && facade.getRobotY(robot2) == 2)
				|| (facade.getRobotX(robot1) == 2 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 3 && facade.getRobotY(robot2) == 1));		  			
	}
	
	@Test
	public void testMoveNextTo_2notEnoughEnergy() {
		Robot robot1 = facade.createRobot(1, 600);
		facade.putRobot(board, 1, 1, robot1);
		Robot robot2 = facade.createRobot(1, 600);
		facade.putRobot(board, 3, 3, robot2);
		facade.moveNextTo(robot2, robot1);
		assertTrue((facade.getRobotX(robot1) == 2 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 3 && facade.getRobotY(robot2) == 2));
	}
	
	@Test
	public void testMoveNextTo_1notEnoughEnergy() {
		Robot robot1 = facade.createRobot(1, 600);
		facade.putRobot(board, 1, 1, robot1);
		Robot robot2 = facade.createRobot(1, 20000);
		facade.putRobot(board, 5, 5, robot2);
		facade.moveNextTo(robot2, robot1);
		assertTrue((facade.getRobotX(robot1) == 1 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 2 && facade.getRobotY(robot2) == 1)
				|| (facade.getRobotX(robot1) == 1 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 1 && facade.getRobotY(robot2) == 2)
				|| (facade.getRobotX(robot1) == 2 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 3 && facade.getRobotY(robot2) == 1)
				|| (facade.getRobotX(robot1) == 2 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 2 && facade.getRobotY(robot2) == 2));		  			
	}
	
	@Test
	public void testMoveNextTo_1notEnoughEnergy_part_b() {
		Robot robot1 = facade.createRobot(1, 600);
		facade.putRobot(board, 1, 1, robot1);
		Robot robot2 = facade.createRobot(1, 20000);
		facade.putRobot(board, 5, 5, robot2);
		facade.moveNextTo(robot1, robot2);
		assertTrue((facade.getRobotX(robot1) == 1 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 2 && facade.getRobotY(robot2) == 1)
				|| (facade.getRobotX(robot1) == 1 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 1 && facade.getRobotY(robot2) == 2)
				|| (facade.getRobotX(robot1) == 2 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 3 && facade.getRobotY(robot2) == 1)
				|| (facade.getRobotX(robot1) == 2 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 2 && facade.getRobotY(robot2) == 2));		  			
	}
	
	@Test
	public void testMoveNextTo_1notEnoughEnergy_part_c() {
		Robot robot1 = facade.createRobot(2, 600);
		facade.putRobot(board, 1, 2, robot1);
		Robot robot2 = facade.createRobot(3, 800);
		facade.putRobot(board, 4, 0, robot2);
		facade.moveNextTo(robot1, robot2);
		assertEquals(2, facade.getRobotX(robot1));
		assertEquals(2, facade.getRobotY(robot1));
		assertEquals(3, facade.getRobotX(robot2));
		assertEquals(0, facade.getRobotY(robot2));
	}
	
	
	@Test
	public void turn_NotEnoughEnergy(){
		Robot robot = facade.createRobot(1, 50);
		facade.putRobot(board, 1, 1, robot);
		facade.turn(robot);
		assertTrue(facade.getOrientation(robot) == 1);
	}	
	@Test
	public void move_OfBoard(){
		Robot robot = facade.createRobot(3, 20000);
		facade.putRobot(board, 0, 2, robot);
		facade.move(robot);
		assertTrue(facade.getRobotX(robot) == 0);
		assertTrue(facade.getRobotY(robot) == 2);
		assertTrue(facade.getOrientation(robot)==3);
		assertTrue(facade.getEnergy(robot)==20000);
	}
	
	@Test
	public void getCurrentEnergyTest(){
		assertTrue(immutableRobotOne.getEnergy() == 20000);
	}
	
	@Test
	public void getMaxEnergyTest(){
		assertTrue(immutableRobotOne.getMaxEnergy() == 20000);
	}
	
	@Test
	public void getEnergyFractionTest(){
		assertTrue(mutableRobotTwo.getEnergyFraction() == 25);
	}
	
	@Test
	public void rechargeEnergy(){
		Robot robot = facade.createRobot(3, 5000);
		facade.putRobot(board, 5, 7, robot);
		robot.recharge(500);
		assertTrue(robot.getEnergy() == 5500);
	}
	
}