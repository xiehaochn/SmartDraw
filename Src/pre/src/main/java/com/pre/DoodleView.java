package com.pre;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DoodleView extends View
{
	public Bitmap bitmap;//画布
	private Canvas bitmapCanvas;//画手
	private Paint bitmapPaint;//画笔
	private Paint outputPaint; //use it to output bitmap
	private Paint screenPaint;
	private static final float TOUCH_TOLERANCE = 4;
	private float mX,mY;
	private Path bitmapPath;
	private static List<DrawPath> savePath;
	private static List<DrawPath> deletePath;
	private DrawPath dp;
	private class DrawPath
	{
		public float x;
		public float y;
		public Path listPath;
		public boolean listModel;
		public int listColor;
		public int listWidth;

	}
	public int paintColor=Color.BLACK;
	public int paintWidth=10;
	public boolean paintModel=true;
	public DoodleView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		screenPaint=new Paint();
		//initialize the bitmapPaint
		bitmapPaint=new Paint();
		bitmapPaint.setAntiAlias(true);
		bitmapPaint.setStrokeWidth(paintWidth);
		bitmapPaint.setColor(Color.BLACK);
		bitmapPaint.setStyle(Paint.Style.STROKE);
		bitmapPaint.setStrokeCap(Paint.Cap.ROUND);
		bitmapPaint.setStrokeJoin(Paint.Join.ROUND);
		bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
		savePath=new ArrayList<DrawPath>();
		deletePath=new ArrayList<DrawPath>();

		outputPaint=new Paint();
		outputPaint.setAntiAlias(true);
		outputPaint.setStyle(Paint.Style.STROKE);
		outputPaint.setStrokeCap(Paint.Cap.ROUND);
		outputPaint.setStrokeJoin(Paint.Join.ROUND);
		outputPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
	}

	@Override
	public void onSizeChanged(int w,int h,int oldW,int oldHE)
	{
		bitmap=Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		bitmapCanvas=new Canvas(bitmap);
		bitmap.eraseColor(Color.TRANSPARENT);
	}

	///////////////////////////////////
	///////////////////////////////////

	public void bitmapClear()
	{
		bitmap.eraseColor(Color.TRANSPARENT);
		deletePath.clear();
		savePath.clear();
		invalidate();
	}
	public void setPaintColor(int color){bitmapPaint.setColor(color);}
	public void setPaintWidth(int width){bitmapPaint.setStrokeWidth(width);}
	public int getPaintColor(){return bitmapPaint.getColor();}
	public int getPaintWidth(){return (int) bitmapPaint.getStrokeWidth();}
	public void setPaintmode_eraser()
	{
		bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
		paintModel=false;
	}
	public void setPaintmode_pen()
	{
		bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
		paintModel=true;
	}


	///////////////////////////////////////
	///////////////////////////////////////
	@Override
	public void onDraw(Canvas canvas)
	{
		canvas.drawBitmap(bitmap, 0, 0, screenPaint);
		if(bitmapPath!=null)canvas.drawPath(bitmapPath, bitmapPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		float x=event.getX();
		float y=event.getY();
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				bitmapPath=new Path();
				dp=new DrawPath();
				dp.listPath=bitmapPath;
				//		dp.listPaint=bitmapPaint;//这里dp.listPaint应该是引用了bitmapPaint的地址，而不是新建另一个了画笔
				dp.listModel=paintModel;  //也就是说dp.listPaint和bitmapPaint是同一个笔,所以可以省略dp.listPaint
				dp.listColor=paintColor;  //而去记录当前画笔的三个信息
				dp.listWidth=paintWidth;
				dp.x=x;
				dp.y=y;
				deletePath.clear();
				touchStart(x,y);
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				touchMove(x,y);
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				touchEnd();
				invalidate();
				break;
		}
		return true;
	}

	private void touchStart(float x,float y)
	{
		bitmapCanvas.drawPoint(x, y, bitmapPaint);
		bitmapPath.moveTo(x, y);
		mX=x;mY=y;
		savePath.add(dp);
	}
	private void touchMove(float x,float y)
	{
		float dx=Math.abs(x-mX);
		float dy=Math.abs(y-mY);
		if(dx>=TOUCH_TOLERANCE||dy>=TOUCH_TOLERANCE)
		{
			bitmapPath.quadTo(mX,mY,(x+mX)/2,(y+mY)/2);
			mX=x;mY=y;
		}
		savePath.set(savePath.size()-1, dp);
	}
	private void touchEnd()
	{
		bitmapPath.lineTo(mX, mY);
		bitmapCanvas.drawPath(bitmapPath, bitmapPaint);//这条语句缺省，会显示绘画路径但松手后路径消失
		savePath.set(savePath.size()-1, dp);
		bitmapPath=null;
	}



	////////////////////////////////////////////
	////////////////////////////////////////////

	public void undo()
	{
		if(savePath!=null && savePath.size()>0)
		{
			deletePath.add(savePath.get(savePath.size()-1));
			savePath.remove(savePath.size()-1);
			redrawOnBitmap();
		}
	}
	public void redo()
	{
		if(deletePath!=null && deletePath.size()>0)
		{
			savePath.add(deletePath.get(deletePath.size()-1));
			deletePath.remove(deletePath.size()-1);
			redrawOnBitmap();
		}
	}
	private void redrawOnBitmap()
	{
		bitmap=Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		bitmapCanvas.setBitmap(bitmap);
		for(int i=0;i<savePath.size();i++)
		{
			DrawPath drawpath=savePath.get(i);
			if(drawpath.listModel==true)bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
			else bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
			bitmapPaint.setColor(drawpath.listColor);
			bitmapPaint.setStrokeWidth(drawpath.listWidth);
			bitmapCanvas.drawPoint(drawpath.x, drawpath.y, bitmapPaint);
			bitmapCanvas.drawPath(drawpath.listPath, bitmapPaint);
		}
		invalidate();
		if(paintModel==true)bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
		else bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
		bitmapPaint.setColor(paintColor);
		bitmapPaint.setStrokeWidth(paintWidth);
	}


	//////////////////////////////////
	//////////////////////////////////
	public void save()
	{
		Bitmap saveBitmap=Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		Canvas saveCanvas=new Canvas(saveBitmap);
		saveBitmap.eraseColor(Color.WHITE);
		for(int i=0;i<savePath.size();i++)
		{
			DrawPath drawpath=savePath.get(i);
			outputPaint.setColor(drawpath.listColor);
			outputPaint.setStrokeWidth(drawpath.listWidth);
			if(drawpath.listModel==false)outputPaint.setColor(Color.WHITE);
			saveCanvas.drawPoint(drawpath.x, drawpath.y, outputPaint);
			saveCanvas.drawPath(drawpath.listPath, outputPaint);
		}
		try
		{
			File file=new File(Environment.getExternalStorageDirectory(),"Works"+System.currentTimeMillis()+".jpg");
			FileOutputStream stream=new FileOutputStream(file);
			saveBitmap.compress(CompressFormat.JPEG, 100, stream);
			stream.close();
			Toast.makeText(getContext(), "保存图片成功",Toast.LENGTH_SHORT).show();
		}
		catch (Exception e)
		{
			Toast.makeText(getContext(), "保存图片失败", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}


	////////////////////////////////////////
	////////////////////////////////////////
	public Bitmap getBitmap()
	{
		Bitmap bitmap=Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas=new Canvas(bitmap);
		bitmap.eraseColor(Color.WHITE);
		for(int i=0;i<savePath.size();i++)
		{
			DrawPath drawpath=savePath.get(i);
			outputPaint.setColor(drawpath.listColor);
			outputPaint.setStrokeWidth(drawpath.listWidth);
			if(drawpath.listModel==false)outputPaint.setColor(Color.WHITE);
			canvas.drawPoint(drawpath.x, drawpath.y, outputPaint);
			canvas.drawPath(drawpath.listPath, outputPaint);
		}
		return bitmap;
	}
}
