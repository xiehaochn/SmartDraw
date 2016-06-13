package com.pre.jason;

import android.graphics.Bitmap;

public class Image
{
    private int width;
    private int height;

    /**
     * get the shape context of one image bitmap
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

        getBinaryBySobel(gray, binary, 125);
        gray = null;

        int boundaryNum = trackBoundary(binary, coordinate);

        int selectNum = selectBoundary(coordinate,coordinate2,boundaryNum,boundaryMax);
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

    private int selectBoundary(int coordinate[][],int coordinate2[][], int num,int max)
    {
        int selectNum = 0;
        int step=1;
        if(num>max)step=(int)num/max;
        int x,y;
        for(int i=0;i<num;i=i+step)
        {
            x=coordinate[0][i];
            y=coordinate[1][i];
            coordinate2[0][selectNum]=x;
            coordinate2[1][selectNum]=y;
            selectNum++;
        }
        return selectNum;
    }
}
