import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class GUI extends JFrame{
    // Dimensions of GUI window
    static int frameX = 900;
	static int frameY = 900;
    // Instance variables
    private javax.swing.JButton findButton;
    private javax.swing.JButton groupButton;
    private ImagePanel imagePanel; // custom JPanel 

    /**
     * Constructor that sets up basic UI 
     */
    public GUI() {
        this.setupGUI();
    }
    
    /**
     * Used to setup the user interface ]
     * 
     * Note: No Image slice is rendered at this point 
     */
    public void setupGUI() {
        // Setup 
        imagePanel = new ImagePanel();
        findButton = new javax.swing.JButton();
        groupButton = new javax.swing.JButton();
        // Window behaviour
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VFDS");
        // Buttons
        findButton.setText("Find");
        groupButton.setText("Group");
        // Buttons - ActionListeners
        groupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // TODO: group button clicked
            }
        });
        findButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // TODO: find button clicked
            }
        });
        // Layout - Outter window
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(findButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(groupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(150, 150, 150))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(findButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(groupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );
        // Render neatly
        pack();
    }


    /**
     * Entry point for the program 
     */
    public static void main(String[] args) {
        try {
            GUI gui = new GUI();
            gui.setVisible(true);

            int imageData[][] = readPGM("/Users/david/Google Drive/Varsity/*Work/CSC 3003S/Capstone/capstone/data/ellipse512_18/test-D512-V6141-F14-281.pgm");
            CTImageSlice imageSlice = new CTImageSlice(38, imageData);

            gui.displayImageSlice(imageSlice);            
            // ArrayList<FractureVoxel> fractureVoxels = imageSlice.findFractureVoxels();

            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        imagePanel.setImageSlice(imageSlice);
    }
}
