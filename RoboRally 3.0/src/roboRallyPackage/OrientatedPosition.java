package roboRallyPackage;

import be.kuleuven.cs.som.annotate.*;

/**
 * Class representing all possible combination of Positions and Orientations
 * 
 * @invar	An OrientatedPostion must have a position.
 * 			| this.getPostition() != null
 * @invar	An OrientatedPostion must have a valid position.
 * 			| Position.isValidPosition(this.getPostition())
 * @invar	An OrientatedPosition must have an orientation.
 * 			| this.getPosition() != null
 * 
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 *
 */
@Value
public class OrientatedPosition
{
	public OrientatedPosition(Orientation orientation, Position position)
	{
		this.setOrientation(orientation);
		this.setPosition(position);
	}
	
	@Basic @Immutable
	public Position getPosition()
	{
		return position;
	}

	private void setPosition(Position position)
	{
		this.position = position;
	}

	private Position position;
	
	@Basic @Immutable
	public Orientation getOrientation()
	{
		return orientation;
	}

	private void setOrientation(Orientation orientation)
	{
		this.orientation = orientation;
	}

	private Orientation orientation;
	
	@Override
	public int hashCode()
	{
		return 0;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (other == null)
		{
			return false;
		}
		if (this.getClass() != other.getClass())
		{
			return false;
		}
		return (this.getPosition().equals(((OrientatedPosition) other).getPosition())
				&& this.getOrientation().equals(((OrientatedPosition) other).getOrientation()));
	}
	
	@Override
	public String toString()
	{
		return "OrientatedPosition with orientation: " + this.getOrientation().toString()
				+ " and " + this.getPosition().toString(); 
	}
	
	public java.util.List<OrientatedPosition> getOtherOrientatedPositionOnPosition()
	{
		java.util.List<OrientatedPosition> otherOrientatedPositions = new java.util.ArrayList<OrientatedPosition>();
		for(Orientation orientation: Orientation.values())
		{
			if(this.getOrientation() != orientation)
			{
				otherOrientatedPositions.add(new OrientatedPosition(orientation, this.getPosition()));
			}
			
		}
		return otherOrientatedPositions;
	}
}
