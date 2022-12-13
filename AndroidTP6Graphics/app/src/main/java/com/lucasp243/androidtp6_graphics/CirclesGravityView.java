package com.lucasp243.androidtp6_graphics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CirclesGravityView extends SurfaceView implements Runnable {

    private Thread thisThread;

    private SensorManager sensorManager;

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final SurfaceHolder surface;

    private final List<Circle> circleList;

    public CirclesGravityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surface = getHolder();

        circleList = new CopyOnWriteArrayList<>();
    }

    public void setSensor(SensorManager sensorManager, Sensor sensor) {
        this.sensorManager = sensorManager;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Circle.setBounds(getWidth(), getHeight());

        if (event.getAction() != MotionEvent.ACTION_DOWN) return false;

        circleList.add(new Circle(event.getX(), event.getY()));

        final int MAX_CIRCLES_COUNT = 200;
        if (circleList.size() > MAX_CIRCLES_COUNT) {
            circleList.remove(0);
        }
        return true;
    }

    private void setGravity(float gravityX, float gravityY) {
        for (Circle c : circleList) {
            c.setAcc(gravityX, gravityY);
        }
    }

    private boolean paused = true;

    @Override
    public void run() {

        Circle.setBounds(getWidth(), getHeight());

        while (!paused) {
            if (!surface.getSurface().isValid()) continue;

            Canvas canvas = surface.lockCanvas();

            // White background
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);

            for (Circle c : circleList) {
                c.tick();
                paint.setColor(c.getColor());
                canvas.drawCircle(c.getX(), c.getY(), c.getRadius(), paint);
            }

            surface.unlockCanvasAndPost(canvas);
        }
    }

    public void resume() {
        paused = false;

        sensorManager.registerListener(
                sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST
        );

        thisThread = new Thread(this);
        thisThread.start();
    }

    public void pause() {
        paused = true;

        sensorManager.unregisterListener(sensorEventListener);

        while (true) {
            try {
                thisThread.join();
                break;
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        thisThread = null;
    }

    private final SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            CirclesGravityView.this.setGravity(-2f * sensorEvent.values[0], 2f * sensorEvent.values[1]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}
    };
}
