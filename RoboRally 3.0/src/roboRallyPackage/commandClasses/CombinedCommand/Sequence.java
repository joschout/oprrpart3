
package roboRallyPackage.commandClasses.CombinedCommand;

import be.kuleuven.cs.som.annotate.Basic;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * Class representing a sequence command.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Sequence extends CombinedCommand
{
	/**
	 * Initializes this sequence command with the given program level and commands.
	 * 
	 * @param	programLevel
	 * 			The program level of this new sequence command.
	 * @param	commands
	 * 			The commands of this new sequence command.
	 * @effect	...
	 * 			| super(programLevel)
	 * @post	...
	 * 			| for each command in commands: this.getSeqCommands().contains(command)
	 */
	public Sequence(int programLevel, java.util.List<Command> seqCommands)
	{
		super(programLevel);
		this.seqCommands = seqCommands;
	}
	
	/**
	 * Returns all the commands of this sequence command.
	 */
	@Basic
	public java.util.List<Command> getSeqCommands()
	{
		return this.seqCommands;
	}

	/**
	 * Variable representing a collection of all the commands of this sequence command.
	 */
	java.util.List<Command> seqCommands;
	
	/**
	 * Returns the index of the next command to be executed.
	 */
	@Basic
	public int getNextCommandToExecute() 
	{
		return this.nextCommandToExecute;
	}
	
	/**
	 * Increases the index that indicates which command to execute.
	 * If all commands are executed, this index will be set to zero again.
	 */
	private void increaseNextCommandToExecute() 
	{
		this.nextCommandToExecute = (this.getNextCommandToExecute() + 1)%this.getSeqCommands().size();
	}

	/**
	 * Variable representing the index of the next command to execute.
	 */
	private int nextCommandToExecute = 0;

	/**
	 * Returns whether this sequence command is fully executed.
	 * This will be so if either non or all of the commands are executed. 
	 */
	public boolean isFullyExecuted()
	{
		return (this.getNextCommandToExecute() == 0);
	}

	/**
	 * Executes this full sequence command. 
	 */
	@Override
	public void execute(Robot robot)
	{
		for(Command command: seqCommands)
		{
			command.execute(robot);
		}
	}
	
	/**
	 * Executes one step of this sequence command.
	 */
	@Override
	public void executeStep(Robot robot)
	{
		this.getSeqCommands().get(this.getNextCommandToExecute()).executeStep(robot);
		this.increaseNextCommandToExecute();
	}
	
	/**
	 * String representation of this sequence command.
	 * 
	 * @return	A pretty print string of this sequence command taking into account the program-level.
	 * 			| ...
	 */
	@Override
	public String toString()
	{
		String result ="(seq";
		String indentation = "";
		for(int i = 1; i <= this.getProgramLevel(); i++)
		{
			indentation = indentation + "  ";
		}
		for(Command command: this.getSeqCommands())
		{
			result = result  + "\n" + indentation+ "  " + command.toString();
		}
		result = result + "\n" + indentation + ")";
		return result;
	}
	
	/**
	 * String representation of this sequence command, in the syntax used by the Parser.
	 * 
	 * @return	A syntax example, as used by the parser, of this sequence command.
	 * 			| result == "(seq '(command)' '(command)' ... '(command)')"
	 */
	@Override
	public String getNotationExample()
	{
		return "(seq '(command)' '(command)' ... '(command)')";
	}

}
