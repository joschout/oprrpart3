package roboRallyPackage.commandClasses.CombinedCondition;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Or extends CombinedCondition
{
	public Or( java.util.List<Condition> conditions)
	{
	//	super(robot);
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

	public java.util.List<Condition> getConditions()
	{
		return this.conditions;
	}
	
	@Override
	public String toString()
	{
		String result ="(or";
		for(Condition condition: this.getConditions())
		{
			result =result  + "\n" + "  " +condition.toString();
		}
		result =result + "\n" + ")";
		return result;
	}
}
