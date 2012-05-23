
package roboRallyPackage.commandClasses.BasicCommandClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * Class representing a move command.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Move extends BasicCommand
{
	/**
	 * Initializes this turn command with the given program level.
	 * 
	 * @param	programLevel
	 * 			The program level of this new shoot command.
	 * @effect	...
	 * 			| super(programLevel)
	 */
	public Move(int programLevel)
	{
		super(programLevel);
	}
	
	/**
	 * Executes this move command. The given robot will move one step.
	 * 
	 * @param	robot
	 * 			The robot to be moved.
	 * @effect	...
	 * 			| robot.moveOneStep()
	 */
	public void execute(Robot robot)
	{
		robot.moveOneStep();
	}
	
	/**
	 * String representation of this move command.
	 */
	@Override
	public String toString()
	{
		return this.getIndentation() + "(move)";
	}
	
	/**
	 * String representation of this move command, in the syntax used by the Parser.
	 */
	@Override
	public String getNotationExample()
	{
		return "(move)";
	}
}
