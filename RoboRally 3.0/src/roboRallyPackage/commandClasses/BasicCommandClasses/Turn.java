
package roboRallyPackage.commandClasses.BasicCommandClasses;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Turn extends BasicCommand
{
	public Turn(Direction direction)
	{
		this.direction = direction;
	}
//	public Turn(Robot robot, Direction direction)
//	{
//		super(robot);
//		this.direction = direction;
//	}
	
	Direction direction;

	/**
	 * @return	...
	 *			| result == ...
	 */
	public Direction getDirection()
	{
		return this.direction;
	}
	
	public void execute(Robot robot)
	{
		if(direction == Direction.CLOCKWISE)
		{
			robot.turnClockwise();
		}
		else
		{
			robot.turnCounterClockwise();
		}
	}
	@Override
	public String toString(){
		return "(turn " + this.getDirection().toString() + ")";
	}
}
