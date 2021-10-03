package VFDS;
import javax.swing.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.event.MouseEvent;

public class GUI extends JFrame{
    // Dimensions of GUI window
    static int frameX = 900;
	static int frameY = 900;
    // Instance variables
    private javax.swing.JButton detectFracturesButton;
    private javax.swing.JButton loadImagesButton;
    private javax.swing.JSlider imageSlider;
    private ImagePanel imagePanel; // custom JPanel 
    private CTImageStack imageStack;
    private FileHandler fileHandler;

    /**
     * Constructor that sets up basic UI 
     */
    public GUI() {
        this.fileHandler = new FileHandler(this);
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
        GUI gui = this;
        loadImagesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // disable buttons
                detectFracturesButton.setEnabled(false);
                loadImagesButton.setEnabled(false);
                // Show popup
                PopupFactory pf = new PopupFactory();
                JPanel popupFrame = new JPanel();
                popupFrame.add(new JLabel("Loading in images..."));
                Popup popup = pf.getPopup(gui, popupFrame, 300, 300);
                popup.show();
                FileInputDialog fileOpener = new FileInputDialog();
                try {
                    fileHandler.loadImages(fileOpener.getFilesFromUser());
                } catch (Exception e) {}
                popup.hide();
                // re-enable buttons
                detectFracturesButton.setEnabled(true);
                loadImagesButton.setEnabled(true); 

                // image analysis --> Determine if noisy; Detect thresholds.
                ArrayList<CTImageSlice> subList = new ArrayList<CTImageSlice>();
                    for (int i = ((imageStack.getSize()/2) - 7);
                        i < ((imageStack.getSize()/2) + 7); i ++){
                            subList.add(imageStack.getImageSlice(i));
                    }
                Dectection.findThresholds(subList);                    
                
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
