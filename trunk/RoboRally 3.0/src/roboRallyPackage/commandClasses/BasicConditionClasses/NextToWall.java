/**
 * 
 */
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.CombinedCondition.*;
/**
 * @author Nele
 *
 */
public class NextToWall extends BasicCondition
{
	public NextToWall(Robot robot)
	{
		super(robot);
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
}
