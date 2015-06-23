package com.woobledev.wooble;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

/**
 * Created by user on 23.06.15.
 */

public class CircleTransformation implements Transformation {
    int borderwidth = 2;
    int bordercolor;
    public CircleTransformation(int borderwidth, int bordercolor) {
        this.bordercolor = bordercolor;
        this.borderwidth = borderwidth;
    }
    @Override
    public Bitmap transform(Bitmap source) {
        if (source == null || source.isRecycled()) {
            return null;
        }

        final int width = source.getWidth() + borderwidth;
        final int height = source.getHeight() + borderwidth;

        Bitmap canvasBitmap = Bitmap.createBitmap(width, height, source.getConfig());
        BitmapShader shader = new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Canvas canvas = new Canvas(canvasBitmap);
        float radius = width > height ? ((float) height) / 2f : ((float) width) / 2f;
        canvas.drawCircle(width / 2, height / 2, radius, paint);

        //border code
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(bordercolor);
        paint.setStrokeWidth(borderwidth);
        canvas.drawCircle(width / 2, height / 2, radius - borderwidth / 2, paint);
        //--------------------------------------

        if (canvasBitmap != source) {
            source.recycle();
        }

        return canvasBitmap;
    }
    @Override
    public String key() {
        return "circle";
    }
}