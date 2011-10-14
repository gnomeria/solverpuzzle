/*
 * Created on 27 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import jyt.game.puzzle.solving.Puzzle;

public interface IPuzzleAnalyzer
{
	double computeFitness(Puzzle<Element> pPuzzle);
	String description();
}
