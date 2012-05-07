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
public class CanHitRobot extends ConditionCommand
{
	public CanHitRobot(Robot robot)
	{
		super(robot);
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
}
