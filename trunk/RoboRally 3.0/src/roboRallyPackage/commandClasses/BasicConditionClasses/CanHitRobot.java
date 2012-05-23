
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.gameElementClasses.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * Class representing a can-hit-robot condition.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class CanHitRobot extends BasicCondition
{
	/**
	 * Initializes this can-hit-robot condition with the given program level and robot.
	 * 
	 * @param	programLevel
	 * 			The program level of this new can-hit-robot condition.
	 * @param	robot
	 * 			The robot of this new can-hit-robot condition.
	 * @effect	...
	 * 			| super(programLevel)
	 * @post	...
	 * 			| (new this).getRobot() == robot
	 */
	public CanHitRobot(int programLevel, Robot robot)
	{
		super(programLevel);
		this.robot = robot;
	}
	
	/**
	 * Variable representing the robot of this can-hit-robot condition.
	 */
	private Robot robot;
	
	/**
	 * Returns the robot of this can-hit-robot condition.
	 */
	@Basic
	public Robot getRobot()
	{
		return this.robot;
	}
	
	/**
	 * Returns the result of this can-hit-robot condition.
	 * This will be true if the robot of this condition can hit another robot facing its current orientation.
	 * This means that on the first occupied position, starting from the robot, moving with its orientation, contains at least a robot.
	 * 
	 * @effect	...
	 * 			| this.results(this.getRobot())
	 */
	public boolean results()
	{
		return this.results(this.getRobot());
	}
	
	/**
	 * Returns the result of this can-hit-robot condition, given the given element.
	 * This will be true if the given element can hit a robot facing its current orientation.
	 * This means that on the first occupied position, starting from the element, moving with its orientation, contains at least a robot.
	 *
	 * @param	element
	 * 			The element on who the result will depend
	 * @return	...
	 * 			| let firstOccupiedPosition be element.getBoard().getFirstElementStartingAt(element.getPosition, element.getOrientation()).getPosition() in
	 * 			| if for some element in element.getBoard().getElements(firstOccupiedPosition): instanceof Robot, then result == true
	 * @throws	IllegalArgumentException
	 * 			...
	 * 			| !(element instanceof Robot)
	 */
	@Override
	public boolean results(Element element) throws IllegalArgumentException
	{
		if(!(element instanceof Robot))
		{
			throw new IllegalArgumentException();
		}
		
		// get the first position in the line of the given robot that contains an element
		Element testElement = element.getBoard().getFirstElementStartingAt(element.getPosition(), ((Robot)element).getOrientation());
	
		// if some element is found it may be possible that more elements are placed on that position
		// if one of them is a robot, this method should return true
		if(testElement != null)
		{
			for(Element currentElement: element.getBoard().getElements(testElement.getPosition()))
			{
				if(currentElement instanceof Robot)
				{
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * String representation of this can-hit-robot condition.
	 */
	@Override
	public String toString()
	{
		return this.getIndentation() + "(can-hit-robot)";
	}
	
	/**
	 * String representation of this can-hit-robot condition, in the syntax used by the Parser.
	 */
	@Override
	public String getNotationExample()
	{
		return "(can-hit-robot)";
	}
}
