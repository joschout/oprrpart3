/**
 * 
 */
package roboRallyPackage.commandClasses.BasicCommand;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
/**
 * @author Nele
 *
 */
public class PickupAndUse extends BasicCommand
{
	public void execute()
	{
		for(Element element: this.getRobot().getBoard().getElements(this.getRobot().getPosition()))
		{
			if(element instanceof Item)
			{
				this.getRobot().pickUp((Item) element);
				this.getRobot().use((Item) element);
				break;
			}
		}
	}
}
