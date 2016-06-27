package com.smartdraw.dijay.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;


import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.slider.AlphaSlider;
import com.flask.colorpicker.slider.LightnessSlider;
import com.smartdraw.R;
import com.smartdraw.dijay.View.BaseDialog;
import com.smartdraw.dijay.View.DoodleView;
import com.smartdraw.hawx.BaseActivity;

import java.util.List;
import java.util.Map;

/**
 * Author：DJ
 * Time：2016/6/17 0017 22:53
 * Name：Src
 * Description：
 */
public class SketchpadActivity extends BaseActivity
{
    public static boolean INIT_STATUS = true;
    private int position;
    private DoodleView doodleView;
    private ImageButton eraser;
    private ImageButton pen;
    private Dialog selectDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        setHideStatusBar(false);
        setContentView (R.layout.activity_sketchpad);
        super.onCreate (savedInstanceState);
        init ();
    }

    public void init()
    {
        INIT_STATUS = getIntent ().getBooleanExtra ("INIT_STATUS", true);
        doodleView=(DoodleView) findViewById(R.id.doodleView);
        pen=(ImageButton) findViewById(R.id.pen);
        eraser=(ImageButton) findViewById(R.id.eraser);
        pen.setEnabled(false);
        pen.setBackgroundResource(R.drawable.btn_tools_selected);
        eraser.setEnabled(true);
        if (INIT_STATUS)
        {
            SelectHint (this);
        } else
        {
            position = getIntent ().getExtras ().getInt ("position");
            TeachingDraw ();
        }
    }






/*选图提示*/
    public void SelectHint(final Context context)
    {
        final BaseDialog selectDialog = new BaseDialog (context, "提示", "先选图吧!", "选图", "取消");
        selectDialog.show ();
        selectDialog.setCancelable (false);
        selectDialog.setDialogClickListener (new BaseDialog.DialogClickListener ()
        {
            @Override
            public void doConfirm()
            {
                selectDialog.dismiss ();
                Intent intent = new Intent ();
                intent.putExtra ("Activity_num", 0);
                intent.setClass (context, SelectPictureActivity.class);
                SketchpadActivity.this.startActivityForResult (intent, 0);
            }

            @Override
            public void doCancel()
            {
                selectDialog.dismiss ();
            }
        });
    }

    public void pen(View view)
    {
        doodleView.setPaintColor(doodleView.paintColor);
        doodleView.setPaintWidth(doodleView.paintWidth);
        doodleView.setPaintmode_pen();
        pen.setEnabled(false);
        pen.setBackgroundResource(R.drawable.btn_tools_selected);
        eraser.setEnabled(true);
        eraser.setBackgroundResource(R.drawable.btn_tools);
    }
    public void eraser(View view)
    {
        doodleView.setPaintmode_eraser();
        doodleView.setPaintColor(Color.WHITE);
        pen.setEnabled(true);
        pen.setBackgroundResource(R.drawable.btn_tools);
        eraser.setEnabled(false);
        eraser.setBackgroundResource(R.drawable.btn_tools_selected);
    }
    public void undo(View view)
    {
        doodleView.undo();
    }
    public void redo(View view)
    {
        doodleView.redo();
    }
    public void clear(View view)
    {
        doodleView.bitmapClear();
    }
    public void color(View view)
    {
        showColorDialog();
    }
    public void save(View view)
    {
        MediaScannerConnection.scanFile(this, new String[]{
                        Environment.getExternalStorageDirectory().getAbsolutePath()},
                null, new MediaScannerConnection.OnScanCompletedListener()
                {
                    public void onScanCompleted(String path, Uri uri)
                    {

                    }
                });
    }

    private void showColorDialog()
    {
        selectDialog=new Dialog(this){
            ColorPickerView colorPickerView;
            LightnessSlider lightnessSlider;
            AlphaSlider alphaSlider;
            ImageView imageView;
            SeekBar seekBar;
            Button confirm;
            Button cancel;
            int tempColor=Color.BLACK;
            int tempProgress=1;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                colorPickerView= (ColorPickerView) findViewById(R.id.color_picker_view);
                lightnessSlider= (LightnessSlider) findViewById(R.id.v_lightness_slider);
                alphaSlider= (AlphaSlider) findViewById(R.id.v_alpha_slider);
                imageView= (ImageView) findViewById(R.id.color_width);
                seekBar= (SeekBar) findViewById(R.id.widthSeekBar);
                confirm= (Button) findViewById(R.id.setColorButton);
                cancel= (Button) findViewById(R.id.setColorButton_cancel);
                final Bitmap bitmap = Bitmap.createBitmap(800, 100, Bitmap.Config.ARGB_8888);
                final Canvas canvas = new Canvas(bitmap);
                final Paint p = new Paint();
                canvas.drawLine(160, 50, 640, 50, p);
                imageView.setImageBitmap(bitmap);
                colorPickerView.addOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int i) {
                        tempColor=i;
                        p.setColor(tempColor);
                        p.setStrokeCap(Paint.Cap.ROUND);
                        p.setStrokeWidth(tempProgress);
                        bitmap.eraseColor(Color.WHITE);
                        canvas.drawLine(160, 50, 640, 50, p);
                        imageView.setImageBitmap(bitmap);
                    }
                });
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        tempProgress=progress;
                        p.setColor(tempColor);
                        p.setStrokeCap(Paint.Cap.ROUND);
                        p.setStrokeWidth(tempProgress);
                        bitmap.eraseColor(Color.WHITE);
                        canvas.drawLine(160, 50, 640, 50, p);
                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doodleView.setPaintColor(tempColor);
                        doodleView.setPaintWidth(tempProgress);
                        doodleView.paintWidth=tempProgress;
                        doodleView.paintColor=tempColor;
                        selectDialog.dismiss(); // dialog is not on the screen

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectDialog.dismiss();
                    }
                });
            }
        };

        selectDialog.setContentView(R.layout.dialog_colorpicker);
        selectDialog.setCancelable(true);
        selectDialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult (requestCode, resultCode, data);
        if (requestCode == 0)
        {
            if (resultCode == RESULT_OK)
            {
                position = data.getExtras ().getInt ("position");
                TeachingDraw ();
            }
        }
    }


    /* 教学导航动画*/

    private void TeachingDraw()
    {

    }

/*自己画提示*/
    public void DrawSelfHint(final Context context)
    {
        final BaseDialog selectDialog = new BaseDialog (context, "Are You Ready?", "开始自己画", "自己画", "再学一遍");
        selectDialog.show ();
        selectDialog.setCancelable (false);

        selectDialog.setDialogClickListener (new BaseDialog.DialogClickListener ()
        {
            @Override
            public void doConfirm()
            {
                /*自己画*/
            }

            @Override
            public void doCancel()
            {
            /*返回跟我画，再重新画一遍*/
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sketchpad,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.zijihua:{
                Toast.makeText(this, "自己画", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.xuantu:{
                Intent intent = new Intent ();
                intent.putExtra ("Activity_num", 0);
                intent.setClass (this, SelectPictureActivity.class);
                SketchpadActivity.this.startActivityForResult (intent, 0);
                break;
            }
            case R.id.quanpin:{
                Toast.makeText(this, "全屏", Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}








