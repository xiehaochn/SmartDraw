package com.pre.jason;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.pre.DoodleView;
import com.pre.R;

import java.util.List;

public class TestSCActivity extends AppCompatActivity
{
    private ImageView iv_after;
    private DoodleView doodleView;
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private Bitmap bitmap4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sc);
        iv_after = (ImageView) findViewById(R.id.iv_after);
        doodleView = (DoodleView) findViewById(R.id.doodleView);

//        BitmapFactory.Options opts=new BitmapFactory.Options();
//        bitmap1= BitmapFactory.decodeResource(getResources(), R.drawable.fish1, opts);
//        bitmap2= BitmapFactory.decodeResource(getResources(), R.drawable.fish2, opts);
//        bitmap3= BitmapFactory.decodeResource(getResources(), R.drawable.fish3, opts);
//        bitmap4= BitmapFactory.decodeResource(getResources(), R.drawable.fish4, opts);
////        Bitmap bitmap = bitmap4.copy(bitmap4.getConfig(),true);
////        imageView.setImageBitmap(bitmap);
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

    public void getSCs(List<Bitmap> bitmaps, float shapeContext[][])
    {
        ImageShanpeContext isc = new ImageShanpeContext();
        int i;
        for(i=0;i<bitmaps.size();++i)
        {
            isc.getShapeContext(bitmaps.get(i),shapeContext[i],125,300,ImageShanpeContext.CENTER_OF_BITMAP);
        }
    }
}
