/**
A SuDoku board
@author H&aring;kon Antonsen
@version 1.0
*/

import java.io.*;

public class Board
{
	private Box[][] boxes;
	private Row[] rows;
	private Coloumn[] cols;
	private Square[][] squares;
	private int size;
	private int width;
	private int height;
	private SudokuCollection solutions;

	/**
	Creates an empty board
	*/
	public Board()
	{
		boxes = null;
		rows = null;
		cols = null;
		squares = null;
		size = 0;
		width = 0;
		height = 0;
		solutions = null;

	} // End constructor

	/**
	Fills a board from the file f with prefilled squares
	@param f The file to read the board from
	@throws FileNotFoundException Throws a <code>FileNotFoundExcpetion</code> if the file supplied is not found
	@throws IOException Throws a <code>IOException</code> if there is an error reading the file
	@throws WrongSizeException Throws a <code>WrongSizeException</code> if the size/width/height specification in the file is wrong
	*/
	public void fillBoard(File f, SudokuCollection sc) throws FileNotFoundException, IOException, WrongSizeException
	{
		solutions = sc;
		FileReader fr = new FileReader(f);
		BufferedReader r = new BufferedReader(fr);

		// Read size
		String line = r.readLine();
		int s = Integer.parseInt(line);

		// Read height
		line = r.readLine();
		int h = Integer.parseInt(line);

		// Read width
		line = r.readLine();
		int w = Integer.parseInt(line);

		// Create the empty board
		createEmptyBoard(s, w, h);

		// Fill board with preset values

		int i = 0;
		int j = 0;

		line = "";

		while((line = r.readLine()) != null)
		{
			for(int k = 0; k < line.length(); k++)
			{
				char c = line.charAt(k);

				if(c != '.' && c != '\n')
				{
					Row row = squares[i][j].getRow();
					Coloumn col = squares[i][j].getCol();
					Box box = squares[i][j].getBox();
					Square next = squares[i][j].getNext();
					String tmp = "" + c;
					Square prev;

					if(i == 0 && j == 0)
						prev = null;
					else if(j == 0)
						prev = squares[i-1][s-1];
					else
						prev = squares[i][j-1];
					// ENDIF
					
					squares[i][j].removeFromCollections();
					squares[i][j] = new PrefilledSquare(this, row, col, box, next, Integer.parseInt(tmp, 16));
					if(prev != null)
						prev.setNext(squares[i][j]);
					// ENDIF
					
				}

				j++;

			}

			i++;

			if(i == s)
				i = 0;
			// ENDIF

			if(j == s)
				j = 0;
			// ENDIF
	
		} // ENDWHILE

	} // End fillBoard

	/**
	Creates a completely empty SuDoku board width the given size, width and height
	@param s The size of the board (board size is SxS)
	@param w The width of the board (the width of the boxes, also number of boxes vertically)
	@param h The height of the board (the height of the boxes, also the number of boxes horizontally)
	@throws WrongSizeException Throws a <code>WrongSizeException</code> if there is an error in the supplied size.
	*/
	public Board(int s, int w, int h, SudokuCollection sc) throws WrongSizeException
	{
		createEmptyBoard(s, w, h);
		solutions = sc;
	}

	/*
	Create an empty board
	*/
	private void createEmptyBoard(int s, int w, int h) throws WrongSizeException
	{
		// Check that the given size numbers are valid
		if((w*h) != s)
			throw new WrongSizeException();
		// ENDIF

		// Save size
		size = s;
		width = w;
		height = h;

		// Create arrays & fill arrays
		boxes = new Box[w][h];
		rows = new Row[size];
		cols = new Coloumn[size];
		squares = new Square[size][size];

		for(int i = 0; i < size; i++)
		{
			rows[i] = new Row(size);
			cols[i] = new Coloumn(size);

		} // ENDFOR

		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				boxes[i][j] = new Box(size);

			} // ENDFOR

		} // ENDFOR

		for(int i = size-1; i >= 0; i--)
		{
			for(int j = size-1; j >= 0; j--)
			{
				Box tmpBox = boxes[i/height][j/width];
				Coloumn tmpCol = cols[j];
				Row tmpRow = rows[i];
				Square tmpNext;

				if(j == size-1 && i == size-1)
					tmpNext = null;
				else if(j == size-1)
					tmpNext = squares[i+1][0];
				else
					tmpNext = squares[i][j+1];
				// ENDIF

				squares[i][j] = new EmptySquare(this, tmpRow, tmpCol, tmpBox, tmpNext);

			}
		}

	} // End createEmptyBoard

	/**
	Prints the board to console
	*/
	public void print()
	{
		System.out.print("  ");
		for(int i = 0; i < size+width; i++)
			System.out.print("--");
		// ENDFOR

		System.out.println();

		for(int i = 0; i < size; i++)
		{
			System.out.print("| ");

			for(int j = 0; j < size; j++)
			{
				System.out.print(Integer.toString(squares[i][j].getValue(), 16).toUpperCase() + " ");
				if(j%width == width-1)
					System.out.print("| ");
				// ENDIF

			} // ENDFOR

			System.out.println();
			if(i%height == height-1)
			{
				System.out.print("  ");
				for(int j = 0; j < size+width; j++)
					System.out.print("--");
				// ENFOR

				System.out.println();

			} // ENDIF

		} // ENDFOR

		System.out.println();

	} // End print

	/**
	Returns the size of the board
	@return Board size
	*/
	public int getSize()
	{
		return size;

	} // End getSize

	/**
	Returns the width of the board
	@return Board width
	*/
	public int getWidth()
	{
		return width;

	} // End getWidth

	/**
	Returns the height of the board
	@return Board height
	*/
	public int getHeight()
	{
		return height;

	} // End getHeight

	/**
	Solves the board by calling <code>fillRemainderOfBoard()</code> on the first square
	@see Square::fillRemainderOfBoard();
	*/
	public void solve()
	{
		squares[0][0].fillRemainderOfBoard();

	} // End solve

	/**
	Stores the board to the <code>SudokuCollection</code> given in <code>fillBoard()</code>
	*/
	public void save()
	{
		solutions.insert(squares);

	} // End save

	/**
	Returns the board in its current state as an array of <code>int</code>s.
	@return An <code>int[][]</code> array.
	*/
	public int[][] getBoard()
	{
		int[][] res = new int[size][size];

		for(int i = 0; i < squares.length; i++)
		{
			for(int j = 0; j < squares[i].length; j++)
			{
				res[i][j] = squares[i][j].getValue();
			}
		}

		return res;

	} // End getBoard
}