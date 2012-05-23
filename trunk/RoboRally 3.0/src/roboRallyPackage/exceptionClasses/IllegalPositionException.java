package roboRallyPackage.exceptionClasses;
import roboRallyPackage.Board;
import be.kuleuven.cs.som.annotate.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class IllegalPositionException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	// note: do not ask a position as formal argument of the constructor
	//       for, this position may be an illegal position that cannot be made without invoking this exception
	//		 this will lead to an endless loop of illegal positions and IllegalPositionExceptions
	public IllegalPositionException(long coordX, long coordY, Board board)
	{
		this.coordX = coordX;
		this.coordY = coordY;
		
		this.board = board;
	}
	
	public IllegalPositionException(String toStringText)
	{
		this.toStringText = toStringText;
	}
	
	private String toStringText;
	
	@Basic
	public long getCoordX()
	{
		return coordX;
	}
	
	private long coordX = 0;
	
	@Basic
	public long getCoordY()
	{
		return coordY;
	}
	
	private long coordY = 0;

	@Basic
	public Board getBoard()
	{
		return this.board;
	}

	private Board board = null;
	
	@Override
	public String toString()
	{
		if(this.toStringText != null)
		{
			return this.toStringText;
		}
		if(this.getBoard() == null)
		{
			return "The position with coordinates (" + this.getCoordX() + "," + this.getCoordY() + ")" + " is not a valid position.";
		}
		return "The position with coordinates (" + this.getCoordX() + "," + this.getCoordY() + ")" + " is not a valid position on the given board.";
	}
}
