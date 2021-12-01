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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Author: your name
 * <p>
 * This is your AI algorithm for the game.
 * Implement it at your own discretion.
 * Pay attention to {@link YourSolverTest} - there is
 * a test framework for you.
 */
public class YourSolver implements Solver<Board> {

    private Dice dice;
    private Board board;
    private List<Point> noWay;

    public YourSolver(Dice dice) {
        this.dice = dice;
        noWay = new ArrayList<>();
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public String get(Board board) {
        this.board = board;
        if (board.isGameOver()) return "";

        // TODO your code here
        System.out.println(board);
        //return whereToGo(board.getHero(),goToNearestEvidence());

        return whereToGo(goToNearestEvidence());

        //return Direction.RIGHT.toString();
    }


    //find all walls
    public boolean canYouMoveThere(Point destination) {
        noWay = new ArrayList<>(board.getWalls());
        for (Point potentialWall : noWay) {
            if (potentialWall.equals(destination)) {
                return false;
            }
        }
        return true;
    }

    //for pipes maybe
    public void isThatAPipe() {

    }

    public boolean canYouMakeAHole(Point destination) {
        if (!canYouMoveThere(destination)) {
            return Element.BRICK.equals(board.getAt(destination));
        }
        return false;
    }


    // TODO: 01.12.2021 добавить поиск по конкретным уликам (чтобы можно было подобрать серии - вроде так больше очков) 
    //logic for going to the nearest evidence
    public Point goToNearestEvidence() {
        List<Point> clues = new ArrayList<>(board.getClues());
        Collections.sort(clues);
        Point currentPosition = board.getHero();
        int distance = 43; //max distance. Math.hypot(N, N);
        Point nearestClue = new ArrayList<>(board.getClues()).get(0);
        for (int i = 0; i < clues.size(); i++) {
            //если под подсказкой нет пола, то пока не ищем
            if (!isWallBellowClue(clues.get(i))) {
                break;
            }
            int tempDistance = (int) Math.sqrt(Math.pow((currentPosition.getX() - clues.get(i).getX()), 2) + (Math.pow((currentPosition.getY() - clues.get(i).getY()), 2)));
            if (distance > tempDistance) {
                nearestClue = clues.get(i);
                distance = tempDistance;
            }
        }
        return nearestClue;

    }


    public String whereToGo(Point destination) {
        atack();
        if (board.getHero().getX() < destination.getX()) {
            if (!board.isWall(new PointImpl(board.getHero().getX() + 1, board.getHero().getY()))) {
                return Direction.RIGHT.toString();
            }
        } else if (board.getHero().getX() > destination.getX()) {
            if (!board.isWall(new PointImpl(board.getHero().getX() - 1, board.getHero().getY()))) {
                return Direction.LEFT.toString();
            }
        } else {
            if (!board.isWall(new PointImpl(board.getHero().getX() + 1, board.getHero().getY() - 1))) {
                return Direction.RIGHT.toString();
            } else if (!board.isWall(new PointImpl(board.getHero().getX() - 1, board.getHero().getY() - 1))) {
                return Direction.LEFT.toString();
            }
        }

        if (board.isWall(new PointImpl(board.getHero().getX() + 1, board.getHero().getY()))) {
            return Direction.LEFT.toString();
        } else if (board.isWall(new PointImpl(board.getHero().getX() - 1, board.getHero().getY()))) {
            return Direction.RIGHT.toString();
        }
        return Direction.ACT(1) + "," + Direction.DOWN.toString();
    }

    private String whereToGo() {
        String direction = null;
        int rand = (int) (10 * Math.random());
        switch (rand) {
            case 1:
                return Direction.RIGHT.toString();
            case 2:
                return Direction.LEFT.toString();
            case 3:
                return Direction.UP.toString();
            case 4:
                return Direction.DOWN.toString();
            default:
                return Direction.ACT.toString();
        }
    }

    public boolean isWallBellowClue(Point clue) {
        return (Element.BRICK.equals(board.getAt(clue.getX(), clue.getY() - 1)) || Element.STONE.equals(board.getAt(clue.getX(), clue.getY() - 1)));
    }

    public boolean isWallAboveClue(Point clue) {
        return (Element.BRICK.equals(board.getAt(clue.getX(), clue.getY() + 1)) || Element.STONE.equals(board.getAt(clue.getX(), clue.getY() + 1)));
    }

    public boolean isItFieldEdge(Point point) {
        return point.getX() == board.size() - 1 || (point.getY() == board.size() - 1) || point.getX() == 0 || point.getY() == 0;
    }

    //only horizontal yet
    public String atack() {
        for (int i = -2; i < 3; i++) {
            if (i == 0) {
                i++;
            }
            if (board.getHero().getX() + i < 0 || board.getHero().getX() + i > board.size()) {
                break;
            }
            if (board.isRobberAt(new PointImpl(board.getHero().getX() + i, board.getHero().getY()))) {
                if (i > 0) {
                    return String.format("%s,%s", Direction.ACT, Direction.RIGHT);
                } else {
                    return String.format("%s,%s", Direction.ACT, Direction.LEFT);
                }
            }
        }
        return Direction.STOP.toString();
    }


}