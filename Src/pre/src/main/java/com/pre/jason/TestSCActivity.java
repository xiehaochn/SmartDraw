package com.pre.jason;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private Button button;
    private List<Bitmap> bitmaps;
    private ImageShapeContext isc;
    private float shapeContexts[][];

    private HandlerThread handlerThread;
    private Handler subHandler;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sc);
        iv_after = (ImageView) findViewById(R.id.iv_after);
        doodleView = (DoodleView) findViewById(R.id.doodleView);
        button = (Button) findViewById(R.id.get);
        init();

        handlerThread = new HandlerThread("subThread");
        handlerThread.start();
        subHandler = new Handler(handlerThread.getLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                try
                {
                    Thread.sleep(100);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                Bitmap bitmap=doodleView.getBitmap();
                float sc1[] = new float[60];
                int boundaryNum = isc.getShapeContext(bitmap, sc1, 125, 5000, ImageShapeContextPre.CENTER_OF_MASS,
                        ImageShapeContextPre.R_MAX_SELF);
                Log.d("debug", "boundaryNum" + ": " + boundaryNum);
                int like = isc.getSimilarityNumber(sc1, shapeContexts, 60);
                Message mainMsg=mainHandler.obtainMessage();
                mainMsg.arg1=like;
                mainHandler.sendMessage(mainMsg);
            }
        };

        mainHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                if(msg.arg1<=bitmaps.size()-2)
                    iv_after.setImageBitmap(bitmaps.get(msg.arg1+1));
                else
                    iv_after.setImageBitmap(bitmaps.get(msg.arg1));
                Message backMsg=subHandler.obtainMessage();
                subHandler.sendMessage(backMsg);
            }
        };

        Message backMsg=subHandler.obtainMessage();
        subHandler.sendMessage(backMsg);

//        button.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Message backMsg=subHandler.obtainMessage();
//                subHandler.sendMessage(backMsg);
//            }
//        });
    }

    private void init()
    {
        BitmapFactory.Options opts=new BitmapFactory.Options();
        Bitmap bitmap1= BitmapFactory.decodeResource(getResources(), R.drawable.fish1, opts);
        Bitmap bitmap2= BitmapFactory.decodeResource(getResources(), R.drawable.fish2, opts);
        Bitmap bitmap3= BitmapFactory.decodeResource(getResources(), R.drawable.fish3, opts);
        Bitmap bitmap4= BitmapFactory.decodeResource(getResources(), R.drawable.fish4, opts);
//        Bitmap bitmap5= BitmapFactory.decodeResource(getResources(), R.drawable.apple5, opts);
//        Bitmap bitmap6= BitmapFactory.decodeResource(getResources(), R.drawable.apple6, opts);
        bitmaps = new ArrayList<>();
        bitmaps.add(bitmap1);
        bitmaps.add(bitmap2);
        bitmaps.add(bitmap3);
        bitmaps.add(bitmap4);
//        bitmaps.add(bitmap5);
//        bitmaps.add(bitmap6);
        isc = new ImageShapeContext();
        shapeContexts = new float[bitmaps.size()][60];
        isc.getShapeContext(bitmaps, shapeContexts, 125, 5000, ImageShapeContextPre.CENTER_OF_MASS,
                ImageShapeContextPre.R_MAX_SELF);
        iv_after.setImageBitmap(bitmap1);
    }
}
