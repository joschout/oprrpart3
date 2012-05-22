
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class isItem extends BasicCondition
{
	public isItem(int programLevel, Robot robot)
	{
		super(programLevel);
		this.robot = robot;
	}
	
	private Robot robot;
	
	public Robot getRobot()
	{
		return this.robot;
	}
	
	@Override
	public String toString()
	{
		return "(item)";
	}

	@Override
	public boolean results()
	{
		return false;
	}

	@Override
	public boolean results(Element element) throws IllegalArgumentException
	{
		return (element instanceof Item);
	}
	
	@Override
	public String getNotationExample()
	{
		return "(item)";
	}
}

