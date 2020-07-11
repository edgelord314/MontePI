package de.edgelord.montepi;

import de.edgelord.saltyengine.core.Game;
import de.edgelord.saltyengine.core.graphics.SaltyGraphics;
import de.edgelord.saltyengine.effect.image.SaltyImage;
import de.edgelord.saltyengine.gameobject.DrawingRoutine;
import de.edgelord.saltyengine.transform.Vector2f;
import de.edgelord.saltyengine.utils.ColorUtil;
import de.edgelord.saltyengine.utils.SaltySystem;

import java.awt.*;
import java.util.SplittableRandom;

public class Scene extends de.edgelord.saltyengine.scene.Scene {

    private final SplittableRandom RNG = new SplittableRandom();
    private final SaltyImage darts = SaltySystem.createPreferredImage(Game.getGameWidth(), Game.getGameHeight());
    private final Vector2f gameCentre = Game.getGameTransform().getCentre();
    private final float diameter = Game.getGameWidth() / 2f;
    private double dartRest = 0;
    private long dartCountTMP = 0;
    private Color dartColor = randomColor();

    public void initialize() {
        addDrawingRoutine(new DrawingRoutine(DrawingRoutine.DrawingPosition.AFTER_GAMEOBJECTS) {
            @Override
            public void draw(final SaltyGraphics saltyGraphics) {
                saltyGraphics.drawImage(darts, 0, 0);

                saltyGraphics.setStroke(new BasicStroke(5));
                saltyGraphics.setColor(ColorUtil.PURPLE_COLOR);
                saltyGraphics.outlineOval(Game.getGameTransform());

                saltyGraphics.setColor(ColorUtil.withAlpha(Color.WHITE, .75f));
                saltyGraphics.drawRect(Game.getHost().getHorizontalCentrePosition(350), Game.getGameHeight() - 75, 350, 75);

                saltyGraphics.setColor(Color.BLACK);
                saltyGraphics.setFont(saltyGraphics.getFont().deriveFont(25f).deriveFont(Font.BOLD));
                saltyGraphics.drawText(String.format("PI: %.15f", Main.PI_APPROXIMATION), gameCentre.getX(), Game.getGameHeight() - 50, SaltyGraphics.TextAnchor.CENTRE);
                saltyGraphics.drawText("Darts: " + Main.DART_COUNT, gameCentre.getX(), Game.getGameHeight() - 25, SaltyGraphics.TextAnchor.CENTRE);
            }
        });

        for (long i = 0; i < Main.START_POINTS; i++) {
            spawnDart();
        }
    }

    @Override
    public void onFixedTick() {
        super.onFixedTick();

        Main.PI_APPROXIMATION = (double) (Main.DARTS_INSIDE_THE_CIRCLE * 4) / (float) Main.DART_COUNT;

        long wholeDarts = (long) Main.POINTS_PER_TICK;
        dartRest += Main.POINTS_PER_TICK - wholeDarts;

        if (dartRest >= 1) {
            wholeDarts++;
            dartRest = 1 - dartRest;
        }
        for (int i = 0; i < wholeDarts; i++) {
            spawnDart();
        }
        Main.POINTS_PER_TICK += Main.SPAWN_RATE_GROWTH;
    }

    private void spawnDart() {
        float x = randomNumber(Game.getGameWidth());
        float y = randomNumber(Game.getGameHeight());
        Graphics2D graphics2D = darts.createGraphics();
        graphics2D.setColor(dartColor);
        graphics2D.fillOval(Math.round(x - Main.DART_SIZE / 2f), Math.round(y - Main.DART_SIZE / 2f), Main.DART_SIZE, Main.DART_SIZE);
        graphics2D.dispose();
        if (new Vector2f(x, y).distance(gameCentre) < diameter) {
            Main.DARTS_INSIDE_THE_CIRCLE++;
        }
        Main.DART_COUNT++;
        if (dartCountTMP >= Main.DARTS_PER_COLOR) {
            dartColor = randomColor();
            dartCountTMP = 0;
        } else {
            dartCountTMP++;
        }
    }

    private Color randomColor() {
        return ColorUtil.randomColor();
        //return new Color((float) RNG.nextDouble(.8, 1), (float) RNG.nextDouble(.5), (float) RNG.nextDouble(.3));
    }

    private float randomNumber(float cap) {
        return (float) RNG.nextDouble() * cap;
    }
}
