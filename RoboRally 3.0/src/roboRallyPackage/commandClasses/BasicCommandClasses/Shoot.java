
package roboRallyPackage.commandClasses.BasicCommandClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
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
