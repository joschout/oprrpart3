
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class NextToWall extends BasicCondition
{
	public NextToWall(Robot robot)
	{
	//	super(robot);
		this.robot = robot;
	}
	
	private Robot robot;
	
	public Robot getRobot(){
		return this.robot;
	}
	
	public boolean results()
	{
		for(Position position: this.getRobot().getPosition().getAllNeighbours())
		{
			for(Element element: this.getRobot().getBoard().getElements(position))
			{
				if(element instanceof Wall)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return "(wall)";
	}
}
