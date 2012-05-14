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
public abstract class BasicCommand extends Command
{
	public BasicCommand(Robot robot)
	{
		super(robot);
	}
	
	public abstract void execute();
}
