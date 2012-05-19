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
	public Or(Robot robot, java.util.List<Condition> conditions)
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
	
	private java.util.List<Condition> conditions;

	/**
	 * @return	...
	 *			| result == ...
	 */
	public java.util.List<Condition> getConditions()
	{
		return this.conditions;
	}
	@Override
	public String toString(){
		String result ="(or";
		for(Condition condition: this.getConditions()){
			result =result  + "\n" + "  " +condition.toString();
		}
		result =result + "\n" + ")";
		return result;
	}
}
