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
        mButton = (Button) findViewById(R.id.button);

        BitmapFactory.Options opts=new BitmapFactory.Options();
        Bitmap bitmap1= BitmapFactory.decodeResource(getResources(), R.drawable.fish1, opts);
        Bitmap bitmap2= BitmapFactory.decodeResource(getResources(), R.drawable.fish2, opts);
        Bitmap bitmap3= BitmapFactory.decodeResource(getResources(), R.drawable.fish3, opts);
        Bitmap bitmap4= BitmapFactory.decodeResource(getResources(), R.drawable.fish4, opts);
        bitmaps = new ArrayList<>();
        bitmaps.add(bitmap1);
        bitmaps.add(bitmap2);
        bitmaps.add(bitmap3);
        bitmaps.add(bitmap4);
//        Bitmap bitmap = bitmap4.copy(bitmap4.getConfig(),true);
        iv_after.setImageBitmap(bitmap4);

        mButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Bitmap bitmap = doodleView.getBitmap();
                ImageShapeContext isc = new ImageShapeContext();
                float sc2[][] = new float[bitmaps.size()][60];
                isc.getShapeContext(bitmaps, sc2, 125, 300, ImageShapeContext.CENTER_OF_BITMAP,
                        ImageShapeContext.R_MAX_WIDTH);
                float sc1[] = new float[60];
                isc.getShapeContext(bitmap, sc1, 125, 300, ImageShapeContext.CENTER_OF_BITMAP,
                        ImageShapeContext.R_MAX_WIDTH);
                int like = isc.getSimilarityNumber(sc1,sc2,60);
                Log.d("debug",like+"");

            }
        });


//
//        float sc[][] = new float[4][60];
//        ImageShanpeContext isc = new ImageShanpeContext();
//        isc.getShapeContext(bitmap1,sc[0],125,300,ImageShanpeContext.CENTER_OF_BITMAP);
//        isc.getShapeContext(bitmap2,sc[1],125,300,ImageShanpeContext.CENTER_OF_BITMAP);
//        isc.getShapeContext(bitmap3,sc[2],125,300,ImageShanpeContext.CENTER_OF_BITMAP);
//        isc.getShapeContext(bitmap4,sc[3],125,300,ImageShanpeContext.CENTER_OF_BITMAP);
//
//        float result[] = new float[3];
//        for(int i=1;i<4;i++)
//        {
//            result[i-1]=isc.getSimilarity(sc[0],sc[i],60);
//        }
//        Log.d("result", "0-1 " + result[0]);
//        Log.d("result","0-2 "+result[1]);
//        Log.d("result","0-3 "+result[2]);
    }
}
