package com.pre.jason;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.List;

public class ImageShapeContextPre
{
    private static String DEBUG_LOG = "Image_Shape_Context";

    public static final int CENTER_OF_BITMAP = 0;
    public static final int CENTER_OF_MASS = 1;

    /**
     * 1.there is assuming that the width of the reference bitmap(for hint)
     * is bigger than the height of itself, so we can let the R_MAX to be width/2.
     *
     * 2.another assumption is the width of the doodleview bitmap is equal to the width of
     * the reference bitmap
    */
    public static final int R_MAX_WIDTH = 0;
    public static final int R_MAX_SELF = 1;
    private int width;
    private int height;

    /**
     * get the similarity number from several shapecontexts
     */
    public int getSimilarityNumber(float sc1[], float sc2[][], int dimension)
    {
        int result = -1;
        int i;
        float similarity[] = new float[sc2.length];
        float min= Integer.MAX_VALUE;
        for(i=0;i<sc2.length;++i)
        {
            similarity[i] = getSimilarity(sc1, sc2[i], dimension);
            if(i==0)
            {
                min = similarity[i];
                result = i;
            }
            else
            {
                if (min>similarity[i])
                {
                    min = similarity[i];
                    result = i;
                }
            }
            Log.d(DEBUG_LOG, "similarity" + i + ": "+similarity[i]);
        }
        return result;
    }

    /**
     * get the similarity between two shapecontext
     */
    public float getSimilarity(float sc1[], float sc2[], int dimension)
    {
        float result= 0f;
        int i;
        for(i=0;i<dimension;++i)
        {
            if(sc1[i]+sc2[i]!=0)
                //result= result + (sc1[i]-sc2[i])*(sc1[i]-sc2[i])/(sc1[i]+sc2[i]);
                result= result + Math.abs(sc1[i] - sc2[i]);
        }
        return result;
    }

    /**
     * get shape contexts of many bitmaps
     * @param sobelThreshold for example 125
     * @param boundaryMax for example 300
     * @param centerModel CENTER_OF_BITMAP or CENTER_OF_MASS
     */
    public void  getShapeContext(List<Bitmap> bitmaps, float shapeContext[][], int sobelThreshold,
                               int boundaryMax, int centerModel,int radiusModel)
    {
        int i;
        for(i=0;i<bitmaps.size();++i)
        {
            getShapeContext(bitmaps.get(i), shapeContext[i], sobelThreshold,
                    boundaryMax, centerModel,radiusModel);
        }
    }

    /**
     * get the shape context of one image bitmap
     * @param shapeContext new float[60]
     * @param sobelThreshold for example 125
     * @param boundaryMax for example 300
     * @param centerModel CENTER_OF_BITMAP or CENTER_OF_MASS
     * @return the final number of boundary
     */
    public int getShapeContext(Bitmap image, float shapeContext[], int sobelThreshold,
                                int boundaryMax, int centerModel,int radiusModel)
    {
        width = image.getWidth();
        height = image.getHeight();
        int pixels[] = new int[width * height];
        int gray[] = new int[width * height];
        boolean binary[] = new boolean[width * height];
        int coordinate[][] = new int[2][width * height];
        int coordinate2[][] = new int[2][boundaryMax];

        image.getPixels(pixels, 0, width, 0, 0, width, height);
        getGray(pixels, gray);
        pixels = null;
        getBinaryBySobel(gray, binary, sobelThreshold);
        gray = null;
        int boundaryNum = trackBoundary(binary, coordinate);
        binary = null;
        int selectNum = selectBoundary(coordinate,coordinate2,boundaryNum,boundaryMax);
        calculateShapeContext(coordinate2,selectNum,shapeContext,centerModel,radiusModel);
        coordinate = null;
        coordinate2 = null;

        //test by show image
//        for(int i=0;i<height;++i)
//        {
//            for(int j=0;j<width;++j)
//            {
//                pixels[i*width+j]=0xFFFFFFFF;
//            }
//        }
//        for(int i=0;i<boundaryNum;++i)
//        {
//            pixels[coordinate[0][i]*width+coordinate[1][i]]=0x000000FF;
//        }
//        image.setPixels(pixels, 0, width, 0, 0, width, height);

        return selectNum;
    }

    /**
     * get the gray image for the pixels of one image
     * the gray should be binaried by one Threshold
     * (there just set the threshold 200, but the OTSU may be the best),
     * that can improve the result of {@link #getBinaryBySobel}
     *
     * @see #getBinaryBySobel(int[],boolean[],int)
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
    private void getBinaryBySobel(int gray[], boolean binary[], int threshold)
    {
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
                binary[width * i + j] = (gradient > threshold);
            }
        }
    }

    /**
     * track the boundary of binary image,save the location of boundary in sequence
     */
    private int trackBoundary(boolean binary[], int coordinate[][])
    {
        int boundaryNum = 0;
        boolean []flag = new boolean[width * height];
        int i=0,j=0;
        int flag_i,flag_j;
        while(i<height&&j<width)
        {
            while(!((binary[width*i+j])&&(!flag[width*i+j])))
            {
                j++;
                if(j==width){j=0;i++;}if(i==height)break;
            }
            if(i==height)break;
            flag_i=i;flag_j=j;
            flag[width*i+j]=true;
            coordinate[0][boundaryNum]=i;
            coordinate[1][boundaryNum]=j;
            boundaryNum++;
            //because the boundary of the binary is invalid value,
            //it need not to check break for i and j
            while((binary[width*i+j+1])&&(!flag[width*i+j+1])
                    || (binary[width*(i+1)+j+1])&&(!flag[width*(i+1)+j])
                    || (binary[width*(i+1)+j])&&(!flag[width*(i+1)+j])
                    || (binary[width*(i+1)+j-1])&&(!flag[width*(i+1)+j])
                    || (binary[width*i+j-1])&&(!flag[width*i+j-1]))
            {
                if(binary[width*i+j+1])
                {
                    j++;
                }
                else if(binary[width*(i+1)+j+1])
                {
                    i++;j++;
                }
                else if(binary[width*(i+1)+j])
                {
                    i++;
                }
                else if(binary[width*(i+1)+j-1])
                {
                    i++;j--;
                }
                else
                {
                    j--;
                }
                flag[width*i+j]=true;
                coordinate[0][boundaryNum]=i;
                coordinate[1][boundaryNum]=j;
                boundaryNum++;
            }
            i=flag_i;j=flag_j+1;if(j==width){j=0;i++;}if(i==height)break;
        }
        return boundaryNum;
    }

    /**
     * select the boundary by a step=num/max
     */
    private int selectBoundary(int coordinate[][],int coordinate2[][], int num,int max)
    {
        int i;
        if(num>max)
        {
            float step=num/max;
            for(i=0;i<max;++i)
            {
                coordinate2[0][i]=coordinate[0][((int) (i * step))];
                coordinate2[1][i]=coordinate[1][((int) (i * step))];
            }
            return max;
        }else
        {
            for(i=0;i<num;++i)
            {
                coordinate2[0][i]=coordinate[0][i];
                coordinate2[1][i]=coordinate[1][i];
            }
            return num;
        }
    }

    /**
     * calculate the ShapeContext for one point(center of bitmap or mass center of boundary)
     */
    private void calculateShapeContext(int[][] coordinate2, int selectNum, float[] shapeContext,
                                       int centerModel, int radiusModel)
    {
        if(selectNum==0) return;

        int centerX=0,centerY=0;
        int i,j,k;
        if(centerModel == CENTER_OF_BITMAP)
        {
            centerX = width/2;
            centerY = height/2;
        }else if(centerModel == CENTER_OF_MASS)
        {
            for(i=0;i<selectNum;++i)
            {
                centerX=centerX+coordinate2[1][i];
                centerY=centerY+coordinate2[0][i];
            }
            centerX=centerX/selectNum;
            centerY=centerY/selectNum;
        }
        //relative position radius and angle
        float reP[][]=new float[2][selectNum];
        float R_max=0,R,r;
        int scHistogram[] = new int[5*12];
        for(i=0;i<selectNum;++i)
        {
            reP[0][i]=(float)Math.sqrt((coordinate2[0][i]-centerY)*(coordinate2[0][i]-centerY)+(coordinate2[1][i]-centerX)*(coordinate2[1][i]-centerX));
            reP[1][i]=(float)Math.toDegrees(Math.atan2(coordinate2[0][i]-centerY,coordinate2[1][i]-centerX));
            if(reP[0][i]>R_max)R_max=reP[0][i];
            //fix the degrees
            if(coordinate2[0][i]<centerY)
                reP[1][i]=0-reP[1][i];
            else if(coordinate2[0][i]>centerY)
                reP[1][i]=360-reP[1][i];
        }
        if(radiusModel == R_MAX_WIDTH)
            R = width/2;
        else
            R = R_max;
        r=R/5;
        for(i=0;i<selectNum;++i)
        {
            for(k=0;k<5;++k)
            {
                if(reP[0][i]>=k*r && reP[0][i]<(k+1)*r)
                {
                    for(j=0;j<12;++j)
                    {
                        if(reP[1][i]>=j*30 && reP[1][i] <(j+1)*30)
                        {
                            scHistogram[j+k*12]++;
                            break;
                        }
                    }
                    break;
                }
            }
        }
        //归一化
        for(i=0;i<60;++i)
        {
            shapeContext[i]=(float)scHistogram[i]/selectNum;
        }
        reP=null;
        scHistogram =null;
    }
}
