/**
 * 
 */
package roboRallyPackage.commandClasses;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
/**
 * @author Nele
 *
 */
public abstract class Command
{
	public Command(Robot robot)
	{
		this.robot = robot;
	}
	
	protected Robot getRobot()
	{
		return robot;
	}
	
	private Robot robot;
}
