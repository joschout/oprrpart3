
package roboRallyPackage.commandClasses.BasicCommandClasses;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public abstract class BasicCommand extends Command
{
	public BasicCommand(Robot robot)
	{
		super(robot);
	}
	
	public abstract void execute();
	
	public void executeStep()
	{
		this.execute();
	}
}
