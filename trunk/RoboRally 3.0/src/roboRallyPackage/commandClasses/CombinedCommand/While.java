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
public class While extends CombinedCommand
{
	public While(Robot robot, Condition condition, Command whileCommand)
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
	public Command getWhileCommand() {
		return this.whileCommand;
	}

	Command whileCommand;
	
	public void execute()
	{
		while(this.getCondition().results())
		{
			this.getWhileCommand().execute();
		}
	}
}
