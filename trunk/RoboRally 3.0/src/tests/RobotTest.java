/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roboRallyPackage.Board;
import roboRallyPackage.Orientation;
import roboRallyPackage.Position;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.guiClasses.Facade;

/**
 * @author Nele
 *
 */
public class RobotTest {
	
	private static Facade facade;
	private Board mutableBoardOne;
	private static Robot immutableRobotOne;
	private Robot mutableRobotOne;
	private Robot mutableRobotTwo;
	private Battery mutableBatteryOne;
	private RepairKit mutableRepairKitOne;
	private SurpriseBox mutableSurpriseBoxOne;
	private Wall mutableWallOne;
	
	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpImmutableFixture() throws Exception {
		facade = new Facade();
		immutableRobotOne = new Robot(new Position(1,1), null, Orientation.RIGHT,(double) 5000,  (double) 20000);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpMutableFixture() throws Exception {
		
		//orientation of the robot is to the right
		mutableRobotOne = new Robot(null, null, Orientation.RIGHT, (double) 18000, (double) 20000); 
		
		mutableRobotTwo = new Robot(null, null, Orientation.RIGHT, (double) 17000, (double) 16000);
		
		mutableBoardOne = new Board(200, 300);
		
		mutableBatteryOne = new Battery(5000,5);
		
		mutableSurpriseBoxOne = new SurpriseBox(5);
		
		mutableRepairKitOne = new RepairKit(2000, 5);
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#terminate()}.
	 */
	@Test
	public void testTerminate_withoutElements() {
		mutableRobotOne.terminate();
		assertTrue(mutableRobotOne.isTerminated());
	}
	
	
//	@Test
//	public void testTerminate_withElements() {
//		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
//		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
//		facade.putRepairKit(mutableBoardOne, 1, 1, mutableRepairKitOne);
//		facade.putSurpriseBox(mutableBoardOne, 1, 1, mutableSurpriseBoxOne);
//		
//		
//		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
//		facade.pickUpRepairKit(mutableRobotOne, mutableRepairKitOne);
//		facade.pickUpSurpriseBox(mutableRobotOne, mutableSurpriseBoxOne);
//		
//		mutableRobotOne.terminate();
//		
//		assertFalse(mutableRobotOne.getPossessions().contains(mutableBatteryOne)
//				|| mutableRobotOne.getPossessions().contains(mutableRepairKitOne)
//				|| mutableRobotOne.getPossessions().contains(mutableSurpriseBoxOne));
//		
//		
//		assertTrue(mutableRobotOne.isTerminated());
//		assertTrue(mutableBatteryOne.isTerminated());
//		assertTrue(mutableRepairKitOne.isTerminated());
//		assertTrue(mutableSurpriseBoxOne.isTerminated());
//		assertFalse(facade.getRobots(mutableBoardOne).contains(mutableRobotOne));
//		
//	}
	
	
	
	
	
	
	
	
	

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#canSharePositionWith(roboRallyPackage.gameElementClasses.Element)}.
	 */
	@Test
	public void testCanSharePositionWith_otherRobot() {
		
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#Robot(roboRallyPackage.Position, roboRallyPackage.Board, roboRallyPackage.Orientation, double, double)}.
	 */
	@Test
	public void testRobot_emptyConstructor() {
		Robot testRobot = new Robot();
		assertTrue(testRobot.getPosition() == null);
		assertTrue(testRobot.getBoard() == null);
		assertTrue(testRobot.getOrientation() == Orientation.RIGHT);
		assertTrue(testRobot.getEnergy(EnergyUnit.WATTSECOND)==20000);
		assertTrue(testRobot.getMaxEnergy()==20000);
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getOrientation()}.
	 */
	@Test
	public void testGetOrientation() {
		
		assertTrue(immutableRobotOne.getOrientation() == Orientation.RIGHT);
		
	}
	

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#canHaveAsOrientation(roboRallyPackage.Orientation)}.
	 */
	@Test
	public void testCanHaveAsOrientation() {
		assertTrue(Robot.canHaveAsOrientation(Orientation.RIGHT)
				&&Robot.canHaveAsOrientation(Orientation.DOWN)
				&&Robot.canHaveAsOrientation(Orientation.LEFT)
				&&Robot.canHaveAsOrientation(Orientation.UP));
		assertFalse(Robot.canHaveAsOrientation(null));
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getEnergy()}.
	 */
	@Test
	public void testGetEnergy() {
		//immutableRobotOne = new Robot(new Position(1,1), null, Orientation.RIGHT,(double) 5000,  (double) 20000);
		assertTrue(immutableRobotOne.getEnergy(EnergyUnit.WATTSECOND) == 5000);
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#setEnergy(double)}.
	 */
	@Test
	public void testSetEnergy_notTerminated() {
		mutableRobotOne.setEnergy(100);
		assertTrue(mutableRobotOne.getEnergy(EnergyUnit.WATTSECOND)==100);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testSetEnergy_Terminated() {
		mutableRobotOne.terminate();
		mutableRobotOne.setEnergy(100);
		}
	
	
	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#canHaveAsEnergy(double)}.
	 */
//	@Test
//	public void testCanHaveAsEnergy() {
//		fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getMaxEnergy()}.
	 */
	@Test
	public void testGetMaxEnergy() {
		assertTrue(immutableRobotOne.getMaxEnergy()== 20000);
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#canHaveAsMaxEnergy(double)}.
	 */
	@Test
	public void testCanHaveAsMaxEnergy() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getEnergyFraction()}.
	 */
	@Test
	public void testGetEnergyFraction() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#recharge(double)}.
	 */
	@Test
	public void testRecharge() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#canAcceptForRecharge(double)}.
	 */
	
	
	@Test
	public void testCanAcceptForRecharge_validAmount() {
		assertTrue(mutableRobotOne.canAcceptForRecharge(100));
	}

	
	@Test
	public void testCanAcceptForRecharge_negativeAmount() {
		assertFalse(mutableRobotOne.canAcceptForRecharge(-1));
	}

	
	@Test
	public void testCanAcceptForRecharge_higherThanMaxEnergy() {
		assertFalse(mutableRobotOne.canAcceptForRecharge(mutableRobotOne.getMaxEnergy()+1));
	}
	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getEnergyRequiredToReach(roboRallyPackage.Position)}.
	 */
	@Test
	public void testGetEnergyRequiredToReach() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getCostToTurn()}.
	 */
	@Test
	public void testGetCostToTurn() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getCostToShoot()}.
	 */
	@Test
	public void testGetCostToShoot() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getCostToMove()}.
	 */
	@Test
	public void testGetCostToMove() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getAdditionalCostToMove()}.
	 */
	@Test
	public void testGetAdditionalCostToMove() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getTotalCostToMove()}.
	 */
	@Test
	public void testGetTotalCostToMove() {
		fail("Not yet implemented");
	}

//	/**
//	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getPossessions()}.
//	 */
//	@Test
//	public void testGetPossessions() {
//		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
//		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
//		facade.putSurpriseBox(mutableBoardOne, 1, 1, mutableSurpriseBoxOne);
//		facade.putRepairKit(mutableBoardOne, 1, 1, mutableRepairKitOne);
//		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
//		facade.pickUpSurpriseBox(mutableRobotOne, mutableSurpriseBoxOne);
//		facade.pickUpRepairKit(mutableRobotOne, mutableRepairKitOne);
//		assertTrue(mutableRobotOne.getPossessions().contains(mutableBatteryOne)
//				&& mutableRobotOne.getPossessions().contains(mutableRepairKitOne)
//				&& mutableRobotOne.getPossessions().contains(mutableSurpriseBoxOne));
//		
//	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getTotalWeightPossessions()}.
	 */
	@Test
	public void testGetTotalWeightPossessions() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getPossessionsString()}.
	 */
	@Test
	public void testGetPossessionsString() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanCarry(){
		
		assertTrue(mutableRobotOne.canCarry(mutableBatteryOne));
	}
	@Test
	public void testCanCarry_null(){
		assertFalse(mutableRobotOne.canCarry(null));
	}
	
	@Test
	public void testCanCarry_terminatedItem(){
		mutableBatteryOne.terminate();
		assertFalse(mutableRobotOne.canCarry(mutableBatteryOne));
	}
//	@Test
//	public void testCanCarry_toHeavy(){
//		Battery testBattery = new Battery(100, Integer.MAX_VALUE);
//		facade.putBattery(mutableBoardOne, 1, 1, testBattery);
//		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
//		facade.pickUpBattery(mutableRobotOne, testBattery);
//		assertFalse(mutableRobotOne.canCarry(mutableBatteryOne));
//		}
	@Test
	public void testCanCarry_robotAlreadyCarriesItem(){
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
		
		assertFalse(mutableRobotOne.canCarry(mutableBatteryOne));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#pickUp(roboRallyPackage.gameElementClasses.Item)}.
	 */
	@Test
	public void testPickUp() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#canPickUp(roboRallyPackage.gameElementClasses.Item)}.
	 */
	@Test
	public void testCanPickUp() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getMaxWeightToCarry()}.
	 */
	@Test
	public void testGetMaxWeightToCarry() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#drop(roboRallyPackage.gameElementClasses.Item)}.
	 */
	@Test
	public void testDrop() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#canDrop(roboRallyPackage.gameElementClasses.Item)}.
	 */
	@Test
	public void testCanDrop() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#use(roboRallyPackage.gameElementClasses.Item)}.
	 */
	@Test
	public void testUse() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#shoot()}.
	 */
	@Test
	public void testShoot() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#canShoot()}.
	 */
	@Test
	public void testCanShoot() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getEnergyToShoot()}.
	 */
	@Test
	public void testGetEnergyToShoot() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#turnClockwise()}.
	 */
	@Test
	public void testTurnClockwise_validCase() {
		mutableRobotOne.turnClockwise();
		assertTrue(mutableRobotOne.getOrientation() == Orientation.DOWN);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testTurnClockwise_terminatedRobot() {
		mutableRobotOne.terminate();
		mutableRobotOne.turnClockwise();
		 
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#turnCounterClockwise()}.
	 */
	@Test
	public void testTurnCounterClockwise() {
		mutableRobotOne.turnCounterClockwise();
		assertTrue(mutableRobotOne.getOrientation() == Orientation.UP);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testTurnCounterClockwise_terminatedRobot() {
		mutableRobotOne.terminate();
		mutableRobotOne.turnCounterClockwise();
	}
	
	
	
	
	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#moveOneStep()}.
	 */
	@Test
	public void testMoveOneStep_validCase() {
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		// energy of mutable robot one is 18000 Ws
		mutableRobotOne.moveOneStep();
		assertTrue(mutableRobotOne.getOrientation() == Orientation.RIGHT);
		assertTrue(mutableRobotOne.getBoard() == mutableBoardOne);
		assertTrue(mutableRobotOne.getPosition().getCoordX() == 2);
		assertTrue(mutableRobotOne.getPosition().getCoordY() == 1);
		assertTrue(mutableRobotOne.getEnergy(EnergyUnit.WATTSECOND) == 18000-Robot.getCostToMove());
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#transferEnergy(roboRallyPackage.gameElementClasses.IEnergyHolder, double)}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testTransferEnergy() {
	immutableRobotOne.transferEnergy(mutableRobotTwo, 100);
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#moveNextTo(roboRallyPackage.gameElementClasses.Robot)}.
	 */
	@Test
	public void testMoveNextTo() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Element#Element(roboRallyPackage.Position, roboRallyPackage.Board)}.
	 */
	@Test
	public void testElement() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Element#getPosition()}.
	 */
	@Test
	public void testGetPosition() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Element#setPosition(roboRallyPackage.Position)}.
	 */
	@Test
	public void testSetPosition() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Element#canHavePosition(roboRallyPackage.Position)}.
	 */
	@Test
	public void testCanHavePosition() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Element#getBoard()}.
	 */
	@Test
	public void testGetBoard() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Element#setBoard(roboRallyPackage.Board)}.
	 */
	@Test
	public void testSetBoard() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Element#toString()}.
	 */
	@Test
	public void testToString1() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link roboRallyPackage.Terminatable#isTerminated()}.
	 */
	@Test
	public void testIsTerminated() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#Object()}.
	 */
	@Test
	public void testObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#getClass()}.
	 */
	@Test
	public void testGetClass() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#clone()}.
	 */
	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#toString()}.
	 */
	@Test
	public void testToString2() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#notify()}.
	 */
	@Test
	public void testNotify() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#notifyAll()}.
	 */
	@Test
	public void testNotifyAll() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#wait(long)}.
	 */
	@Test
	public void testWaitLong() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#wait(long, int)}.
	 */
	@Test
	public void testWaitLongInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#wait()}.
	 */
	@Test
	public void testWait() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#finalize()}.
	 */
	@Test
	public void testFinalize() {
		fail("Not yet implemented");
	}

}
