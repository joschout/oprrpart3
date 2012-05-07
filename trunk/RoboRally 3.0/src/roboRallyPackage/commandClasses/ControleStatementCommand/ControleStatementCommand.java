package roboRallyPackage.commandClasses.ControleStatementCommand;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * 
 */

/**
 * @author Nele
 *
 */
public abstract class ControleStatementCommand extends Command implements Executable
{
	public ControleStatementCommand(Robot robot)
	{
		super(robot);
	}
	
	public abstract void execute();
}
