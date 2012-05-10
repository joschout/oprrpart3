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
public class Or extends CombinedCondition
{
	public Or(Robot robot, Condition[] conditions)
	{
		super(robot);
		this.conditions = conditions;
	}
	
	public boolean results()
	{
		boolean result = true;
		
		for(Condition condition: this.getConditions())
		{
			result = result || condition.results();
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
