package de.edgelord.montepi;

import de.edgelord.saltyengine.core.Game;
import de.edgelord.saltyengine.core.GameConfig;
import de.edgelord.saltyengine.core.SceneManager;
import de.edgelord.saltyengine.core.WindowClosingHooks;

public class Main extends Game {

    public static final long START_POINTS = 1000000000000000000L;
    public static double POINTS_PER_TICK = 0;
    public static final int DART_SIZE = 5;
    public static double SPAWN_RATE_GROWTH = 0;

    public static long DARTS_PER_COLOR = 300000;

    public static long DART_COUNT = 0;
    public static long DARTS_INSIDE_THE_CIRCLE = 0;

    public static double PI_APPROXIMATION = 0;

    public static void main(String[] args) {
        init(GameConfig.config(1000, 1000, "MontePi", 10));
        start(30);

        WindowClosingHooks.addShutdownHook(() -> System.out.println("Number of darts: " + DART_COUNT + "\n" + "PI: " + PI_APPROXIMATION));
        SceneManager.setCurrentScene(new Scene());
    }
}
