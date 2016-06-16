package com.pre.jason;

import android.graphics.Bitmap;

public class ImageShanpeContext
{
    private static final int CENTER_OF_BITMAP = 0;
    private static final int CENTER_OF_MASS = 1;
    private int width;
    private int height;

    public float getSimilarity(float sc1[], float sc2[], int dimension)
    {
        float result= 0f;
        int i;
        for(i=0;i<dimension;++i)
        {
            if(sc1[i]+sc2[i]!=0)
                result= result + (sc1[i]-sc2[i])*(sc1[i]-sc2[i])/(sc1[i]+sc2[i]);
        }
        return result;
    }

    /**
     * get the shape context of one image bitmap
     * @param shapeContext new float[60]
     * @param sobelThreshold for example 125
     * @param boundaryMax for example 300
     */
    public void getShapeContext(Bitmap image, float shapeContext[], int sobelThreshold, int boundaryMax)
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
        selectBoundary(coordinate,coordinate2,boundaryNum,boundaryMax);
        calculateShapeContext(coordinate2,boundaryMax,shapeContext,CENTER_OF_BITMAP);
        coordinate = null;
        coordinate2 = null;
    }

    /**
     * get the gray image for the pixels of one image
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
                gray[i * width + j] = temp;
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
    private void selectBoundary(int coordinate[][],int coordinate2[][], int num,int max)
    {
        int step=1;
        if(num>max)step=num/max;
        int x,y;
        for(int i=0;i<max;++i)
        {
            y=coordinate[0][i*step];
            x=coordinate[1][i*step];
            coordinate2[0][i]=y;
            coordinate2[1][i]=x;
        }
    }

    /**
     * calculate the ShapeContext for one point(center of bitmap or mass center of boundary)
     */
    private void calculateShapeContext(int[][] coordinate2, int selectNum, float[] shapeContext, int centerModel)
    {
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
            if(selectNum!=0)
            {
                centerX=centerX/selectNum;
                centerY=centerY/selectNum;
            }
        }
        //relative position radius and angle
        float reP[][]=new float[2][selectNum];
        float R=0,r;
        int scHistogram[] = new int[5*12];
        for(i=0;i<selectNum;++i)
        {
            reP[0][i]=(float)Math.sqrt((coordinate2[0][i]-centerX)*(coordinate2[0][i]-centerX)+(coordinate2[1][i]-centerY)*(coordinate2[1][i]-centerY));
            reP[1][i]=(float)Math.toDegrees(Math.atan2(coordinate2[0][i]-centerY,coordinate2[1][i]-centerX));
            if(reP[0][i]>R)R=reP[0][i];
            if(coordinate2[0][i]<centerY && coordinate2[1][i]>centerX)reP[1][i]=0-reP[1][i];
            else if(coordinate2[0][i]>centerY && coordinate2[1][i]>centerX)reP[1][i]=360-reP[1][i];
            else reP[1][i]=180-reP[1][i];
        }
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
