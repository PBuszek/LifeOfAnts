package com.codecool.PBuszek;

import com.codecool.PBuszek.ants.*;
import com.codecool.PBuszek.util.DisplayUtil;
import com.codecool.PBuszek.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A colony of ants on the grid.
 */
public class Colony {
    /**
     * Max absolute value of coordinates on the grid the ant lives on.
     */
    private final int gridSize;
    private Supplier<Position> queenPositionGetter;
    private Consumer<MatingPartner> bootyCall;

    private final List<Ant> ants = new ArrayList<>();

    public Colony(final int gridSize, final int workerNum, final int soldierNum, final int droneNum) {
        this.gridSize = gridSize;
        init(workerNum, soldierNum, droneNum);
    }

    public void moveAnts() {
        for (final Ant ant : ants) {
            ant.moveStep();
        }
    }

    private void init(final int workerNum, final int soldierNum, final int droneNum) {
        createQueen();
        createWorkers(workerNum);
        createSoldiers(soldierNum);
        createDrones(droneNum);
    }

    private void createQueen() {
        final Queen queen = new Queen(gridSize);
        ants.add(queen);
        queenPositionGetter = queen::getPosition;
        bootyCall = queen::tryToMate;
    }

    private void createSoldiers(final int num) {
        for (int i = 0; i < num; i++) {
            ants.add(new Soldier(gridSize, queenPositionGetter));
        }
    }

    private void createWorkers(final int num) {
        for (int i = 0; i < num; i++) {
            ants.add(new Worker(gridSize, queenPositionGetter));
        }
    }

    private void createDrones(final int num) {
        for (int i = 0; i < num; i++) {
            ants.add(new Drone(gridSize, queenPositionGetter, bootyCall));
        }
    }

    @Override
    public String toString() {
        final String placeholder = ".";
        final int gridActualSize = gridSize * 2 + 1;
        final String[][] grid = new String[gridActualSize][gridActualSize];

        // init grid
        for (int i = 0; i < gridActualSize; i++) {
            for (int j = 0; j < gridActualSize; j++) {
                grid[i][j] = placeholder;
            }
        }
        // add ants
        for (final Ant ant : ants) {
            final Position antPos = ant.getPosition();
            grid[gridSize - antPos.getY()][antPos.getX() + gridSize] = ant.getSymbol();
        }

        return DisplayUtil.arrayToString(grid);
    }

}
