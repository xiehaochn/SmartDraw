package com.pre.jason;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.pre.DoodleView;
import com.pre.R;

import java.util.ArrayList;
import java.util.List;

public class TestSCActivity extends AppCompatActivity
{
    private ImageView iv_after;
    private DoodleView doodleView;
    private Button buttonGet;
    private Button buttonCancel;
    private List<Bitmap> bitmaps;
    private HintUtils hintUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sc);
        iv_after = (ImageView) findViewById(R.id.iv_after);
        doodleView = (DoodleView) findViewById(R.id.doodleView);
        buttonGet = (Button) findViewById(R.id.get);
        buttonCancel = (Button) findViewById(R.id.cancel);
        init();
        buttonGet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                hintUtils.getScore(doodleView.getBitmap());
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                hintUtils.cancelHint();
            }
        });
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
        iv_after.setImageBitmap(bitmap1);

        hintUtils = new HintUtils()
        {
            @Override
            public void onInitComplete()
            {
                Toast.makeText(TestSCActivity.this,"init complete",Toast.LENGTH_SHORT).show();
                doodleView.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        hintUtils.startHint(doodleView.getBitmap());
                    }
                });
            }

            @Override
            public Bitmap onUpdateHint(int index)
            {
                if(index<0)
                {
                    Toast.makeText(TestSCActivity.this,"don't rudely drawing",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(index<=bitmaps.size()-2)
                        iv_after.setImageBitmap(bitmaps.get(index+1));
                    else
                        iv_after.setImageBitmap(bitmaps.get(index));
                }
                return doodleView.getBitmap();
            }

            @Override
            public void onGetScore(int score)
            {
                Toast.makeText(TestSCActivity.this,"score:"+score,Toast.LENGTH_SHORT).show();
            }
        };
        hintUtils.init(bitmaps);

    }
}
