/**
 * 
 */
package roboRallyPackage.commandClasses.CombinedCondition;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.CombinedCondition.*;
/**
 * @author Nele
 *
 */
public abstract class CombinedCondition extends Condition
{
	public CombinedCondition(Robot robot)
	{
		super(robot);
	}
	
	public abstract boolean results();
}
