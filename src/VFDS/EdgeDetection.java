package VFDS;

public class EdgeDetection {
    int[][] Mx = new int[][]{{-1, 0, 1},
            {-1, 0, 1},
            {-1, 0, 1}};
    int[][] My = new int[][]{{-1, -1, -1},
            {0, 0, 0},
            {1, 1, 1}};

    public static int[][] sobel(int[][] imageData){
        int[][] edgeDetect = new int[imageData.length][imageData.length];
        for (int x = 1; x < imageData.length-1; x ++){
            for (int y = 1; y < imageData.length-1; y ++){
                int Gx =imageData[x-1][y-1]-imageData[x+1][y-1]+2 * imageData[x-1][y]-
                        2*imageData[x+1][y]+imageData[x-1][y+1]-imageData[x+1][y+1];

                int Gy = imageData[x-1][y-1] + 2* imageData[x][y-1]+imageData[x+1][y-1]-
                        imageData[x-1][y+1] - 2*imageData[x][y+1] + imageData[x+1][y+1];
                double G = Math.sqrt(Gx*Gx + Gy*Gy);
                int G1 = (int) Math.round(G);
                if (G1 > 200){
                    G1 = 200;
                }
                edgeDetect[x][y] = G1;
            }
        }
        return edgeDetect;
    }
    public static int[][] prewitt(int[][] imageData) {
        imageData = Smoothing.medianFilter(imageData);
        for (int i = 1; i <= 2; i ++){
            imageData = Smoothing.medianFilter(imageData);
        }
        int[][] edgeDetect = new int[imageData.length][imageData.length];
        for (int x = 1; x < imageData.length-1; x ++){
            for (int y = 1; y < imageData.length-1; y ++){
                int Gx = -imageData[x-1][y-1]+imageData[x+1][y-1]-imageData[x-1][y]+
                        imageData[x+1][y]-imageData[x-1][y+1]+imageData[x+1][y+1];
                int Gy = -imageData[x-1][y-1] -imageData[x][y-1]-imageData[x+1][y-1]+
                        imageData[x-1][y+1] +imageData[x][y+1] + imageData[x+1][y+1];
                double G = Math.sqrt(Gx*Gx + Gy*Gy);
                int G1 = (int) Math.round(G);
                if (G1 > 200){
                    G1 = 200;
                }
                System.out.print(G1 + " ");
                edgeDetect[x][y] = G1;
            }
            System.out.println();
        }
        return edgeDetect;
    }
}
