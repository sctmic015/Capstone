import java.util.*;

public class Smoothing {

    public static int[][] SimpleMovingAverage(int[][] imageData) {
        int[][] smoothedImage = new int[imageData.length][imageData.length];
        for (int x = 1; x < imageData.length-1; x ++){
            for (int y = 1; y < imageData.length-1; y ++){
                smoothedImage[x][y] = (imageData[x-1][y-1]+imageData[x][y-1]+imageData[x+1][y-1]+
                        imageData[x-1][y]+imageData[x][y]+imageData[x+1][y]+
                        imageData[x-1][y+1]+imageData[x][y+1]+imageData[x+1][y+1])/9;
            }
        }
        System.out.print(Arrays.toString(smoothedImage));
        return smoothedImage;
    }

    public static int[][] medianFilter(int[][] imageData){
        int[][] smoothedImage = new int[imageData.length][imageData.length];

        for (int x = 1; x < imageData.length-1; x ++){
            for (int y = 1; y < imageData.length-1; y ++){
                int[] tempArray = new int[]{imageData[x-1][y-1], imageData[x][y-1], imageData[x+1][y-1],
                        imageData[x-1][y], imageData[x][y], imageData[x+1][y],
                        imageData[x-1][y+1], imageData[x][y+1], imageData[x+1][y+1]};
                Arrays.sort(tempArray);
                int middle = tempArray[4];
                smoothedImage[x][y] = middle;
            }
        }
        return smoothedImage;
    }

    public static int[][] medianFilterWithTwist(int[][] imageData){
        int[][] smoothedImage = new int[imageData.length][imageData.length];

        for (int x = 1; x < imageData.length-1; x ++){
            for (int y = 1; y < imageData.length-1; y ++){
                int[] tempArray = new int[]{imageData[x-1][y-1], imageData[x][y-1], imageData[x+1][y-1],
                        imageData[x-1][y], imageData[x][y], imageData[x+1][y],
                        imageData[x-1][y+1], imageData[x][y+1], imageData[x+1][y+1]};
                Arrays.sort(tempArray);
                int middle = tempArray[4];
                if (imageData[x][y] < 150 && imageData[x][y] < middle){
                    smoothedImage[x][y]=imageData[x][y];
                }
                else
                    smoothedImage[x][y] = middle;
            }
        }
        return smoothedImage;
    }
}
