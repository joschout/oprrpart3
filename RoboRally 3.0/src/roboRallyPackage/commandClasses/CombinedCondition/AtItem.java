/**
 * 
 */
package roboRallyPackage.commandClasses.CombinedCondition;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.CombinedCondition.*;
/**
 * @author Nele
 *
 */
public class AtItem extends CombinedCondition
{
	public AtItem(Robot robot)
	{
		super(robot);
	}
	
	public boolean results()
	{
		for(Element element: this.getRobot().getBoard().getElements(this.getRobot().getPosition()))
		{
			if(element instanceof Item)
			{
				return true;
			}
		}
		return false;
	}
}
