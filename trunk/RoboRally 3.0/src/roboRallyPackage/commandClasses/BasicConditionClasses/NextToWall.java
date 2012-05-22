
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * Class representing a next-to-wall condition
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class NextToWall extends BasicCondition
{
	/**
	 * Initializes this next-to-wall condition with the given program level and robot.
	 * 
	 * @param	programLevel
	 * 			The program level of this new next-to-wall condition.
	 * @param	robot
	 * 			The new robot of this next-to-wall condition.
	 * @effect	...
	 * 			| super(programLevel)
	 */
	public NextToWall(int programLevel,Robot robot)
	{
		super(programLevel);
		this.robot = robot;
	}
	
	/**
	 * Variable representing the robot of this next-to-wall condition.
	 */
	private Robot robot;
	
	/**
	 * Returns the robot of this next-to-wall condition.
	 */
	@Basic
	public Robot getRobot()
	{
		return this.robot;
	}
	
//  other version of results: returns true if the robot is standing next to a wall
	//	public boolean results()
	//	{
	//		for(Position position: this.getRobot().getPosition().getAllNeighbours())
	//		{
	//			for(Element element: this.getRobot().getBoard().getElements(position))
	//			{
	//				if(element instanceof Wall)
	//				{
	//					return true;
	//				}
	//			}
	//		}
	//		return false;
	//	}
	
	
	/**
	 * Returns the result of this next-to-wall condition. 
	 * This will be true if the robot of the condition is standing to the right of a wall.
	 * 
	 * @return	...
	 * 			| result == this.results(this.getRobot())
	 */
	@Override
	public boolean results()
	{
		return this.results(this.getRobot());
	}

	/**
	 * Returns the result of this next-to-wall condition, given the given element.
	 * This will always be if the given element is standing to the right of a wall.
	 * 
	 * @param	element
	 * 			The element on who the result will depend
	 * @return	...
	 * 			| if one element in element.getBoard().getElements(element.getPosition().getNeighbour(Orientation.RIGHT)) is instanceof Wall then result == true
	 */
	@Override
	public boolean results(Element element)
	{
		Position rightPosition = element.getPosition().getNeighbour(Orientation.RIGHT);
		// check whether one of the elements on the position to the right of the given element is a wall
		for(Element checkedElement: element.getBoard().getElements(rightPosition))
		{
			if(checkedElement instanceof Wall)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * String representation of this next-to-wall condition.
	 * 
	 * @return	...
	 * 			| result == "(wall)"
	 */
	@Override
	public String toString()
	{
		return "(wall)";
	}

	/**
	 * String representation of this next-to-wall condition, in the syntax used by the Parser.
	 * 
	 * @return	...
	 * 			| result == "(wall)"
	 */
	@Override
	public String getNotationExample()
	{
		return "(wall)";
	}
}

