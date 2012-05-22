
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * Class representation an is-item representation.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class isItem extends BasicCondition
{
	/**
	 * Initializes this is-item condition with the given program level.
	 * 
	 * @param	programLevel
	 * 			The program level of this new is-item condition.
	 * @effect	...
	 * 			| super(programLevel)
	 */
	public isItem(int programLevel)
	{
		super(programLevel);
	}
	
	/**
	 * This method is not supported by this is-item condition.
	 * 
	 * @throws	UnsupportesOperationException
	 * 			...
	 * 			| true
	 */
	@Override
	public boolean results() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the result of this is-item condition, given the given element.
	 * This will be true if the given element is an item.
	 * 
	 * @param	element
	 * 			The element on who the result will depend
	 * @return	...
	 * 			| result == (element instanceof Item)
	 */
	@Override
	public boolean results(Element element)
	{
		return (element instanceof Item);
	}
	
	/**
	 * String representation of this is-item condition.
	 * 
	 * @return	...
	 * 			| result == "(true)"
	 */
	@Override
	public String toString()
	{
		return "(item)";
	}

	/**
	 * String representation of this is-item condition, in the syntax used by the Parser.
	 * 
	 * @return	...
	 * 			| result == "(item)"
	 */
	@Override
	public String getNotationExample()
	{
		return "(item)";
	}
}

