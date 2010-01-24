/*
 * Created on 20 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving;

public class Puzzle<T>
{
	private T[][] mGrid;

	@SuppressWarnings("unchecked")
	public Puzzle(int pWidth, int pHeight)
	{
		super();
		mGrid = (T[][])new Object[pWidth][pHeight];
		for (int i = 0; i < mGrid.length; i++)
			for (int j = 0; j < mGrid[i].length; j++)
				mGrid[i][j] = null;
	}

	public Puzzle(final Puzzle<? extends T> pPuzzle)
	{
		super();
		for (int i = 0; i < pPuzzle.mGrid.length; i++)
			for (int j = 0; j < pPuzzle.mGrid[i].length; j++)
				mGrid[i][j] = pPuzzle.mGrid[i][j];
	}

	public T get(int x, int y)
	{
		return mGrid[x][y];
	}

	public void set(int x, int y, T pElement)
	{
		mGrid[x][y] = pElement;
	}

	public int getWidth()
	{
		return mGrid.length;
	}
	
	public int getHeight()
	{
		return mGrid[0].length;
	}
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mGrid[0].length; i++)
		{
			for (int j = 0; j < mGrid.length; j++)
			{
				sb.append(mGrid[j][i] == null ? ' ' : mGrid[j][i].toString().charAt(0));
			}
			sb.append('\n');
		}
		return sb.toString();
	}
}
