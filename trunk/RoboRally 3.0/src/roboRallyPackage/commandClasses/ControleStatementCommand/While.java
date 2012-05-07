/**
 * 
 */
package roboRallyPackage.commandClasses.ControleStatementCommand;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
/**
 * @author Nele
 *
 */
public class While extends ControleStatementCommand
{
	public While(Robot robot, Condition condition, Executable whileCommand)
	{
		super(robot);
		this.condition = condition;
		this.whileCommand = whileCommand;
	}
	
	Condition condition;
	/**
	 * @return	...
	 *			| result == ...
	 */
	public Condition getCondition() {
		return this.condition;
	}

	/**
	 * @return	...
	 *			| result == ...
	 */
	public Executable getWhileCommand() {
		return this.whileCommand;
	}

	Executable whileCommand;
	
	public void execute()
	{
		while(this.getCondition().results())
		{
			this.getWhileCommand().execute();
		}
	}
}
