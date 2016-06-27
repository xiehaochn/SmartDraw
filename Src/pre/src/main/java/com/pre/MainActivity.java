package com.pre;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.pre.jason.TestSCActivity;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends Activity
{
	private DoodleView doodleView;
	private ImageView iv;
	private Dialog currentDialog;
	private AtomicBoolean dialogIsVisible = new AtomicBoolean();
	private  int setcolortemp;

	private float []SC=new float[60];
	private final float [][]appleSC=new float[6][60];
	private final float [][]gooseSC=new float[8][60];
	private final float [][][]planeSC=new float[5][8][60];
	private final int [][][]planesc={{{13, 11, 8, 0, 0, 0, 12, 7, 1, 7, 9, 20, 19, 29, 7, 0, 0, 0, 24, 12, 0, 0, 0, 3, 29, 24, 0, 0, 0, 0, 18, 27, 0, 0, 0, 0, 34, 8, 0, 0, 0, 0, 5, 40, 0, 0, 0, 0, 33, 0, 0, 0, 0, 0, 0, 44, 0, 0, 0, 0},
			{15, 13, 6, 0, 0, 0, 0, 0, 0, 4, 14, 12, 41, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 38, 35, 0, 0, 0, 0, 7, 10, 0, 0, 0, 0, 32, 18, 0, 0, 0, 0, 24, 38, 0, 0, 0, 0, 7, 3, 0, 0, 0, 0, 24, 41, 0, 0, 0, 0, 4},
			{17, 7, 5, 0, 0, 0, 0, 0, 0, 6, 17, 7, 17, 0, 0, 0, 0, 14, 0, 0, 0, 0, 1, 38, 0, 0, 0, 0, 0, 34, 0, 0, 0, 0, 0, 25, 0, 0, 0, 0, 0, 46, 0, 0, 0, 0, 0, 27, 1, 0, 0, 0, 0, 22, 17, 0, 0, 0, 0, 17},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 22, 15, 9, 0, 0, 0, 1, 15, 14, 13, 2, 21, 5, 0, 2, 0, 0, 3, 35, 5, 0, 8, 24, 24, 0, 0, 0, 0, 0, 43, 0, 0, 0, 0, 10, 27, 0, 0, 0, 0, 18, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 9, 2, 16, 9, 10, 0, 0, 0, 2, 17, 11, 14, 44, 18, 2, 0, 3, 0, 0, 11, 21, 3, 0, 0, 20, 21, 0, 0, 0, 0, 0, 18, 0, 0, 0, 0, 1, 19, 0, 0, 0, 0, 18, 0, 0, 0, 0, 0, 1},
			{0, 0, 5, 0, 0, 0, 1, 0, 0, 2, 0, 8, 10, 17, 9, 0, 0, 0, 12, 0, 0, 5, 9, 19, 23, 14, 0, 6, 0, 0, 10, 16, 15, 0, 0, 27, 21, 3, 0, 0, 0, 0, 31, 10, 0, 0, 0, 0, 8, 0, 0, 0, 0, 15, 5, 0, 0, 0, 0, 1},
			{0, 2, 5, 0, 0, 0, 0, 0, 0, 4, 0, 0, 9, 13, 11, 0, 0, 0, 0, 0, 0, 12, 10, 0, 23, 4, 0, 0, 13, 13, 1, 15, 2, 0, 1, 33, 31, 0, 0, 0, 1, 35, 10, 3, 0, 0, 0, 23, 1, 0, 0, 0, 0, 5, 24, 0, 0, 0, 0, 4},
			{0, 0, 0, 2, 3, 0, 1, 4, 3, 0, 0, 0, 0, 8, 8, 1, 12, 1, 9, 8, 8, 10, 1, 0, 17, 10, 12, 12, 7, 17, 14, 2, 0, 3, 8, 13, 9, 11, 0, 0, 25, 13, 7, 0, 0, 0, 2, 21, 0, 0, 0, 0, 0, 0, 13, 0, 0, 0, 0, 12}},
			{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 14, 6, 14, 18, 17, 0, 0, 9, 0, 0, 6, 20, 21, 32, 3, 3, 14, 0, 9, 23, 15, 0, 8, 0, 0, 1, 0, 0, 0, 0, 26, 5, 32, 0, 1, 1, 0, 2, 1, 1, 0, 0, 24, 1, 0, 0, 0},
					{3, 7, 7, 5, 8, 1, 1, 3, 5, 5, 5, 9, 18, 19, 19, 17, 20, 17, 0, 0, 12, 4, 0, 6, 13, 17, 27, 8, 5, 16, 0, 7, 27, 20, 0, 8, 0, 0, 0, 0, 0, 0, 0, 23, 8, 24, 1, 0, 0, 0, 0, 0, 0, 0, 0, 25, 0, 0, 0, 0},
					{3, 11, 12, 0, 0, 0, 0, 0, 0, 8, 0, 1, 23, 6, 1, 0, 0, 32, 0, 0, 0, 12, 26, 3, 0, 0, 0, 1, 53, 53, 0, 0, 0, 0, 2, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 28},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16, 4, 0, 0, 1, 14, 0, 0, 1, 27, 23, 18, 0, 17, 12, 0, 31, 2, 13, 11, 1, 9, 0, 0, 0, 0, 36, 19, 3, 0, 0, 17, 3, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 2, 16},
					{4, 8, 3, 0, 0, 4, 0, 0, 0, 2, 6, 3, 22, 7, 10, 10, 2, 7, 0, 1, 11, 7, 3, 16, 26, 4, 0, 1, 14, 10, 3, 17, 1, 0, 10, 18, 0, 0, 0, 0, 0, 27, 9, 0, 0, 0, 0, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14},
					{6, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 6, 7, 5, 5, 0, 0, 5, 1, 0, 0, 4, 6, 8, 19, 1, 0, 12, 8, 12, 0, 11, 8, 0, 15, 20, 14, 0, 0, 0, 6, 16, 12, 0, 0, 0, 13, 31, 0, 0, 0, 0, 0, 32, 0, 0, 0, 0, 0, 17},
					{4, 2, 1, 2, 0, 0, 0, 3, 4, 1, 2, 4, 6, 4, 10, 5, 0, 6, 7, 5, 7, 3, 5, 8, 13, 12, 4, 3, 9, 6, 9, 9, 0, 0, 16, 14, 13, 2, 0, 0, 6, 11, 13, 5, 0, 0, 7, 24, 2, 0, 0, 0, 0, 23, 4, 5, 0, 0, 0, 3},
					{0, 2, 0, 2, 0, 0, 2, 3, 4, 2, 4, 0, 10, 3, 6, 8, 0, 1, 7, 6, 9, 5, 3, 9, 8, 13, 6, 4, 12, 5, 6, 5, 13, 1, 16, 11, 19, 3, 0, 0, 0, 11, 7, 3, 1, 0, 12, 17, 6, 0, 0, 0, 0, 22, 7, 9, 0, 0, 0, 3}},
			{{0, 2, 11, 0, 0, 0, 0, 0, 0, 15, 16, 3, 7, 35, 9, 0, 0, 0, 15, 0, 0, 0, 3, 27, 23, 11, 0, 0, 0, 0, 51, 0, 0, 0, 0, 0, 40, 0, 0, 0, 0, 0, 14, 38, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0, 0, 29, 0, 0, 0, 0},
					{1, 6, 6, 0, 0, 0, 0, 0, 0, 1, 0, 1, 17, 8, 8, 0, 2, 28, 0, 0, 0, 21, 17, 1, 0, 0, 0, 0, 10, 18, 0, 0, 0, 0, 13, 11, 0, 0, 0, 0, 0, 30, 0, 0, 0, 0, 0, 28, 1, 0, 0, 0, 0, 36, 29, 0, 0, 0, 0, 19},
					{6, 7, 9, 0, 12, 7, 0, 0, 0, 10, 0, 1, 0, 0, 0, 6, 29, 16, 0, 0, 0, 10, 17, 0, 0, 0, 0, 0, 4, 53, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 46, 0, 0, 0, 0, 0, 29, 2, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 29},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 12, 1, 4, 10, 4, 0, 0, 0, 0, 0, 0, 11, 2, 14, 10, 0, 17, 0, 5, 15, 16, 0, 3, 6, 0, 0, 0, 0, 17, 1, 23, 0, 4, 17, 1, 21, 0, 0, 0, 0, 20, 32, 2, 0, 0, 6, 28},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 2, 5, 1, 7, 0, 0, 0, 5, 9, 8, 10, 14, 9, 9, 17, 10, 10, 17, 10, 13, 2, 0, 0, 0, 0, 0, 19, 22, 12, 0, 0, 15, 29, 16, 0, 0, 0, 0, 22, 9, 0, 0, 0, 0, 4},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 5, 3, 0, 0, 0, 5, 0, 0, 0, 2, 7, 12, 9, 10, 12, 13, 23, 10, 7, 15, 19, 20, 1, 1, 0, 0, 0, 0, 22, 31, 16, 0, 0, 8, 18, 12, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 1},
					{1, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 2, 8, 5, 12, 0, 0, 4, 2, 6, 0, 16, 13, 2, 0, 10, 11, 8, 9, 14, 4, 7, 7, 9, 15, 16, 0, 8, 0, 0, 0, 17, 21, 20, 9, 0, 10, 11, 0, 0, 0, 0, 0, 23, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 4, 0, 5, 8, 0, 0, 0, 6, 0, 0, 6, 6, 1, 6, 5, 5, 16, 11, 10, 7, 5, 13, 13, 15, 8, 0, 8, 10, 11, 9, 15, 15, 10, 10, 10, 15, 7, 1, 0, 0, 0, 0, 17, 0, 7, 0, 0, 8, 0}},
			{{11, 14, 6, 0, 0, 0, 0, 0, 0, 9, 12, 5, 28, 2, 0, 0, 0, 0, 0, 0, 0, 0, 3, 26, 36, 0, 0, 0, 0, 0, 16, 0, 0, 0, 0, 22, 28, 0, 0, 0, 0, 0, 39, 0, 0, 0, 0, 3, 24, 0, 0, 0, 0, 0, 25, 1, 0, 0, 0, 4},
					{8, 6, 3, 0, 0, 0, 0, 0, 0, 3, 8, 10, 23, 0, 0, 14, 12, 8, 0, 0, 17, 0, 0, 24, 23, 0, 0, 0, 0, 23, 0, 0, 0, 0, 0, 22, 36, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 22, 1, 0, 0, 0, 0, 0, 30, 0, 0, 0, 0, 6},
					{0, 0, 2, 0, 0, 0, 0, 0, 0, 3, 0, 0, 1, 15, 6, 13, 8, 15, 0, 0, 9, 6, 18, 0, 22, 0, 0, 0, 0, 6, 0, 16, 7, 0, 5, 11, 26, 0, 0, 0, 0, 0, 17, 14, 0, 0, 0, 26, 37, 0, 0, 0, 0, 0, 26, 0, 0, 0, 0, 0},
					{1, 0, 6, 4, 3, 0, 1, 0, 9, 7, 0, 2, 1, 12, 6, 9, 7, 13, 0, 0, 12, 7, 15, 3, 18, 0, 0, 0, 0, 4, 0, 17, 4, 0, 5, 11, 28, 0, 0, 0, 0, 0, 10, 11, 0, 0, 0, 19, 36, 0, 0, 0, 0, 0, 30, 0, 0, 0, 0, 0},
					{3, 6, 6, 7, 8, 4, 7, 0, 0, 1, 4, 6, 1, 10, 8, 12, 6, 8, 13, 0, 14, 7, 13, 0, 18, 0, 0, 0, 0, 4, 13, 16, 0, 0, 3, 15, 21, 0, 0, 0, 0, 0, 30, 7, 0, 0, 0, 10, 32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{4, 3, 5, 1, 8, 2, 5, 5, 0, 2, 7, 5, 4, 8, 2, 12, 11, 8, 10, 2, 8, 18, 24, 1, 12, 0, 0, 0, 0, 7, 12, 14, 5, 0, 1, 15, 15, 0, 0, 0, 0, 1, 27, 7, 0, 0, 0, 4, 31, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{4, 3, 8, 0, 2, 4, 1, 3, 6, 8, 6, 4, 7, 10, 6, 7, 9, 0, 0, 9, 2, 1, 17, 6, 16, 7, 0, 9, 1, 10, 15, 6, 9, 0, 9, 11, 11, 3, 0, 0, 0, 6, 5, 13, 0, 0, 5, 11, 16, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 12},
					{3, 1, 4, 2, 0, 5, 0, 2, 6, 5, 5, 1, 5, 10, 12, 5, 7, 3, 7, 2, 4, 11, 10, 9, 14, 9, 0, 6, 0, 6, 18, 19, 15, 0, 10, 14, 10, 5, 0, 0, 0, 4, 3, 16, 0, 0, 5, 7, 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3}},
			{{2, 1, 14, 4, 2, 0, 6, 11, 4, 9, 6, 14, 0, 0, 23, 0, 0, 0, 0, 0, 23, 12, 0, 16, 0, 0, 37, 0, 0, 0, 0, 0, 35, 0, 0, 3, 0, 0, 42, 0, 0, 0, 0, 0, 36, 0, 0, 0, 0, 0, 26, 0, 0, 0, 0, 0, 38, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 16, 11, 3, 0, 6, 13, 13, 6, 0, 4, 13, 12, 0, 3, 18, 1, 16, 1, 0, 15, 5, 19, 6, 0, 0, 0, 19, 11, 9, 0, 0, 7, 9, 17, 0, 1, 0, 0, 7, 18, 0, 0, 0, 0, 16, 8, 0, 0},
					{7, 0, 0, 0, 0, 1, 5, 1, 0, 0, 0, 3, 6, 16, 4, 2, 13, 6, 7, 16, 3, 6, 13, 2, 0, 0, 19, 15, 3, 0, 0, 3, 17, 16, 0, 0, 0, 0, 23, 16, 0, 0, 0, 0, 18, 15, 0, 0, 0, 0, 15, 21, 0, 0, 0, 0, 15, 16, 0, 0},
					{4, 5, 0, 4, 4, 3, 0, 0, 0, 0, 0, 0, 0, 4, 4, 12, 3, 0, 0, 5, 5, 9, 4, 0, 16, 8, 14, 10, 1, 13, 2, 7, 0, 2, 7, 6, 0, 15, 14, 13, 17, 0, 11, 0, 0, 0, 0, 11, 0, 0, 22, 15, 8, 0, 27, 0, 0, 0, 0, 4},
					{4, 2, 0, 0, 2, 3, 0, 4, 0, 0, 2, 8, 0, 10, 6, 4, 11, 2, 1, 10, 8, 8, 7, 0, 6, 9, 12, 12, 0, 11, 8, 0, 10, 12, 7, 7, 12, 0, 7, 11, 0, 8, 6, 0, 4, 8, 0, 7, 8, 0, 0, 0, 0, 17, 25, 0, 0, 0, 0, 9},
					{3, 0, 0, 0, 1, 1, 1, 1, 0, 0, 4, 2, 1, 6, 5, 5, 6, 6, 7, 2, 6, 5, 6, 0, 0, 16, 10, 11, 9, 8, 6, 5, 13, 10, 7, 0, 8, 0, 6, 4, 13, 6, 5, 8, 7, 12, 3, 4, 14, 0, 1, 0, 0, 11, 22, 0, 0, 4, 0, 12},
					{4, 1, 0, 0, 1, 0, 4, 1, 0, 0, 4, 2, 0, 5, 4, 2, 5, 8, 5, 6, 5, 9, 2, 0, 1, 11, 15, 10, 7, 8, 6, 8, 16, 6, 11, 1, 12, 2, 8, 8, 7, 5, 5, 8, 10, 7, 2, 8, 14, 0, 1, 0, 0, 13, 16, 0, 0, 2, 0, 13},
					{5, 0, 0, 2, 2, 3, 3, 2, 1, 0, 2, 5, 1, 3, 9, 4, 8, 5, 6, 6, 2, 8, 4, 0, 0, 12, 9, 14, 4, 6, 6, 8, 12, 9, 11, 2, 10, 3, 9, 7, 10, 7, 3, 8, 5, 7, 1, 8, 12, 0, 1, 0, 0, 9, 16, 0, 0, 2, 0, 11}},
	};
	private final int [][]applesc={{0, 0, 1, 0, 0, 0, 0, 0, 0, 7, 7, 2, 3, 20, 24, 0, 0, 0, 18, 0, 0, 9, 9, 25, 1, 30, 0, 0, 0, 0, 43, 5, 0, 0, 0, 0, 36, 5, 0, 0, 0, 0, 4, 46, 0, 0, 0, 0, 49, 0, 0, 0, 0, 0, 0, 34, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19, 9, 0, 0, 0, 9, 1, 0, 0, 0, 0, 0, 2, 15, 31, 12, 29, 11, 0, 0, 22, 23, 0, 0, 0, 0, 5, 22, 0, 6, 0, 24, 16, 25, 20, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 13, 0, 0, 0, 0, 0, 1, 17, 0, 1, 3, 4, 9, 25, 22, 31, 7, 26, 22, 8, 18, 0, 30, 23, 0, 0, 0, 0, 16, 2, 0, 0, 2, 28},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 6, 0, 1, 0, 0, 15, 0, 0, 0, 0, 0, 0, 23, 4, 2, 0, 10, 0, 8, 10, 12, 0, 0, 10, 18, 12, 3, 30, 12, 0, 18, 11, 10, 22, 24, 12, 6, 0, 20},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 0, 0, 0, 0, 0, 0, 0, 5, 1, 0, 3, 19, 0, 0, 0, 0, 0, 0, 17, 12, 11, 0, 10, 6, 9, 1, 9, 7, 6, 9, 13, 12, 24, 23, 8, 0, 15, 15, 16, 14, 22, 10, 1, 0, 3},
			{2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 2, 17, 15, 0, 0, 0, 1, 0, 0, 19, 7, 10, 1, 5, 13, 0, 0, 0, 13, 0, 0, 19, 17, 6, 9, 0, 0, 0, 0, 0, 11, 23, 0, 8, 15, 0, 10, 0, 0, 19, 13, 15, 0, 1, 21, 0, 10, 1}};
	private final int [][]goosesc={{12, 9, 6, 2, 4, 5, 5, 9, 4, 5, 8, 21, 37, 21, 5, 0, 0, 13, 25, 12, 0, 0, 0, 14, 39, 0, 0, 0, 0, 19, 47, 1, 0, 0, 0, 6, 42, 0, 0, 0, 0, 12, 45, 0, 0, 0, 0, 6, 33, 0, 0, 0, 0, 0, 40, 0, 0, 0, 0, 1},
			{0, 0, 0, 4, 0, 1, 0, 3, 1, 2, 0, 1, 1, 0, 1, 5, 8, 1, 6, 9, 4, 11, 7, 14, 10, 8, 10, 0, 3, 11, 16, 9, 0, 11, 17, 15, 16, 22, 7, 0, 22, 10, 25, 1, 0, 1, 0, 1, 0, 0, 0, 12, 7, 1, 13, 0, 0, 1, 0, 0},
			{2, 2, 7, 0, 1, 0, 3, 9, 2, 3, 1, 1, 3, 15, 21, 0, 0, 0, 0, 16, 0, 8, 3, 2, 0, 16, 11, 0, 0, 0, 0, 18, 2, 29, 16, 0, 0, 1, 32, 6, 0, 0, 0, 3, 37, 9, 0, 0, 0, 0, 0, 31, 7, 0, 0, 0, 1, 0, 0, 0},
			{2, 2, 0, 2, 3, 3, 6, 1, 4, 5, 2, 3, 12, 7, 8, 0, 0, 3, 3, 7, 12, 11, 0, 1, 11, 2, 15, 0, 0, 0, 0, 13, 12, 15, 19, 0, 11, 0, 16, 0, 0, 0, 0, 15, 13, 23, 0, 0, 9, 0, 0, 27, 0, 0, 0, 0, 12, 0, 0, 0},
			{0, 1, 0, 0, 0, 0, 2, 0, 0, 3, 0, 2, 1, 6, 0, 0, 3, 1, 7, 3, 0, 4, 7, 7, 6, 9, 1, 3, 10, 7, 4, 11, 0, 10, 6, 6, 9, 5, 5, 5, 9, 17, 12, 0, 0, 6, 14, 1, 6, 4, 1, 16, 3, 4, 1, 19, 15, 22, 11, 0},
			{0, 1, 1, 0, 0, 0, 2, 0, 0, 1, 1, 4, 0, 5, 1, 0, 8, 5, 6, 0, 0, 6, 4, 4, 12, 6, 0, 7, 11, 9, 7, 3, 0, 9, 10, 9, 13, 4, 0, 10, 5, 9, 11, 2, 3, 12, 11, 0, 17, 0, 0, 7, 0, 5, 6, 19, 12, 10, 15, 2},
			{0, 1, 0, 0, 0, 2, 0, 0, 1, 0, 1, 0, 9, 2, 8, 7, 9, 9, 1, 0, 3, 1, 5, 4, 6, 6, 0, 13, 3, 8, 10, 0, 0, 7, 10, 13, 15, 7, 0, 10, 10, 7, 8, 7, 10, 6, 12, 2, 1, 0, 0, 0, 6, 16, 14, 4, 0, 10, 18, 1},
			{3, 3, 1, 3, 2, 2, 0, 0, 1, 0, 0, 0, 4, 4, 5, 7, 4, 4, 1, 3, 2, 2, 2, 1, 9, 11, 8, 16, 3, 5, 7, 15, 17, 2, 9, 19, 8, 13, 1, 0, 13, 10, 13, 12, 0, 9, 7, 3, 0, 0, 0, 0, 3, 11, 10, 0, 0, 9, 7, 0}};
	private int whichOne;
	private Bitmap sampleBitmap;
	private Bitmap blankBitmap;

	private int superNumber;
	private int subNumber;
	private int typeBefore=0;
	private static final int TOTAL_POINTS_PLANE=500;
	private static final int POINTS_APPLE=200;
	private static final int POINTS_GOOSE=200;
	private class MatchFlag
	{
		public int matchType;
		public int matchIndex;
	}

	private Handler backHandler;
	private Handler mainHandler;
	private HandlerThread thread;
	private boolean background_flag=false;
	private boolean runnable_flag=false;

	private ImageButton delete;
	private ImageButton pen;
	private ImageButton eraser;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		doodleView=(DoodleView) findViewById(R.id.doodleView);
		iv=(ImageView) findViewById(R.id.iv_after);
		setcolortemp=doodleView.paintColor;
		delete=(ImageButton) findViewById(R.id.delete);
		pen=(ImageButton) findViewById(R.id.pen);
		eraser=(ImageButton) findViewById(R.id.eraser);
		pen.setEnabled(false);
		eraser.setEnabled(true);

		thread=new HandlerThread("BackgroundThread");
		thread.start();
		backHandler=new Handler(thread.getLooper())
		{
			@Override
			public void handleMessage(Message msg)
			{
				if(background_flag==true)
				{
					Bitmap bitmap=doodleView.getBitmap();
					int points=shapeContext(bitmap,SC);//注意，这里是边界点数，不是所有点数，愿意想返回所有点数
					MatchFlag match=useShapeContext(SC,points,typeBefore);
					typeBefore=match.matchType;
					System.out.println("类型"+match.matchType);
					try
					{
						Thread.sleep(100);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					Message mainMsg=mainHandler.obtainMessage();
					mainMsg.arg1=match.matchType;
					mainMsg.arg2=match.matchIndex;
					mainHandler.sendMessage(mainMsg);
				}
			}
		};

		mainHandler=new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				BitmapFactory.Options opts=new Options();
				if(background_flag==true)
				{
					if(whichOne==31)
					{
						switch(msg.arg1)
						{
							case 1:
								switch(msg.arg2)
								{
									case 1:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane12, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 2:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane13, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 3:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane14, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 4:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane15, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 5:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane16, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 6:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane17, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 7:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane18, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 8:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane18, opts);
										iv.setImageBitmap(sampleBitmap);break;
									default:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.blank, opts);
										iv.setImageBitmap(sampleBitmap);break;
								}
								break;
							case 2:
								switch(msg.arg2)
								{
									case 1:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane22, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 2:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane23, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 3:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane24, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 4:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane25, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 5:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane26, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 6:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane27, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 7:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane28, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 8:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane28, opts);
										iv.setImageBitmap(sampleBitmap);break;
									default:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.blank, opts);
										iv.setImageBitmap(sampleBitmap);break;
								}
								break;
							case 3:
								switch(msg.arg2)
								{
									case 1:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane32, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 2:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane33, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 3:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane34, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 4:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane35, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 5:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane36, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 6:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane37, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 7:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane38, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 8:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane38, opts);
										iv.setImageBitmap(sampleBitmap);break;
									default:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.blank, opts);
										iv.setImageBitmap(sampleBitmap);break;
								}
								break;
							case 4:
								switch(msg.arg2)
								{
									case 1:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane42, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 2:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane43, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 3:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane44, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 4:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane45, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 5:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane46, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 6:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane47, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 7:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane48, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 8:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane48, opts);
										iv.setImageBitmap(sampleBitmap);break;
									default:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.blank, opts);
										iv.setImageBitmap(sampleBitmap);break;
								}
								break;
							case 5:
								switch(msg.arg2)
								{
									case 1:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane52, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 2:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane53, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 3:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane54, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 4:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane55, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 5:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane56, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 6:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane57, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 7:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane58, opts);
										iv.setImageBitmap(sampleBitmap);break;
									case 8:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.plane58, opts);
										iv.setImageBitmap(sampleBitmap);break;
									default:
										sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.blank, opts);
										iv.setImageBitmap(sampleBitmap);break;
								}
								break;
							default:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.blank, opts);
								iv.setImageBitmap(sampleBitmap);
								break;
						}
					}
					else if(whichOne==11)
					{
						switch(msg.arg2)
						{
							case 0:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.apple1, opts);
								iv.setImageBitmap(sampleBitmap);break;
							case 1:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.apple2, opts);
								iv.setImageBitmap(sampleBitmap);break;
							case 2:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.apple3, opts);
								iv.setImageBitmap(sampleBitmap);break;
							case 3:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.apple4, opts);
								iv.setImageBitmap(sampleBitmap);break;
							case 4:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.apple5, opts);
								iv.setImageBitmap(sampleBitmap);break;
							case 5:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.apple6, opts);
								iv.setImageBitmap(sampleBitmap);break;
							case 6:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.apple6, opts);
								iv.setImageBitmap(sampleBitmap);break;
							default:break;
						}
					}
					else if(whichOne==21)
					{
						switch(msg.arg2)
						{
							case 0:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.goose1, opts);
								iv.setImageBitmap(sampleBitmap);break;
							case 1:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.goose2, opts);
								iv.setImageBitmap(sampleBitmap);break;
							case 2:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.goose3, opts);
								iv.setImageBitmap(sampleBitmap);break;
							case 3:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.goose4, opts);
								iv.setImageBitmap(sampleBitmap);break;
							case 4:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.goose5, opts);
								iv.setImageBitmap(sampleBitmap);break;
							case 5:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.goose6, opts);
								iv.setImageBitmap(sampleBitmap);break;
							case 6:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.goose7, opts);
								iv.setImageBitmap(sampleBitmap);break;
							case 7:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.goose8, opts);
								iv.setImageBitmap(sampleBitmap);break;
							case 8:
								sampleBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.goose8, opts);
								iv.setImageBitmap(sampleBitmap);break;
							default:break;
						}
					}
					Message backMsg=backHandler.obtainMessage();
					backHandler.sendMessage(backMsg);
				}
			}
		};
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		background_flag=false;
		//    	delete.setText("开启");
		if(runnable_flag==true)
		{
			iv.setImageBitmap(blankBitmap);
			delete.setImageResource(R.drawable.open);
		}
	}

	Runnable scRunnable=new Runnable()
	{

		@Override
		public void run()
		{
			blankBitmap=Bitmap.createBitmap(doodleView.getWidth(), doodleView.getHeight(), Config.ARGB_8888);
			if(whichOne==31)
			{
				for(int n=0;n<superNumber;n++)
				{
					for (int i=0;i<subNumber;i++)
					{
						float sum=0;//if the type of sum is int ,planeSC will be all zero
						for(int j=0;j<60;j++)sum=sum+planesc[n][i][j];
						for(int k=0;k<60;k++)planeSC[n][i][k]=planesc[n][i][k]/sum;
					}
				}
				Message msg=mainHandler.obtainMessage();
				msg.arg1=0;    //arg1 is type,arg2 is index
				msg.arg2=0;
				mainHandler.sendMessage(msg);
			}
			else if(whichOne==11)
			{
				for (int i=0;i<subNumber;i++)
				{
					float sum=0;//if the type of sum is int ,planeSC will be all zero
					for(int j=0;j<60;j++)sum=sum+applesc[i][j];
					for(int k=0;k<60;k++)appleSC[i][k]=applesc[i][k]/sum;
				}
				Message msg=mainHandler.obtainMessage();
				msg.arg2=0;
				mainHandler.sendMessage(msg);
			}
			else if(whichOne==21)
			{
				for (int i=0;i<subNumber;i++)
				{
					float sum=0;//if the type of sum is int ,planeSC will be all zero
					for(int j=0;j<60;j++)sum=sum+goosesc[i][j];
					for(int k=0;k<60;k++)gooseSC[i][k]=goosesc[i][k]/sum;
				}
				Message msg=mainHandler.obtainMessage();
				msg.arg2=0;
				mainHandler.sendMessage(msg);
			}

		}

	};



	public void pen(View view)
	{
		doodleView.setPaintColor(doodleView.paintColor);
		doodleView.setPaintWidth(doodleView.paintWidth);
		doodleView.setPaintmode_pen();
		pen.setEnabled(false);
		eraser.setEnabled(true);
	}
	public void eraser(View view)
	{
		doodleView.setPaintmode_eraser();
		doodleView.setPaintColor(Color.WHITE);
		pen.setEnabled(true);
		eraser.setEnabled(false);
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
		doodleView.save();
//		Intent intent=new Intent();
//		intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
//		intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
//		sendBroadcast(intent);
		//以上代码在4.4以上的系统会强退
		//文件系统会更新，但相册不能，相册只有才重启后才能更新
		MediaScannerConnection.scanFile(this, new String[]{
				Environment.getExternalStorageDirectory().getAbsolutePath()},
				null, new MediaScannerConnection.OnScanCompletedListener()
				{
					public void onScanCompleted(String path, Uri uri)
					{

					}
				});
	}

	public void select(View view)
	{
		Intent intent=new Intent();
		intent.setClassName(this, "com.pre.SelectScreenActivity");
		startActivityForResult(intent, 1);
	}
	@Override
	public void onActivityResult(int requestCode,int  resultCode,Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(data!=null)
		{
			//    		delete.setText("关闭");
			delete.setImageResource(R.drawable.close);

			doodleView.bitmapClear();
			String str=data.getData().toString();
			if(str.equals("plane"))
			{
				whichOne=31;
				superNumber=5;
				subNumber=8;
				background_flag=true;
				runnable_flag=true;
				backHandler.post(scRunnable);
			}
			else if(str.equals("apple"))
			{
				whichOne=11;
				superNumber=1;
				subNumber=6;
				background_flag=true;
				runnable_flag=true;
				backHandler.post(scRunnable);
			}
			else if(str.equals("goose"))
			{
				whichOne=21;
				superNumber=1;
				subNumber=8;
				background_flag=true;
				runnable_flag=true;
				backHandler.post(scRunnable);
			}
		}
	}


	public void delete(View view)
	{
		if(background_flag==true && runnable_flag==true)
		{
			background_flag=false;
			iv.setImageBitmap(blankBitmap);
			//        	delete.setText("开启");
			delete.setImageResource(R.drawable.open);
		}
		else if(background_flag==false && runnable_flag==true)
		{
			background_flag=true;
			Message bitmapMsg=backHandler.obtainMessage();
			backHandler.sendMessage(bitmapMsg);
			//    		delete.setText("关闭");
			delete.setImageResource(R.drawable.close);
		}

	}


	//选择颜色和宽度
	private void showColorDialog()
	{
		currentDialog = new Dialog(this);
		currentDialog.setContentView(R.layout.color_dialog);
		currentDialog.setTitle("颜色和线宽");
		currentDialog.setCancelable(true);
		SeekBar widthSeekBar = (SeekBar) currentDialog.findViewById(R.id.widthSeekBar);
		widthSeekBar.setOnSeekBarChangeListener(widthSeekBarChanged);
		widthSeekBar.setProgress(doodleView.paintWidth);
		setcolortemp=doodleView.paintColor;

		Button setButton = (Button) currentDialog.findViewById(R.id.setColorButton);
		setButton.setOnClickListener(setButtonListener);
		//设置固定颜色选取色条并监听
		Button setblack=(Button)currentDialog.findViewById(R.id.setblack);
		Button setgrey=(Button)currentDialog.findViewById(R.id.setgrey);
		Button setpurple=(Button)currentDialog.findViewById(R.id.setpurple);
		Button setpeach=(Button)currentDialog.findViewById(R.id.setpeach);
		Button setblue=(Button)currentDialog.findViewById(R.id.setblue);
		Button setred=(Button)currentDialog.findViewById(R.id.setred);
		Button setorange=(Button)currentDialog.findViewById(R.id.setorange);
		Button setyellow=(Button)currentDialog.findViewById(R.id.setyellow);
		Button setgreen=(Button)currentDialog.findViewById(R.id.setgreen);
		Button setcyan=(Button)currentDialog.findViewById(R.id.setcyan);
		setblack.setOnClickListener(setcolorListener);
		setgrey.setOnClickListener(setcolorListener);
		setpurple.setOnClickListener(setcolorListener);
		setpeach.setOnClickListener(setcolorListener);
		setblue.setOnClickListener(setcolorListener);
		setred.setOnClickListener(setcolorListener);
		setorange.setOnClickListener(setcolorListener);
		setyellow.setOnClickListener(setcolorListener);
		setgreen.setOnClickListener(setcolorListener);
		setcyan.setOnClickListener(setcolorListener);
		dialogIsVisible.set(true);
		currentDialog.show();
	}
	private OnClickListener setcolorListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			ImageView color_width = (ImageView) currentDialog.findViewById(R.id.color_width);
			SeekBar widthSeekBar = (SeekBar) currentDialog.findViewById(R.id.widthSeekBar);
			Bitmap bitmap = Bitmap.createBitmap(400, 100, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			switch(v.getId())
			{
				case R.id.setblack:
					setcolortemp=Color.argb(255, 0, 0, 0);break;
				case R.id.setgrey:
					setcolortemp=Color.argb(255, 128, 128, 128);break;
				case R.id.setpurple:
					setcolortemp=Color.argb(255, 128, 0, 128);break;
				case R.id.setpeach:
					setcolortemp=Color.argb(255, 255, 218, 185);break;
				case R.id.setblue:
					setcolortemp=Color.argb(255, 0, 0, 205);break;
				case R.id.setred:
					setcolortemp=Color.argb(255,255, 0, 0);break;
				case R.id.setorange:
					setcolortemp=Color.argb(255,255,165, 0);break;
				case R.id.setyellow:
					setcolortemp=Color.argb(255,255, 255, 0);break;
				case R.id.setgreen:
					setcolortemp=Color.argb(255,0, 128, 0);break;
				case R.id.setcyan:
					setcolortemp=Color.argb(255,0, 255, 255);break;
			}
			Paint p = new Paint();
			p.setColor(setcolortemp);
			p.setStrokeCap(Paint.Cap.ROUND);
			p.setStrokeWidth(widthSeekBar.getProgress());
			bitmap.eraseColor(Color.WHITE);
			canvas.drawLine(30, 50, 370, 50, p);
			color_width.setImageBitmap(bitmap);
		}
	};

	private OnSeekBarChangeListener widthSeekBarChanged = new OnSeekBarChangeListener()
	{
		Bitmap bitmap = Bitmap.createBitmap(400, 100, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
		{
			ImageView color_width = (ImageView) currentDialog.findViewById(R.id.color_width);
			Paint p = new Paint();
			p.setColor(setcolortemp);
			p.setStrokeCap(Paint.Cap.ROUND);
			p.setStrokeWidth(progress);
			bitmap.eraseColor(Color.WHITE);
			canvas.drawLine(30, 50, 370, 50, p);
			color_width.setImageBitmap(bitmap);
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar)
		{}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar)
		{}
	};
	// OnClickListener for the color dialog's Set Color Button
	private OnClickListener setButtonListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			doodleView.setPaintColor(setcolortemp);
			SeekBar widthSeekBar = (SeekBar) currentDialog.findViewById(R.id.widthSeekBar);
			doodleView.setPaintWidth(widthSeekBar.getProgress());
			doodleView.paintWidth=widthSeekBar.getProgress();
			doodleView.paintColor=setcolortemp;
			dialogIsVisible.set(false); // dialog is not on the screen
			currentDialog.dismiss(); // hide the dialog
			currentDialog = null; // dialog no longer needed
		}
	};



	/////////////////////////////////////////////////
	/////////////////////////////////////////////////
	public int shapeContext(Bitmap img,float []SC)
	{
		int width=img.getWidth();
		int height=img.getHeight();
		int []pixels = new int[width * height]; //storage original pixels
		int []pixelstemp = new int[width * height];  //include all the boundary pixels
		int []pixelstemp2 = new int[width * height]; //include reduced boundary pixels
		byte []flag = new byte[width * height];  //the sign of tract
		int []number=new int[1];number[0]=0;     //the number of all the boundary pixels
		int []number2=new int[1];number2[0]=0;   //the number of reduced boundary pixels
		int array[][] = new int[2][width * height]; //storage the coordinates of all the boundary pixels
		int array2[][] = new int[2][600];//storage the coordinates of reduced boundary pixels
		int schistogram[]=new int[60];   //storage the shape context histogram of the barycenter
		img.getPixels(pixels, 0, width, 0, 0, width, height);
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				int grey = pixels[width * i + j];
				int red = ((grey  & 0x00FF0000 ) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);
				grey = (int)((float) red * 0.3 + (float)green * 0.59 + (float)blue * 0.11);
				pixels[width * i + j] = grey;
			}
		}
		sobel(pixels,pixelstemp,height,width);
		trackBoundary(pixelstemp,flag,array,height,width,number);
		selectPoints(array,array2,pixelstemp2,height,width,number[0],number2);
		calculateShapecontext(array,array2,schistogram,number,number2);
		float sum=0;//归一化
		for(int i=0;i<60;i++)
		{
			sum=sum+schistogram[i];
		}
		for(int i=0;i<60;i++)
		{
			SC[i]=schistogram[i]/sum;
		}
		return number[0];
		//           pixels=null;pixelstemp=null;pixelstemp2=null;flag=null;number=null;number2=null;
		//           array=null;array2=null;schistogram=null;
	}

	private void getGrayData(int[] ch,int h,int w)
	{
		for(int i = 0; i < h; i++)
		{
			for(int j = 0; j < w; j++)
			{
				int grey = ch[w*i+j];
				int red = ((grey  & 0x00FF0000 ) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);
				grey = (int)((float) red * 0.3 + (float)green * 0.59 + (float)blue * 0.11);
				ch[w * i + j] = grey;
			}
		}
	}

	private void sobel(int[] ch,int []sh,int h, int w)//引用传递
	{
		for(int i=1; i<h-1; i++)
		{
			for(int j=1; j<w-1; j++)
			{
				int gradientX=Math.abs((ch[w*(i+1)+j-1]+2*ch[w*(i+1)+j]+ch[w*(i+1)+j+1])-(ch[w*(i-1)+j-1]+2*ch[w*(i-1)+j]+ch[w*(i-1)+j+1]));
				int gradientY=Math.abs((ch[w*(i-1)+j+1]+2*ch[w*i+j+1]+ch[w*(i+1)+j+1])-(ch[w*(i-1)+j-1]+2*ch[w*i+j-1]+ch[w*(i+1)+j-1]));
				int gradient=gradientX+gradientY;
				if(gradient<125)gradient=0;else gradient=255;   //自己选了一个阈值125
				sh[w*i+j]=gradient;
			}
		}
	}

	private void normalizeSC(float []SC,int []sc)
	{
		float sum=0;
		for(int i=0;i<60;i++)sum=sum+sc[i];
		for(int i=0;i<60;i++)SC[i]=sc[i]/sum;
	}

	private void trackBoundary(int[] ch,byte[] f,int[][]a,int h,int w,int[] number)//3方向是否有待修改
	{
		int i=0,j=0;int flag_i,flag_j;
		while(i<h&&j<w)
		{
			while(!((ch[w*i+j]==255)&&(f[w*i+j]==0)))
			{
				j++;
				if(j==w){j=0;i++;}if(i==h)break;
			}
			if(i==h)break;
			flag_i=i;flag_j=j;
			f[w*i+j]=1;a[0][number[0]]=i;a[1][number[0]]=j;number[0]=number[0]+1;
			while((ch[w*i+j+1]==255)&&(f[w*i+j+1]==0) || (ch[w*(i+1)+j]==255)&&(f[w*(i+1)+j]==0) || (ch[w*i+j-1]==255)&&(f[w*i+j-1]==0))
			{
				if(ch[w*i+j+1]==255)
				{
					j=j+1;if(j==w){j=0;i++;}if(i==h)break;
					f[w*i+j]=1;
					a[0][number[0]]=i;a[1][number[0]]=j;number[0]=number[0]+1;
				}
				else if(ch[w*(i+1)+j]==255)
				{
					i=i+1;if(i==h)break;
					f[w*i+j]=1;
					a[0][number[0]]=i;a[1][number[0]]=j;number[0]=number[0]+1;
				}
				else
				{
					j=j-1;if(j<0){j=w-1;i--;}if(i<0)break;
					f[w*i+j]=1;
					a[0][number[0]]=i;a[1][number[0]]=j;number[0]=number[0]+1;
				}
			}
			i=flag_i;j=flag_j+1;if(j==w){j=0;i++;}if(i==h)break;
		}
	}

	private void selectPoints(int[][] a,int[][] a2,int[] sh,int h,int w,int index,int[] number2)
	{
		int step=1;
		if(index>300)step=(int)index/300;
		for(int i=0;i<index;i=i+step)
		{
			int x=a[0][i],y=a[1][i];
			sh[w*x+y]=255;
			a2[0][number2[0]]=x;a2[1][number2[0]]=y;number2[0]=number2[0]+1;
		}
	}

	private void calculateShapecontext(int[][] a,int[][] a2,int[] sch,int[] number,int[] number2)//直角坐标系中建立，没有利用对数极坐标
	{
		int x=0,y=0;
		float reP[][]=new float[2][600];//relative position radius and angle
		float R=0,r=0;
		for(int i=0;i<number[0];i++)
		{
			x=x+a[0][i];y=y+a[1][i];
		}
		System.out.println("个数"+number[0]);
		if(number[0]!=0){x=(int)x/number[0];y=(int)y/number[0];}
		for(int i=0;i<number2[0];i++)
		{
			reP[0][i]=(float)Math.sqrt((a2[0][i]-x)*(a2[0][i]-x)+(a2[1][i]-y)*(a2[1][i]-y));
			reP[1][i]=(float)Math.toDegrees(Math.atan2(a2[0][i]-x,a2[1][i]-y));
			if(reP[0][i]>R)R=reP[0][i];
			if(a2[0][i]<x && a2[1][i]>y)reP[1][i]=0-reP[1][i];
			else if(a2[0][i]>x && a2[1][i]<y)reP[1][i]=360-reP[1][i];
			else reP[1][i]=180-reP[1][i];
		}
		r=R/5;
		for(int i=0;i<number2[0];i++)
		{
			if(reP[0][i]<r)
			{
				if(reP[1][i]<30){sch[0]=sch[0]+1;}
				else if(reP[1][i]>30 && reP[1][i]<60){sch[1]=sch[1]+1;}
				else if(reP[1][i]>60 && reP[1][i]<90){sch[2]=sch[2]+1;}
				else if(reP[1][i]>90 && reP[1][i]<120){sch[3]=sch[3]+1;}
				else if(reP[1][i]>120 && reP[1][i]<150){sch[4]=sch[4]+1;}
				else if(reP[1][i]>150 && reP[1][i]<180){sch[5]=sch[5]+1;}
				else if(reP[1][i]>180 && reP[1][i]<210){sch[6]=sch[6]+1;}
				else if(reP[1][i]>210 && reP[1][i]<240){sch[7]=sch[7]+1;}
				else if(reP[1][i]>240 && reP[1][i]<270){sch[8]=sch[8]+1;}
				else if(reP[1][i]>270 && reP[1][i]<300){sch[9]=sch[9]+1;}
				else if(reP[1][i]>300 && reP[1][i]<330){sch[10]=sch[10]+1;}
				else{sch[11]=sch[11]+1;}
			}
			else if(reP[0][i]>r && reP[0][i]<2*r)
			{
				if(reP[1][i]<30){sch[12]=sch[12]+1;}
				else if(reP[1][i]>30 && reP[1][i]<60){sch[13]=sch[13]+1;}
				else if(reP[1][i]>60 && reP[1][i]<90){sch[14]=sch[14]+1;}
				else if(reP[1][i]>90 && reP[1][i]<120){sch[15]=sch[15]+1;}
				else if(reP[1][i]>120 && reP[1][i]<150){sch[16]=sch[16]+1;}
				else if(reP[1][i]>150 && reP[1][i]<180){sch[17]=sch[17]+1;}
				else if(reP[1][i]>180 && reP[1][i]<210){sch[18]=sch[18]+1;}
				else if(reP[1][i]>210 && reP[1][i]<240){sch[19]=sch[19]+1;}
				else if(reP[1][i]>240 && reP[1][i]<270){sch[20]=sch[20]+1;}
				else if(reP[1][i]>270 && reP[1][i]<300){sch[21]=sch[21]+1;}
				else if(reP[1][i]>300 && reP[1][i]<330){sch[22]=sch[22]+1;}
				else{sch[23]=sch[23]+1;}
			}
			else if(reP[0][i]>2*r && reP[0][i]<3*r)
			{
				if(reP[1][i]<30){sch[24]=sch[24]+1;}
				else if(reP[1][i]>30 && reP[1][i]<60){sch[25]=sch[25]+1;}
				else if(reP[1][i]>60 && reP[1][i]<90){sch[26]=sch[26]+1;}
				else if(reP[1][i]>90 && reP[1][i]<120){sch[27]=sch[27]+1;}
				else if(reP[1][i]>120 && reP[1][i]<150){sch[28]=sch[28]+1;}
				else if(reP[1][i]>150 && reP[1][i]<180){sch[29]=sch[29]+1;}
				else if(reP[1][i]>180 && reP[1][i]<210){sch[30]=sch[30]+1;}
				else if(reP[1][i]>210 && reP[1][i]<240){sch[31]=sch[31]+1;}
				else if(reP[1][i]>240 && reP[1][i]<270){sch[32]=sch[32]+1;}
				else if(reP[1][i]>270 && reP[1][i]<300){sch[33]=sch[33]+1;}
				else if(reP[1][i]>300 && reP[1][i]<330){sch[34]=sch[34]+1;}
				else{sch[35]=sch[35]+1;}
			}
			else if(reP[0][i]>3*r && reP[0][i]<4*r)
			{
				if(reP[1][i]<30){sch[36]=sch[36]+1;}
				else if(reP[1][i]>30 && reP[1][i]<60){sch[37]=sch[37]+1;}
				else if(reP[1][i]>60 && reP[1][i]<90){sch[38]=sch[38]+1;}
				else if(reP[1][i]>90 && reP[1][i]<120){sch[39]=sch[39]+1;}
				else if(reP[1][i]>120 && reP[1][i]<150){sch[40]=sch[40]+1;}
				else if(reP[1][i]>150 && reP[1][i]<180){sch[41]=sch[41]+1;}
				else if(reP[1][i]>180 && reP[1][i]<210){sch[42]=sch[42]+1;}
				else if(reP[1][i]>210 && reP[1][i]<240){sch[43]=sch[43]+1;}
				else if(reP[1][i]>240 && reP[1][i]<270){sch[44]=sch[44]+1;}
				else if(reP[1][i]>270 && reP[1][i]<300){sch[45]=sch[45]+1;}
				else if(reP[1][i]>300 && reP[1][i]<330){sch[46]=sch[46]+1;}
				else{sch[47]=sch[47]+1;}
			}
			else
			{
				if(reP[1][i]<30){sch[48]=sch[48]+1;}
				else if(reP[1][i]>30 && reP[1][i]<60){sch[49]=sch[49]+1;}
				else if(reP[1][i]>60 && reP[1][i]<90){sch[50]=sch[50]+1;}
				else if(reP[1][i]>90 && reP[1][i]<120){sch[51]=sch[51]+1;}
				else if(reP[1][i]>120 && reP[1][i]<150){sch[52]=sch[52]+1;}
				else if(reP[1][i]>150 && reP[1][i]<180){sch[53]=sch[53]+1;}
				else if(reP[1][i]>180 && reP[1][i]<210){sch[54]=sch[54]+1;}
				else if(reP[1][i]>210 && reP[1][i]<240){sch[55]=sch[55]+1;}
				else if(reP[1][i]>240 && reP[1][i]<270){sch[56]=sch[56]+1;}
				else if(reP[1][i]>270 && reP[1][i]<300){sch[57]=sch[57]+1;}
				else if(reP[1][i]>300 && reP[1][i]<330){sch[58]=sch[58]+1;}
				else{sch[59]=sch[59]+1;}
			}
		}
	}

	////////////////////////////////////////////
	////////////////////////////////////////////
	public MatchFlag useShapeContext(float []SC,int points,int type)
	{
		MatchFlag matchFlag =new MatchFlag();
		if(whichOne==31)
		{
			if(points<=0)
			{
				matchFlag.matchType=0;
				matchFlag.matchIndex=0;
			}
			else if(points<=TOTAL_POINTS_PLANE || type==0)
			{
				int flag=0;float []match=new float[superNumber];
				for(int i=0;i<superNumber;i++)
				{
					for(int j=0;j<60;j++)
					{
						if(planeSC[i][0][j]+SC[j]!=0)match[i]=match[i]+(planeSC[i][0][j]-SC[j])*(planeSC[i][0][j]-SC[j])/(planeSC[i][0][j]+SC[j]);
					}
					match[i]=match[i]/2;
				}
				for(int i=1;i<superNumber;i++)
				{
					if(match[flag]>match[i])flag=i;
				}
				matchFlag.matchType=flag+1;
				matchFlag.matchIndex=1;
			}
			else
			{
				int flag=0;float []match=new float[subNumber];
				for(int i=0;i<subNumber;i++)
				{
					for(int j=0;j<60;j++)
					{
						if(planeSC[type-1][i][j]+SC[j]!=0)match[i]=match[i]+(planeSC[type-1][i][j]-SC[j])*(planeSC[type-1][i][j]-SC[j])/(planeSC[type-1][i][j]+SC[j]);
					}
					match[i]=match[i]/2;
				}
				for(int i=1;i<subNumber;i++)
				{
					if(match[flag]>match[i])flag=i;
				}
				matchFlag.matchIndex=flag+1;
				matchFlag.matchType=type;
			}
		}
		else if(whichOne==11)
		{
			int flag=0;float []match=new float[subNumber];
			for(int i=0;i<subNumber;i++)
			{
				for(int j=0;j<60;j++)
				{
					if(appleSC[i][j]+SC[j]!=0)match[i]=match[i]+(appleSC[i][j]-SC[j])*(appleSC[i][j]-SC[j])/(appleSC[i][j]+SC[j]);
				}
				match[i]=match[i]/2;
			}
			for(int i=1;i<subNumber;i++)
			{
				if(match[flag]>match[i])flag=i;
			}
			if(flag==0 && points<=POINTS_APPLE)flag=-1;
			matchFlag.matchIndex=flag+1;
		}
		else if(whichOne==21)
		{
			int flag=0;float []match=new float[subNumber];
			for(int i=0;i<subNumber;i++)
			{
				for(int j=0;j<60;j++)
				{
					if(gooseSC[i][j]+SC[j]!=0)match[i]=match[i]+(gooseSC[i][j]-SC[j])*(gooseSC[i][j]-SC[j])/(gooseSC[i][j]+SC[j]);
				}
				match[i]=match[i]/2;
			}
			for(int i=1;i<subNumber;i++)
			{
				if(match[flag]>match[i])flag=i;
			}
			if(flag==0 && points<=POINTS_GOOSE)flag=-1;
			matchFlag.matchIndex=flag+1;
		}
		return matchFlag;

		//		for(int i=0;i<bitmapNumber;i++)
		//		{
		//			System.out.println("匹配系数"+match[i]);
		//		}

	}

	public void sctest(View view)
	{
		Intent intent = new Intent(this, TestSCActivity.class);
		startActivity(intent);
	}
}
