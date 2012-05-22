
package roboRallyPackage.commandClasses.BasicCommandClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class PickupAndUse extends BasicCommand
{
	public PickupAndUse(int programLevel)
	{
		super(programLevel);
	}
//	public PickupAndUse(Robot robot)
//	{
//		super(robot);
//	}
	
	public void execute(Robot robot)
	{
		for(Element element: robot.getBoard().getElements(robot.getPosition()))
		{
			if(element instanceof Item)
			{
				robot.pickUp((Item) element);
				robot.use((Item) element);
				break;
			}
		}
	}
	
	@Override
	public String toString()
	{
		return "(pick-up-and-use)";
	}
	
	@Override
	public String getNotationExample()
	{
		return "(pick-up-and-use)";
	}
}
