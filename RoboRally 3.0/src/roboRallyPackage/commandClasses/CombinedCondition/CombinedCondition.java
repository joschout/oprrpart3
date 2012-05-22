
package roboRallyPackage.commandClasses.CombinedCondition;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * Class representing a combined condition.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public abstract class CombinedCondition extends Condition
{
	/**
	 * Initializes this combined condition with the given program levels.
	 * 
	 * @param	programLevel
	 * 			The program level of this new combined condition.
	 */
	public CombinedCondition(int programLevel)
	{
		super(programLevel);
	}

	/**
	 * Returns the result of this combined condition.
	 */
	@Override
	public abstract boolean results();
	
	/**
	 * Returns the result of this combined condition, given the given element.
	 * 
	 * @param	element
	 * 			The element on who the result will depend
	 * @throws	IllegalArgumentException
	 * 			When the combined condition cannot be invoked with the given class of elements.
	 * 			| ...
	 */
	@Override
	public abstract boolean results(Element element) throws IllegalArgumentException;
	
	/**
	 * String representation of this combined condition, in the syntax used by the Parser.
	 */
	@Override
	public abstract String getNotationExample();
}
