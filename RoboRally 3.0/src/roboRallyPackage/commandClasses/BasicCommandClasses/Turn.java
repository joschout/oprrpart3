
package roboRallyPackage.commandClasses.BasicCommandClasses;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * Class representing a turn command.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Turn extends BasicCommand
{
	/**
	 * Initializes this turn command with the given program level and direction.
	 * 
	 * @param	programLevel
	 * 			The program level of this new shoot command.
	 * @param	direction
	 * 			The direction where to the robot that executes this command will turn.
	 * @effect	...
	 * 			| super(programLevel)
	 */
	public Turn(int programLevel, Direction direction)
	{
		super(programLevel);
		this.direction = direction;
	}
	
	/**
	 * Variable representing the direction where to the robot has to turn.
	 */
	private Direction direction;

	/**
	 * Returns the direction where to the robot has to turn.
	 */
	@Basic
	public Direction getDirection()
	{
		return this.direction;
	}
	
	/**
	 * Executes this turn command. The given robot will turn one time.
	 * 
	 * @param	robot
	 * 			The robot to be turned.
	 * @effect	...
	 * 			| if(this.getDirection() == Direction.CLOCKWISE) then robot.turnClockwise()
	 * 			| else robot.turnCounterClockwise()
	 */
	public void execute(Robot robot)
	{
		if(this.getDirection() == Direction.CLOCKWISE)
		{
			robot.turnClockwise();
		}
		else
		{
			robot.turnCounterClockwise();
		}
	}
	
	/**
	 * String representation of this turn command.
	 * 
	 * @return	...
	 * 			| result == "(turn " + this.getDirection().toString() + ")"
	 */
	@Override
	public String toString()
	{
		return "(turn " + this.getDirection().toString() + ")";
	}
	
	/**
	 * String representation of this turn command, in the syntax used by the Parser.
	 * 
	 * @return	...
	 * 			| result == "(turn 'direction')"
	 */
	@Override
	public String getNotationExample()
	{
		return "(turn 'direction')";
	}
}
