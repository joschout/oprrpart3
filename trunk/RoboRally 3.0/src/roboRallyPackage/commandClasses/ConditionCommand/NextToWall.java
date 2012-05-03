/**
 * 
 */
package roboRallyPackage.commandClasses.ConditionCommand;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.ConditionCommand.*;
/**
 * @author Nele
 *
 */
public class nextToWall extends ConditionCommand
{
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
