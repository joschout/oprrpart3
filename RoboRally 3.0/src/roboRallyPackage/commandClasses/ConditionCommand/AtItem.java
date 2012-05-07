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
public class AtItem extends ConditionCommand
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
