
package roboRallyPackage.commandClasses.CombinedCommand;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * Class representing a while command.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class While extends CombinedCommand
{
	/**
	 * Initializes this while command with the given program level, condition and command.
	 * 
	 * @param	programLevel
	 * 			The program level of this new while command.
	 * @param	condition
	 * 			The condition of this new while command.
	 * @param	whileCommand
	 * 			The command of this new while command.
	 * @effect	...
	 * 			| super(programLevel)
	 * @post	...
	 * 			| this.getCondition() == condition
	 * @post	...
	 * 			| this.getWhileCommand() == whileCommand
	 */
	public While(int programLevel, Condition condition, Command whileCommand)
	{
		super(programLevel);
		this.condition = condition;
		this.whileCommand = whileCommand;
	}
	
	/**
	 * Returns the condition of this while command.
	 */
	@Basic
	public Condition getCondition()
	{
		return this.condition;
	}

	/**
	 * Variable representing the condition of this while command.
	 */
	private Condition condition;

	/**
	 * Returns the command of this while command.
	 */
	@Basic
	public Command getWhileCommand()
	{
		return this.whileCommand;
	}

	/**
	 * Variable representing the command of this while command.
	 */
	private Command whileCommand;
	
	
	/**
	 * Executes this full while command. While the conditions results true, the command is executed.
	 */
	@Override
	public void execute(Robot robot)
	{
		while(this.getCondition().results() || !this.getWhileCommand().isFullyExecuted())
		{
			this.getWhileCommand().execute(robot);
		}
	}
	
	/**
	 * Executes one step of this while command.
	 */
	@Override
	public void executeStep(Robot robot)
	{
		if(this.getCondition().results() || !this.getWhileCommand().isFullyExecuted())
		{
			this.getWhileCommand().executeStep(robot);
		}
	}
	
	/**
	 * String representation of this while command.
	 */
	@Override
	public String toString()
	{
		return this.getIndentation() + "(while" + "\n" 
				+ this.getCondition().toString() + "\n"
				+ this.getWhileCommand().toString() + "\n"
				+ this.getIndentation() + ")";
	}
	
	/**
	 * String representation of this while command, in the syntax used by the Parser.
	 */
	@Override
	public String getNotationExample()
	{
		return "(while '(condition)' '(command)')";
	}
}
