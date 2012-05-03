package roboRallyPackage.exceptionClasses;
import roboRallyPackage.Board;
import roboRallyPackage.Position;
import be.kuleuven.cs.som.annotate.*;

public class IllegalPositionException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalPositionException(Position position, Board board)
	{
		if(position != null)
		{
			this.coordX = position.getCoordX();
			this.coordY = position.getCoordY();
		}
		else
		{
			this.coordX = -1;
			this.coordY = -1;
		}
		
		if(board != null)
		{
			this.boardWidth = board.getWidth();
			this.boardHeight = board.getHeight();
		}
		else
		{
			this.boardWidth = Board.getMaxWidth();
			this.boardHeight = Board.getMaxHeight();
		}
	}
	
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
	public long getBoardWidth()
	{
		return this.boardWidth;
	}
	
	private long boardWidth = 0;

	@Basic
	public long getBoardHeight()
	{
		return this.boardHeight;
	}

	private long boardHeight = 0;
	
	@Override
	public String toString()
	{
		return "The position with coordinates (" + this.getCoordX() + "," + this.getCoordY() + ")" + " is not a valid position.";
	}
}
