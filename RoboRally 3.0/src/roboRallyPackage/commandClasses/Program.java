
package roboRallyPackage.commandClasses;

import roboRallyPackage.gameElementClasses.Robot;

/**
 * 
 * 
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public abstract class Program
{
	public void run(String program, Robot robot)
	{
		this.getCommand(program).execute(robot);
	}
	
	public Command getCommand(String program)
	{
		return null;
	}
	
	public void executeStep(Robot robot)
	{
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
	 *  '(If...) represents an instance of the subclass If and so on.
	 *  Here, while is the most outer piece of the program, it contains the other pieces. So its program level is 0.
	 *  The pieces representing the condition of the while loop (here 'true') 
	 *  and the if- and else-commands are just inside the while loop. So they get program level 1.
	 * 
	 */
	public int programLevel;
	
	public int getProgramLevel(){
		return programLevel;
	}
}

