
package roboRallyPackage.commandClasses.CombinedCommand;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public abstract class CombinedCommand extends Command
{
	
	public CombinedCommand(int programLevel)
	{
		super(programLevel);
	}
	
	public abstract void execute(Robot robot);
	
	public abstract void executeStep(Robot robot);
}
