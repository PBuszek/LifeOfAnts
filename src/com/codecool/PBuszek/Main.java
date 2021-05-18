package com.codecool.PBuszek;

import com.codecool.PBuszek.util.DisplayUtil;

public class Main {


    public static void main(String[] args) {
        Colony colony = initColony();
        showColony(colony);

        play(100, colony);
    }

    private static Colony initColony() {
        return new Colony(15, 2, 3, 4);
    }

    private static void play(int steps, Colony colony) {
        for (int i = 0; i < steps; i++) {
            stepTime(colony);
        }
    }

    private static void stepTime(Colony colony) {
        colony.moveAnts();
        showColony(colony);
        DisplayUtil.waitSeconds(1);
    }

    private static void showColony(Colony colony) {
        System.out.println(colony.toString());
    }

}
