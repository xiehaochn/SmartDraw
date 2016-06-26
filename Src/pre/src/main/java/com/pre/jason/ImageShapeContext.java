package com.pre.jason;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class ImageShapeContext
{
    private static boolean DEBUG = true;
    public static void setDEBUG(boolean DEBUG)
    {
        ImageShapeContext.DEBUG = DEBUG;
    }
    private static String DEBUG_LOG = "Image_Shape_Context";

    /**
     * 1.there is assuming that the width of the reference bitmap(for hint)
     * is bigger than the height of itself, so we can let the R_MAX to be width/2.<br>
     *
     * 2.another assumption is the width of the doodleview bitmap is equal to the width of
     * the hit bitmap.(however the hint bitmap showed in the imageview will be scaled when the
     * scaleType="fitCenter", so the width of the doodelview will not be equal to the
     * width of the hint bitmap in most cases. Luckily the shapecontext of CENTER_OF_BITMAP
     * is expanded invariant)
     *
     */
    public static final int CENTER_OF_BITMAP = 0;
    /**
     * the shapecontext of CENTER_OF_MASS is expanded and translated invariant
     */
    public static final int CENTER_OF_MASS = 1;

    //basic parameters for shapecontext
    public static final int SC_RADIUS = 5;
    public static final int SC_ANGLE = 12;
    public static final int SC_DIMENSION = SC_RADIUS*SC_ANGLE;
    public static final float SC_CELL_DEGREE = 360/SC_ANGLE;
    private int width;
    private int height;

    //parameters for get similarity number
    public int minBoundaryNum;
    private static float UNLIKE_THRESHOLD_FIRST = 0.8f;
    private static float UNLIKE_THRESHOLD_SECOND = 0.9f;
    private static int UNLIKE_NUM_MAX = 10;
    private int unlikeNum = 0;

    /**
     * get the score of the doodleView bitmap which is compared with the final hint bitmap
     */
    public int getScore(float scDoodle[], float scHint[], int dimension)
    {
        float similarity = getSimilarity(scDoodle, scHint, dimension);
        return (int) (50*(2-similarity));
    }

    /**
     * get the similarity number from several shapecontexts
     */
    public int getSimilarityNumber(float scDoodle[], float scHints[][], int dimension)
    {
        int result = -1;
        int i;
        float similarity[] = new float[scHints.length];
        float min= Float.MAX_VALUE;
        for(i=0;i<scHints.length;++i)
        {
            similarity[i] = getSimilarity(scDoodle, scHints[i], dimension);
            if (min>similarity[i])
            {
                min = similarity[i];
                result = i;
            }
            if(DEBUG)
            {
                Log.d(DEBUG_LOG, "similarity" + i + ": " + similarity[i]);
            }
        }
        Arrays.sort(similarity);
        if(similarity[0]>UNLIKE_THRESHOLD_FIRST && similarity[1]>UNLIKE_THRESHOLD_SECOND)
            unlikeNum++;
        else
            unlikeNum = 0;
        if(unlikeNum>UNLIKE_NUM_MAX)
        {
            result = -1;
        }
        return result;
    }

    /**
     * get the similarity between two shapecontext
     */
    public float getSimilarity(float scDoodle[], float scHint[], int dimension)
    {
        float result= 0f;
        int i;
        for(i=0;i<dimension;++i)
        {
//            if(scDoodle[i]+scHints[i]!=0)
//                result= result + (sc1[i]-sc2[i])*(sc1[i]-sc2[i])/(sc1[i]+sc2[i]);
            result= result + Math.abs(scDoodle[i] - scHint[i]);
        }
        return result;
    }

    /**
     * get shape contexts of many bitmaps
     * @param sobelThreshold for example 125
     * @param centerModel CENTER_OF_BITMAP or CENTER_OF_MASS
     */
    public void  getShapeContext(List<Bitmap> bitmaps, float shapeContext[][], int sobelThreshold,
                                int centerModel)
    {
        int i;
        for(i=0;i<bitmaps.size();++i)
        {
            int boundaryNum = getShapeContext(bitmaps.get(i), shapeContext[i], sobelThreshold,
                    centerModel);
            if(i==0)minBoundaryNum = boundaryNum/2;
            if(DEBUG)
            {
                Log.d(DEBUG_LOG, "boundaryNum" + i + ": " + boundaryNum);
            }

        }
    }

    /**
     * get the shape context of one image bitmap
     * @param shapeContext new float[60]
     * @param sobelThreshold for example 125
     * @param centerModel CENTER_OF_BITMAP or CENTER_OF_MASS
     * @return the final number of boundary
     */
    public int getShapeContext(Bitmap image, float shapeContext[], int sobelThreshold,
                               int centerModel)
    {
        width = image.getWidth();
        height = image.getHeight();
        int pixels[] = new int[width * height];
        int gray[] = new int[width * height];
        boolean binary[] = new boolean[width * height];
        int coordinate[][] = new int[2][width * height];

        image.getPixels(pixels, 0, width, 0, 0, width, height);
        getGray(pixels, gray);
        pixels = null;
        int boundaryNum = getBinaryBySobel(gray, binary, coordinate, sobelThreshold);
        gray = null;
        calculateShapeContext(coordinate,boundaryNum,shapeContext,centerModel);
        coordinate = null;

        return boundaryNum;
    }

    /**
     * get the gray image for the pixels of one image
     * the gray should be binaried by one Threshold
     * (TODO:there just set the threshold 200, but the OTSU may be the best),
     * that can improve the result of {@link #getBinaryBySobel}
     *
     * @see #getBinaryBySobel(int[],boolean[],int[][],int)
     */
    private void getGray(int pixels[], int gray[])
    {
        int i, j;
        int value, red, green, blue, temp;
        for (i = 0; i < height; ++i)
        {
            for (j = 0; j < width; ++j)
            {
                value = pixels[i * width + j];
                red = ((value & 0x00FF0000) >> 16);
                green = ((value & 0x0000FF00) >> 8);
                blue = (value & 0x000000FF);
                temp = (int) ((float) red * 0.299 + (float) green * 0.587 + (float) blue * 0.114);
                if(temp<200)
                {
                    gray[i * width + j] = 0;
                }else
                {
                    gray[i * width + j] = 255;
                }
            }
        }
    }

    /**
     * get the gradient image using sobel, and binary it by threshold.
     * notice that the boundary of the binary is invalid value,
     * because not calculate for the boundary
     */
    private int getBinaryBySobel(int gray[], boolean binary[], int coordinate[][], int threshold)
    {
        int boundaryNum=0;
        int gradientX, gradientY, gradient;
        for (int i = 1; i < height - 1; i++)
        {
            for (int j = 1; j < width - 1; j++)
            {
                gradientX = Math.abs((gray[width * (i + 1) + j - 1] + 2 * gray[width * (i + 1) +
                        j] + gray[width * (i + 1) + j + 1]) - (gray[width * (i - 1) + j - 1] + 2
                        * gray[width * (i - 1) + j] + gray[width * (i - 1) + j + 1]));
                gradientY = Math.abs((gray[width * (i - 1) + j + 1] + 2 * gray[width * i + j + 1]
                        + gray[width * (i + 1) + j + 1]) - (gray[width * (i - 1) + j - 1] +
                        2 * gray[width * i + j - 1] + gray[width * (i + 1) + j - 1]));
                gradient = gradientX + gradientY;
                if(gradient > threshold)
                {
                    binary[width * i + j] = true;
                    coordinate[0][boundaryNum]=i;
                    coordinate[1][boundaryNum]=j;
                    boundaryNum++;
                }
            }
        }
        return boundaryNum;
    }

    /**
     * calculate the ShapeContext for one point(center of bitmap or mass center of boundary)
     */
    private void calculateShapeContext(int[][] coordinate, int boundaryNum, float[] shapeContext,
                                       int centerModel)
    {
        if(boundaryNum==0) return;

        int centerX=0,centerY=0;
        int i,j,k;
        if(centerModel == CENTER_OF_BITMAP)
        {
            centerX = width/2;
            centerY = height/2;
        }else if(centerModel == CENTER_OF_MASS)
        {
            for(i=0;i<boundaryNum;++i)
            {
                centerX=centerX+coordinate[1][i];
                centerY=centerY+coordinate[0][i];
            }
            centerX=centerX/boundaryNum;
            centerY=centerY/boundaryNum;
        }
        //relative position radius and angle
        float reP[][]=new float[2][boundaryNum];
        float R_max=0,R,r;
        int scHistogram[] = new int[SC_DIMENSION];
        for(i=0;i<boundaryNum;++i)
        {
            reP[0][i]=(float)Math.sqrt((coordinate[0][i]-centerY)*(coordinate[0][i]-centerY)+
                    (coordinate[1][i]-centerX)*(coordinate[1][i]-centerX));
            reP[1][i]=(float)Math.toDegrees(Math.atan2(coordinate[0][i]-centerY,coordinate[1][i]-centerX));
            if(reP[0][i]>R_max)R_max=reP[0][i];
            //fix the degrees
            if(coordinate[0][i]<centerY)
                reP[1][i]=0-reP[1][i];
            else if(coordinate[0][i]>centerY)
                reP[1][i]=360-reP[1][i];
        }
        if(centerModel == CENTER_OF_BITMAP)
            R = width/2;
        else
            R = R_max;
        r=R/SC_RADIUS;
        for(i=0;i<boundaryNum;++i)
        {
            for(k=0;k<SC_RADIUS;++k)
            {
                if(reP[0][i]>k*r && reP[0][i]<=(k+1)*r)
                {
                    for(j=0;j<SC_ANGLE;++j)
                    {
                        if(reP[1][i]>=j*SC_CELL_DEGREE && reP[1][i] <(j+1)*SC_CELL_DEGREE)
                        {
                            scHistogram[j+k*SC_ANGLE]++;
                            break;
                        }
                    }
                    break;
                }
            }
        }
        //归一化
        for(i=0;i<SC_DIMENSION;++i)
        {
            shapeContext[i]=(float)scHistogram[i]/boundaryNum;
        }
        reP=null;
        scHistogram =null;
    }
}
