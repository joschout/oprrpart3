package roboRallyPackage;
import be.kuleuven.cs.som.annotate.*;

/**
 * An enumeration introducing different orientations.
 * 
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 */
@Value
public enum Orientation
{
	UP
	{
		/**
		 * Returns the coordinates relative to the current position when facing upwards.
		 * 
		 * @return	...
		 *			| result == {0,-1}
		 */
		@Override
		public int[] getRelativeMoveCoordinates()
		{
			int[] result = {0,-1};
			return result;
		}
	},

	RIGHT
	{
		/**
		 * Returns the coordinates relative to the current position when facing to the right.
		 * 
		 * @return	...
		 *			| result == {1,0}
		 */
		@Override
		public int[] getRelativeMoveCoordinates()
		{
			int[] result = {1,0};
			return result;
		}
	},

	DOWN
	{
		/**
		 * Returns the coordinates relative to the current position when facing downwards.
		 * 
		 * @return	...
		 *			| result == {0,1}
		 */
		@Override
		public int[] getRelativeMoveCoordinates()
		{
			int[] result = {0,1};
			return result;
		}	
	},

	LEFT
	{
		/**
		 * Returns the coordinates relative to the current position when facing to the left.
		 * 
		 * @return	...
		 *			| result == {-1,0}
		 */
		@Override
		public int[] getRelativeMoveCoordinates()
		{
			int[] result = {-1,0};
			return result;
		}
	};

	/**
	 * Turns the current orientation with 90 degrees in a clockwise direction.
	 * @return	...
	 * 			| result == Orientation.values()[(this.ordinal() - 1) mod(Orientation.values().length)];
	 */
	public Orientation turn90CounterClockwise()
	{
		return Orientation.values()[(this.ordinal() + (Orientation.values().length - 1)) % Orientation.values().length];
	}

	/**
	 * Turns the current orientation with 90 degrees in a counterclockwise direction.
	 * @return	...
	 * 			| result == Orientation.values()[(this.ordinal() + 1) % Orientation.values().length];
	 */
	public Orientation turn90Clockwise()
	{
		return Orientation.values()[(this.ordinal()+1)%Orientation.values().length];
	}

	/**
	 * Turns the current orientation with 90 degrees.
	 * @param	direction
	 * 			The direction in which the current orientation has to change: clockwise or counter-clockwise. 
	 * @return ...
	 * 		| if(direction == Direction.CLOCKWISE)
	 *		|   then result == this.turn90Clockwise();
	 *		| if(direction == Direction.COUNTER_CLOCKWISE)
	 *		|   then result == this.turn90CounterClockwise();
	 */
	public Orientation turn90(Direction direction)
	{
		Orientation newOrientation = null;
		if(direction == Direction.CLOCKWISE)
		{
			newOrientation = this.turn90Clockwise();
		}
		if(direction == Direction.COUNTER_CLOCKWISE)
		{
			newOrientation = this.turn90CounterClockwise();
		}

		return newOrientation;
	}

	/**
	 * Returns the coordinates relative to the current position when given a certain orientation.
	 * 
	 * @return	...
	 * 			| if(this == Orientation.UP) then result == {0,-1}
	 * 			| if(this == Orientation.RIGHT) then result == {1,0}
	 * 			| if(this == Orientation.DOWN) then result == {0,1}
	 * 			| if(this == Orientation.LEFT) then result == {-1,0}
	 */
	public abstract int[] getRelativeMoveCoordinates();
	
	/**
	 * Returns the orientation that is represented by the given integer.
	 * 
	 * @param	integer
	 * 			The integer that represents a certain orientation
	 * @return	...
	 * 			| for one i in 0..Orientation.values().size(): orientation.ordinal() == (integer % Orientation.values().length)
	 * 			| result == orientation
	 */
	public static final Orientation convertIntToOrientation(int integer)
	{
		for(Orientation orientation: Orientation.values())
		{
			// the ordinal of the checked orientation matches with the given integer
			// this is the orientation we are looking for.
			if (orientation.ordinal() == (integer % Orientation.values().length))
			{
				return orientation;
			}
		}
		return null;
	}
}
