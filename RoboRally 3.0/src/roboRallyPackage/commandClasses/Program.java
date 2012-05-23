
package roboRallyPackage.commandClasses;

import roboRallyPackage.gameElementClasses.Robot;

/**
 * Class representing a program
 *
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public abstract class Program
{	
	
	/**
	 * Initializes this program with the given program level.
	 */
	public Program(int programLevel)
	{
		this.programLevel = programLevel;
	}
	
	/**
	 * Variable representing the level of the program. This variable helps for dealing with 'nested' programs.
	 * For example, given the program represented by the following lines of pseudo-code:
	 * 	(while
  	 *		(true)
  	 *		(if
  	 *			(can-hit-robot)
  	 *			(shoot)
  	 *		(turn clockwise)
  	 *		)
	 *	) 
	 * 
	 * In this example, '(while ...)' represents an instance of the subclass While of Program,
	 * '(If...) represents an instance of the subclass If and so on.
	 * Here, while is the most outer piece of the program, it contains the other pieces. So its program level is 0.
	 * The pieces representing the condition of the while loop (here 'true') 
	 * and the if- and else-commands are just inside the while loop. So they get program level 1.
	 */
	private int programLevel;
	
	/**
	 * Returns a spatial string that determines the indentation of the toString, taking in account the program level.
	 */
	protected String getIndentation()
	{
		String indentation = "";
		for(int i = 1; i <= this.getProgramLevel(); i++)
		{
			indentation = indentation + "  ";
		}
		return indentation;
	}
	
	/**
	 * Returns the program level of this program.
	 */
	protected int getProgramLevel()
	{
		return programLevel;
	}
	
	/**
	 * Returns whether this program is fully executed.
	 */
	public boolean isFullyExecuted()
	{
		return true;
	}
	
	/**
	 * Executes one step of this program.
	 */
	public void executeStep(Robot robot)
	{
		
	}
	
	/**
	 * Executes this full program. 
	 */
	public void execute(Robot robot)
	{
		
	}

	/**
	 * String representation of this program, in the syntax used by the Parser.
	 */
	public abstract String getNotationExample();
}

