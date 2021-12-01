package com.codenjoy.dojo.games.clifford;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class YourSolverTest {

    private Dice dice;
    private Solver ai;
    // private Board board;

    @Before
    public void setup() {
        dice = mock(Dice.class);
        ai = new YourSolver(dice);
        /*board = (Board) new Board().forString("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼   W                $       ☼\n" +
                "☼##H########################H☼\n" +
                "☼  H    &      & $          H☼\n" +
                "☼H☼☼#☼☼H    H#########H»  $ H☼\n" +
                "☼H    &H    H   &     H#####H☼\n" +
                "☼H#☼#☼#H WW$H   &   m H  ~~~$☼\n" +
                "☼H  ~  H~~~~H~~~~~~   H      ☼\n" +
                "☼H     H    H$@   H###☼☼☼☼☼☼H☼\n" +
                "☼H   )&H    H#####HU m   @  H☼\n" +
                "☼☼###☼##☼##☼H         H###H##☼\n" +
                "☼☼###☼~~~~  Hx&      &H & H##☼\n" +
                "☼☼   ☼      H   ~~~~~~H   H  ☼\n" +
                "☼########H###☼☼☼☼&&   H  ####☼\n" +
                "☼       &H      &    &H      ☼\n" +
                "☼H##########################H☼\n" +
                "☼H    $          W@~~~      H☼\n" +
                "☼#######H#######       x    H☼\n" +
                "☼       H~~~~~~~~~~        &H☼\n" +
                "☼       H  FF##H & #######H##☼\n" +
                "☼   $   H    ##H        $ H  ☼\n" +
                "☼##H#####    ########H#######☼\n" +
                "☼  H          @&     H  &  & ☼\n" +
                "☼#########H##########H       ☼\n" +
                "☼         H         $H     F ☼\n" +
                "☼☼☼@      H~~~~~~~~~~H       ☼\n" +
                "☼    H###### W &     #######H☼\n" +
                "☼H☼  H      m               H☼\n" +
                "☼##########☼☼☼######☼☼######H☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼");*/
    }

    private Board board(String board) {
        return (Board) new Board().forString(board);
    }

    @Test
    public void should() {

        // TODO these asserts are here for an example, delete it and write your own

       /* asertAI("☼☼☼☼☼☼☼" +
                "☼ $  H☼" +
                "☼ ###H☼" +
                "☼    H☼" +
                "☼►   H☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼", Direction.RIGHT);

        asertAI("☼☼☼☼☼☼☼" +
                "☼ $  H☼" +
                "☼ ###H☼" +
                "☼    H☼" +
                "☼ ►  H☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼", Direction.RIGHT);

        asertAI("☼☼☼☼☼☼☼" +
                "☼ $  H☼" +
                "☼ ###H☼" +
                "☼    H☼" +
                "☼  ► H☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼", Direction.RIGHT);

        asertAI("☼☼☼☼☼☼☼" +
                "☼ $  H☼" +
                "☼ ###H☼" +
                "☼    H☼" +
                "☼   ►H☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼", Direction.RIGHT);*/
    }

    @Test
    public void nearestClueTest() {
        assertNearestClue(
                "☼☼☼☼☼☼☼" +
                "☼ $  H☼" +
                "☼ ###H☼" +
                "☼    H☼" +
                "☼   ►H☼" +
                "☼#####☼" +
                "☼☼☼☼☼☼☼",2, 5);
        assertNearestClue(
                "☼☼☼☼☼☼☼" +
                        "☼ $  H☼" +
                        "☼ ###H☼" +
                        "☼    H☼" +
                        "☼@  ►H☼" +
                        "☼#####☼" +
                        "☼☼☼☼☼☼☼",1, 2);
        assertNearestClue(
                  "@      " +
                        "       " +
                        "       " +
                        "    $  " +
                        "       " +
                        "   U   " +
                        " &     " +
                        " ☼     ",0, 1 );
    }

    @Test
    public void walls( ) {
        assertIsWallBelowTest(
                "☼☼☼☼☼☼☼" +
                        "☼ $  H☼" +
                        "☼ ###H☼" +
                        "☼    H☼" +
                        "☼@  ►H☼" +
                        "☼#####☼" +
                        "☼☼☼☼☼☼☼", true, 1, 2);

        assertIsWallBelowTest(
                "☼☼☼☼☼☼☼" +
                        "☼ $  H☼" +
                        "☼ ###H☼" +
                        "☼@   H☼" +
                        "☼   ►H☼" +
                        "☼#####☼" +
                        "☼☼☼☼☼☼☼", false, 1, 3);

        assertYouCanMoveThere(
                "☼☼☼☼☼☼☼" +
                        "☼ $  H☼" +
                        "☼ ###H☼" +
                        "☼@    ☼" +
                        "☼  ► ☼ " +
                        "☼#####☼" +
                        "☼☼☼☼☼☼☼", true, 1,3 );
        assertYouCanMoveThere(
                "☼☼☼☼☼☼☼" +
                        "☼ $  H☼" +
                        "☼ ###H☼" +
                        "☼@    ☼" +
                        "☼  ► ☼ " +
                        "☼#####☼" +
                        "☼☼☼☼☼☼☼", true, 1,2 );
        assertYouCanMoveThere(
                "☼☼☼☼☼☼☼" +
                        "☼ $  H☼" +
                        "☼ ###H☼" +
                        "☼@    ☼" +
                        "☼  ► ☼ " +
                        "☼#####☼" +
                        "☼☼☼☼☼☼☼", true, 1,4 );
        assertYouCanMoveThere(
                "☼☼☼☼☼☼☼" +
                        "☼ $  H☼" +
                        "☼ ###H☼" +
                        "☼@    ☼" +
                        "☼  ► ☼ " +
                        "☼#####☼" +
                        "☼☼☼☼☼☼☼", false, 5,2 );
        assertYouCanMakeAHole(
                "☼☼☼☼☼☼☼" +
                        "☼ $  H☼" +
                        "☼ ###H☼" +
                        "☼@    ☼" +
                        "☼  ► ☼ " +
                        "☼#####☼" +
                        "☼☼☼☼☼☼☼", false, 5,2 );
        assertYouCanMakeAHole(
                "☼☼☼☼☼☼☼" +
                        "☼ $  H☼" +
                        "☼ ###H☼" +
                        "☼@    ☼" +
                        "☼  ► ☼ " +
                        "☼#####☼" +
                        "☼☼☼☼☼☼☼", true, 1,1 );
    }




    private void asertAI(String board, Direction expected) {
        String actual = ai.get(board(board));
        System.out.println(board);
        System.out.println(actual);
        assertEquals(expected.toString(), actual);
    }

    private void assertNearestClue(String board, int x, int y) {
        YourSolver yourSolver = new YourSolver(dice);
        yourSolver.setBoard(board(board));
        Point pt = yourSolver.goToNearestEvidence();
        System.out.println(board(board).isClue(pt));
        System.out.println(pt.toString());
        assertEquals(x, pt.getX());
        assertEquals(y, pt.getY());
        System.out.println(Arrays.toString(board(board).getField()));
        for (char[] row:board(board).getField()) {
            System.out.println(Arrays.toString(row));
        }
    }

    private void assertIsWallBelowTest(String board, boolean expected, int x, int y) {
        YourSolver yourSolver = new YourSolver(dice);
        yourSolver.setBoard(board(board));
        Point pt = yourSolver.goToNearestEvidence();
        Boolean methodResult = yourSolver.isWallBellowClue(pt);
        assertEquals(expected, methodResult);
    }

    private void assertIsWallToTheRight(String board, Object expected, int x, int y) {
        YourSolver yourSolver = new YourSolver(dice);
        yourSolver.setBoard(board(board));
        Point pt = yourSolver.goToNearestEvidence();
        assertEquals(expected,yourSolver.whereToGo(pt));
    }

    private void assertYouCanMoveThere(String board, boolean expected, int x, int y) {
        YourSolver yourSolver = new YourSolver(dice);
        yourSolver.setBoard(board(board));
        assertEquals(expected, yourSolver.canYouMoveThere(new PointImpl(x,y)));
    }

    private void assertYouCanMakeAHole(String board, boolean expected, int x, int y) {
        YourSolver yourSolver = new YourSolver(dice);
        yourSolver.setBoard(board(board));
        assertEquals(expected, yourSolver.canYouMakeAHole(new PointImpl(x,y)));
    }

    private void assertIsItFieldEdge()

    private void dice(Direction direction) {
        when(dice.next(anyInt())).thenReturn(direction.value());
    }
}
