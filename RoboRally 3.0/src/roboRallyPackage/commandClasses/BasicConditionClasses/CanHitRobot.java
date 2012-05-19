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
public class CanHitRobot extends BasicCondition
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
	@Override
	public String toString(){
		return "(can-hit-robot)";
	}
}
