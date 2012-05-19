/**
 * 
 */
package roboRallyPackage.commandClasses.BasicCommandClasses;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
/**
 * @author Nele
 *
 */
public class Shoot extends BasicCommand
{
	public Shoot(Robot robot)
	{
		super(robot);
	}
	
	public void execute()
	{
		this.getRobot().shoot();
	}
	@Override
	public String toString(){
		return "(shoot)";
	}

}
