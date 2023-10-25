package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

//steal literally EVERYTHING from square
public class Rectangle {
    // steal pretty much everything from ball except for radius. We don't need that. because shapes.
    float x, y;                // square's x,y position
    float width = 100, height = 50; // just change the width of this to 100
    float speedX;           // rectangle's speed
    float speedY;
    private RectF bounds;   // Needed for Canvas.draw
    private Paint paint;    // The paint style, color used for drawing

    private double ax, ay, az = 0; // acceleration from different axis

    public void setAcc(double ax, double ay, double az) {
        this.ax = ax;
        this.ay = ay;
        this.az = az;
    }

    Random r = new Random();  // seed random number generator

    public Rectangle(int color) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        // random position and speed
        x = width + r.nextInt(800);
        y = height + r.nextInt(800);
        speedX = r.nextInt(10) - 5;
        speedY = r.nextInt(10) - 5;
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

    // Circle-Rectangle Collision
    boolean isCircleRectangleCollision(float circleX, float circleY, float circleRadius, float rectX, float rectY, float rectWidth, float rectHeight) {
        float closestX = Math.max(rectX, Math.min(circleX, rectX + rectWidth));
        float closestY = Math.max(rectY, Math.min(circleY, rectY + rectHeight));

        float distance = (float) Math.sqrt((circleX - closestX) * (circleX - closestX) + (circleY - closestY) * (circleY - closestY));

        return distance <= circleRadius;
    }

    // Square-Rectangle Collision
    boolean isSquareRectangleCollision(float squareX, float squareY, float squareSize, float rectX, float rectY, float rectWidth, float rectHeight) {
        boolean xOverlap = squareX + squareSize >= rectX && squareX <= rectX + rectWidth;
        boolean yOverlap = squareY + squareSize >= rectY && squareY <= rectY + rectHeight;

        return xOverlap && yOverlap;
    }
}