
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class CanHitRobot extends BasicCondition
{
	public CanHitRobot(Robot robot)
	{
		this.robot = robot;
	//	super(robot);
	}
	
	public Robot robot;
	
	public Robot getRobot(){
		return this.robot;
	}
	
	
	public boolean results()
	{
		Element testElement = this.getRobot().getBoard().getFirstElementStartingAt(this.getRobot().getPosition(), this.getRobot().getOrientation());
		for(Element element: this.getRobot().getBoard().getElements(testElement.getPosition()))
		{
			if(element instanceof Robot)
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return "(can-hit-robot)";
	}
}
