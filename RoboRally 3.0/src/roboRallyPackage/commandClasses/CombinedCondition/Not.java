
package roboRallyPackage.commandClasses.CombinedCondition;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * Class representing a not condition
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Not extends CombinedCondition
{
	/**
	 * Initializes this not condition with the given program level and condition.
	 * 
	 * @param	programLevel
	 * 			The program level of this new not condition.
	 * @param	condition
	 * 			The condition of this new not condition.
	 * @effect	...
	 * 			| super(programLevel)
	 * @post	...
	 * 			| this.getCondition() == condition
	 */
	public Not(int programLevel, Condition condition)
	{
		super(programLevel);
		this.condition = condition;
	}
	
	/**
	 * Returns the condition of this not condition.
	 */
	@Basic
	public Condition getCondition()
	{
		return this.condition;
	}
	
	/**
	 * Variable representing the condition of this not condition.
	 */
	private Condition condition;

	/**
	 * Returns the result of this not condition.
	 * This will be true when at the condition of this not condition results false.
	 * 
	 * @effect	True the condition of this not condition results false.
	 * 			| result == !condition.results()
	 */
	public boolean results()
	{
		return !condition.results();
	}

	/**
	 * Returns the result of this not condition, given the given element.
	 * This will be true when at the condition of this not condition results false.
	 * 
	 * @param	element
	 * 			The element on who the result will depend
	 * @return	True the condition of this not condition results false.
	 * 			| result == !condition.results(element)
	 * @throws	IllegalArgumentException
	 * 			When the condition of this not condition throws an IllegalArgumentException
	 * 			| ...
	 */
	public boolean results(Element element) throws IllegalArgumentException
	{
		return !condition.results(element);
	}

	/**
	 * String representation of this not condition.
	 */
	@Override
	public String toString()
	{
		return this.getIndentation() + "(not " + "/n"
				+ this.getCondition().toString() + ")";
	}
	
	/**
	 * String representation of this not condition, in the syntax used by the Parser.
	 */
	@Override
	public String getNotationExample()
	{
		return "(not '(condition)')";
	}
}
