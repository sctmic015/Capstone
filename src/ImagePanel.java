import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;

public class ImagePanel extends JPanel{
    private static final int PANEL_SIZE = 600;
    // Instance variables 
    private CTImageSlice imageSlice;
    private BufferedImage overlayImage;
    private FractureCollection fractureCollection;
    private ArrayList<FractureVoxel> fractureVoxels;

    /**
     * Basic constructor that intializes empty image panel
     */
    public ImagePanel() {
        setupPanelBasics();
    }

    /**
     * Constructor for image panel that takes in an image slice
     * This image slice is stored and rendered (through swing)
     * @param imageSlice
     */
    public ImagePanel(CTImageSlice imageSlice) {
        setupPanelBasics();
        this.imageSlice = imageSlice;
        fractureVoxels = Dectection.findFractureVoxels(imageSlice); // detect fracture voxels 
    }

    /**
     * Sets up basic layout for image panel
     */
    private void setupPanelBasics() {
        // Setup layout
        javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(this);
        this.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, ImagePanel.PANEL_SIZE, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, ImagePanel.PANEL_SIZE, Short.MAX_VALUE)
        );
    }
    
    /**
     * Paints image slice onto panel
     */
	@Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
        if (imageSlice != null) {
            // draw the image slice in greyscale as an image
            if (imageSlice.getImage() != null){
                g.drawImage(imageSlice.getImage().getScaledInstance(ImagePanel.PANEL_SIZE, ImagePanel.PANEL_SIZE, 0), 0, 0, null);
            }
            // draw the overlay image to show fracture colours 
            if (overlayImage != null) {
                g.drawImage(overlayImage.getScaledInstance(ImagePanel.PANEL_SIZE, ImagePanel.PANEL_SIZE, 0), 0, 0, null);
            }
        }
	}

    /**
     * Adds CTImageSlice to image panel and repaints panel
     * @param imageSlice CTImageSlice The image slice to paint in this panel
     */
    public void setImageSlice(CTImageSlice imageSlice) {
        // clearOverlay();
        this.imageSlice = imageSlice;
        fractureVoxels = Dectection.findFractureVoxels(imageSlice); // detect fracture voxels 
        repaint();
    }

    /**
     * Dectects fractures on CTImageSlice and displays them
     */
    public void findFractures() {
        if (imageSlice != null) {
            // Fracture collection 
            fractureCollection = new FractureCollection(fractureVoxels);
            ColourBuilder.assignColorsToFractures(fractureCollection.getFractures(fractureVoxels));
            overlayImage = fractureCollection.getImage(imageSlice.getZCoOrd(), imageSlice.getImage().getWidth(), imageSlice.getImage().getHeight()); // TODO: remove hardcoded values
            repaint();
        }
    }


    /**
     * Clears the entire image panel canvas
     */
    public void clearOverlay() {
        overlayImage = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
    }
}
