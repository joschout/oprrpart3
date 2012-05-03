package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import roboRallyPackage.guiClasses.*;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;

public class BatteryTest {
 
	private Board board;
	
	private Facade facade;
	private Battery batteryOne;
	
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	
	}

	@Before
	public void setUp() throws Exception {
		facade = new Facade();
		board = facade.createBoard(15, 15);
	}

	@Test
	public void addBatteryTest_normal() {
		batteryOne = facade.createBattery(1000, 5);
		facade.putBattery(board, 1, 2, batteryOne);
		
		assertTrue(facade.getBatteryX(batteryOne) ==1);
		assertTrue(facade.getBatteryY(batteryOne) ==2);
		assertTrue(batteryOne.getBoard() == board);
		assertFalse(batteryOne.isTerminated());
		assertTrue(facade.getBatteries(board).contains(batteryOne));
		assertTrue(batteryOne.getWeight() == 5);
		
	}

	
		
	
	@Test
	public void addBatteryTest_negativeWeight(){
		
		batteryOne = facade.createBattery(100, -5);
		facade.putBattery(board, 1, 2, batteryOne);
		assertTrue(facade.getBatteryX(batteryOne) ==1);
		assertTrue(facade.getBatteryY(batteryOne) ==2);
		assertTrue(batteryOne.getBoard() == board);
		assertTrue(batteryOne.getWeight() == 0);
		assertTrue(facade.getBatteries(board).contains(batteryOne));
	}
	


}
