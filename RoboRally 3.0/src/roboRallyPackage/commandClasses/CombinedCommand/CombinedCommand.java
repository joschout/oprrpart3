
package roboRallyPackage.commandClasses.CombinedCommand;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * Class representing a combined command.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public abstract class CombinedCommand extends Command
{
	/**
	 * Initializes this combined command with the given program level.
	 * 
	 * @param	programLevel
	 * 			The program level of this new while command.
	 * @effect	...
	 * 			| super(programLevel)
	 */
	public CombinedCommand(int programLevel)
	{
		super(programLevel);
	}
	
	/**
	 * Executes this full combined combined.
	 */
	@Override
	public abstract void execute(Robot robot);
	
	/**
	 * Executes one step of this combined command.
	 */
	@Override
	public abstract void executeStep(Robot robot);
	
	/**
	 * String representation of this while command, in the syntax used by the Parser.
	 */
	@Override
	public abstract String getNotationExample();

}
