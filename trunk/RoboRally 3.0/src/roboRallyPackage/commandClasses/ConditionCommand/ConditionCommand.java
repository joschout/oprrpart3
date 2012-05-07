/**
 * 
 */
package roboRallyPackage.commandClasses.ConditionCommand;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.ConditionCommand.*;
/**
 * @author Nele
 *
 */
public abstract class ConditionCommand extends Command implements Condition
{
	public ConditionCommand(Robot robot)
	{
		super(robot);
	}
	
	public abstract boolean results();
}
