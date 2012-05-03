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
public class Or extends BooleanCommand
{
	public Or(Condition[] conditions)
	{
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
