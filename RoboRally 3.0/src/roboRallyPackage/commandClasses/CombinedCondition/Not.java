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
public class Not extends CombinedCondition
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
	@Override
	public String toString(){
		return "(not " + this.getCondition().toString()+ ")";
	}
}
