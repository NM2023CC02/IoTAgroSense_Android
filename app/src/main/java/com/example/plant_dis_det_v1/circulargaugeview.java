package com.example.plant_dis_det_v1;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.Locale;

public class circulargaugeview extends View {
    private final Paint borderPaint;
    private final Paint gaugePaint, textPaint;
    private float progress;

    public circulargaugeview(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize the border paint
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(20);
        borderPaint.setColor(Color.BLACK);

        // Initialize the gauge paint
        gaugePaint = new Paint();
        gaugePaint.setStyle(Paint.Style.STROKE);
        gaugePaint.setStrokeWidth(18);
        gaugePaint.setColor(Color.GREEN);

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(8);
        textPaint.setColor(Color.GREEN);
        textPaint.setTextSize(100f);

        // Set the initial progress to 0%
        progress = 0f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect textBounds = new Rect();

        // Get the center coordinates of the view
        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;

        // Calculate the radius of the gauge
        float radius = 200f;

        // Calculate the angle of the arc
        float startAngle = 135f;
        float sweepAngle = 270f;

        // Draw the background of the gauge
        canvas.drawArc(cx - radius, cy - radius, cx + radius, cy + radius,
                startAngle, sweepAngle, false, borderPaint);

        // Only draw the progress arc if the progress is greater than zero
        if (progress > 0f) {
            // Calculate the angle of the progress arc
            sweepAngle = progress / 10000f * 270f;

            // Draw the border of the gauge
            canvas.drawCircle(cx, cy, radius, borderPaint);

            // Draw the progress of the gauge
            canvas.drawArc(cx - radius, cy - radius, cx + radius, cy + radius,
                    startAngle, sweepAngle, false, gaugePaint);

            // Draw the progress text in the center of the gauge
            String text;
            if (progress >= 1000f) {
                text = String.format(Locale.getDefault(), "%,.0f", progress);
            } else {
                text = String.format(Locale.getDefault(), "%.0f", progress);
            }
            gaugePaint.getTextBounds(text, 0, text.length(), textBounds);

            float textX = cx - textBounds.width() / 2f;
            float textY = cy - textBounds.height() / 2f;

            canvas.drawText(text, textX - 100, textY + 50, textPaint);
        } else {
            // Draw the border of the gauge
            canvas.drawCircle(cx, cy, radius, borderPaint);
        }
    }

    public void setProgress(float progress) {
        // Make sure the progress is within the valid range
        this.progress = Math.max(0f, Math.min(progress, 10000f));
        //
    }
}
