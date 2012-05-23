
package roboRallyPackage.commandClasses.CombinedCondition;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * Class representing an and condition.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class And extends CombinedCondition
{
	/**
	 * Initializes this and condition with the given program level and list of conditions.
	 * 
	 * @param	programLevel
	 * 			The program level of this new and condition.
	 * @param	conditions
	 * 			The conditions of this new and condition.
	 * @effect	...
	 * 			| super(programLevel)
	 * @post	...
	 * 			| for each condition in conditions: this.getConditions().contains(condition)
	 */
	public And(int programLevel, java.util.List<Condition> conditions)
	{
		super(programLevel);
		this.conditions = conditions;
	}
	
	/**
	 * Returns a list of all the conditions of this and condition.
	 */
	public java.util.List<Condition> getConditions()
	{
		return this.conditions;
	}

	/**
	 * Variable representing a collection of all the condition of this and condition.
	 */
	private java.util.List<Condition> conditions;

	/**
	 * Returns the result of this and condition.
	 * This will be true when all the condition of this and condition are true.
	 * 
	 * @effect	True if all the conditions of this and condition are true.
	 * 			| ...
	 */
	@Override
	public boolean results()
	{
		boolean result = true;
		
		for(Condition condition: this.getConditions())
		{
			result = result && condition.results();
		}
		
		return results();
	}
	
	/**
	 * Returns the result of this and condition, given the given element.
	 * This will be true when all of the condition of this and condition are true.
	 * 
	 * @param	element
	 * 			The element on who the result will depend
	 * @return	True if all the conditions of this and condition are true.
	 * 			| ...
	 * @throws	IllegalArgumentException
	 * 			When one of the conditions of this and condition throws an IllegalArgumentException
	 * 			| ...
	 */
	@Override
	public boolean results(Element element) throws IllegalArgumentException
	{
		boolean result = true;
		
		for(Condition condition: this.getConditions())
		{
			result = result && condition.results(element);
		}
		
		return result;
	}
	
	/**
	 * String representation of this and condition.
	 */
	@Override
	public String toString()
	{
		String conditionString = "";
		for(Condition condition: this.getConditions())
		{
			conditionString = conditionString + "\n" + condition.toString();
		}
		return this.getIndentation() + "(and" + "\n" 
				+ conditionString + "\n"
				+ this.getIndentation() + ")";
	}
	
	/**
	 * String representation of this and condition, in the syntax used by the Parser.
	 */
	@Override
	public String getNotationExample()
	{
		return "(and '(condition)' '(condition)' ... '(condition))";
	}
}
