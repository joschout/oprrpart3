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
public abstract class BasicCommand extends Command implements Executable
{
	public abstract void execute();
}
