package com.lucasp243.androidtp6_graphics;

import android.graphics.Color;
import android.util.Log;

import java.util.Random;

public class Circle {

    private static final Random rand = new Random();

    private static int BOUND_X, BOUND_Y;

    private static final int RADIUS_MIN = 20, RADIUS_MAX = 80;

    private float
            xPos, yPos,
            xVel = 0f, yVel = 0f,
            xAcc = 0f, yAcc = 0f;

    private final int radius;

    private final int color;

    public Circle(float x, float y) {
        xPos = x;
        yPos = y;
        color = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        radius = (int) (RADIUS_MIN + Math.random() * (RADIUS_MAX - RADIUS_MIN));
    }

    public static void setBounds(int width, int height) {
        BOUND_X = width;
        BOUND_Y = height;
    }

    public void setAcc(float xAcc, float yAcc) {
        this.xAcc = xAcc;
        this.yAcc = yAcc;
    }

    public float getX() {
        return xPos;
    }

    public float getY() {
        return yPos;
    }

    public int getRadius() {
        return radius;
    }

    public int getColor() {
        return color;
    }

    public void tick() {

        final float CIRCLE_INERTIA = 0.5f + radius / 200f;

        if ((xAcc > 0 && xPos < BOUND_X - radius) || (xAcc < 0 && xPos > radius)) {
            xVel = (xVel + xAcc) * CIRCLE_INERTIA;
        }
        else {
            xVel = 0;
        }

        if ((yAcc > 0 && yPos < BOUND_Y - radius) || (yAcc < 0 && yPos > radius)) {
            yVel = (yVel + yAcc) * CIRCLE_INERTIA;
        }
        else {
            yVel = 0;
        }

        xPos += xVel;
        yPos += yVel;
    }
}
