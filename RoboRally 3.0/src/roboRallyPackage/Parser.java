/**
 * 
 */
package roboRallyPackage;

import static java.lang.System.out;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.BasicCommand.*;
import roboRallyPackage.commandClasses.BooleanCommand.*;
import roboRallyPackage.commandClasses.ConditionCommand.*;
import roboRallyPackage.commandClasses.ControleStatementCommand.*;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.gameElementClasses.*;

/**
 * @author Nele
 *
 */
public class Parser
{
	Parser(Robot robot)
	{
		this.robot = robot;
	}
	
	private Robot robot;
	
	public Robot getRobot()
	{
		return this.robot;
	}

	public Executable parse(String fullProgram)
	{
		fullProgram = fullProgram.trim().toLowerCase();
		Executable resultCommand  = null;
		
		// BasicCommands
		if(fullProgram.startsWith("(move)"))
		{
			resultCommand = new Move(this.getRobot());
		}
		if(fullProgram.startsWith("(turn"))
		{
			String subProgram = fullProgram.substring("(turn".length()).trim();
			if(subProgram.startsWith("clockwise)"))
			{
				resultCommand = new Turn(this.getRobot(),Direction.CLOCKWISE);
			}
			if(subProgram.startsWith("counterclockwise)"))
			{
				resultCommand = new Turn(this.getRobot(),Direction.COUNTER_CLOCKWISE);
			}
		}
		if(fullProgram.startsWith("(shoot)"))
		{
			resultCommand = new Shoot(this.getRobot());
		}
		if(fullProgram.startsWith("(pick-up-and-use)"))
		{
			resultCommand = new PickupAndUse(this.getRobot());
		}
		
		// ControleStatementCommands
		if(fullProgram.startsWith("(seq"))
		{
			java.util.List<Executable> commands = new java.util.ArrayList<Executable>();
			String subProgram = fullProgram.substring("(seq".length()).trim();
			
			boolean stop = false;
			int i = 0;
			
			while(! stop)
			{
				if(subProgram.startsWith(")"))
				{
					stop = true;
				}
				else
				{
					commands.add(this.parse(subProgram));
				}
			}
				
			resultCommand = new Sequence(this.getRobot(), commands);
		}
		if(fullProgram.startsWith("(while"))
		{
			resultCommand = new While();
		}
		if(fullProgram.startsWith("(if"))
		{
			resultCommand = new If();
		}
		
		return resultCommand;
	}
	

}
