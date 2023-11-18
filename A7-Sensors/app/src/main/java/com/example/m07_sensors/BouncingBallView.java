package com.example.m07_sensors;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

/**
 * Created by Russ on 08/04/2014.
 * Edited by Mike LeBlanc on 11/17/23.
 */
public class BouncingBallView extends View implements SensorEventListener {

    private ArrayList<Ball> balls = new ArrayList<>(); // list of Balls
    private Box box;

    // For touch inputs - previous touch (x, y)
    private float previousX;
    private float previousY;
    boolean isCreationOkay = false;

    // Constructor
    public BouncingBallView(Context context) {
        super(context);

        // create the box
        box = new Box(Color.WHITE);  // ARGB

        //ball_1 = new Ball(Color.GREEN);
        balls.add(new Ball(Color.GREEN));

        balls.add(new Ball(Color.YELLOW));

        // Set up status message on paint object
        Paint paint = new Paint();

        // Set the font face and size of drawing text
        paint.setTypeface(Typeface.MONOSPACE);
        paint.setTextSize(32);
        paint.setColor(Color.CYAN);

        // To enable keypad
        this.setFocusable(true);
        this.requestFocus();
        // To enable touch mode
        this.setFocusableInTouchMode(true);
    }

    // Called back to draw the view. Also called after invalidate().
    @Override
    protected void onDraw(Canvas canvas) {
        // Draw the components
        box.draw(canvas);

        // Redraw the balls after we move the position in the last loop.
        for (Ball b : balls) {
            b.draw(canvas);  //draw each ball in the list
            b.moveWithCollisionDetection(box);  // Update the position of the ball

            // Checks to see if a ball collides with a ball other than itself.
            for (int i = 1; i < balls.size(); i++) { //collision detection WORKS PROGRESS!
                if (b != balls.get(i)) {
                    if (b.isCollide(balls.get(i))) {
                        Log.v("Bouncing balls", "Contact!");
                        balls.get(i).speedX = -balls.get(i).speedX + 0.5;
                        balls.get(i).speedY = -balls.get(i).speedY + 0.5;
                        isCreationOkay = true;
                        b.speedX = -b.speedX + 0.5;
                        b.speedY = -b.speedY + 0.5;
                    }
                }
            }
        }

        // Force a re-draw
        this.invalidate();
    }

    // Called back when the view is first created or its size changes (rotates).
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        // Set the movement bounds for the ball
        box.set(0, 0, w, h);
    }


    // Touch-input handler
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();
        float deltaX, deltaY;
        float slow_down_speed_factor = 10.0f;

        if (event.getAction() == MotionEvent.ACTION_MOVE) { // Modify rotational angles according to movement
            deltaX = (currentX - previousX) / slow_down_speed_factor;
            deltaY = (currentY - previousY) / slow_down_speed_factor;
            // Creates a new ball when the finger is dragged on the screen.
            balls.add(new Ball(Color.RED, previousX, previousY, deltaX, deltaY));  // add ball at every touch event

            // A way to clear list when too many balls
            if (balls.size() > 20) {
                // Remove all balls, then add a new one to start over the game.
                balls.clear();
                balls.add(new Ball(Color.GREEN));
            }

            // Variable changed from canCreateBall to isCreationOkay, complies with boolean variable naming
            // After a collision between different balls, we can create an additional ball.
            if (isCreationOkay) {
                balls.add(new Ball(Color.CYAN, 300, 300, 15, 20));
                isCreationOkay = false;
            }
        }

        // Save current x, y
        previousX = currentX;
        previousY = currentY;

        return true;  // Event handled
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        // Lots of sensor types...get which one, unpack accordingly
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            double ax = -event.values[0];  // turns out x is backwards...on my screen?
            double ay = event.values[1];   // y component of Accelerometer
            double az = event.values[2];   // z component of Accelerometer

            for (Ball b : balls) {
                b.setAcc(ax, ay, az);  //draw each ball in the list
            }
        }
    }

    // We needed this implemented, but requires no action in this environment.
    // Further testing may be required.
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}

