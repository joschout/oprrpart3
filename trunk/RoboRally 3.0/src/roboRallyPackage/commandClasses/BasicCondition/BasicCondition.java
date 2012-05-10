/**
 * 
 */
package roboRallyPackage.commandClasses.BasicCondition;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.CombinedCondition.*;
/**
 * @author Nele
 *
 */
public abstract class BasicCondition extends Command implements Condition
{
	public BasicCondition(Robot robot)
	{
		super(robot);
	}
	
	public abstract boolean results();
}
