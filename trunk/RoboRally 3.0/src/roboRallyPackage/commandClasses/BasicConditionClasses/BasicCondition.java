/**
 * 
 */
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.CombinedCondition.*;
/**
 * @author Nele
 *
 */
public abstract class BasicCondition extends Condition
{
//	public BasicCondition(Robot robot)
//	{
//		super(robot);
//	}
	public BasicCondition(Robot robot)
	{
		this.robot = robot;
	}
	
	protected Robot getRobot()
	{
		return robot;
	}
	
	private Robot robot; 
	public abstract boolean results();
}
