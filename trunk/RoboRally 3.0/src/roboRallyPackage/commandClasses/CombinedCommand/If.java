/**
 * 
 */
package roboRallyPackage.commandClasses.CombinedCommand;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
/**
 * @author Nele
 *
 */
public class If extends CombinedCommand
{
	public If(Robot robot, Condition condition, Command ifCommand, Command elseCommand)
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
	public Command getIfCommand() {
		return this.ifCommand;
	}

	/**
	 * @return	...
	 *			| result == ...
	 */
	public Command getElseCommand() {
		return this.elseCommand;
	}

	private Command ifCommand;
	private Command elseCommand;
	
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
