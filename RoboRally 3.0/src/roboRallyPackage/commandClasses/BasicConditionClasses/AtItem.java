
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.gameElementClasses.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * Class representing an at-item condition.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class AtItem extends BasicCondition
{
	/**
	 * Initializes this at-item condition with the given program level and robot.
	 * 
	 * @param	programLevel
	 * 			The program level of this new at-item condition.
	 * @param	robot
	 * 			The robot of this new at-item condition.
	 * @effect	...
	 * 			| super(programLevel)
	 * @post	...
	 * 			| (new this).getRobot() == robot
	 */
	public AtItem(int programLevel, Robot robot)
	{
		super(programLevel);
		this.robot = robot;
	}
	
	/**
	 * Variable representing the robot of this at-item condition.
	 */
	private Robot robot;
	
	/**
	 * Returns the robot of this at-item condition.
	 */
	@Basic
	public Robot getRobot()
	{
		return this.robot;
	}
	
	/**
	 * Returns the result of this at-item condition.
	 * This will be true when at least one item is placed on the same position as the robot of this condition.
	 * 
	 * @effect	...
	 * 			| result == this.results(this.getRobot())
	 */
	public boolean results()
	{
		return this.results(this.getRobot());
	}
	
	
	/**
	 * Returns the result of this at-item condition, given the given element.
	 * This will be true when at least one item is placed on the same position as the given element.
	 * 
	 * @param	element
	 * 			The element on who the result will depend
	 * @return	...
	 * 			| if (for some element in element.getBoard().getElements(this.getRobot().getPosition): element instanceof Item) then result == true
	 */
	@Override
	public boolean results(Element element)
	{
		// for each element on the same position of the given element, check whether it is an item.
		for(Element currentElement: element.getBoard().getElements(this.getRobot().getPosition()))
		{
			if(currentElement instanceof Item)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * String representation of this at-item condition.
	 */
	@Override
	public String toString()
	{
		return this.getIndentation() + "(at-item)";
	}
	
	/**
	 * String representation of this at-item condition, in the syntax used by the Parser.
	 */
	@Override
	public String getNotationExample()
	{
		return "(at-item)";
	}
}
