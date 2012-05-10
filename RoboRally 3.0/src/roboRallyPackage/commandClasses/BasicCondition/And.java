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
public class And extends BasicCondition
{
	public And(Robot robot, Condition[] conditions)
	{
		super(robot);
		this.conditions = conditions;
	}
	
	public boolean results()
	{
		boolean result = true;
		
		for(Condition condition: this.getConditions())
		{
			result = result && condition.results();
		}
		
		return result;
	}
	
	private Condition[] conditions;

	/**
	 * @return	...
	 *			| result == ...
	 */
	public Condition[] getConditions()
	{
		return this.conditions;
	}
}
