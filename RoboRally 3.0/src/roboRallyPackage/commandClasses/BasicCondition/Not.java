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
public class Not extends BasicCondition
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
