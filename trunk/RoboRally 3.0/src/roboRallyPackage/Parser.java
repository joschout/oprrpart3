/**
 * 
 */
package roboRallyPackage;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.BasicCommand.*;
import roboRallyPackage.commandClasses.BasicCondition.*;
import roboRallyPackage.commandClasses.CombinedCondition.*;
import roboRallyPackage.commandClasses.CombinedCommand.*;
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

	public Command parse(String fullProgram)
	{
		fullProgram = fullProgram.replaceAll(" ","").toLowerCase();
		
		Command resultCommand  = null;
		
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
			String subProgram = fullProgram.substring("(seq".length()).trim();
			
			java.util.List<Executable> commands = new java.util.ArrayList<Executable>();
			boolean stop = false;
			
			while(! stop)
			{
				if(subProgram.startsWith(")"))
				{
					stop = true;
				}
				else
				{
					// get the first command of the current subProgram
					commands.add((Executable) this.parse(subProgram));
					// cut the command that we just found off the current subProgram-string
					int index = subProgram.indexOf(")");
					subProgram = subProgram.substring(index + 1).trim();
				}
			}
				
			resultCommand = new Sequence(this.getRobot(), commands);
		}
		if(fullProgram.startsWith("(while"))
		{
			String subProgram = fullProgram.substring("(while".length()).trim();

			Condition condition;
			Executable executable;

			condition = (Condition) this.parse(subProgram);
			
			subProgram.substring(subProgram.indexOf("(")).trim();
			if(subProgram.substring(0,subProgram.indexOf(")")).contains("("))
			{
				
			}
			subProgram.substring(subProgram.indexOf(")")).trim();
			
					if(this.parse(subProgram) instanceof Condition)
					{
						condition = this.parse(subProgram) instanceof Condition;
					}
				}
			}

			resultCommand = new While(this.getRobot(),condition,executable);
		}
		if(fullProgram.startsWith("(if"))
		{
			resultCommand = new If();
		}
		
		return resultCommand;
	}
	
	
	public java.util.ArrayList<Program> parse2(String inputProgram){
		
		inputProgram = inputProgram.replaceAll(" ","").toLowerCase();
		java.util.ArrayList<String> progAsSubProgs = this.getSubstringsBracketCutter(inputProgram);
		java.util.ArrayList<> program = new ArrayList<Command>();
		
		for(int i=0; i<=progAsSubProgs.size()-1;i++){
		// BasicCommands
				if(progAsSubProgs.get(i).equals("(move)"))
				{
					program.add(new Move(this.getRobot()));
				}
				if(progAsSubProgs.get(i).equals("(turnclockwise)"))
				{
					program.add( new Turn(this.getRobot(),Direction.CLOCKWISE));
				}
				if(progAsSubProgs.get(i).equals("(turncounterclockwise)"))
				{
					program.add( new Turn(this.getRobot(),Direction.COUNTER_CLOCKWISE));
				}
				if(progAsSubProgs.get(i).equals("(shoot)"))
				{
					program.add(new Shoot(this.getRobot()));
				}
				if(progAsSubProgs.get(i).equals("(pick-up-and-use)"));
				{
					program.add(new PickupAndUse(this.getRobot()));
				}
				
				// ControleStatementCommands
				if(progAsSubProgs.get(i).startsWith("(seq"))
				{
					
					String subProgram = progAsSubProgs.get(i).replaceFirst("(seq", "").trim();
					// het haakje ')' van "(seq" moet er nog vanachter afgeknipt worden
					subProgram = subProgram.substring(0, subProgram.length()-2);
					java.util.ArrayList subProgAsSubProgs=this.parse2(subProgram);
					if(subProgAsSubProgs.contains(Condition.class)){
						throw new IllegalSyntaxException ;
					}
					
					program.add(new Sequence(this.getRobot(), ((java.util.ArrayList<Command>) subProgAsSubProgs)));
					
				}
				if(progAsSubProgs.get(i).startsWith("(while"))
				{
					String subProgram = progAsSubProgs.get(i).replaceFirst("(while", "").trim();
					subProgram = subProgram.substring(0, subProgram.length()-2);
					java.util.ArrayList<Program> subProgAsSubProgs=this.parse2(subProgram);
					
					if(subProgAsSubProgs.size() == 2 
							&& (subProgAsSubProgs.get(0) instanceof  Condition)
							&& (subProgAsSubProgs.get(1) instanceof  Command)){
					program.add(new While(this.getRobot(), (Condition) subProgAsSubProgs.get(0), (Command)subProgAsSubProgs.get(1)));
					}
				
				}
				if(progAsSubProgs.get(i).startsWith("(if"))
				{
					String subProgram = progAsSubProgs.get(i).replaceFirst("(if", "").trim();
					subProgram = subProgram.substring(0, subProgram.length()-2);
					java.util.ArrayList<Program> subProgAsSubProgs=this.parse2(subProgram);
					
					if(subProgAsSubProgs.size() == 3 && (subProgAsSubProgs.get(0) instanceof Condition) && (subProgAsSubProgs.get(1) instanceof Command )&&(subProgAsSubProgs.get(2) instanceof Command)){
						program.add(new If(this.getRobot(), ((Condition) subProgAsSubProgs.get(0)), ((Command)subProgAsSubProgs.get(1)), ((Command )subProgAsSubProgs.get(2)));
				} 
				}
				return program;
			}
	}
	
	public java.util.ArrayList<String> getSubstringsBracketCutter(String fullProgram){
		// stores the index of the first '(' in the given String
		int openingBracketIndex = 0;
		// stores the index of the ')' that closes the first '('
		int closingBracketIndex = 0;
		
		// counts the number of opening brackets '(' the loop encounters until the loop encounters its closing bracket ')'
		int openingBracketCounter = 0;
		// counts the number of closing brackets ')' the loop encounters 
		// until the loop encounters the closing bracket ')' of the first opening bracket.
		int closingBracketCounter = 0;
	
		
		char[] fullPrStrings = fullProgram.toCharArray();
		java.util.ArrayList<String> subStringList= new ArrayList<String>();

		for(int index=0; index <= fullPrStrings.length-1; index++) {
			if(closingBracketCounter <= openingBracketCounter){

				if (fullPrStrings[index] == '('){
					if(openingBracketCounter == 0)
						openingBracketIndex = index;
					openingBracketCounter++;
				}
				if (fullPrStrings[index] == ')'){
					closingBracketCounter++;
					if (openingBracketCounter == closingBracketCounter){
						closingBracketIndex =index;
					}
				}
			}
			else{
			openingBracketCounter = 0;
			closingBracketCounter = 0;

			subStringList.add(fullProgram.substring(openingBracketIndex, closingBracketIndex));
			}
		}
		return subStringList;
	}

}
