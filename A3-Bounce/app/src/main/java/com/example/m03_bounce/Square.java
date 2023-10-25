package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

public class Square {
    // steal pretty much everything from ball except for radius. We don't need that. because shapes.
    float x, y;                // square's x,y position
    float width = 50, height = 50;
    float speedX;           // square's speed
    float speedY;
    private RectF bounds;   // Needed for Canvas.draw
    private Paint paint;    // The paint style, color used for drawing

    private double ax, ay, az = 0; // acceleration from different axis

    public void setAcc(double ax, double ay, double az){
        this.ax = ax;
        this.ay = ay;
        this.az = az;
    }

    public Square(int color, float x, float y, float speedX, float speedY) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        // use parameter position and speed
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void moveWithCollisionDetection(Box box) {
        // Get new (x,y) position
        x += speedX;
        y += speedY;

        // Add acceleration to speed
        speedX += ax;
        speedY += ay;

        // Detect collision and react
        if (x + width > box.xMax) {
            speedX = -speedX;
            x = box.xMax - width;
        } else if (x - width < box.xMin) {
            speedX = -speedX;
            x = box.xMin + width;
        }
        if (y + height > box.yMax) {
            speedY = -speedY;
            y = box.yMax - height;
        } else if (y - height < box.yMin) {
            speedY = -speedY;
            y = box.yMin + height;
        }
    }

    public void draw(Canvas canvas) {
        bounds.set(x - width, y - height, x + width, y + height);
        canvas.drawRect(bounds, paint);
    }
}