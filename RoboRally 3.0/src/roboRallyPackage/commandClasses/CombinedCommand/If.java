
package roboRallyPackage.commandClasses.CombinedCommand;

import be.kuleuven.cs.som.annotate.Basic;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * Class representing an if command.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class If extends CombinedCommand
{
	/**
	 * Initializes this if command with the given program level, condition and commands.
	 * 
	 * @param	programLevel
	 * 			The program level of this new if command.
	 * @param	condition
	 * 			The condition of this new if command.
	 * @param	ifCommand
	 * 			The if command of this new if command.
	 * @param	elseCommand
	 * 			The else command of this new if command.
	 * @effect	...
	 * 			| super(programLevel)
	 * @post	...
	 * 			| this.getCondition() == condition
	 * @post	...
	 * 			| this.getIfCommand() == ifCommand
	 * @post	...
	 * 			| this.getElseCommand() == elseCommand
	 */
	public If(int programLevel, Condition condition, Command ifCommand, Command elseCommand)
	{
		super(programLevel);
		this.condition = condition;
		this.ifCommand = ifCommand;
		this.elseCommand = elseCommand;
	}
	
	/**
	 * Returns the condition of this if command.
	 */
	@Basic
	public Condition getCondition()
	{
		return this.condition;
	}

	/**
	 * Variable representing the condition of this if command.
	 */
	private Condition condition;

	/**
	 * Returns the if command of this if command.
	 */
	@Basic
	public Command getIfCommand()
	{
		return this.ifCommand;
	}

	/**
	 * Variable representing the if command of this if command.
	 */
	private Command ifCommand;

	/**
	 * Returns the else command of this if command.
	 */
	@Basic
	public Command getElseCommand() 
	{
		return this.elseCommand;
	}

	/**
	 * Variable representing the else command of this if command.
	 */
	private Command elseCommand;
	
	/**
	 * Executes this full if command.
	 */
	@Override
	public void execute(Robot robot)
	{
		if(this.getCondition().results())
		{
			this.getIfCommand().execute(robot);
		}
		else
		{
			this.getElseCommand().execute(robot);
		}
	}
	
	/**
	 * Executes one step of this if command.
	 */
	@Override
	public void executeStep(Robot robot)
	{
		if(this.getCondition().results())
		{
			this.getIfCommand().executeStep(robot);
		}
		else
		{
			this.getElseCommand().executeStep(robot);
		}
	}
	
	/**
	 * String representation of this if command.
	 */
	@Override
	public String toString()
	{ 
		return this.getIndentation() + "(if" + "\n"
				 + this.getCondition().toString() + "\n"
				 + this.getIfCommand().toString() + "\n"
				 + this.getElseCommand().toString() + "\n"
				 + this.getIndentation() +")";
	}
	
	/**
	 * String representation of this if command, in the syntax used by the Parser.
	 */
	@Override
	public String getNotationExample()
	{
		return "(if '(condition)' '(if-command)' '(else-command)')";
	}
}
