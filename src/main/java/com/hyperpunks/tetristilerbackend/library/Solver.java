package com.hyperpunks.tetristilerbackend.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Solver {

    public static Set<String> findAllSolutions(Grid startingGrid, List<Shape> shapes, boolean canRotate, boolean canFlip) {
        Set<String> solutions = new TreeSet<>();
        if (startingGrid.countUnfilled() % 5 != 0) {
            return solutions;
        }
        List<List<Shape>> allShapesAndVariants = shapes.stream().map(shape -> shape.generateAllVariants(canRotate, canFlip)).toList();
        List<Grid> gridStates = new ArrayList<>();
        gridStates.add(startingGrid);
        List<Grid> nextGridStates = new ArrayList<>();
        for (List<Shape> shape : allShapesAndVariants) {
            for (Shape shapeVariant : shape) {
                for (int i =0; i < gridStates.size(); i++) {
                    Grid stateGrid = gridStates.get(i);
                    Grid nextGrid = stateGrid.clone();
                    List<int[]> allPlacements;
                    if (gridStates.size() == 1) {
                        allPlacements = nextGrid.findAllPlacements(shapeVariant);
                    } else {
                        allPlacements = nextGrid.findAllTouchingPlacements(shapeVariant);
                    }
                    for (int[] coordinates : allPlacements) {
                        if (nextGrid.place(coordinates[0], coordinates[1], shapeVariant)) {
                            boolean foundWrongSize = false;
                            for (int size : nextGrid.getAllEmptyGroupsSizes()) {
                                if (size % 5 != 0) {
                                    nextGrid = stateGrid.clone();
                                    foundWrongSize = true;
                                    break;
                                }
                            }
                            if (foundWrongSize) {
                                break;
                            }
                            if (nextGrid.countUnfilled() == 0) {
                                solutions.add(nextGrid.toString());
                            } else {
                                nextGridStates.add(nextGrid);
                                nextGrid = stateGrid.clone();
                            }
                        }
                    }
                }
            }
            gridStates = nextGridStates;
        }
        return solutions;
    }
}
