package com.pmhart83.android.androidsamples.ui;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingCaptureView extends View {

    private int _drawSize = 1;
    private int _drawColor = Color.BLACK;
    private Path _drawPath;
    private Paint _drawPaint;
    private Paint _canvasPaint;
    private Bitmap _canvasBitmap;
    private Canvas _drawCanvas;

    public DrawingCaptureView(Context context, AttributeSet attrs) {
        super(context, attrs);

        _drawPath = new Path();

        _canvasPaint = new Paint(Paint.DITHER_FLAG);

        _drawPaint = new Paint();
        _drawPaint.setAntiAlias(true);
        _drawPaint.setStrokeWidth(_drawSize);
        _drawPaint.setStyle(Paint.Style.STROKE);
        _drawPaint.setStrokeJoin(Paint.Join.ROUND);
        _drawPaint.setStrokeCap(Paint.Cap.ROUND);
        _drawPaint.setColor(_drawColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        _canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        _drawCanvas = new Canvas(_canvasBitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:

                _drawPath.moveTo(touchX, touchY);
                break;

            case MotionEvent.ACTION_MOVE:

                _drawPath.lineTo(touchX, touchY);
                break;

            case MotionEvent.ACTION_UP:

                _drawCanvas.drawPath(_drawPath, _drawPaint);
                _drawPaint.reset();
                break;

            default:
                return false;
        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(_canvasBitmap, 0, 0, _canvasPaint);
        canvas.drawPath(_drawPath, _drawPaint);
    }
}
