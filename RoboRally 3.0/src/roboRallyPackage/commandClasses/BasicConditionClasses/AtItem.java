
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class AtItem extends BasicCondition
{
	public AtItem(int programLevel,Robot robot)
	{
		super(programLevel);
		this.robot = robot;
		//super(robot);
	}
	
	public Robot robot;
	
	public Robot getRobot(){
		return this.robot;
	}
	
	public boolean results()
	{
		return this.results(this.getRobot());
	}
	
	
	@Override
	public boolean results(Element element) throws IllegalArgumentException{
	
		if(!(element instanceof Robot)){
		throw new IllegalArgumentException();
	}
	for(Element el: element.getBoard().getElements(this.getRobot().getPosition()))
	{
		if(el instanceof Item)
		{
			return true;
		}
	}
	return false;
	}

	@Override
	public String toString()
	{
		return "(at-item)";
	}
	
	@Override
	public String getNotationExample()
	{
		return "(at-item)";
	}
}
