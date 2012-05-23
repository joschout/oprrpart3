
package roboRallyPackage.commandClasses.BasicCommandClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * Class representing a shoot command.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Shoot extends BasicCommand
{
	/**
	 * Initializes this shoot command with the given programLevel
	 * 
	 * @param	programLevel
	 * 			The program level of this new shoot command
	 * @effect	...
	 * 			| super(programLevel)
	 */
	public Shoot(int programLevel)
	{
		super(programLevel);
	}
	
	/**
	 * Executes this shoot command. The given robot will shoot one time.
	 * 
	 * @param	robot
	 * 			The robot that will shoot.
	 * @effect	...
	 * 			| robot.shoot()
	 */
	public void execute(Robot robot)
	{
		robot.shoot();
	}
	
	/**
	 * String representation of this shoot command.
	 */
	@Override
	public String toString()
	{
		return this.getIndentation() + "(shoot)";
	}
	
	/**
	 * String representation of this shoot command, in the syntax used by the Parser.
	 */
	@Override
	public String getNotationExample()
	{
		return "(shoot)";
	}

}
