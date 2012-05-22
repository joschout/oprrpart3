
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * Class representing a true condition
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class True extends BasicCondition
{
	/**
	 * Initializes this true condition with the given program level.
	 * 
	 * @param	programLevel
	 * 			The program level of this new true condition.
	 * @effect	...
	 * 			| super(programLevel)
	 */
	public True(int programLevel)
	{
		super(programLevel);
	}
	
	/**
	 * Returns the result of this true condition. This will always be true.
	 * 
	 * @return	...
	 * 			| result == true
	 */
	@Override
	public boolean results()
	{
		return true;
	}
	
	/**
	 * Returns the result of this true condition, given the given element. This will always be true.
	 * 
	 * @param	element
	 * 			The element on who the result will depend
	 * @return	...
	 * 			| result == true
	 */
	@Override
	public boolean results(Element element)
	{
		return true;
	}

	/**
	 * String representation of this true condition.
	 * 
	 * @return	...
	 * 			| result == "(true)"
	 */
	@Override
	public String toString()
	{
		return "(true)";
	}

	/**
	 * String representation of this true condition, in the syntax used by the Parser.
	 * 
	 * @return	...
	 * 			| result == "(true)"
	 */
	@Override
	public String getNotationExample()
	{
		return "(true)";
	}
}
