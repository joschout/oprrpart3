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
public class Move extends BasicCommand
{
	public Move(Robot robot)
	{
		super(robot);
	}
	
	public void execute()
	{
		this.getRobot().moveOneStep();
	}
}
