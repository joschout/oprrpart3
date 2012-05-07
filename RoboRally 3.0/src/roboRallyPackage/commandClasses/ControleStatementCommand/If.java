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
public class If extends ControleStatementCommand
{
	public If(Robot robot, Condition condition, Executable ifCommand, Executable elseCommand)
	{
		super(robot);
		this.condition = condition;
		this.ifCommand = ifCommand;
		this.elseCommand = elseCommand;
	}
	
	private Condition condition;
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
	public Executable getIfCommand() {
		return this.ifCommand;
	}

	/**
	 * @return	...
	 *			| result == ...
	 */
	public Executable getElseCommand() {
		return this.elseCommand;
	}

	private Executable ifCommand;
	private Executable elseCommand;
	
	public void execute()
	{
		if(this.getCondition().results())
		{
			this.getIfCommand().execute();
		}
		else
		{
			this.getElseCommand().execute();
		}
	}
}
