/**
 * 
 */
package roboRallyPackage.commandClasses.BooleanCommand;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.ConditionCommand.*;
/**
 * @author Nele
 *
 */
public class Not extends BooleanCommand
{
	public Not(Robot robot, Condition condition)
	{
		super(robot);
		this.condition = condition;
	}
	
	public boolean results()
	{
		return ! condition.results();
	}
	
	private Condition condition;

	/**
	 * @return	...
	 *			| result == ...
	 */
	public Condition getCondition()
	{
		return this.condition;
	}
}
