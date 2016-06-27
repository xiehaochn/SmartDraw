package com.pre.jason;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class ShapeContextHandler extends Handler
{
    private ShapeContextCallback shapeContextCallback;

    public ShapeContextHandler(Looper looper)
    {
        super(looper);
    }

    @Override
    public void handleMessage(Message msg)
    {
        switch (msg.arg1)
        {
            case ImageShapeContext.CENTER_OF_BITMAP:
                if(shapeContextCallback!=null)
                {
                    shapeContextCallback.onCenterofBitmap();
                }
                break;
            case ImageShapeContext.CENTER_OF_MASS:
                if(shapeContextCallback!=null)
                {
                    shapeContextCallback.onCenterofMass();
                }
                break;
            default:
                break;
        }
    }

    public void setShapeContextCallback(ShapeContextCallback shapeContextCallback)
    {
        this.shapeContextCallback = shapeContextCallback;
    }

    public static interface ShapeContextCallback
    {
        void onCenterofBitmap();
        void onCenterofMass();
    }
}
