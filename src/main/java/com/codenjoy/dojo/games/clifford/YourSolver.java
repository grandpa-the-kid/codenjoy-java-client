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

import java.util.*;

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
    private List<Point> clues;
    public Map<Point, List<Point>> adjacencyMatrix;
    public List<Point> route;
    private Set<Point> visitedNodes;
    private Point nearestEvidence;

    public YourSolver(Dice dice) {
        this.dice = dice;
        noWay = new ArrayList<>();
        adjacencyMatrix = new HashMap<>();
        route = new ArrayList<>();
        visitedNodes = new HashSet<>();
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Point> getRoute() {
        return route;
    }

    @Override
    public String get(Board board) {
        this.board = board;
        if (board.isGameOver()) {
            return "";
        }

        route = new ArrayList<>();
        try {
            if (!board.isClue(nearestEvidence)) {
                nearestEvidence = goToNearestEvidence();
            }
        } catch (NullPointerException e) {
            nearestEvidence = goToNearestEvidence();
        }
        visitedNodes.clear();

        //Надо ли?
        //route.clear();
        // TODO your code here
        //System.out.println(board);
        //return whereToGo(board.getHero(),goToNearestEvidence());

        return mainLogic();

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
        System.out.println("looking for nearest evidence");
        clues = new ArrayList<>(board.getClues());
        clues.addAll(board.getPotions());
        Collections.sort(clues);
        Point currentPosition = board.getHero();
        for (int x = -28; x < board.size(); x++) {
            try {
                if (board.isClue(new PointImpl(currentPosition.getX() + x, currentPosition.getY()))) {
                    return new PointImpl(currentPosition.getX() + x, currentPosition.getY());
                }
            } catch (IndexOutOfBoundsException e) {
                continue;
            }
        }


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


    public boolean isWallBellowClue(Point clue) {
        return (Element.BRICK.equals(board.getAt(clue.getX(), clue.getY() - 1)) || Element.STONE.equals(board.getAt(clue.getX(), clue.getY() - 1)));
    }

    public boolean isWallAboveClue(Point clue) {
        return (Element.BRICK.equals(board.getAt(clue.getX(), clue.getY() + 1)) || Element.STONE.equals(board.getAt(clue.getX(), clue.getY() + 1)));
    }

    public boolean isItFieldEdge(Point point) {
        return point.getX() == board.size() - 1 || (point.getY() == board.size() - 1) || point.getX() == 0 || point.getY() == 0;
    }

    public boolean isItPipeNear(Point point, Direction direction) {
        return point.getX() == board.size() - 1 || (point.getY() == board.size() - 1) || point.getX() == 0 || point.getY() == 0;
    }

    //only horizontal yet
    public String atack() {
        Point currentHeroPosition = board.getHero();
        List<Point> otherHeroes = new ArrayList<>(board.getOtherHeroes());
        for (Point otherHero : otherHeroes) {
            if (currentHeroPosition.getY() == otherHero.getY()) {
                if (currentHeroPosition.getX() - otherHero.getY() > 0) {
                    return String.format("%s,%s", Direction.ACT(1), Direction.RIGHT);
                }
            }
        }

        try {
            for (int i = -2; i < 3; i++) {
                if (i == 0) {
                    i++;
                }
                if (board.getHero().getX() + i < 0 || board.getHero().getX() + i > board.size()) {
                    break;
                }
                if (board.isOtherHeroAt(new PointImpl(board.getHero().getX() + i, board.getHero().getY()))) {
                    if (i > 0) {
                        return String.format("%s,%s", Direction.ACT(1), Direction.RIGHT);
                    } else {
                        return String.format("%s,%s", Direction.ACT(1), Direction.LEFT);
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e);
        }
        return Direction.STOP.toString();
    }

    //наполняю граф (таблицу смежности)
    public void fillAdjacencyMatrix() {
        noWay = new ArrayList<>(board.getWalls());
        for (int y = 0; y < board.size(); y++) {
            for (int x = 0; x < board.size(); x++) {
                if (noWay.contains(new PointImpl(x, y))) {
                    continue;
                }
                Element currentElement = board.getAt(x, y);
                nodeAdd(currentElement, x, y);
            }

        }
        System.out.println("filling adjacency Matrix");
    }

    //здесь добавляю узлы в которые можно попасть из переданного
    public void nodeAdd(Element currentElement, int x, int y) {
        List<Point> neighbourNodes = new ArrayList<>();
        switch (currentElement.toString()) {
            case " ": //empty place
            case "F": //other hero fall
            case "f": //other hero mask fall
            case "U": //hero fall
            case "u": //hero mask fall
            case "x": //robber fall
                noneCase(neighbourNodes, x, y);
                adjacencyMatrix.put(new PointImpl(x, y), neighbourNodes);
                break;

            case "W": //back way door

            case "~": //pipe

            case "C": //other hero Die
            case "«": //other hero Left
            case "»": //other hero right
            case "J": //other hero pipe
            case "K": //other hero pit

            case "c": //other hero mask die
            case "Z": //other hero mask left
            case "z": //other hero mask right
            case "j": //other hero mask pipe
            case "k": //other hero mask pit

            case "O": //hero die
            case "◄": //hero left
            case "►": //hero right
            case "I": //hero pipe
            case "E": //hero pit

            case "o": //hero mask die
            case "h": //hero mask left
            case "w": //hero mask right
            case "i": //hero mask pipe
            case "e": //hero mask pit

            case "$": //knife
            case "&": //glove
            case "@": //ring
            case "m": //mask potion

            case ")": //robber left
            case "(": //robber right
            case "Y": //robber pipe

                otherCase(neighbourNodes, x, y);
                adjacencyMatrix.put(new PointImpl(x, y), neighbourNodes);
                break;

            case "H": //ladder
            case "D": //other hero ladder
            case "A": //hero ladder
            case "X": //robber ladder
            case "a": //hero mask ladder
            case "d": //other hero mask ladder
            case "N": //enemy hero ladder
            case "n": //enemy hero mask ladder
                ladderCase(neighbourNodes, x, y);
                adjacencyMatrix.put(new PointImpl(x, y), neighbourNodes);
                break;

        }
    }


    //как будем поступать с остальным
    // TODO проблема в том, что добавляю в этих методах несуществующие точки
    private void otherCase(List<Point> neighbourNodes, int x, int y) {
        //below
        Point point = new PointImpl(x, y - 1);
        if (!noWay.contains(point) && board.size()) { //проверка на то, что это не стена
            neighbourNodes.add(point);
        }
        //above can't be added

        //right
        point = new PointImpl(x + 1, y);
        if (!noWay.contains(point)) {
            neighbourNodes.add(point);
        }
        //left
        point = new PointImpl(x - 1, y);
        if (!noWay.contains(point)) {
            neighbourNodes.add(point);
        }

    }

    //как будем поступать с пустотой
    private void noneCase(List<Point> neighbourNodes, int x, int y) {
        //below
        Point point = new PointImpl(x, y - 1);
        if (!noWay.contains(point)) { //проверка на то, что это не стена
            neighbourNodes.add(point);
        }
        //above can't be added
        //тут если под пустотой пустота, то только вниз можно (и если труба, то тоже, только вниз)
        if (board.getAt(point).equals(Element.NONE) || board.getAt(point).equals(Element.HERO_FALL) ||
                board.getAt(point).equals(Element.OTHER_HERO_FALL) || board.getAt(point).equals(Element.HERO_MASK_FALL) ||
                board.getAt(point).equals(Element.ROBBER_FALL) || board.getAt(point).equals(Element.HERO_PIPE) ||
                board.getAt(point).equals(Element.HERO_MASK_PIPE) || board.getAt(point).equals(Element.OTHER_HERO_PIPE) ||
                board.getAt(point).equals(Element.OTHER_HERO_MASK_PIPE) || board.getAt(point).equals(Element.ENEMY_HERO_PIPE) ||
                board.getAt(point).equals(Element.ENEMY_HERO_MASK_PIPE) || board.getAt(point).equals(Element.ROBBER_PIPE) ||
                board.getAt(point).equals(Element.PIPE)) {
            return;
        } else {
            //right
            point = new PointImpl(x + 1, y);
            if (!noWay.contains(point)) {
                neighbourNodes.add(point);
            }
            //left
            point = new PointImpl(x - 1, y);
            if (!noWay.contains(point)) {
                neighbourNodes.add(point);
            }
        }

    }

    //с лестницами
    private void ladderCase(List<Point> neighbourNodes, int x, int y) {
        //below
        Point point = new PointImpl(x, y - 1);
        if (!noWay.contains(point)) { //проверка на то, что это не стена
            neighbourNodes.add(point);
        }
        //above
        point = new PointImpl(x, y + 1);
        if (!noWay.contains(point)) { //проверка на то, что это не стена, вверх можно куда угодно
            neighbourNodes.add(point);
        }
        //right
        point = new PointImpl(x + 1, y);
        if (!noWay.contains(point)) {
            neighbourNodes.add(point);
        }
        //left
        point = new PointImpl(x - 1, y);
        if (!noWay.contains(point)) {
            neighbourNodes.add(point);
        }
    }

    //создаем маршрут, после того, как граф наполнен
    public void makeRoute(Point position, Point destination) {
        if (destination.equals(position)) {
            route.add(destination);
            return;
        }

        List<Point> nodeLinkedNodes = adjacencyMatrix.get(position);
        System.out.println("making route");
        //Collections.sort(nodeLinkedNodes);
        for (Point point : nodeLinkedNodes) {
            if (!visitedNodes.contains(point) && !board.getHero().equals(point)) {
                visitedNodes.add(point);
                makeRoute(point, destination);
                if (route.contains(destination)) {
                    if (route.contains(position)) {
                        route.remove(position);
                        return;
                    }
                    if (!destination.equals(point)) {
                        route.add(0, point);
                    }

                    break;
                }
            } else {
                route.clear();
            }
        }
        //route.add(position);
    }

    //идём по маршруту
    public List<Direction> goByRouteTest() {
        List<Direction> test = new ArrayList<>();
        boolean loop = true;
        while (loop) {
            for (int i = 0; i < route.size(); i++) {
                Point A = new PointImpl();
                Point B = new PointImpl();
                if (i == 0) {
                    A = board.getHero();
                    B = route.get(i);
                } else {
                    A = route.get(i - 1);
                    try {
                        B = route.get(i);
                    } catch (IndexOutOfBoundsException e) {
                        loop = false;
                        //return Direction.STOP.toString();
                    }
                }

                if (A.getX() - B.getX() != 0) {
                    if (A.getX() - B.getX() > 0) {
                        test.add(Direction.LEFT);
                        //return Direction.LEFT.toString();
                    } else {
                        test.add(Direction.RIGHT);
                        //return Direction.RIGHT.toString();
                    }
                }

                if (A.getY() - B.getY() != 0) {
                    if (A.getY() - B.getY() > 0) {
                        test.add(Direction.DOWN);
                        //return Direction.DOWN.toString();
                    } else {
                        test.add(Direction.UP);
                        //return Direction.UP.toString();
                    }
                }

            }
            loop = false;
        }
        return test;
        //return Direction.STOP.toString();
    }

    public String goByRoute() {
        List<Direction> test = new ArrayList<>();
        atack();
        /*while (loop) {*/
        for (int i = 0; i < route.size(); i++) {
            Point A = new PointImpl();
            Point B = new PointImpl();
            if (i == 0) {
                A = board.getHero();
                B = route.get(i);
                //route.remove(i);
            } else {
                A = route.get(i - 1);
                try {
                    B = route.get(i);
                } catch (IndexOutOfBoundsException e) {
                    //loop = false;
                    if (route.isEmpty()) {
                        nearestEvidence = null;
                    }

                    return Direction.STOP.toString();
                }
            }
            route.remove(i);
            if (A.getX() - B.getX() != 0) {
                if (A.getX() - B.getX() > 0) {
                    test.add(Direction.LEFT);
                    return Direction.LEFT.toString();
                } else {
                    test.add(Direction.RIGHT);
                    return Direction.RIGHT.toString();
                }
            }

            if (A.getY() - B.getY() != 0) {
                if (A.getY() - B.getY() > 0) {
                    test.add(Direction.DOWN);
                    return Direction.DOWN.toString();
                } else {
                    test.add(Direction.UP);
                    return Direction.UP.toString();
                }
            }

            /*}
            loop = false;*/
        }
        return Direction.STOP.toString();
    }

    //тут вся логика, отсюда отправляемся
    public String mainLogic() {
        fillAdjacencyMatrix();

        makeRoute(board.getHero(), nearestEvidence);
        System.out.println(route);
        return goByRoute();
    }

    public List<Direction> mainLogicTest() {
        fillAdjacencyMatrix();

        return goByRouteTest();
    }
}