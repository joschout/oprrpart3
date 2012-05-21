
package roboRallyPackage.commandClasses;

import roboRallyPackage.gameElementClasses.Robot;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public abstract class Program
{
	public void run(String program, Robot robot)
	{
		this.getCommand(program).execute(robot);
	}
	
	public Command getCommand(String program)
	{
		return null;
	}
	
	public void executeStep()
	{
	}
}
