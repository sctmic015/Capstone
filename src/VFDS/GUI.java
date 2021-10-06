package VFDS;
import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;

public class GUI extends JFrame{
    // Dimensions of GUI window
    static int frameX = 900;
	static int frameY = 900;
    // Instance variables
    private javax.swing.JButton detectFracturesButton;
    private javax.swing.JButton loadImagesButton;
    private javax.swing.JButton saveFracturesButton;
    private javax.swing.JButton loadFracturesButton;
    private javax.swing.JSlider imageSlider;
    private ImagePanel imagePanel; // custom JPanel 
    private XYImageFrame xView;
    private XYImageFrame yView;
    private CTImageStack imageStack;
    private FileHandler fileHandler;
    //private FileChooser fileChooser;

    /**
     * Constructor that sets up basic UI 
     */
    public GUI() {
        this.fileHandler = new FileHandler(this);
        //this.fileChooser = new FileChooser(this);
        this.setupGUI();
    }


    /**
     * Entry point for the program 
     */
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setVisible(true);
    }

    

    /**
     * Used to setup the user interface ]
     * 
     * Note: No Image slice is rendered at this point 
     */
    public void setupGUI() {
        // Setup 
        imagePanel = new ImagePanel(imageStack);
        xView = new XYImageFrame(0, imageStack);
        yView = new XYImageFrame(1, imageStack);
        detectFracturesButton = new javax.swing.JButton();
        loadImagesButton = new javax.swing.JButton();
        saveFracturesButton = new javax.swing.JButton();
        loadFracturesButton = new javax.swing.JButton();
        imageSlider = new javax.swing.JSlider();
        // Window behaviour
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VFDS");


        // Buttons
        detectFracturesButton.setText("Detect fractures");
        loadImagesButton.setText("Load images");
        saveFracturesButton.setText("Save Fractures");
        loadFracturesButton.setText("Load Fractures");
        // Buttons - ActionListeners
        loadImagesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // disable buttons
                detectFracturesButton.setEnabled(false);
                loadImagesButton.setEnabled(false);
                saveFracturesButton.setEnabled(false);
                loadFracturesButton.setEnabled(false);
                // Show popup
                PopupFactory pf = new PopupFactory();
                JPanel popupFrame = new JPanel();
                popupFrame.add(new JLabel("Loading in images..."));
                Popup popup = pf.getPopup(imagePanel, popupFrame, 300, 300);
                popup.show();
                // this will load in images by reading in image files, creating image slices 
                // and generating a CTImageStack which is stored locally here in GUI object
                try {
                fileHandler.loadImages();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Make srue to pass GUI to FileHandler");
                }
                popup.hide();
                // re-enable buttons
                detectFracturesButton.setEnabled(true);
                loadImagesButton.setEnabled(true);
                saveFracturesButton.setEnabled(true);
                loadFracturesButton.setEnabled(true);

                Dectection.findThresholds(imageStack);  // get threshold for images
                
            }
        });
        detectFracturesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detectFracturesButton.setEnabled(false);
                loadImagesButton.setEnabled(false);
                
                // detect fracture voxels, detects fractures + colours them
                // NOTE: ImagePanel will repaint and show coloured fractures
                imageStack.detectFractures();
                imagePanel.repaint(); // refresh imagePanel
                loadImagesButton.setEnabled(true);
                detectFracturesButton.setEnabled(true);
            }
        });

        saveFracturesButton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                try{
                    File file = fileHandler.saveFractures();
                    imageStack.saveFractures(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /*
                try {
                    imageStack.saveFractures();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                 */
            }
        });

        loadFracturesButton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                PopupFactory pf = new PopupFactory();
                JPanel popupFrame = new JPanel();
                //popupFrame.add(new JLabel("Loading in images..."));
                Popup popup = pf.getPopup(imagePanel, popupFrame, 300, 300);
                popup.show();
                // this will load in images by reading in image files, creating image slices
                // and generating a CTImageStack which is stored locally here in GUI object
                try {
                    File file = fileHandler.loadSavedFractures();
                    imageStack.loadFractures(file);
                } catch (Exception e) {}

                popup.hide();
                imagePanel.repaint();


            }
        });



        // ImageSlider Setup
        imageSlider.setMinimum(0);
        imageSlider.setMaximum(imageStack == null ? 0 : imageStack.getSize());
        imageSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        imageSlider.setValue(0);
        imageSlider.setEnabled(false);
        imageSlider.addChangeListener(e -> 
        {
            int sliceSelected = imageSlider.getValue();
            System.out.println(sliceSelected);
            if (imageStack != null) {
                imageSlider.setEnabled(true);
                this.displaySlice(sliceSelected);
                // this.xView.refresh(sliceSelected);
            }
        });


        // Mouse listner - gets fractures on click
		imagePanel.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				int mouseX=e.getX();
				int mouseY=e.getY();
                // As the image is scaled you have to adjust for this to get the right 
                // x/y co-ordinate in the CTImageSlice's array 
                double scaleFactor = (double)imagePanel.getPanelSize() / (double)imagePanel.getImageSlice().getXDimension();
                int xCoOrd = (int) Math.round(mouseX/scaleFactor);
                int yCoOrd = (int) Math.round(mouseY/scaleFactor);
                // Fracture selectedFracture;
                imagePanel.getImageSlice().getFractureVoxels().forEach((voxel) -> {
                    int curserBuffer = 4;
                    if ( Math.abs(voxel.getX()-xCoOrd) <= curserBuffer && Math.abs(voxel.getY()-yCoOrd) <= curserBuffer ){
                        // NOTE: uses scaled ref. 
                        // TODO: test this algorithm is correct 
                        System.out.println( voxel.getAssignedFracture() );
                    }
                });
			}
			public void mousePressed(MouseEvent e){};
			public void mouseReleased(MouseEvent e){};
			public void mouseEntered(MouseEvent e){};
			public void mouseExited(MouseEvent e){};

		});
        
        // Layout - Icon
            // ICON SETUP
            JLabel iconViewLabel;
            JPanel iconViewPanel;
            iconViewPanel = new JPanel();
            iconViewLabel = new JLabel("IMAGE NOT FOUND");
            // set icon
            try {
                URL url= new URL("https://i.imgur.com/y2iQrQU.jpg");
                Image image = ImageIO.read(url);
                ImageIcon icon = new ImageIcon(image);
                iconViewLabel = new JLabel(icon);
                iconViewPanel.add(iconViewLabel);
            } catch (Exception e1) {
                System.out.println("Couldt fectch images for icons");
            }
            iconViewPanel.setVisible(true);

        GroupLayout iconViewPanelLayout = new GroupLayout(iconViewPanel);
        iconViewPanel.setLayout(iconViewPanelLayout);
        iconViewPanelLayout.setHorizontalGroup(
            iconViewPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, iconViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconViewLabel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );
        iconViewPanelLayout.setVerticalGroup(
            iconViewPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(iconViewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconViewLabel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );

        // Layout - Outter window
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(loadFracturesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(detectFracturesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(loadImagesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(saveFracturesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(imageSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(iconViewPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imageSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(imagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(iconViewPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(loadFracturesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(detectFracturesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(loadImagesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(saveFracturesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        // Render neatly
        pack();
    }



    /**
     * Sets image stack this GUI is responsible for
     * and displays first image in the stack 
     * @param imageStack image stack to associate with this gui 
     */
    public void setImageStack(CTImageStack imageStack) {
        if (imageStack != null) {
            this.imageStack=imageStack;
            this.imagePanel.setImageStack(imageStack); // pass imageStack to ImagePanel 
            imageSlider.setEnabled(true); // enable slider
            this.imageSlider.setMaximum(imageStack == null ? 0 : imageStack.getSize()-1); // set slider scale to no. CTImageSlices
            this.displaySlice(0); // display first slice in stack 

            // Open X-axis view 
            xView.setImageStack(imageStack);
            yView.setImageStack(imageStack);
        }
    }


    /**
     * Displays given image slice number (determined by zCoOrd) on GUI 
     * The ImagePanel handles the actual rendering.
     * 
     * Note: ImagePanel will automatically repaint/refresh the panel
     * @param zCoOrd 
     */
    private void displaySlice(int zCoOrd) {
        imagePanel.displaySlice(zCoOrd);
    }

}
