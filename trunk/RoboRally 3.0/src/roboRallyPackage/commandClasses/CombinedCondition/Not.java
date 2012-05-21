
package roboRallyPackage.commandClasses.CombinedCondition;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Not extends CombinedCondition
{
	public Not(int programLevel,Condition condition)
	{
		super(programLevel);
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
	public String toString()
	{
		return "(not " + this.getCondition().toString()+ ")";
	}
}
