package com.example.android.truecaller.customwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.example.android.truecaller.R;

/**
 * Created by Duke on 11/17/2015.
 */
public class CustomProgressBar extends View {

    //Sizes (with defaults)
    private int layoutHeight = 0;
    private int layoutWidth = 0;
    private int fullRadius = 100;
    private int circleRadius = 80;
    private int barLength = 60;
    private int barWidth = 20;
    private int rimWidth = 20;
    private int textSize = 20;
    private float contourSize = 0;

    //Padding (with defaults)
    private int paddingTop = 5;
    private int paddingBottom = 5;
    private int paddingLeft = 5;
    private int paddingRight = 5;

    //Colors (with defaults)
    private int barColor = 0xAA000000;
    private int contourColor = 0xAA000000;
    private int circleColor = 0x00000000;
    private int rimColor = 0xAADDDDDD;
    private int textColor = 0xFF000000;

    //Paints
    private Paint barPaint = new Paint();
    private Paint circlePaint = new Paint();
    private Paint rimPaint = new Paint();
    private Paint textPaint = new Paint();
    private Paint contourPaint = new Paint();

    //Rectangles
    private RectF innerCircleBounds = new RectF();
    private RectF circleBounds = new RectF();
    private RectF circleOuterContour = new RectF();
    private RectF circleInnerContour = new RectF();

    //Animation
    //The amount of pixels to move the bar by on each draw
    private float spinSpeed = 2f;
    //The number of milliseconds to wait in between each draw
    private int delayMillis = 10;
    private float progress = 0;
    boolean isSpinning = false;

    //Other
    private String text = "";
    private String[] splitText = {};

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context.obtainStyledAttributes(attrs,
                R.styleable.CustomProgressBar));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size = 0;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (heightMode != MeasureSpec.UNSPECIFIED && widthMode != MeasureSpec.UNSPECIFIED) {
            if (widthWithoutPadding > heightWithoutPadding) {
                size = heightWithoutPadding;
            } else {
                size = widthWithoutPadding;
            }
        } else {
            size = Math.max(heightWithoutPadding, widthWithoutPadding);
        }


        setMeasuredDimension(
                size + getPaddingLeft() + getPaddingRight(),
                size + getPaddingTop() + getPaddingBottom());
    }


    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
        layoutWidth = newWidth;
        layoutHeight = newHeight;
        setupBounds();
        setupPaints();
        invalidate();
    }


    private void setupPaints() {
        barPaint.setColor(barColor);
        barPaint.setAntiAlias(true);
        barPaint.setStyle(Paint.Style.STROKE);
        barPaint.setStrokeWidth(barWidth);

        rimPaint.setColor(rimColor);
        rimPaint.setAntiAlias(true);
        rimPaint.setStyle(Paint.Style.STROKE);
        rimPaint.setStrokeWidth(rimWidth);

        circlePaint.setColor(circleColor);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);

        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);

        contourPaint.setColor(contourColor);
        contourPaint.setAntiAlias(true);
        contourPaint.setStyle(Paint.Style.STROKE);
        contourPaint.setStrokeWidth(contourSize);
    }


    private void setupBounds() {

        int minValue = Math.min(layoutWidth, layoutHeight);

        // Calc the Offset if needed
        int xOffset = layoutWidth - minValue;
        int yOffset = layoutHeight - minValue;

        // Add the offset
        paddingTop = this.getPaddingTop() + (yOffset / 2);
        paddingBottom = this.getPaddingBottom() + (yOffset / 2);
        paddingLeft = this.getPaddingLeft() + (xOffset / 2);
        paddingRight = this.getPaddingRight() + (xOffset / 2);

        int width = getWidth();
        int height = getHeight();

        innerCircleBounds = new RectF(
                paddingLeft + (1.5f * barWidth),
                paddingTop + (1.5f * barWidth),
                width - paddingRight - (1.5f * barWidth),
                height - paddingBottom - (1.5f * barWidth));
        circleBounds = new RectF(
                paddingLeft + barWidth,
                paddingTop + barWidth,
                width - paddingRight - barWidth,
                height - paddingBottom - barWidth);
        circleInnerContour = new RectF(
                circleBounds.left + (rimWidth / 2.0f) + (contourSize / 2.0f),
                circleBounds.top + (rimWidth / 2.0f) + (contourSize / 2.0f),
                circleBounds.right - (rimWidth / 2.0f) - (contourSize / 2.0f),
                circleBounds.bottom - (rimWidth / 2.0f) - (contourSize / 2.0f));
        circleOuterContour = new RectF(
                circleBounds.left - (rimWidth / 2.0f) - (contourSize / 2.0f),
                circleBounds.top - (rimWidth / 2.0f) - (contourSize / 2.0f),
                circleBounds.right + (rimWidth / 2.0f) + (contourSize / 2.0f),
                circleBounds.bottom + (rimWidth / 2.0f) + (contourSize / 2.0f));

        fullRadius = (width - paddingRight - barWidth) / 2;
        circleRadius = (fullRadius - barWidth) + 1;
    }


    private void parseAttributes(TypedArray a) {
        barWidth = (int) a.getDimension(R.styleable.CustomProgressBar_pwBarWidth, barWidth);
        rimWidth = (int) a.getDimension(R.styleable.CustomProgressBar_pwRimWidth, rimWidth);
        spinSpeed = (int) a.getDimension(R.styleable.CustomProgressBar_pwSpinSpeed, spinSpeed);
        barLength = (int) a.getDimension(R.styleable.CustomProgressBar_pwBarLength, barLength);

        delayMillis = a.getInteger(R.styleable.CustomProgressBar_pwDelayMillis, delayMillis);
        if (delayMillis < 0) {
            delayMillis = 10;
        }

        // Only set the text if it is explicitly defined
        if (a.hasValue(R.styleable.CustomProgressBar_pwText)) {
            setText(a.getString(R.styleable.CustomProgressBar_pwText));
        }

        barColor = a.getColor(R.styleable.CustomProgressBar_pwBarColor, barColor);
        textColor = a.getColor(R.styleable.CustomProgressBar_pwTextColor, textColor);
        rimColor = a.getColor(R.styleable.CustomProgressBar_pwRimColor, rimColor);
        circleColor = a.getColor(R.styleable.CustomProgressBar_pwCircleColor, circleColor);
        contourColor = a.getColor(R.styleable.CustomProgressBar_pwContourColor, contourColor);

        textSize = (int) a.getDimension(R.styleable.CustomProgressBar_pwTextSize, textSize);
        contourSize = a.getDimension(R.styleable.CustomProgressBar_pwContourSize, contourSize);

        a.recycle();
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Draw the inner circle
        canvas.drawArc(innerCircleBounds, 360, 360, false, circlePaint);
        //Draw the rim
        canvas.drawArc(circleBounds, 360, 360, false, rimPaint);
        canvas.drawArc(circleOuterContour, 360, 360, false, contourPaint);
        //canvas.drawArc(circleInnerContour, 360, 360, false, contourPaint);
        //Draw the bar
        if (isSpinning) {
            canvas.drawArc(circleBounds, progress - 90, barLength, false, barPaint);
        } else {
            canvas.drawArc(circleBounds, -90, progress, false, barPaint);
        }
        //Draw the text (attempts to center it horizontally and vertically)
        float textHeight = textPaint.descent() - textPaint.ascent();
        float verticalTextOffset = (textHeight / 2) - textPaint.descent();

        for (String line : splitText) {
            float horizontalTextOffset = textPaint.measureText(line) / 2;
            canvas.drawText(
                    line,
                    this.getWidth() / 2 - horizontalTextOffset,
                    this.getHeight() / 2 + verticalTextOffset,
                    textPaint);
        }
        if (isSpinning) {
            scheduleRedraw();
        }
    }

    private void scheduleRedraw() {
        progress += spinSpeed;
        if (progress > 360) {
            progress = 0;
        }
        postInvalidateDelayed(delayMillis);
    }


    public boolean isSpinning() {
        return isSpinning;
    }

    public void resetCount() {
        progress = 0;
        setText("0%");
        invalidate();
    }


    public void stopSpinning() {
        isSpinning = false;
        progress = 0;
        postInvalidate();
    }

    public void startSpinning() {
        isSpinning = true;
        postInvalidate();
    }


    public void incrementProgress() {
        incrementProgress(1);
    }

    public void incrementProgress(int amount) {
        isSpinning = false;
        progress += amount;
        if (progress > 360)
            progress %= 360;
        postInvalidate();
    }


    public void setProgress(int i) {
        isSpinning = false;
        progress = i;
        postInvalidate();
    }


    public void setText(String text) {
        this.text = text;
        splitText = this.text.split("\n");
    }

    public int getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
    }

    public int getBarLength() {
        return barLength;
    }

    public void setBarLength(int barLength) {
        this.barLength = barLength;
    }

    public int getBarWidth() {
        return barWidth;
    }

    public void setBarWidth(int barWidth) {
        this.barWidth = barWidth;

        if (this.barPaint != null) {
            this.barPaint.setStrokeWidth(this.barWidth);
        }
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;

        if (this.textPaint != null) {
            this.textPaint.setTextSize(this.textSize);
        }
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public int getBarColor() {
        return barColor;
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;

        if (this.barPaint != null) {
            this.barPaint.setColor(this.barColor);
        }
    }

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;

        if (this.circlePaint != null) {
            this.circlePaint.setColor(this.circleColor);
        }
    }

    public int getRimColor() {
        return rimColor;
    }

    public void setRimColor(int rimColor) {
        this.rimColor = rimColor;

        if (this.rimPaint != null) {
            this.rimPaint.setColor(this.rimColor);
        }
    }

    public Shader getRimShader() {
        return rimPaint.getShader();
    }

    public void setRimShader(Shader shader) {
        this.rimPaint.setShader(shader);
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;

        if (this.textPaint != null) {
            this.textPaint.setColor(this.textColor);
        }
    }

    public float getSpinSpeed() {
        return spinSpeed;
    }

    public void setSpinSpeed(float spinSpeed) {
        this.spinSpeed = spinSpeed;
    }

    public int getRimWidth() {
        return rimWidth;
    }

    public void setRimWidth(int rimWidth) {
        this.rimWidth = rimWidth;

        if (this.rimPaint != null) {
            this.rimPaint.setStrokeWidth(this.rimWidth);
        }
    }

    public int getDelayMillis() {
        return delayMillis;
    }

    public void setDelayMillis(int delayMillis) {
        this.delayMillis = delayMillis;
    }

    public int getContourColor() {
        return contourColor;
    }

    public void setContourColor(int contourColor) {
        this.contourColor = contourColor;

        if (contourPaint != null) {
            this.contourPaint.setColor(this.contourColor);
        }
    }

    public float getContourSize() {
        return this.contourSize;
    }

    public void setContourSize(float contourSize) {
        this.contourSize = contourSize;

        if (contourPaint != null) {
            this.contourPaint.setStrokeWidth(this.contourSize);
        }
    }

    public int getProgress() {
        return (int) progress;
    }
}
