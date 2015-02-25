/**
Represents one of the squares in a SuDoku board.
@author Håkon Antonsen
@version 1.0
*/

public abstract class Square
{
	protected int val;						// Square value
	protected Board board;					// The board this square is placed in
	protected Row row;						// Row the square is placed in
	protected Coloumn col;					// Coloumn the square is placed in
	protected Box box;						// Box the square is placed in
	protected Square next;					// The next square

	/**
	Creates a square
	@param r The row the square belongs to
	@param c The coloumn the square belongs to
	@param b The box the square belongs to
	@param n The next square
	*/
	public Square(Board br, Row r, Coloumn c, Box b, Square n)
	{
		val = -1;
		board = br;
		row = r;
		col = c;
		box = b;
		next = n;

		row.insertSquare(this);
		col.insertSquare(this);
		box.insertSquare(this);

	} // End constructor

	/**
	Returns the value of the square
	@return The value
	*/
	public int getValue()
	{
		return val;

	} // End getValue

	/**
	Sets the value of this square to v, if this is not a prefilled square
	@param v The value to insert
	*/
	public void setValue(int v)
	{
		// Check that this object is an EmptySquare, and if so, change the value
		if(this instanceof EmptySquare)
			val = v;
		else if(val == -1)
			val = v;
		// ENDIF

	} // End setValue

	/**
	Tries to fill this square, and if that is successful, tells the next square to try to fill itself.
	*/
	public void fillRemainderOfBoard()
	{
		// Checks whether it is a prefilled square
		if(this instanceof PrefilledSquare)
		{
			// If this is the last square save the board
			if(next == null)
			{
				// Save board
				board.save();
				return;

			} // ENDIF

			// If not try to fill the next square
			next.fillRemainderOfBoard();
			return;

		} // ENDIF

		// Try each allowed number in the square
		for(int i = 1; i <= board.getSize(); i++)
		{
			// If a number can be put
			if(fillSquare(i))
			{
				// save board if this the last square
				if(next == null)
				{
					// Save board
					board.save();
					continue;

				} // ENDIF

				// If not try to fill next square
				next.fillRemainderOfBoard();

			} // ENDIF

		} // ENDFOR

		// If no numbers can be put, set value back to -1 (empty)
		setValue(-1);

	} // End fillRemainderOfBoard

	/**
	Tries to fill the square with value v
	If v exists in box, coloumn or row, it wont be put in
	*/
	private boolean fillSquare(int v)
	{
		if(box.availableValue(v) && col.availableValue(v) && row.availableValue(v))
		{
			setValue(v);
			return true;

		} // ENDIF

		return false;

	} // End fillSquare

	/**
	Returns the row of the square
	@return the row the square is in
	*/
	public Row getRow()
	{
		return row;
	} // End getRow

	/**
	Returns the coloumn of the square
	@return The row the square is in
	*/
	public Coloumn getCol()
	{
		return col;
	} // End getCol

	/**
	Returns the box of the square
	@return The box the square is in
	*/
	public Box getBox()
	{
		return box;
	} // End getBox

	/**
	Returns the next square in the board
	@return the next square in the board
	*/
	public Square getNext()
	{
		return next;
	} // End getNext

	/**
	Removes the square from the row, coloumn and box it is in
	*/
	public void removeFromCollections()
	{
		col.removeSquare(this);
		row.removeSquare(this);
		box.removeSquare(this);
	}

	/**
	Sets the next square in the board to be <code>s</code>
	@param s The new next pointer
	*/
	public void setNext(Square s)
	{
		next = s;
	} // End setNext

} // End Square