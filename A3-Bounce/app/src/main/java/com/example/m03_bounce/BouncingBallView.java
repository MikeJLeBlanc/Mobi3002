package com.example.m03_bounce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Russ on 08/04/2014.
 */
public class BouncingBallView extends View  {
    private int collisionCount = 0;
    private TextView collisionCountTextView = findViewById(R.id.scoreNumb);
    private ArrayList<Ball> balls = new ArrayList<>(); // list of Balls
    private ArrayList<Square> squares = new ArrayList<>(); // list of Balls
    private Ball ball_1;  // use this to reference first ball in arraylist
    private Box box;

    private Paint paint;

    private String[] debug_dump2 = new String[200];

    // For touch inputs - previous touch (x, y)
    private float previousX;
    private float previousY;
    private Rectangle rect;
    private int randomColor;
    private List<Integer> colorList = new ArrayList<>(); //create a color list to add colors and choose from them in our game

    public BouncingBallView(Context context, AttributeSet attrs) {
        super(context, attrs);;

        Log.v("BouncingBallView", "Constructor BouncingBallView");

        //add colors to our arrayList, instead of creating a new array every touch event
        colorList.add(Color.CYAN);
        colorList.add(Color.RED);
        colorList.add(Color.BLUE);
        colorList.add(Color.BLACK);
        colorList.add(Color.YELLOW);
        colorList.add(Color.MAGENTA);
        colorList.add(Color.DKGRAY);
        colorList.add(Color.WHITE);

        // Init the array
        for (int i = 1; i < 200; i++) {
            debug_dump2[i] = "  ";
        }

        // create the box
        box = new Box(Color.GRAY);  // ARGB

        //ball_1 = new Ball(Color.GREEN);
        balls.add(new Ball(Color.GREEN));
        ball_1 = balls.get(0);  // points ball_1 to the first; (zero-ith) element of list
        Log.w("BouncingBallLog", "Just added a bouncing ball");

        //ball_2 = new Ball(Color.CYAN);
        balls.add(new Ball(Color.CYAN));
        Log.w("BouncingBallLog", "Just added another bouncing ball");

        rect = new Rectangle(colorList.get(randomColor));

        // Set up status message on paint object
        paint = new Paint();

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

        Log.v("BouncingBallView", "onDraw");

        // Draw the components
        box.draw(canvas);
        //canvas.drawARGB(0,25,25,25);
        //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        rect.draw(canvas);
        rect.moveWithCollisionDetection(box);

        circleCollisionDetection(canvas);
        squareCollisionDetection(canvas);

        // Check what happens if you draw the box last
        //box.draw(canvas);

        // Check what happens if you don't call invalidate (redraw only when stopped-started)
        // Force a re-draw
        this.invalidate();
    }

    // Called back when the view is first created or its size changes.
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        // Set the movement bounds for the ball
        box.set(0, 0, w, h);
        Log.w("BouncingBallLog", "onSizeChanged w=" + w + " h=" + h);
    }


    // Touch-input handler
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();
        float deltaX, deltaY;
        float scalingFactor = 5.0f / ((box.xMax > box.yMax) ? box.yMax : box.xMax);
        randomColor = new Random().nextInt(colorList.size()); // new random colour when called

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // Modify rotational angles according to movement
                deltaX = currentX - previousX;
                deltaY = currentY - previousY;
                ball_1.speedX += deltaX * scalingFactor;
                ball_1.speedY += deltaY * scalingFactor;
                //Log.w("BouncingBallLog", " Xspeed=" + ball_1.speedX + " Yspeed=" + ball_1.speedY);
                Log.w("BouncingBallLog", "x,y= " + previousX + " ," + previousY + "  Xdiff=" + deltaX + " Ydiff=" + deltaY);
                balls.add(new Ball(colorList.get(randomColor), previousX, previousY, deltaX, deltaY));  // add ball at every touch event

                // A way to clear list when too many balls
                if (balls.size() > 20) {
                    resetList(balls);
                }
        }
        // Save current x, y
        previousX = currentX;
        previousY = currentY;

        // Try this (remove other invalidate(); )
        //this.invalidate();

        return true;  // Event handled
    }

    public void resetList(List list) {
        // leave first ball, remove the rest
        Log.v("BouncingBallLog", "too many balls, clear back to 1");
        list.clear();

    }

    Random rand = new Random();
    // called when button is pressed
    public void notRussButtonPressed() {
        Log.d("BouncingBallView  BUTTON", "User tapped the  button ... VIEW");
        randomColor = new Random().nextInt(colorList.size()); // new random colour when called

        //get half of the width and height as we are working with a circle
        int viewWidth = 300;   // this.getMeasuredWidth();
        int viewHeight = 300;  // this.getMeasuredHeight();

        // make random x,y, velocity
        int x = rand.nextInt(viewWidth);
        int y = rand.nextInt(viewHeight);
        int dx = rand.nextInt(20);
        int dy = rand.nextInt(20);

        squares.add(new Square(colorList.get(randomColor), x, y, dx, dy));  // add ball at every touch event

        if (squares.size() > 20) {
            resetList(squares);
        }
    }

    public void circleCollisionDetection(Canvas canvas) {
        for (Ball b : balls) {
            if (rect.isCircleRectangleCollision(previousX, previousY, 50, rect.x, rect.y, rect.width, rect.height)) {
                Log.v("BouncingBallView", "circle collision with rectangle");
                b.speedX = -b.speedX;
                b.speedY = -b.speedY;
                rect.speedX = -rect.speedX;
                rect.speedY = -rect.speedY;
                updateCollisionCountOnScreen(collisionCount);
                Log.v("BouncingBallView", "Score is: " + collisionCount);
            }
            b.draw(canvas);  //draw each ball in the list
            b.moveWithCollisionDetection(box);  // Update the position of the ball
        }
    }
    public void squareCollisionDetection(Canvas canvas) {
        for (Square s : squares) {
            if (rect.isSquareRectangleCollision(previousX, previousY, 50, rect.x, rect.y, rect.width, rect.height)) {
                Log.v("BouncingBallView", "square collision with rectangle");
                s.speedX = -s.speedX;
                s.speedY = -s.speedY;
                rect.speedX = -rect.speedX;
                rect.speedY = -rect.speedY;
                updateCollisionCountOnScreen(collisionCount);
                Log.v("BouncingBallView", "Score is: " + collisionCount);
            }
            s.draw(canvas);  //draw each square in the list
            s.moveWithCollisionDetection(box);  // Update the position of the square
        }
    }
    private void updateCollisionCountOnScreen(int collisionCount) {
        this.collisionCount = collisionCount;
        collisionCountTextView.setText(collisionCount);
    }
}