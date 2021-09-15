package VFDS;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class ImagePanel extends JPanel{
    private static final int PANEL_SIZE = 600;
    // Instance variables 
    private CTImageStack imageStack;
    private boolean showFractures=false; // true: colour fractures
    private CTImageSlice imageSlice;
    private BufferedImage overlayImage;

    /**
     * Basic constructor that intializes empty image panel
     */
    public ImagePanel() {
        setupPanelBasics();
    }

    /**
     * Constructor for image panel that takes in an image stacl
     * This image stack is stored and rendered (through swing)
     * @param imageStack 
     */
    public ImagePanel(CTImageStack imageStack) {
        setupPanelBasics();
        this.setImageStack(imageStack);
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
            if (showFractures && overlayImage != null) {
                g.drawImage(overlayImage.getScaledInstance(ImagePanel.PANEL_SIZE, ImagePanel.PANEL_SIZE, 0), 0, 0, null);
            }
        }
	}

    /**
     * Adds CTImageSlice to image panel and repaints panel
     * @param imageSlice CTImageSlice The image slice to paint in this panel
     */
    private void setImageSlice(CTImageSlice imageSlice) {
        this.imageSlice = imageSlice;
        overlayImage = imageStack.getFractures().getImage(imageSlice.getZCoOrd(), imageSlice.getImage().getWidth(), imageSlice.getImage().getHeight()); // TODO: remove hardcoded values
        repaint();
    }

    /**
     * Clears the entire image panel canvas
     */
    private void clearOverlay() {
        overlayImage = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Sets the imageStack which this ImagePanel will represent 
     * @param imageStack
     */
    public void setImageStack(CTImageStack imageStack) {
        this.imageStack = imageStack;
    }

    /**
     * Displays CTImageSlice in CTImageStack given the zCoOrd / layer number 
     * @param zCoOrd zCoOrd of CTImageSlice 
     */
    public void displaySlice(int zCoOrd) {
        this.clearOverlay();
        this.setImageSlice( imageStack.getImageSlice(zCoOrd) );
    }
    
    /**
     * Gets currsent slice being displayed
     * @return CTImageSlice current slice being displayed
     */
    public CTImageSlice getImageSlice() {
        return this.imageSlice;
    }

    /**
     * Dectects fractures on CTImageSlice and displays them
     */
    public void showFractures() {
        showFractures = true;
        if (imageStack != null) {
            // Fracture collection 
            overlayImage = imageStack.getFractures().getImage(imageSlice.getZCoOrd(), imageSlice.getImage().getWidth(), imageSlice.getImage().getHeight()); // TODO: remove hardcoded values
            repaint();
        }
    }

    /**
     * Gets size of image panel in pixels 
     * @return width of image panel 
     */
    public int getPanelSize() {
        return PANEL_SIZE;
    }

}
