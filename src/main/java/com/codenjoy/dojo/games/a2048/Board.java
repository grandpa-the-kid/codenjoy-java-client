package com.codenjoy.dojo.games.a2048;

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


import com.codenjoy.dojo.client.AbstractBoard;
import com.codenjoy.dojo.services.Direction;

import static com.codenjoy.dojo.games.a2048.Element.NONE;

/**
 * The class is a wrapper over the board string
 * coming from the server. Contains a number of
 * inherited methods {@link AbstractBoard},
 * but you can add any methods based on them here.
 */
public class Board extends AbstractBoard<Element> {

    @Override
    public Element valueOf(char ch) {
        return Element.valueOf(ch);
    }

    @Override
    protected int inversionY(int y) { // TODO разобраться с этим чудом
        return size - 1 - y;
    }

    public int getSumCountFor(Direction direction) {
        int result = 0;

        for (int y = 0; y < size; y++) {
            int fromX = 0;
            int toX = 0;
            while (fromX < size && toX < size - 1) {
                toX++;

                Element at = getAt(fromX, y);
                Element at2 = getAt(toX, y);
                if (at == NONE) {
                    fromX++;
                    continue;
                }
                if (at2 == NONE) {
                    continue;
                }

                if (at != NONE && at == at2) {
                    result++;
                    fromX = toX + 1;
                    toX = fromX;
                }
            }
        }
        return result;
    }

}
