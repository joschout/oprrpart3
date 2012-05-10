package roboRallyPackage.commandClasses.CombinedCommand;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * 
 */

/**
 * @author Nele
 *
 */
public abstract class CombinedCommand extends Command implements Executable
{
	public CombinedCommand(Robot robot)
	{
		super(robot);
	}
	
	public abstract void execute();
}
