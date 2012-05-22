
package roboRallyPackage.commandClasses.BasicCommandClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * Class representing a pick-up-and-use command.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class PickupAndUse extends BasicCommand
{
	/**
	 * Initializes this pick-up-and-use command with the given program level.
	 * 
	 * @param	programLevel
	 * 			The program level of this new shoot command.
	 * @effect	...
	 * 			| super(programLevel)
	 */
	public PickupAndUse(int programLevel)
	{
		super(programLevel);
	}
	
	/**
	 * Executes this pick-up-and-use command. The given robot will pick up an item on the position it is standing and use it immediately.
	 * If no items are present on the position of the robot, this method will have no effect.
	 * 
	 * @param	robot
	 * 			The robot that will pickup and use an item.
	 * @effect	...
	 * 			| for maximum one element instanceof Item in robot.getBoard().getElements(robot.getPosition()):
	 * 			|  robot.pickup(element) && robot.use(element)
	 */
	public void execute(Robot robot)
	{
		// get all the elements on the position of the given robot.
		for(Element element: robot.getBoard().getElements(robot.getPosition()))
		{
			// if the current element is an item, the given robot will pick it up and use it.
			if(element instanceof Item)
			{
				robot.pickUp((Item) element);
				robot.use((Item) element);
				break;
			}
		}
	}
	
	/**
	 * String representation of this pick-up-and-use command.
	 * 
	 * @return	...
	 * 			| result == "(pick-up-and-use)"
	 */
	@Override
	public String toString()
	{
		return "(pick-up-and-use)";
	}
	
	/**
	 * String representation of this pick-up-and-use command, in the syntax used by the Parser.
	 * 
	 * @return	...
	 * 			| result == "(pick-up-and-use)"
	 */
	@Override
	public String getNotationExample()
	{
		return "(pick-up-and-use)";
	}
}
