package com.codenjoy.dojo.games.icancode;

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
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Dice;

public abstract class AbstractSolver implements Solver<Board>  {

    protected Dice dice;

    public AbstractSolver(Dice dice) {
        this.dice = dice;
    }

    @Override
    public String get(Board board) {
        if (board.getMe() == null) {
            System.out.println("Please call Dojo Sensei for debug!!!"); // TODO debug this issue
            return Command.die().toString();
        }
        return whatToDo(board).toString();
    }

    public abstract Command whatToDo(Board board);

    public static void connectClient(String url, Solver solver) {
        WebSocketRunner.runClient(url, solver, new Board());
    }
}
