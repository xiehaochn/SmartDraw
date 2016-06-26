package com.pre.jason;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.util.List;

public abstract class HintUtils
{
    private ImageShapeContext isc;
    /**
     * constant CENTER_OF_BITMAP shapecontext of the bitmaps for hint
     */
    private float[][] scHints;
    /**
     * constant CENTER_OF_MASS shapecontext of the final bitmap for hint
     */
    private float[] scFinalHint;
    /**
     * the shapecontext of the bitmap get from doodleView
     */
    private float[] scDoodle;


    /**
     * the sub thread to run the time-consuming shapecontext algorithm
     */
    private HandlerThread subThread;
    /**
     * handle the message that contain bitmap
     * it let the shapecontext algorithm running in the sub thread
     */
    private SubHandler subHandler;
    /**
     * handle the message that contain hint index or score and so on.
     * the function that update the UI should run there
     */
    private MainHandler mainHandler;

    private static int INIT_SC = 0;
    private static int GET_SIMILARITY_CENTER_OF_BITMAP = 1;
    private static int GET_SCORE_CENTER_OF_MASS = 2;

    private static int INIT_COMPLETE = 0;
    private static int UPDATE_HINT = 1;
    private static int GET_SCORE = 2;

    private static int RUNNING = 0;
    private static int CANCELED = 1;
    private int mState;

    /**
     * initialize the {@link #subThread}, {@link #subHandler}, {@link #mainHandler},
     * {@link #isc}, {@link #scFinalHint}, {@link #scDoodle}
     */
    public HintUtils()
    {
        subThread = new HandlerThread("subThread");
        subThread.start();
        subHandler = new SubHandler(subThread.getLooper());
        mainHandler = new MainHandler();
        isc = new ImageShapeContext();
        scFinalHint = new float[ImageShapeContext.SC_DIMENSION];
        scDoodle = new float[ImageShapeContext.SC_DIMENSION];
    }

    /**
     * initialize the {@link #scHints},and get the shapecontexts of the bitmaps for hint
     */
    public void init(List<Bitmap> bitmaps)
    {
        scHints = new float[bitmaps.size()][ImageShapeContext.SC_DIMENSION];
        Message backMsg=subHandler.obtainMessage();
        backMsg.arg1 = INIT_SC;
        backMsg.obj = bitmaps;
        subHandler.sendMessage(backMsg);
    }

    /**
     * give the bitmap of doodleView to start or restart hint
     */
    public void startHint(Bitmap bitmap)
    {
        mState = RUNNING;
        Message backMsg=subHandler.obtainMessage();
        backMsg.arg1 = GET_SIMILARITY_CENTER_OF_BITMAP;
        backMsg.obj = bitmap;
        subHandler.sendMessage(backMsg);
    }

    /**
     * cancel the hint
     */
    public void cancelHint()
    {
        mState = CANCELED;
        subHandler.removeCallbacksAndMessages(null);
        mainHandler.removeCallbacksAndMessages(null);
    }

    /**
     * get the score of the doodleView bitmap
     */
    public void getScore(Bitmap bitmap)
    {
        Message backMsg=subHandler.obtainMessage();
        backMsg.arg1 = GET_SCORE_CENTER_OF_MASS;
        backMsg.obj = bitmap;
        subHandler.sendMessage(backMsg);
    }

    class SubHandler extends Handler
    {
        public SubHandler(Looper looper)
        {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg)
        {
            if(msg.arg1 == INIT_SC)
            {
                List<Bitmap> bitmaps = (List<Bitmap>) msg.obj;
                isc.getShapeContext(bitmaps, scHints,125,ImageShapeContext.CENTER_OF_BITMAP);
                isc.getShapeContext(bitmaps.get(bitmaps.size()-1), scFinalHint,125,
                        ImageShapeContext.CENTER_OF_MASS);
                Message mainMsg=mainHandler.obtainMessage();
                mainMsg.arg1=INIT_COMPLETE;
                mainHandler.sendMessage(mainMsg);
            }else if(msg.arg1 == GET_SIMILARITY_CENTER_OF_BITMAP)
            {
                Bitmap bitmap = (Bitmap) msg.obj;
                int boundaryNum = isc.getShapeContext(bitmap, scDoodle,125,ImageShapeContext.CENTER_OF_BITMAP);
                int like;
                if(boundaryNum<isc.minBoundaryNum)
                    like = 0;
                else
                    like = isc.getSimilarityNumber(scDoodle,scHints,ImageShapeContext.SC_DIMENSION);
                Message mainMsg=mainHandler.obtainMessage();
                mainMsg.arg1=UPDATE_HINT;
                mainMsg.arg2=like;
                mainHandler.sendMessage(mainMsg);
            }else if(msg.arg1 == GET_SCORE_CENTER_OF_MASS)
            {
                Bitmap bitmap = (Bitmap) msg.obj;
                isc.getShapeContext(bitmap, scDoodle,125,ImageShapeContext.CENTER_OF_MASS);
                int score = isc.getScore(scDoodle, scFinalHint, ImageShapeContext.SC_DIMENSION);
                Message mainMsg=mainHandler.obtainMessage();
                mainMsg.arg1=GET_SCORE;
                mainMsg.arg2=score;
                mainHandler.sendMessage(mainMsg);
            }
        }
    }

    class MainHandler extends Handler
    {
        public MainHandler()
        {
            super();
        }

        @Override
        public void handleMessage(Message msg)
        {
            if(msg.arg1 == INIT_COMPLETE)
            {
                onInitComplete();
            }else if(msg.arg1 == UPDATE_HINT)
            {
                Bitmap bitmap = onUpdateHint(msg.arg2);
                if(bitmap!=null && mState==RUNNING)
                {
                    Message backMsg=subHandler.obtainMessage();
                    backMsg.arg1 = GET_SIMILARITY_CENTER_OF_BITMAP;
                    backMsg.obj = bitmap;
                    subHandler.sendMessage(backMsg);
                }

            }else if(msg.arg1 == GET_SCORE)
            {
                onGetScore(msg.arg2);
            }
        }
    }

    /**
     * this function will be called when the {@link #init(List)} is completed
     */
    public abstract void onInitComplete();

    /**
     * this function will be called when the subThread get the similarity of the doodleView bitmap,
     * and it must return the new bitmap that got from doodleView
     */
    public abstract Bitmap onUpdateHint(int index);

    /**
     * this function will be called when the {@link #getScore(Bitmap)} is completed
     */
    public abstract void onGetScore(int score);

}
