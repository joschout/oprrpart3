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
import roboRallyPackage.commandClasses.CombinedCommand.*;

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
	
	@Test
	public void testTerminate_withElements() {
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		facade.putRepairKit(mutableBoardOne, 1, 1, mutableRepairKitOne);
		facade.putSurpriseBox(mutableBoardOne, 1, 1, mutableSurpriseBoxOne);
		
		
		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
		facade.pickUpRepairKit(mutableRobotOne, mutableRepairKitOne);
		facade.pickUpSurpriseBox(mutableRobotOne, mutableSurpriseBoxOne);
		
		mutableRobotOne.terminate();
		
		assertFalse(mutableRobotOne.getPossessions().contains(mutableBatteryOne)
				|| mutableRobotOne.getPossessions().contains(mutableRepairKitOne)
				|| mutableRobotOne.getPossessions().contains(mutableSurpriseBoxOne));
		
		
		assertTrue(mutableRobotOne.isTerminated());
		assertTrue(mutableBatteryOne.isTerminated());
		assertTrue(mutableRepairKitOne.isTerminated());
		assertTrue(mutableSurpriseBoxOne.isTerminated());
		assertFalse(facade.getRobots(mutableBoardOne).contains(mutableRobotOne));
		
	}
	
	
	
	
	
	
	
	
	

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
		Robot testBot = new Robot(new Position(2,2), mutableBoardOne, Orientation.RIGHT, 20000, 20000);
		Position position = new Position(3,3);
		Double energy = testBot.getEnergyRequiredToReach(position);
		assertTrue(energy == (2*testBot.getTotalCostToMove()+Robot.getCostToTurn()));	
	}
	
	
	@Test(expected = IllegalBoardException.class)
	public void testGetEnergyRequiredToReach_boardNull() {
		//the board of mutableRobot is null
		mutableRobotOne.getEnergyRequiredToReach(new Position(1,1));
	}
	
	@Test
	public void testGetEnergyRequiredToReach_notEnoughEnergy() {
		//the board of mutableRobot is null
		Robot tempBot = new Robot(new Position(4,4), mutableBoardOne, Orientation.RIGHT, 100, 20000);
		assertTrue(tempBot.getEnergyRequiredToReach(new Position(10,10))==-1);
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

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Robot#getPossessions()}.
	 */
	@Test
	public void testGetPossessions() {
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		facade.putSurpriseBox(mutableBoardOne, 1, 1, mutableSurpriseBoxOne);
		facade.putRepairKit(mutableBoardOne, 1, 1, mutableRepairKitOne);
		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
		facade.pickUpSurpriseBox(mutableRobotOne, mutableSurpriseBoxOne);
		facade.pickUpRepairKit(mutableRobotOne, mutableRepairKitOne);
		assertTrue(mutableRobotOne.getPossessions().contains(mutableBatteryOne)
				&& mutableRobotOne.getPossessions().contains(mutableRepairKitOne)
				&& mutableRobotOne.getPossessions().contains(mutableSurpriseBoxOne));
		
	}

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
		assertFalse(mutableRobotOne.canPickUp(mutableBatteryOne));
		
	}


	@Test
	public void testCanPickUp_itemOtherPositionThanRobot() {
		facade.putBattery(mutableBoardOne, 1, 1, mutableBatteryOne);
		facade.putRobot(mutableBoardOne, 2, 2, mutableRobotOne);
		assertFalse(mutableRobotOne.canPickUp(mutableBatteryOne));	
	}
	
	@Test
	public void testCanPickUp_boardItemNull() {
		Battery bat = new Battery(new Position(2, 2), null, 100, 5);
		Robot bot = new Robot(new Position(2,2), mutableBoardOne, Orientation.RIGHT, 18000, 20000);
		
		assertFalse(bot.canPickUp(bat));	
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
	@Test(expected = IllegalStateException.class)
	public void testDrop_robotTerminated() {
		facade.putRobot(mutableBoardOne, 1, 1, mutableRobotOne);
		facade.putBattery(mutableBoardOne, 2, 1, mutableBatteryOne);
		facade.move(mutableRobotOne);
		facade.pickUpBattery(mutableRobotOne, mutableBatteryOne);
		assertTrue(mutableRobotOne.getPossessions().contains(mutableBatteryOne));
		facade.move(mutableRobotOne);
		mutableRobotOne.terminate();
		mutableRobotOne.drop(mutableBatteryOne);
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
		Board tempBoard = new Board(10, 10);
		Robot tempRobotOne = new Robot(new Position(1,1), tempBoard, Orientation.RIGHT, 18000, 20000);
		Robot tempRobotTwo = new Robot(new Position(2,1), tempBoard, Orientation.RIGHT, 18000, 20000);
		tempRobotOne.shoot();
		
		assertTrue(tempRobotOne.getEnergy(EnergyUnit.WATTSECOND) == (18000-Robot.getCostToShoot()));
		assertTrue(tempRobotTwo.getMaxEnergy() == 16000);
		assertTrue(tempRobotTwo.getEnergy(EnergyUnit.WATTSECOND)==16000);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testShoot_robotIsTerminated(){
		mutableRobotOne.terminate();
		mutableRobotOne.shoot();
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
		facade.move(testRobot);
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
		facade.move(tempBot);
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
//	@Test
//	public void testMoveNextTo_validCase(){
//		Board tempBoard = new Board(10, 10);
//		Robot tempRobotOne = new Robot(new Position(1,1), tempBoard, Orientation.RIGHT, 18000, 20000);
//		Robot tempRobotTwo = new Robot(new Position(4,4), tempBoard, Orientation.RIGHT, 18000, 20000);
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void testMoveNextTo_enoughEnergy() {
		Board tempBoard = new Board(10, 10);
		Robot robot1 = new Robot(new Position(1,1), tempBoard, Orientation.RIGHT, 10000, 20000);
		Robot robot2 = new Robot(new Position(3,3), tempBoard, Orientation.RIGHT, 10000, 20000);
		robot1.moveNextTo(robot2);
		assertTrue(robot1.getPosition().getCoordX() == 2 && robot1.getPosition().getCoordY() == 1);
		assertTrue(robot2.getPosition().getCoordX() == 3 && robot2.getPosition().getCoordY() == 1);
	}
	
	@Test
	public void testMoveNextTo_2notEnoughEnergy() {
		Board tempBoard = new Board(10, 10);
		Robot robot1 = facade.createRobot(1, 600);
		facade.putRobot(tempBoard, 1, 1, robot1);
		Robot robot2 = facade.createRobot(1, 600);
		facade.putRobot(tempBoard, 3, 3, robot2);
		facade.moveNextTo(robot2, robot1);
		assertTrue((facade.getRobotX(robot1) == 2 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 3 && facade.getRobotY(robot2) == 2));
	}
	
	@Test
	public void testMoveNextTo_1notEnoughEnergy() {
		Board tempBoard = new Board(10, 10);
		Robot robot1 = facade.createRobot(1, 600);
		facade.putRobot(tempBoard, 1, 1, robot1);
		Robot robot2 = facade.createRobot(1, 20000);
		facade.putRobot(tempBoard, 5, 5, robot2);
		facade.moveNextTo(robot2, robot1);
		assertTrue((facade.getRobotX(robot1) == 1 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 2 && facade.getRobotY(robot2) == 1)
				|| (facade.getRobotX(robot1) == 1 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 1 && facade.getRobotY(robot2) == 2)
				|| (facade.getRobotX(robot1) == 2 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 3 && facade.getRobotY(robot2) == 1)
				|| (facade.getRobotX(robot1) == 2 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 2 && facade.getRobotY(robot2) == 2));		  			
	}
	
	@Test
	public void testMoveNextTo_1notEnoughEnergy_part_b() {
		Board tempBoard = new Board(10, 10);
		Robot robot1 = facade.createRobot(1, 600);
		facade.putRobot(tempBoard, 1, 1, robot1);
		Robot robot2 = facade.createRobot(1, 20000);
		facade.putRobot(tempBoard, 5, 5, robot2);
		facade.moveNextTo(robot1, robot2);
		assertTrue((facade.getRobotX(robot1) == 1 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 2 && facade.getRobotY(robot2) == 1)
				|| (facade.getRobotX(robot1) == 1 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 1 && facade.getRobotY(robot2) == 2)
				|| (facade.getRobotX(robot1) == 2 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 3 && facade.getRobotY(robot2) == 1)
				|| (facade.getRobotX(robot1) == 2 && facade.getRobotY(robot1) == 1 && facade.getRobotX(robot2) == 2 && facade.getRobotY(robot2) == 2));		  			
	}
	
	@Test
	public void testMoveNextTo_1notEnoughEnergy_part_c() {
		Board tempBoard = new Board(10, 10);
		Robot robot1 = facade.createRobot(2, 600);
		facade.putRobot(tempBoard, 1, 2, robot1);
		Robot robot2 = facade.createRobot(3, 800);
		facade.putRobot(tempBoard, 4, 0, robot2);
		facade.moveNextTo(robot1, robot2);
		assertEquals(2, facade.getRobotX(robot1));
		assertEquals(2, facade.getRobotY(robot1));
		assertEquals(3, facade.getRobotX(robot2));
		assertEquals(0, facade.getRobotY(robot2));
	}
	
	
	@Test
	public void turn_NotEnoughEnergy(){
		Board tempBoard = new Board(10, 10);
		Robot robot = facade.createRobot(1, 50);
		facade.putRobot(tempBoard, 1, 1, robot);
		facade.turn(robot);
		assertTrue(facade.getOrientation(robot) == 1);
	}	
	@Test
	public void move_OfBoard(){
		Board tempBoard = new Board(10, 10);
		Robot robot = facade.createRobot(3, 20000);
		facade.putRobot(tempBoard, 0, 2, robot);
		facade.move(robot);
		assertTrue(facade.getRobotX(robot) == 0);
		assertTrue(facade.getRobotY(robot) == 2);
		assertTrue(facade.getOrientation(robot)==3);
		assertTrue(facade.getEnergy(robot)==20000);
	}
	
	

	/**
	 * Test method for {@link roboRallyPackage.gameElementClasses.Element#getPosition()}.
	 */
	@Test
	public void testGetPosition() {
		Position position =new Position(3,4);
		Robot tempBot = new Robot(position, mutableBoardOne, Orientation.RIGHT, 18000, 20000);
		assertTrue(tempBot.getPosition() == position);
	}

	@Test
	public void testTakeHit(){
		Robot tempBot = new Robot(new Position(3,4), mutableBoardOne, Orientation.RIGHT, 18000, 20000);
		tempBot.takeHit();
		assertTrue(tempBot.getMaxEnergy() == 16000);
	}
	@Test
	public void testTakeHit_robotOnLowEnergy(){
		Robot tempBot = new Robot(new Position(3,4), mutableBoardOne, Orientation.RIGHT, 600, 2000);
		tempBot.takeHit();
		assertTrue(tempBot.isTerminated());
	}
	
	@Test
	public void testSetProgram(){
		mutableRobotOne.setProgram("(if (not (at-item)) (move) (turn clockwise))");
		assertFalse(mutableRobotOne.getProgram() == null);
		assertTrue(mutableRobotOne.getProgram().getClass() == If.class);
	}
}
