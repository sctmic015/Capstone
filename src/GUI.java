import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class GUI extends JFrame{
    // Dimensions of GUI window
    static int frameX = 900;
	static int frameY = 900;
    // Instance variables
    private javax.swing.JButton detectFracturesButton;
    private javax.swing.JButton loadImagesButton;
    private javax.swing.JSlider imageSlider;
    private ImagePanel imagePanel; // custom JPanel 

    /**
     * Constructor that sets up basic UI 
     */
    public GUI() {
        this.setupGUI();
    }


    /**
     * Entry point for the program 
     */
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setVisible(true);

        // gui.loadNewImage("/Users/david/Google Drive/Varsity/*Work/CSC 3003S/Capstone/capstone/data/1/cross38.pgm");
    }

    

    /**
     * Used to setup the user interface ]
     * 
     * Note: No Image slice is rendered at this point 
     */
    public void setupGUI() {
        // Setup 
        imagePanel = new ImagePanel();
        detectFracturesButton = new javax.swing.JButton();
        loadImagesButton = new javax.swing.JButton();
        imageSlider = new javax.swing.JSlider();
        // Window behaviour
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VFDS");
        // Buttons
        detectFracturesButton.setText("Detect fractures");
        loadImagesButton.setText("Load images");
        // Buttons - ActionListeners
        loadImagesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // TODO: file button clicked
                FileOpener fileOpener = new FileOpener();
                loadNewImage(fileOpener.getFileFromUser());
            }
        });
        detectFracturesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imagePanel.findFractures(); // detects fractures + colours them 
            }
        });
        // ImageSlider Setup
        imageSlider.setMajorTickSpacing(127);
        imageSlider.setMaximum(1);
        imageSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        imageSlider.setValue(0);
        // Layout - Outter window
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(detectFracturesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(loadImagesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(imageSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imageSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(imagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(detectFracturesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loadImagesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        // Render neatly
        pack();
    }


    /**
     * Loads new image into GUI given file path 
     * @param filePath Path to .pgm image file 
     * @return boolean True if successfull, false otherwise
     */
    private boolean loadNewImage(String filePath) {
        if (filePath != null) {
            int imageData[][];
            try {
                imageData = readPGM(filePath);
                CTImageSlice imageSlice = new CTImageSlice(38, imageData);
                this.displayImageSlice(imageSlice); 
                return true;
            } catch (IOException e) {
                // TODO error reading in file
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * Method to read PGM pixel values into a 2D integer Array
     * @param fileName
     * @return Integer array of pixel values
     * @throws IOException
     * Code adapted from somewhere on stackoverflow
     */
    public static int[][] readPGM(String fileName) throws IOException {
        String filePath = fileName;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Scanner scan = new Scanner(fileInputStream);
        // Discard the magic number
        scan.nextLine();
        // Discard the comment line
        scan.nextLine();
        // Read pic width, height and max value
        int picWidth = scan.nextInt();
        int picHeight = scan.nextInt();
        // int maxvalue = scan.nextInt();
        scan.nextInt();

        scan.close();
        fileInputStream.close();

        fileInputStream = new FileInputStream(filePath);
        DataInputStream dis = new DataInputStream(fileInputStream);

        // look for 4 lines (i.e.: the header) and discard them
        int numnewlines = 4;
        while (numnewlines > 0) {
            char c;
            do {
                c = (char)(dis.readUnsignedByte());
            } while (c != '\n');
            numnewlines--;
        }

        int[][] imageData = new int[picHeight][picWidth]; // 2D array storing image colour values 
        for (int row = 0; row < picHeight; row++) {
            for (int col = 0; col < picWidth; col++) {
                imageData[row][col] = dis.readUnsignedByte();
                /*
                if(imageData[row][col] > 200){
                // if(imageData[row][col] != 0 && imageData[row][col] != 200){
                    System.out.print(imageData[row][col] + " ");
                }
                */
            }
            // System.out.println();
        }

        return imageData;
    }

    /**
     * Displays given image slice on GUI 
     * It takes the image slice and passes it to the ImagePanel, 
     * which handles the actual rendering.
     * 
     * Note: ImagePanel will automatically repaint/refresh the panel
     * @param imageSlice
     */
    public void displayImageSlice(CTImageSlice imageSlice) {
        imagePanel.clearOverlay();
        imagePanel.setImageSlice(imageSlice);
    }

}
