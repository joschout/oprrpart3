
package roboRallyPackage.commandClasses.CombinedCondition;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * Class representing an or condition.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Or extends CombinedCondition
{
	/**
	 * Initializes this or condition with the given program level and list of conditions.
	 * 
	 * @param	programLevel
	 * 			The program level of this new or condition.
	 * @param	conditions
	 * 			The conditions of this new or condition.
	 * @effect	...
	 * 			| super(programLevel)
	 * @post	...
	 * 			| for each condition in conditions: this.getConditions().contains(condition)
	 */
	public Or(int programLevel, java.util.List<Condition> conditions)
	{
		super(programLevel);
		this.conditions = conditions;
	}
	
	/**
	 * Returns a list of all the conditions of this or condition.
	 */
	@Basic
	public java.util.List<Condition> getConditions()
	{
		return this.conditions;
	}
	
	/**
	 * Variable representing a collection of all the condition of this or condition.
	 */
	private java.util.List<Condition> conditions;

	/**
	 * Returns the result of this or condition.
	 * This will be true when at least one of the condition of this or condition is true.
	 * 
	 * @effect	True if at least one of the conditions of this or condition is true.
	 * 			| ...
	 */
	public boolean results()
	{
		boolean result = false;
		
		for(Condition condition: this.getConditions())
		{
			result = result || condition.results();
		}
		
		return result;
	}

	/**
	 * Returns the result of this or condition, given the given element.
	 * This will be true when at least one of the condition of this or condition is true.
	 * 
	 * @param	element
	 * 			The element on who the result will depend
	 * @return	True if at least one of the conditions of this or condition is true.
	 * 			| ...
	 * @throws	IllegalArgumentException
	 * 			When one of the conditions of this or condition throws an IllegalArgumentException
	 * 			| ...
	 */
	@Override
	public boolean results(Element element) throws IllegalArgumentException
	{
		boolean result = false;
		
		for(Condition condition: this.getConditions())
		{
			result = result || condition.results(element);
		}
		
		return result;
	}

	/**
	 * String representation of this or condition.
	 * 
	 * @return	A pretty print string of this or condition taking into account the program-level.
	 * 			| ...
	 */
	@Override
	public String toString()
	{
		String result ="(or";
		String indentation = "";
		
		for(int i = 0; i <= this.getProgramLevel(); i++)
		{
			indentation = indentation + "  ";
		}
		for(Condition condition: this.getConditions())
		{
			result = result  + "\n" + "  " + condition.toString();
		}
		
		result = result + "\n" + indentation + ")";
		return result;
	}
	
	/**
	 * String representation of this or condition, in the syntax used by the Parser.
	 * 
	 * @return	A syntax example, as used by the parser, of this or condition.
	 * 			| result == "(or '(condition)' '(condition)' ... '(condition))"
	 */
	@Override
	public String getNotationExample()
	{
		return "(or '(condition)' '(condition)' ... '(condition))";
	}
}
