
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * Class representing a basic condition.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public abstract class BasicCondition extends Condition
{
	/**
	 * Initializes this true condition with the given program level.
	 */
	public BasicCondition(int programLevel)
	{
		super(programLevel);
	}
	
	/**
	 * Returns the result of this true condition.
	 */
	@Override
	public abstract boolean results();
	
	/**
	 * Returns the result of this true condition, given the given element.
	 * 
	 * @param	element
	 * 			The element on who the result will depend
	 */
	@Override
	public abstract boolean results(Element element);
	
	/**
	 * String representation of this true condition, in the syntax used by the Parser.
	 */
	@Override
	public abstract String getNotationExample();
}
