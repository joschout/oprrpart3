
package roboRallyPackage.commandClasses.BasicCommandClasses;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * Abstract class representing a basic command. 
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public abstract class BasicCommand extends Command
{
	/**
	 * Initializes this basic command with the given program level.
	 * 
	 * @param	programLevel
	 * 			The program level of this new basic command.
	 * @effect	...
	 * 			| super(programLevel)
	 */
	public BasicCommand(int programLevel)
	{
		super(programLevel);
	}
	
	/**
	 * Executes this basic command.
	 * 
	 * @param	robot
	 * 			The robot that will execute the basic command.
	 */
	@Override
	public abstract void execute(Robot robot);
	
	/**
	 * Executes one step of this basic command. However, a basic command is an atomic command,
	 * so this has the same effect as executing the full command.
	 * 
	 * @effect	...
	 * 			| this.execute()
	 */
	@Override
	public void executeStep(Robot robot)
	{
		this.execute(robot);
	}
	
	/**
	 * String representation of this basic command, in the syntax used by the Parser.
	 */
	@Override
	public abstract String getNotationExample();
}
