
package roboRallyPackage.commandClasses.CombinedCondition;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class And extends CombinedCondition
{
	public And(int programLevel, java.util.List<Condition> conditions)
	{
		super(programLevel);
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
	
	private java.util.List<Condition> conditions;

	public java.util.List<Condition> getConditions()
	{
		return this.conditions;
	}
	
	@Override
	public String toString()
	{
		String result ="(and";
		String indentation = "";
		for(int i = 0; i <= this.getProgramLevel(); i++){
			indentation = indentation + "  ";
		}
		for(Condition condition: this.getConditions())
		{
			result =result  + "\n" + indentation+"  " +condition.toString();
		}
		result =result + "\n" + ")";
		return result;
	}
}
