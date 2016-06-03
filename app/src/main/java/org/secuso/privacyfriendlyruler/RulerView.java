package org.secuso.privacyfriendlyruler;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.view.View;

import static org.secuso.privacyfriendlyruler.R.color.darkblue;

/**
 * Created by roberts on 02.06.16.
 */
public class RulerView extends View {

    double dpmm;
    double heightPx;
    double heightmm;
    double widthPx;
    float lineWidth;
    int textSize ;
    int db;
//    Activity activity;

    public RulerView(Context context, double ydpmm, double height, double width) {
        super(context);

//        activity = val;
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        this.getDisplay().getMetrics(displayMetrics);
//        heightPx = this.getMeasuredHeight();
//        widthPx = this.getMeasuredWidth();
//        ydpi = displayMetrics.ydpi;
        dpmm = ydpmm;//ydpi / 25.4;
        heightPx = height;//displayMetrics.heightPixels;
        heightmm = heightPx/dpmm;
        widthPx = width;//displayMetrics.widthPixels;

        db = ContextCompat.getColor(context, R.color.darkblue);
        textSize = (int)(dpmm *2.5);
        lineWidth = (float)(dpmm*0.15);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(db);
        paint.setAlpha(255);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(lineWidth);

//        canvas.drawRect((float)widthPx-500, (float)heightPx-500, (float)widthPx+50, (float)heightPx+50, paint);
//        SharedPreferences prefs = activity.getSharedPreferences("settings", 0);
//
//        if (prefs.getString("pref_leftruler", "cm").equals("cm")){
            drawLeftCm(canvas, paint);
//        } else if (prefs.getString("pref_leftruler", "inch").equals("cm")) {
//            drawLeftIn(canvas, paint);
//        }
//
//        if (prefs.getString("pref_rightruler", "inch").equals("cm")){
//            drawRightCm(canvas, paint);
//        } else if (prefs.getString("pref_rightruler", "inch").equals("inch")) {
            drawRightIn(canvas, paint);
//        }
//
//
//        if (prefs.getString("pref_anglemeasure", "off").equals("degree")){
//            drawAngleMeasureDeg(canvas, paint);
//        } else if (prefs.getString("pref_anglemeasure", "off").equals("radian")) {
//            drawAngleMeasureRad(canvas, paint);
//        }


    }

    private void drawAngleMeasureDeg(Canvas canvas, Paint paint){
        float radius = (float)widthPx/2;
        float half = (float)(heightPx/2-dpmm*9);
        float degInRad = (float)Math.PI /180;
        Path path = new Path();

        canvas.drawLine(0, half-radius, 0, half+radius, paint);
        canvas.drawLine(0, half, radius, half, paint);
        canvas.drawLine(0, half, (float)Math.sin(45*degInRad)*radius, (float)(half-Math.cos(45*degInRad)*radius), paint);
        canvas.drawLine(0, half, (float)Math.sin(135*degInRad)*radius, (float)(half-Math.cos(135*degInRad)*radius), paint);

        for (int i = 0; i <= 180; i++){
            if (i%10 == 0){
                canvas.drawLine((float)Math.sin(i*degInRad)*radius, (float)(half-Math.cos(i*degInRad)*radius),
                        (float)(Math.sin(i*degInRad)*(radius+dpmm*8)), (float)(half-Math.cos(i*degInRad)*(radius+dpmm*8)), paint);
                //draw a number every 10 deg (except 0 and 180)
                if (i != 0 && i != 180){
                    path.reset();
                    path.moveTo((float)(Math.sin(i*degInRad)*(radius+dpmm*8)+textSize/5),
                            (float)(half-Math.cos(i*degInRad)*(radius+dpmm*8)-textSize*0.75));
                    path.lineTo((float)(Math.sin(i*degInRad)*(radius+dpmm*8)+textSize/5),
                            (float)(half-Math.cos(i*degInRad)*(radius+dpmm*8)+textSize*0.6));
                    canvas.drawTextOnPath(""+i, path, 0, 0, paint);
                }
            } else if (i%5 == 0){
                canvas.drawLine((float)Math.sin(i*degInRad)*radius, (float)(half-Math.cos(i*degInRad)*radius),
                        (float)(Math.sin(i*degInRad)*(radius+dpmm*5)), (float)(half-Math.cos(i*degInRad)*(radius+dpmm*5)), paint);
            } else {
                canvas.drawLine((float)Math.sin(i*degInRad)*radius, (float)(half-Math.cos(i*degInRad)*radius),
                        (float)(Math.sin(i*degInRad)*(radius+dpmm*3)), (float)(half-Math.cos(i*degInRad)*(radius+dpmm*3)), paint);
            }
        }
    }

    private void drawAngleMeasureRad(Canvas canvas, Paint paint){
        float radius = (float)widthPx/2;
        float half = (float)(heightPx/2-dpmm*9);
        float piOver24 = (float)Math.PI /24;
        Path path = new Path();
        String[] labels = {"0", "π/12", "π/6", "π/4", "π/3", "5π/12", "π/2", "7π/12", "2π/3", "3π/4", "5π/6", "11π/12", "π"};

        canvas.drawLine(0, half-radius, 0, half+radius, paint);
        canvas.drawLine(0, half, radius, half, paint);
        canvas.drawLine(0, half, (float)Math.sin(6*piOver24)*radius, (float)(half-Math.cos(6*piOver24)*radius), paint);
        canvas.drawLine(0, half, (float)Math.sin(18*piOver24)*radius, (float)(half-Math.cos(18*piOver24)*radius), paint);

        for (int i = 0; i <= 24; i++){
            if (i%6 == 0){
                canvas.drawLine((float)Math.sin(i*piOver24)*radius, (float)(half-Math.cos(i*piOver24)*radius),
                        (float)(Math.sin(i*piOver24)*(radius+dpmm*8)), (float)(half-Math.cos(i*piOver24)*(radius+dpmm*8)), paint);
                if (i!= 0 && i != 24) {
                    path.reset();
                    path.moveTo((float) (Math.sin(i * piOver24) * (radius + dpmm * 8) + textSize / 5),
                            (float) (half - Math.cos(i * piOver24) * (radius + dpmm * 8) - textSize * 0.85));
                    path.lineTo((float) (Math.sin(i * piOver24) * (radius + dpmm * 8) + textSize / 5),
                            (float) (half - Math.cos(i * piOver24) * (radius + dpmm * 8) + textSize * 0.85));
                    canvas.drawTextOnPath(labels[i / 2], path, 0, 0, paint);
                }
            } else if (i%2 == 0){
                canvas.drawLine((float)Math.sin(i*piOver24)*radius, (float)(half-Math.cos(i*piOver24)*radius),
                        (float)(Math.sin(i*piOver24)*(radius+dpmm*5)), (float)(half-Math.cos(i*piOver24)*(radius+dpmm*5)), paint);
                path.reset();
                path.moveTo((float) (Math.sin(i * piOver24) * (radius + dpmm * 5) + textSize / 4),
                        (float) (half - Math.cos(i * piOver24) * (radius + dpmm * 5) - textSize * 1.4));
                path.lineTo((float) (Math.sin(i * piOver24) * (radius + dpmm * 5) + textSize / 4),
                        (float) (half - Math.cos(i * piOver24) * (radius + dpmm *5) + textSize * 1.4));
                canvas.drawTextOnPath(labels[i / 2], path, 0, 0, paint);
            } else {
                canvas.drawLine((float)Math.sin(i*piOver24)*radius, (float)(half-Math.cos(i*piOver24)*radius),
                        (float)(Math.sin(i*piOver24)*(radius+dpmm*3)), (float)(half-Math.cos(i*piOver24)*(radius+dpmm*3)), paint);
            }
        }
    }

    private void drawLeftCm(Canvas canvas, Paint paint){
        for (int i = 0; i < heightmm; i++){
            if (i%10 == 0) {
                //draw 8mm line every cm
                canvas.drawLine(0, (float)dpmm*i, (float)dpmm*8, (float)dpmm*i, paint);
                //draw a number every cm
                canvas.drawText(""+i/10, (float)dpmm*8+(textSize/5), (float)(dpmm*i+textSize), paint);
            } else if (i%5 == 0) {
                //draw 5mm line every 5mm
                canvas.drawLine(0, (float)dpmm*i, (float)dpmm*5, (float)dpmm*i, paint);
            } else {
                //draw 3mm line every mm
                canvas.drawLine(0, (float)dpmm*i, (float)dpmm*3, (float)dpmm*i, paint);
            }
        }
    }

    private void drawLeftIn(Canvas canvas, Paint paint){
        for (int i = 0; i < (heightmm*25.4*32); i++){
            if (i%32 == 0) {
                //draw 8mm line every inch
                canvas.drawLine(0, (float)dpmm*i, (float)(dpmm*8), (float)dpmm*i, paint);
                //draw a number every inch
                canvas.drawText(""+i/32, (float)dpmm*8+(textSize/5), (float)(dpmm*i+textSize), paint);
            } else if (i%16 == 0) {
                //draw 6mm line every 1/2 inch
                canvas.drawLine(0, (float)dpmm*i, (float)(dpmm*6), (float)dpmm*i, paint);
            } else if (i%8 == 0) {
                //draw 4mm line every 1/4 inch
                canvas.drawLine(0, (float)dpmm*i, (float)(dpmm*4), (float)dpmm*i, paint);
            } else if (i%4 == 0) {
                //draw 3mm line every 1/8 inch
                canvas.drawLine(0, (float)dpmm*i, (float)(dpmm*3), (float)dpmm*i, paint);
            } else if (i%2 == 0) {
                //draw 2mm line every 1/16 inch
                canvas.drawLine(0, (float)dpmm*i, (float)(dpmm*2), (float)dpmm*i, paint);
            } else {
                //draw 1.5mm line every 1/32 inch
                canvas.drawLine(0, (float)dpmm*i, (float)(dpmm*1.5), (float)dpmm*i, paint);
            }
        }
    }

    private void drawRightCm(Canvas canvas, Paint paint){
        Path path = new Path();

        for (int i = 0; i < heightmm; i++){
            if (i%10 == 0) {
                //draw 8mm line every cm
                canvas.drawLine((float)(widthPx-dpmm*(8+5)), (float)(heightPx-dpmm*(i+18)),
                        (float)(widthPx-dpmm*5), (float)(heightPx-dpmm*(i+18)), paint);
                //draw a number every cm
                path.reset();
                path.moveTo((float)(widthPx-dpmm*(8+5)-textSize/5), (float)(heightPx-dpmm*(i+16)-textSize));
                path.lineTo((float)(widthPx-dpmm*(8+5)-textSize/5), (float)(heightPx-dpmm*(i+16)-2*textSize));
                canvas.drawTextOnPath(""+i/10, path, 0, 0, paint);
            } else if (i%5 == 0) {
                //draw 5mm line every 5mm
                canvas.drawLine((float)(widthPx-dpmm*(5+5)), (float)(heightPx-dpmm*(i+18)),
                        (float)(widthPx-dpmm*5), (float)(heightPx-dpmm*(i+18)), paint);
            } else {
                //draw 3mm line every mm
                canvas.drawLine((float)(widthPx-dpmm*(3+5)), (float)(heightPx-dpmm*(i+18)),
                        (float)(widthPx-dpmm*5), (float)(heightPx-dpmm*(i+18)), paint);
            }
        }
    }

    private void drawRightIn(Canvas canvas, Paint paint){
        Path path = new Path();

        for (int i = 0; i < (heightmm*25.4*32); i++){
            if (i%32 == 0) {
                //draw 8mm line every inch
                canvas.drawLine((float)(widthPx-dpmm*(8+5)), (float)(heightPx-dpmm*(i+18)),
                        (float)(widthPx-dpmm*5), (float)(heightPx-dpmm*(i+18)), paint);
                //draw a number every inch
                path.reset();
                path.moveTo((float)(widthPx-dpmm*(8+5)-textSize/5), (float)(heightPx-dpmm*(i+16)-textSize));
                path.lineTo((float)(widthPx-dpmm*(8+5)-textSize/5), (float)(heightPx-dpmm*(i+16)-2*textSize));
                canvas.drawTextOnPath(""+i/32, path, 0, 0, paint);
            } else if (i%16 == 0) {
                //draw 6mm line every 1/2 inch
                canvas.drawLine((float)(widthPx-dpmm*(6+5)), (float)(heightPx-dpmm*(i+18)),
                        (float)(widthPx-dpmm*5), (float)(heightPx-dpmm*(i+18)), paint);
            } else if (i%8 == 0) {
                //draw 4mm line every 1/4 inch
                canvas.drawLine((float)(widthPx-dpmm*(4+5)), (float)(heightPx-dpmm*(i+18)),
                        (float)(widthPx-dpmm*5), (float)(heightPx-dpmm*(i+18)), paint);
            } else if (i%4 == 0) {
                //draw 3mm line every 1/8 inch
                canvas.drawLine((float)(widthPx-dpmm*(3+5)), (float)(heightPx-dpmm*(i+18)),
                        (float)(widthPx-dpmm*5), (float)(heightPx-dpmm*(i+18)), paint);
            } else if (i%2 == 0) {
                //draw 2mm line every 1/16 inch
                canvas.drawLine((float)(widthPx-dpmm*(2+5)), (float)(heightPx-dpmm*(i+18)),
                        (float)(widthPx-dpmm*5), (float)(heightPx-dpmm*(i+18)), paint);
            } else {
                //draw 1.5mm line every 1/32 inch
                canvas.drawLine((float)(widthPx-dpmm*(1.5+5)), (float)(heightPx-dpmm*(i+18)),
                        (float)(widthPx-dpmm*5), (float)(heightPx-dpmm*(i+18)), paint);
            }
        }
    }
}
