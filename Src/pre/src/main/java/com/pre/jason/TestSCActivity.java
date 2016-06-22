package com.pre.jason;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pre.DoodleView;
import com.pre.R;

import java.util.ArrayList;
import java.util.List;

public class TestSCActivity extends AppCompatActivity
{
    private ImageView iv_after;
    private ImageView iv_show;
    private DoodleView doodleView;
    private Button mButton;
    private List<Bitmap> bitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sc);
        iv_after = (ImageView) findViewById(R.id.iv_after);
        doodleView = (DoodleView) findViewById(R.id.doodleView);
//        iv_show = (ImageView) findViewById(R.id.iv_show);
        mButton = (Button) findViewById(R.id.button);

        BitmapFactory.Options opts=new BitmapFactory.Options();
        Bitmap bitmap1= BitmapFactory.decodeResource(getResources(), R.drawable.fish1, opts);
        Bitmap bitmap2= BitmapFactory.decodeResource(getResources(), R.drawable.fish2, opts);
        Bitmap bitmap3= BitmapFactory.decodeResource(getResources(), R.drawable.fish3, opts);
        Bitmap bitmap4= BitmapFactory.decodeResource(getResources(), R.drawable.fish4, opts);

        bitmaps = new ArrayList<>();
        Bitmap bitmap11 = bitmap1.copy(bitmap1.getConfig(), true);
        Bitmap bitmap12 = bitmap2.copy(bitmap2.getConfig(), true);
        Bitmap bitmap13 = bitmap3.copy(bitmap3.getConfig(), true);
        Bitmap bitmap14 = bitmap4.copy(bitmap4.getConfig(), true);
        bitmaps.add(bitmap11);
        bitmaps.add(bitmap12);
        bitmaps.add(bitmap13);
        bitmaps.add(bitmap14);

        final ImageShapeContext isc = new ImageShapeContext();
        final float sc2[][] = new float[bitmaps.size()][60];
        isc.getShapeContext(bitmaps, sc2, 125, 3000, ImageShapeContext.CENTER_OF_MASS,
                ImageShapeContext.R_MAX_SELF);

        iv_after.setImageBitmap(bitmap14);

//        Log.d("debug", "bitmap4 height:" + bitmap4.getHeight());
//        Log.d("debug", "bitmap4 width:" + bitmap4.getWidth());
//
//        DisplayMetrics metric = new DisplayMetrics();
//        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//        wm.getDefaultDisplay().getMetrics(metric);
//        Log.d("debug", "window height:" + metric.heightPixels);
//        Log.d("debug", "window width:" + metric.widthPixels);
//        Log.d("debug", "window density:" + metric.density);
//        Log.d("debug", "window dpi:" + metric.densityDpi);

        mButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bitmap bitmap = doodleView.getBitmap();

                float sc1[] = new float[60];
                isc.getShapeContext(bitmap, sc1, 125, 3000, ImageShapeContext.CENTER_OF_MASS,
                        ImageShapeContext.R_MAX_SELF);
                int like = isc.getSimilarityNumber(sc1,sc2,60);
//                iv_show.setImageBitmap(bitmap);
                Log.d("debug", like + "");
//                Log.d("debug", "imageview height:" + iv_after.getHeight());
//                Log.d("debug","imageview width:"+iv_after.getWidth());
//                Log.d("debug", "doodleView height:" + doodleView.getHeight());
//                Log.d("debug","doodleView width:"+doodleView.getWidth());


            }
        });
    }
}
