
package roboRallyPackage.guiClasses;

import java.io.BufferedWriter;
import java.io.Writer;
import java.util.Set;

import roboRallyPackage.exceptionClasses.IllegalBoardException;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.*;

import roboRallyPackage.Board;

/**
 * Implement this interface to connect your code to the user interface.
 * 
 * <ul>
 * <li>Connect your classes to the user interface by creating a class named <code>Facade</code> that implements <code>IFacade</code>. The header
 *     of the class <code>Facade</code> should look as follows:
 *     <p><code>class Facade implements IFacade&ltBoardImpl, RobotImpl, WallImpl, BatteryImpl, RepairKitImpl, SurpriseBoxImpl&gt { ... }</code></p>
 *     The code snippet shown above assumes that your classes representing boards, robots, walls and batteries are respectively named
 *     <code>BoardImpl</code>, <code>RobotImpl</code>, <code>WallImpl</code>, <code>BatteryImpl</code>, <code>RepairKitImpl</code> and <code>SurpriseBoxImpl</code>. Consult the
 *     <a href="http://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html">Java tutorial</a> for more information on interfaces.</li>
 * <li>Modify the code between <code>&ltbegin&gt</code> and <code>&ltend&gt</code> in RoboRally.java: instantiate the generic arguments with
 *     your own classes and replace <code>new roborally.model.Facade()</code> with <code>new yourpackage.Facade()</code>.
 * <li>You may assume that only non-null objects returned by <code>createBoard</code>, <code>createRobot</code>, <code>createWall</code> and <code>createBattery</code>
 *     are passed to <code>putRobot</code>, <code>getBatteryX</code>, <code>getWallY</code>, <code>move</code>, etc.</li>
 * <li>The methods in this interface should not throw exceptions (unless specified otherwise in the documentation of a method). Prevent precondition violations for nominal methods (by checking before calling a method that its precondition holds)
 *   and catch exceptions for defensive methods. If a problem occurs (e.g. insufficient energy to move, trying to use a battery not held by the robot, ...), do not modify the program state and print an error message on standard error (<code>System.err</code>).</li>
 * <li>The rules described above and the documentation described below for each method apply only to the class implementing IFacade. Your classes for representing boards, robots, walls and batteries should follow the rules described in the assignment.</li>
 * <li>Do not modify the signatures of the methods defined in this interface. You can however add additional methods, as long as these additional methods do not overload the existing ones. Each additional method should of course
 *     be implemented in your class <code>Facade</code>.</li>
 * </ul> 
 * 
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Facade implements IFacade<Board, Robot, Wall, Battery, RepairKit, SurpriseBox>
{		
	/**
	 * Create a new board with the given <code>width</code> and <code>height</code>. 
	 * 
	 * This method must return <code>null</code> if the given <code>width</code> and <code>height</code> are invalid. 
	 */
	@Override
	public Board createBoard(long width, long height)
	{
		return new Board(width, height);
	}		

	/**
	 * Merge <code>board1</code> and <code>board2</code>. 
	 */
	@Override
	public void merge(Board board1, Board board2)
	{
		try
		{
			board1.merge(board2);
		}
		catch(IllegalStateException exc)
		{
			System.out.println("Either this board or the other board is terminated; the boards cannot be merged.");
		}
	}
	/**
	 * Create a new battery with initial energy equal to <code>initialEnergy</code> and weight equal to <code>weight</code>. 
	 * 
	 * This method must return <code>null</code> if the given parameters are invalid (e.g. negative weight). 
	 */
	@Override
	public Battery createBattery(double initialEnergy, int weight)
	{
		return new Battery(initialEnergy, weight);
	}

	/**
	 * Put <code>battery</code> at position (<code>x</code>, <code>y</code>) on <code>board</code> (if possible).
	 */
	@Override
	public void putBattery(Board board, long x, long y, Battery battery)
	{
		try
		{
			battery.setPosition(new Position(x,y));
			battery.setBoard(board);
		}
		catch(IllegalStateException exc)
		{
			System.out.println("Either this battery or this board is terminated; the battery cannot be placed on the board.");
		}
	}

	/**
	 * Return the x-coordinate of <code>battery</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>battery</code> is not placed on a board.
	 */
	@Override
	public long getBatteryX(Battery battery) throws IllegalStateException
	{
		if (battery.getBoard() != null)
		{
			return battery.getPosition().getCoordX();
		}
		else throw new IllegalStateException();
	}

	/**
	 * Return the y-coordinate of <code>battery</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>battery</code> is not placed on a board.
	 */
	@Override
	public long getBatteryY(Battery battery) throws IllegalStateException
	{
		if (battery.getBoard() != null)
		{
			return battery.getPosition().getCoordY();
		}
		else throw new IllegalStateException();
	}

	/**
	 * Create a new repair kit that repairs <code>repairAmount</code>. 
	 * 
	 * This method must return <code>null</code> if the given parameters are invalid (e.g. negative <code>repairAmount</code>). 
	 */
	@Override
	public RepairKit createRepairKit(double repairAmount, int weight)
	{
		return new RepairKit(repairAmount, weight);
	}

	/**
	 * Put <code>repairKit</code> at position (<code>x</code>, <code>y</code>) on <code>board</code> (if possible).
	 */
	@Override
	public void putRepairKit(Board board, long x, long y, RepairKit repairKit)
	{
		try
		{
			repairKit.setPosition(new Position(x,y));
			repairKit.setBoard(board);
		}
		catch(IllegalStateException exc)
		{
			System.out.println("Either this repair kit or this board is terminated; the repair kit cannot be placed on the board.");
		}
	}

	/**
	 * Return the x-coordinate of <code>repairKit</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>repairKit</code> is not placed on a board.
	 */
	@Override
	public long getRepairKitX(RepairKit repairKit) throws IllegalStateException
	{
		if (repairKit.getBoard() != null)
		{
			return repairKit.getPosition().getCoordX();
		}
		else throw new IllegalStateException();
	}

	/**
	 * Return the y-coordinate of <code>repairKit</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>repairKit</code> is not placed on a board.
	 */
	@Override
	public long getRepairKitY(RepairKit repairKit) throws IllegalStateException
	{
		if (repairKit.getBoard() != null)
		{
			return repairKit.getPosition().getCoordY();
		}
		else throw new IllegalStateException();
	}

	/**
	 * Create a new surprise box with weighing <code>weight</code>. 
	 * 
	 * This method must return <code>null</code> if the given parameters are invalid (e.g. negative <code>weight</code>). 
	 */
	@Override
	public SurpriseBox createSurpriseBox(int weight)
	{
		return new SurpriseBox(weight);
	}

	/**
	 * Put <code>surpriseBox</code> at position (<code>x</code>, <code>y</code>) on <code>board</code> (if possible).
	 */
	@Override
	public void putSurpriseBox(Board board, long x, long y, SurpriseBox surpriseBox)
	{
		try
		{
			surpriseBox.setPosition(new Position(x,y));
			surpriseBox.setBoard(board);
		}
		catch(IllegalStateException exc)
		{
			System.out.println("Either this surprise box or this board is terminated; the surprise box cannot be placed on the board.");
		}
	}

	/**
	 * Return the x-coordinate of <code>surpriseBox</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>surpriseBox</code> is not placed on a board.
	 */
	@Override
	public long getSurpriseBoxX(SurpriseBox surpriseBox) throws IllegalStateException
	{
		if (surpriseBox.getBoard() != null)
		{
			return surpriseBox.getPosition().getCoordX();
		}
		else throw new IllegalStateException();
	}

	/**
	 * Return the y-coordinate of <code>surpriseBox</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>surpriseBox</code> is not placed on a board.
	 */
	@Override
	public long getSurpriseBoxY(SurpriseBox surpriseBox) throws IllegalStateException
	{
		if (surpriseBox.getBoard() != null)
		{
			return surpriseBox.getPosition().getCoordY();
		}
		else throw new IllegalStateException();
	}

	/** 
	 * Create a new Robot looking at <code>orientation</code> with <code>energy</code> watt-second.
	 * 
	 * This method must return <code>null</code> if the given parameters are invalid (e.g. negative energy). 
	 *  
	 * <p>0, 1, 2, 3 respectively represent up, right, down and left.</p>
	 */
	@Override
	public Robot createRobot(int orientation, double initialEnergy)
	{
		Orientation ori = Orientation.convertIntToOrientation(orientation);
		
		if(Robot.canHaveAsOrientation(ori))
		{
			Robot robot = new Robot(null, null, ori, initialEnergy, (double) 20000);
			if(robot.canHaveAsEnergy(initialEnergy))
			{
				return robot;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}

	/**
	 * Put <code>robot</code> at position (<code>x</code>, <code>y</code>) on <code>board</code> (if possible).
	 */
	@Override
	public void putRobot(Board board, long x, long y, Robot robot)
	{
		try
		{
			robot.setPosition(new Position(x,y));
			robot.setBoard(board);
		}
		catch(IllegalStateException exc)
		{
			System.out.println("Either this robot or this board is terminated; the robot cannot be placed on the board.");
		}
	}

	/**
	 * Return the x-coordinate of <code>robot</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>robot</code> is not placed on a board.
	 */
	@Override
	public long getRobotX(Robot robot) throws IllegalStateException
	{
		if (robot.getBoard() != null)
		{
			return robot.getPosition().getCoordX();
		}
		else throw new IllegalStateException();
	}

	/**
	 * Return the y-coordinate of <code>robot</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>robot</code> is not placed on a board.
	 */
	@Override
	public long getRobotY(Robot robot) throws IllegalStateException
	{
		if (robot.getBoard() != null)
		{
			return robot.getPosition().getCoordY();
		}
		else throw new IllegalStateException();
	}

	/**
	 * Return the orientation (either 0, 1, 2 or 3) of <code>robot</code>. 
	 * 
	 * <p>0, 1, 2, 3 respectively represent up, right, down and left.</p>
	 */
	@Override
	public int getOrientation(Robot robot)
	{
		return robot.getOrientation().ordinal();
	}

	/**
	 * Return the current energy in watt-second of <code>robot</code>.
	 */
	@Override
	public double getEnergy(Robot robot)
	{
		return robot.getEnergy(EnergyUnit.WATTSECOND);
	}

	/**
	 * Move <code>robot</code> one step in its current direction if the robot has sufficient energy. Do not modify the state of the robot
	 * if it has insufficient energy.
	 */
	@Override
	public void move(Robot robot)
	{
		try
		{
			robot.moveOneStep();
		}
		catch(IllegalStateException exc)
		{
			System.out.println("A terminated robot cannot be moved.");
		}
	}

	/**
	 * Turn <code>robot</code> 90 degrees in clockwise direction if the robot has sufficient energy. Do not modify the state of the robot
	 * if it has insufficient energy.
	 */
	@Override
	public void turn(Robot robot)
	{
		try
		{
			robot.turnClockwise();
		}
		catch(IllegalStateException exc)
		{
			System.out.println("A terminated robot cannot be turned.");
		}
	}

	/**
	 * Make <code>robot</code> pick up <code>battery</code> (if possible).
	 */
	@Override
	public void pickUpBattery(Robot robot, Battery battery)
	{	
		try
		{
			robot.pickUp(battery);
		}
		catch(IllegalStateException exc)
		{
			System.out.println("Either this robot or this battery is terminated; the robot cannot pick up the battery.");
		}
	}

	/**
	 * Make <code>robot</code> use <code>battery</code> (if possible).
	 */
	@Override
	public void useBattery(Robot robot, Battery battery)
	{
		try
		{
			robot.use(battery);
		}
		catch(IllegalStateException exc)
		{
			System.out.println("Either this robot or this battery is terminated; the robot cannot use the battery.");
		}
	}

	/**
	 * Make <code>robot</code> drop <code>battery</code> (if possible).
	 */
	@Override
	public void dropBattery(Robot robot, Battery battery)
	{
		try
		{
			robot.drop(battery);
		}
		catch(IllegalStateException exc)
		{
			System.out.println("Either this robot or this battery is terminated; the robot cannot drop the battery.");
		}
	}

	/**
	 * Make <code>robot</code> pick up <code>repairKit</code> (if possible).
	 */
	@Override
	public void pickUpRepairKit(Robot robot, RepairKit repairKit)
	{	
		try
		{
			robot.pickUp(repairKit);
		}
		catch(IllegalStateException exc)
		{
			System.out.println("Either this robot or this repair kit is terminated; the robot cannot pick up the repair kit.");
		}
	}

	/**
	 * Make <code>robot</code> use <code>repairKit</code> (if possible).
	 */
	@Override
	public void useRepairKit(Robot robot, RepairKit repairKit)
	{
		try
		{
			robot.use(repairKit);
		}
		catch(IllegalStateException exc)
		{
			System.out.println("Either this robot or this repair kit is terminated; the robot cannot use the repair kit.");
		}
	}

	/**
	 * Make <code>robot</code> drop <code>repairKit</code> (if possible).
	 */
	@Override
	public void dropRepairKit(Robot robot, RepairKit repairKit)
	{
		try
		{
			robot.drop(repairKit);
		}
		catch(IllegalStateException exc)
		{
			System.out.println("Either this robot or this repair kit is terminated; the robot cannot drop the repair kit.");
		}
	}

	/**
	 * Make <code>robot</code> pick up <code>surpriseBox</code> (if possible).
	 */
	@Override
	public void pickUpSurpriseBox(Robot robot, SurpriseBox surpriseBox)
	{	
		try
		{
			robot.pickUp(surpriseBox);
		}
		catch(IllegalStateException exc)
		{
			System.out.println("Either this robot or this surprise box is terminated; the robot cannot pick up the surprise box.");
		}
	}

	/**
	 * Make <code>robot</code> use <code>surpriseBox</code> (if possible).
	 */
	@Override
	public void useSurpriseBox(Robot robot, SurpriseBox surpriseBox)
	{
		try
		{
			robot.use(surpriseBox);
		}
		catch(IllegalStateException exc)
		{
			System.out.println("Either this robot or this surprise box is terminated; the robot cannot use the surprise box.");
		}
	}

	/**
	 * Make <code>robot</code> drop <code>surpriseBox</code> (if possible).
	 */
	@Override
	public void dropSurpriseBox(Robot robot, SurpriseBox surpriseBox)
	{
		try
		{
			robot.drop(surpriseBox);
		}
		catch(IllegalStateException exc)
		{
			System.out.println("Either this robot or this surprise box is terminated; the robot cannot drop the surprise box.");
		}
	}

	/**
	 * Transfer all items possessed by <code>from</code> to <code>to</code>.  
	 */
	@Override
	public void transferItems(Robot from, Robot to)
	{
		from.transferItems(to);
	}

	/**
	 * Return whether your implementation of <code>isMinimalCostToReach</code> takes into account other robots, walls and turning (required to score 17+). The return
	 * value of this method determines the expected return value of <code>isMinimalCostToReach</code> in the test suite.
	 * 
	 * This method must return either 0 or 1.
	 */
	@Override
	public int isMinimalCostToReach17Plus()
	{
		return 1;
	} 

	/**
	 * Return the minimal amount of energy required for <code>robot</code> to reach (<code>x</code>, </code>y</code>) taking into account the robot's current load and energy level. Do not take into account
	 * shooting and picking up/using/dropping batteries. 
	 * <p>
	 * The expected return value of this method depends on <code>isMinimalCostToReach17Plus</code>:
	 * <ul>
	 * <li>If <code>isMinimalCostToReach17Plus</code> returns <code>0</code>, then <code>getMinimalCostToReach</code> will only be called if there are no obstacles in the rectangle
	 * covering <code>robot</code> and the given position. Moreover, the result of this method should not include the energy required for turning.</li>
	 * <li>If <code>isMinimalCostToReach17Plus</code> returns <code>1</code>, then <code>getMinimalCostToReach</code> must take into account obstacles (i.e. walls, other robots) and the 
	 * fact that turning consumes energy. This method must return <code>-1</code> if the given position is not reachable given the current amount of energy.</li>
	 * </ul>
	 * </p>
	 * In any case, this method must return <code>-1</code> if <code>robot</code> is not placed on a board.
	 */
	@Override
	public double getMinimalCostToReach(Robot robot, long x, long y)
	{
		try
		{
			return robot.getEnergyRequiredToReach(new Position(x, y));
		}
		catch(IllegalBoardException exc)
		{
			System.out.println("The given robot is not placed on a board..");
			return -1;
		}
		catch(IllegalStateException exc)
		{
			System.out.println("The given robot is terminated.");
			return -1; 
		}
	}

	/**
	 * Return whether your implementation of <code>moveNextTo</code> takes into account other robots, walls and the fact that turning consumes energy (required to score 18+). The return
	 * value of this method determines the expected effect of <code>moveNextTo</code> in the test suite.
	 * 
	 * This method must return either 0 or 1.
	 */
	@Override
	public int isMoveNextTo18Plus()
	{
		return 1;
	} 

	/**
	 * Move <code>robot</code> as close as possible (expressed as the manhattan distance) to <code>other</code> given their current energy and load. If multiple optimal (in distance) solutions
	 * exist, select the solution that requires the least amount of total energy. Both robots can move and turn to end up closer to each other. Do not take into account shooting and picking up/using/dropping
	 * batteries.  
	 * <p>
	 * The expected return value of this method depends on <code>isMoveNextTo18Plus</code>:
	 * <ul>
	 * <li>If <code>isMoveNextTo18Plus</code> returns <code>0</code>, then <code>moveNextTo</code> will only be called if there are no obstacles in the rectangle
	 * covering <code>robot</code> and <code>other</code>. Moreover, your implementation must be optimal only in the number of moves (i.e. ignore the fact that turning consumes energy).</li>
	 * <li>If <code>isMoveNextTo18Plus</code> returns <code>1</code>, then <code>moveNextTo</code> must take into account obstacles (i.e. walls, other robots) and the 
	 * fact that turning consumes energy.</li>
	 * </ul>
	 * </p>
	 * Do not change the state if <code>robot</code> and <code>other</code> are not located on the same board.
	 */
	@Override
	public void moveNextTo(Robot robot, Robot other)
	{
		try
		{
			robot.moveNextTo(other);
		}
		catch(IllegalBoardException exc)
		{
			System.out.println("Both robots are not placed on the same board");
		}
		catch(IllegalStateException exc)
		{
			System.out.println("One or both robots is either terminated or does not have a board.");
		}
	}

	/**
	 * Make <code>robot</code> shoot in the orientation it is currently facing (if possible).
	 * 
	 * Students working on their own are allowed to throw <code>UnsupportedOperationException</code>.
	 */
	@Override
	public void shoot(Robot robot) throws UnsupportedOperationException
	{
		try
		{
			robot.shoot();
		}
		catch(IllegalStateException exc)
		{
			System.out.println("A terminated robot cannot shoot.");
		}
	}

	/**
	 * Create a new wall.
	 * 
	 * Students working on their own are allowed to throw <code>UnsupportedOperationException</code>.
	 */
	@Override
	public Wall createWall() throws UnsupportedOperationException
	{
		return new Wall();
	}

	/**
	 * Put <code>robot</code> at position (<code>x</code>, <code>y</code>) on <code>board</code> (if possible).
	 * 
	 * Students working on their own are allowed to throw <code>UnsupportedOperationException</code>.
	 */
	@Override
	public void putWall(Board board, long x, long y, Wall wall) throws UnsupportedOperationException
	{
		try
		{
			wall.setPosition(new Position(x,y));
			wall.setBoard(board);
		}
		catch(IllegalStateException exc)
		{
			System.out.println("Either this wall or this board is terminated; the wall cannot be placed on the board.");
		}
	}

	/**
	 * Return the x-coordinate of <code>wall</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>wall</code> is not placed on a board.
	 * 
	 * Students working on their own are allowed to throw <code>UnsupportedOperationException</code>.
	 */
	@Override
	public long getWallX(Wall wall) throws IllegalStateException, UnsupportedOperationException
	{
		if (wall.getBoard() != null)
		{
			return wall.getPosition().getCoordX();
		}
		else throw new IllegalStateException();
	}

	/**
	 * Return the y-coordinate of <code>wall</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>wall</code> is not placed on a board.
	 * 
	 * Students working on their own are allowed to throw <code>UnsupportedOperationException</code>.
	 */
	@Override
	public long getWallY(Wall wall) throws IllegalStateException, UnsupportedOperationException
	{
		if (wall.getBoard() != null)
		{
			return wall.getPosition().getCoordY();
		}
		else throw new IllegalStateException();
	}

	/**
	 * Return a set containing all robots on <code>board</code>.
	 */
	@Override
	public Set<Robot> getRobots(Board board)
	{
		return board.getElements(Robot.class);
	}

	/**
	 * Return a set containing all walls on <code>board</code>.
	 * 
	 * Students working on their own are allowed to throw <code>UnsupportedOperationException</code>.
	 */
	@Override
	public Set<Wall> getWalls(Board board) throws UnsupportedOperationException
	{
		return board.getElements(Wall.class);
	}

	/**
	 * Return a set containing all repair kits on <code>board</code>.
	 */
	@Override
	public Set<RepairKit> getRepairKits(Board board)
	{
		return board.getElements(RepairKit.class);
	}

	/**
	 * Return a set containing all surprise boxes on <code>board</code>.
	 */
	@Override
	public Set<SurpriseBox> getSurpriseBoxes(Board board)
	{
		return board.getElements(SurpriseBox.class);
	}

	/**
	 * Return a set containing all batteries on <code>board</code>.
	 */
	@Override
	public Set<Battery> getBatteries(Board board)
	{
		return board.getElements(Battery.class);
	}

	/**
	 * Load the program stored at <code>path</code> and assign it to <code>robot</code>.
	 * 
	 * Return <code>0</code> if the operation completed successfully; otherwise, return a negative number.
	 */
	@Override
	public int loadProgramFromFile(Robot robot, String path)
	{
		String inputProgram = null;
		
		java.io.File file = new java.io.File(path);
		if(file.exists() && file.canRead())
		{
			java.io.FileReader fileReader = new java.io.FileReader(file);
			StringBuffer buffer = new StringBuffer();
			int len;
			
			try
			{
				while ((len = fileReader.read(chr)) > 0) 
				{
					buffer.append(chr, 0, len);
				}
			} 
			finally 
			{
				fileReader.close();
			}
			inputProgram = buffer.toString();
		}
		
		return robot.setProgram(inputProgram);
	}

	/**
	 * Save the program of <code>robot</code> in a file at <code>path</code>.
	 * 
	 * Return <code>0</code> if the operation completed successfully; otherwise, return a negative number.
	 */
	@Override
	public int saveProgramToFile(Robot robot, String path)
	{
		return 0;
	}

	/**
	 * Pretty print the program of <code>robot</code> via <code>writer</code>.
	 */
	@Override
	public void prettyPrintProgram(Robot robot, Writer writer)
	{
	if(robot.getProgram() == null)
		System.out.println("This robot does not yet contain a program in its memory.");
//	else{
//		Writer writer = new BufferedWriter(arg0)
//	}
	}

	/**
	 * Execute <code>n</code> basic steps in the program of <code>robot</code>.
	 * 
	 * <p>For example, consider the program (seq (move) (shoot)). The first step performs a move command,
	 * the second step performs a shoot command and all subsequent steps have no effect.</p> 
	 * 
	 * <p>Note that if n equals 1, then only the move command is executed. The next call to stepn then starts
	 * with the shoot command.</p>
	 */
	@Override
	public void stepn(Robot robot, int n)
	{
		robot.runProgramStep(n);
	}

}
