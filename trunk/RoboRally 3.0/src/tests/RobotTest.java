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
import roboRallyPackage.exceptionClasses.IllegalBoardException;
import roboRallyPackage.exceptionClasses.IllegalPositionException;
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
//	
	
	
	
	
	
	
	
	

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
		assertTrue(testRobot.getEnergy(EnergyUnit.WATTSECOND)==0);
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

	@Test
	public void testCanHaveAsMaxEnergy_validCase() {
		assertTrue(immutableRobotOne.canHaveAsMaxEnergy(18000));
	}
	
	
	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#canHaveAsMaxEnergy(double)}.
	 */
	@Test
	public void testCanHaveAsMaxEnergy_ToHigh() {
		assertFalse(immutableRobotOne.canHaveAsMaxEnergy(30000));
	}

	@Test
	public void testCanHaveAsMaxEnergy_ToLow() {
		assertFalse(immutableRobotOne.canHaveAsMaxEnergy(10));
	}
	
	@Test
	public void testCanHaveAsMaxEnergy_UnvalidEnergy() {
		assertFalse(immutableRobotOne.canHaveAsMaxEnergy(-1));
	}
	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getEnergyFraction()}.
	 */
	@Test
	public void testGetEnergyFraction() {
		assertTrue(mutableRobotOne.getEnergyFraction() == 90);
	}

	@Test
	public void testRecharge(){
		mutableRobotOne.recharge(100);
		assertTrue(mutableRobotOne.getEnergy(EnergyUnit.WATTSECOND) == 18100);
	}
	
	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#recharge(double)}.
	 */
	@Test(expected = IllegalStateException.class)
	public void testRecharge_terminatedRobot() {
		mutableRobotOne.terminate();
		mutableRobotOne.recharge(100);
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
		//the board of mutableRobot is null
		Robot testBot = new Robot(new Position(1,1), mutableBoardOne, Orientation.RIGHT, 20000, 20000);
		
		Position position = new Position(2,2);
		assertTrue(testBot.getEnergyRequiredToReach(position) == (2*testBot.getTotalCostToMove()+Robot.getCostToTurn()));	
	}
	
	
	@Test(expected = IllegalBoardException.class)
	public void testGetEnergyRequiredToReach_boardNull() {
		//the board of mutableRobot is null
		mutableRobotOne.getEnergyRequiredToReach(new Position(1,1));
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getCostToTurn()}.
	 */
	@Test
	public void testGetCostToTurn() {
		assertTrue(Robot.getCostToTurn() == 100);
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getCostToShoot()}.
	 */
	@Test
	public void testGetCostToShoot() {
		assertTrue(Robot.getCostToShoot() == 1000);
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getCostToMove()}.
	 */
	@Test
	public void testGetCostToMove() {
		assertTrue(Robot.getCostToMove() == 500);
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getAdditionalCostToMove()}.
	 */
	@Test
	public void testGetAdditionalCostToMove() {
		assertTrue(Robot.getAdditionalCostToMove() == 50);		
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getTotalCostToMove()}.
	 */
	@Test
	public void testGetTotalCostToMove() {
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		Battery testBat = new Battery(1000, 5000);
		facade.putBattery(mutableBoardOne, 1, 1, testBat);
		facade.pickUpBattery(mutableRobotOne, testBat);
		assertTrue(mutableRobotOne.getTotalCostToMove() == 750);
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
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		
		facade.putRepairKit(mutableBoardOne, 1, 1, mutableRepairKitOne);
		facade.putSurpriseBox(mutableBoardOne, 1, 1, mutableSurpriseBoxOne);
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		
		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
		facade.pickUpRepairKit(mutableRobotOne, mutableRepairKitOne);
		facade.pickUpSurpriseBox(mutableRobotOne, mutableSurpriseBoxOne);
		
		assertTrue(mutableRobotOne.getTotalWeightPossessions() == 15);
	}

	
	@Test
	public void testGetIthHeaviestPossession_validCase(){
		Battery testBat = new Battery(2000, 5000);
		RepairKit testKit = new RepairKit(2000, 2000);
				
		facade.putBattery(mutableBoardOne, 1, 1,testBat);
		facade.putRepairKit(mutableBoardOne, 1, 1, testKit);
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.pickUpRepairKit(mutableRobotOne, testKit);
		facade.pickUpBattery(mutableRobotOne, testBat);
		assertTrue(mutableRobotOne.getIthHeaviestPossession(1) ==  testBat );
		
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetIthHeaviestPossession_indexToHigh(){
		mutableRobotOne.getIthHeaviestPossession(4);
	}
	
	
	
	
	
	
	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getPossessionsString()}.
	 */
	@Test
	public void testGetPossessionsString() {	
	
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
		assertTrue(mutableRobotOne.getPossessionsString().getClass() == String.class);
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
	public void testPickUp_validCase() {
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		
		assertTrue(mutableRobotOne.canCarry(mutableBatteryOne));
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#canPickUp(roboRallyPackage.gameElementClasses.Item)}.
	 */
	@Test
	public void testCanPickUp_cannotCarry() {
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		mutableBatteryOne.terminate();
		assertFalse(mutableRobotOne.canCarry(mutableBatteryOne));
		
	}

	@Test
	public void testCanPickUp_positionItemNull() {
		Battery bat= new Battery(null,mutableBoardOne, 1000, 5);
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		assertFalse(mutableRobotOne.canCarry(bat));
		
	}
	
	@Test
	public void testCanPickUp_itemOtherPositionThanRobot() {
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		facade.putRobot(mutableBoardOne, 2, 2, mutableRobotOne);
		assertFalse(mutableRobotOne.canCarry(mutableBatteryOne));	
	}
	
	@Test
	public void testCanPickUp_boardItemNull() {
		facade.putBattery(null, 1, 1, mutableBatteryOne);
		facade.putRobot(mutableBoardOne, 2, 2, mutableRobotOne);
		assertFalse(mutableRobotOne.canCarry(mutableBatteryOne));	
	}
	
	
	
	
	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getMaxWeightToCarry()}.
	 */
	@Test
	public void testGetMaxWeightToCarry() {
		assertTrue(Robot.getMaxWeightToCarry() == Integer.MAX_VALUE);
	}

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#drop(roboRallyPackage.gameElementClasses.Item)}.
	 */
	@Test
	public void testDrop_validCase() {
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.putBattery(mutableBoardOne, 2, 1, mutableBatteryOne);
		facade.move(mutableRobotOne);
		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
		assertTrue(mutableRobotOne.getPossessions().contains(mutableBatteryOne));
		facade.move(mutableRobotOne);
		facade.dropBattery(mutableRobotOne, mutableBatteryOne);
		assertFalse(mutableRobotOne.getPossessions().contains(mutableBatteryOne));
		assertTrue(mutableBatteryOne.getPosition().getCoordX()==3 && mutableBatteryOne.getPosition().getCoordY()==1);
	}
	@Test(expected = AssertionError.class)
	public void testDrop_robotTerminated() {
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.putBattery(mutableBoardOne, 2, 1, mutableBatteryOne);
		facade.move(mutableRobotOne);
		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
		assertTrue(mutableRobotOne.getPossessions().contains(mutableBatteryOne));
		facade.move(mutableRobotOne);
		mutableRobotOne.terminate();
		facade.dropBattery(mutableRobotOne, mutableBatteryOne);
	}
	
	
	
	
	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#canDrop(roboRallyPackage.gameElementClasses.Item)}.
	 */
	@Test
	public void testCanDrop() {
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
		assertTrue(mutableRobotOne.canDrop(mutableBatteryOne));
	}

	@Test
	public void testCanDrop_itemNull() {
		assertFalse(mutableRobotOne.canDrop(null));
	}
	
	@Test
	public void testCanDrop_boardNull() {
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
		mutableRobotOne.setBoard(null);
		assertFalse(mutableRobotOne.canDrop(null));
	}	
	
	@Test
	public void testCanDrop_RobotPossesionsDoesntContainItem() {
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		assertFalse(mutableRobotOne.canDrop(mutableBatteryOne));
	}
	
	
	@Test
	public void testUse_validCaseAndItemTerminatedAfterUse(){
		
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		Battery tempBat = new Battery(100, 5000);
		facade.putBattery(mutableBoardOne, 1, 1, tempBat);
		facade.pickUpBattery(mutableRobotOne, tempBat);
		mutableRobotOne.use(tempBat);
		assertTrue(mutableRobotOne.getEnergy(EnergyUnit.WATTSECOND) == 18100);
		assertTrue(tempBat.isTerminated());
	}
	
	
	@Test
	public void testUse_validCaseAndItemNotTerminatedAfterUse(){
		
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		Battery tempBat = new Battery(5000, 5000);
		facade.putBattery(mutableBoardOne, 1, 1, tempBat);
		facade.pickUpBattery(mutableRobotOne, tempBat);
		mutableRobotOne.use(tempBat);
		assertTrue(mutableRobotOne.getEnergy(EnergyUnit.WATTSECOND) == 20000);
		assertFalse(tempBat.isTerminated());
		assertTrue(tempBat.getEnergy(EnergyUnit.WATTSECOND) == 3000);
	}
	
	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#canUse(roboRallyPackage.gameElementClasses.Item)}.
	 */
	@Test
	public void testCanUse_validCase() {
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
		mutableRobotOne.canUse(mutableBatteryOne);
	}
	
	@Test
	public void testCanUse_itemNull() {
		assertFalse(immutableRobotOne.canUse(null));
	}

	@Test
	public void testCanUse_RobotPossesionsDoesntContainItem() {
		assertFalse(immutableRobotOne.canUse(mutableBatteryOne));
	}
	
	
	@Test
	public void testTransferItems_validCase(){
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
		
		facade.putRobot(mutableBoardOne, 2, 1, mutableRobotTwo);
		
		mutableRobotOne.transferItems(mutableRobotTwo);
		
		assertFalse(mutableRobotOne.getPossessions().contains(mutableBatteryOne));
		assertTrue(mutableRobotTwo.getPossessions().contains(mutableBatteryOne));
	}
	
	
	
	@Test(expected = IllegalPositionException.class)
	public void testTransferItems_robotsNotNextToEachOther(){
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
		
		facade.putRobot(mutableBoardOne, 3, 3, mutableRobotTwo);
		
		mutableRobotOne.transferItems(mutableRobotTwo);
	}
	
	@Test(expected = IllegalBoardException.class)
	public void testTransferItems_robotsNotOnSameBoard(){
		Board tempBoard = new Board(100, 200);
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
		
		facade.putRobot(tempBoard, 2, 1, mutableRobotTwo);
		
		mutableRobotOne.transferItems(mutableRobotTwo);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testTransferItems_transferingRobotIsTerminated(){
		
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
		
		facade.putRobot(mutableBoardOne, 2, 1, mutableRobotTwo);
		mutableRobotOne.terminate();
		
		mutableRobotOne.transferItems(mutableRobotTwo);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testTransferItems_RobotToWhichShouldTransferredIsTerminated(){
		
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
		
		facade.putRobot(mutableBoardOne, 2, 1, mutableRobotTwo);
		mutableRobotTwo.terminate();
		
		mutableRobotOne.transferItems(mutableRobotTwo);
	}
	
	
	
	
	@Test
	public void testShoot(){
		fail("Not yet implemented");
	}
	
	
	
	
	@Test
	public void testCanShoot_validCase() {
		
		Robot tempBot = new Robot(new Position(2,2), mutableBoardOne, Orientation.RIGHT, 12000, 20000);
		assertTrue(tempBot.canShoot());
	}
	
	
	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#shoot()}.
	 */
	@Test
	public void testCanShoot_boardNull() {
		Robot tempBot = new Robot(new Position(2,2), null, Orientation.RIGHT, 12000, 20000);
		assertFalse(tempBot.canShoot());
	}

	@Test
	public void testCanShoot_energyLessThanCostToShoot() {
		Robot tempBot = new Robot(new Position(2,2), mutableBoardOne, Orientation.RIGHT, 100, 20000);
		assertFalse(tempBot.canShoot());
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
	
	@Test
	public void testMoveOneStep_toIllegalPosition() {
		
		Robot testRobot = new Robot(new Position(0,0),mutableBoardOne,Orientation.UP, (double)15000,(double) 20000);
		// energy of mutable robot one is 18000 Ws
		testRobot.moveOneStep();
		assertTrue(testRobot.getOrientation() == Orientation.UP);
		assertTrue(testRobot.getBoard() == mutableBoardOne);
		assertTrue(testRobot.getPosition().getCoordX() == 0);
		assertTrue(testRobot.getPosition().getCoordY() == 0);
		assertTrue(testRobot.getEnergy(EnergyUnit.WATTSECOND) == 15000);
	}

	@Test
	public void testMoveOneStep_illegalCombinationOnBoard() {
		Robot tempBot = new Robot(new Position(4,4), mutableBoardOne, Orientation.RIGHT, 18000, 20000);
		Wall tempWall = new Wall(new Position(5, 4), mutableBoardOne);
		tempBot.moveOneStep();
		assertTrue(tempBot.getPosition().getCoordX() == 4 && tempBot.getPosition().getCoordY() == 4);
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
		assertTrue(immutableRobotOne.toString().getClass()== String.class);
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
