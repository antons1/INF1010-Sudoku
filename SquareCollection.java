/**
A collection of squares in a SuDoku boards (not the board) which is used to check
whether a value is available in a square
@author H&aring;kon Antonsen
@version 1.0
*/

public abstract class SquareCollection
{
	private Square[] squares;

	/**
	Creates an empty SquareCollection
	*/
	public SquareCollection(int s)
	{
		squares = new Square[s];
	} // End constructor

	/**
	Checks whether a value can be put in this collection
	@param v The value to check availability for
	@return <code>true</code> if the value is available, <code>false</code> if not.
	*/
	public boolean availableValue(int v)
	{
		for(int i = 0; i < squares.length; i++){
			if(v == squares[i].getValue())
				return false;
			// ENDIF

		} // ENDFOR

		return true;

	} // End availableValue

	/**
	Puts s into the collection
	@param s The Square to insert
	*/
	public void insertSquare(Square s)
	{
		for(int i = 0; i < squares.length; i++)
		{
			if(squares[i] == null)
			{
				squares[i] = s;
				return;
			} // ENDIF

		} // ENDFOR

	} // End insertSquare

	/**
	Prints the squares in this collection to console with the prefix <code>pre</code>
	@param pre The prefix before the squares
	*/
	public void print(String pre)
	{
		System.out.println(pre + ": ");
		for(int i = 0; i < squares.length; i++)
			System.out.println(squares[i]);
		// ENDFOR

		System.out.println();
	} // End print

	/**
	Removes the square s from the collection
	@param s The square to remove
	*/
	public void removeSquare(Square s)
	{
		for(int i = 0; i < squares.length; i++)
		{
			if(squares[i] == s)
				squares[i] = null;
			// ENDIF
		} // ENDFOR
	} // End removeSquare

} // End SquareCollection