/**
 * 
 */
package roboRallyPackage.commandClasses.BasicCommand;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
/**
 * @author Nele
 *
 */
public class Turn extends BasicCommand
{
	public Turn(Direction direction)
	{
		this.direction = direction;
	}
	
	Direction direction;

	/**
	 * @return	...
	 *			| result == ...
	 */
	public Direction getDirection()
	{
		return this.direction;
	}
	
	public void execute()
	{
		if(direction == Direction.CLOCKWISE)
		{
			this.getRobot().turnClockwise();
		}
		else
		{
			this.getRobot().turnCounterClockwise();
		}
	}
}
