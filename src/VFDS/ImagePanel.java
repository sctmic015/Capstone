package VFDS;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class ImagePanel extends JPanel{

    private static final int PANEL_SIZEX = (int)(GUI.dim.height/1.5);
    private static final int PANEL_SIZEY = (int)(GUI.dim.height/1.5);
    // Instance variables 
    private CTImageStack imageStack;
    private CTImageSlice currentImageSlice;
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
            .addGap(0, ImagePanel.PANEL_SIZEX, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, ImagePanel.PANEL_SIZEY, Short.MAX_VALUE)
        );
    }
    
    /**
     * Paints image slice onto panel
     */
	@Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
        if (currentImageSlice != null) {
            // draw the image slice in greyscale as an image
            if (currentImageSlice.getImage() != null){
                Image img = currentImageSlice.getImage().getScaledInstance(ImagePanel.PANEL_SIZEX, ImagePanel.PANEL_SIZEY, 0);
                g.drawImage(img, 0, 0, null);
            }
            // draw the overlay image to show fracture colours 
            if (imageStack.getFractures() != null) {
                overlayImage = imageStack.getFractures().getImage(currentImageSlice.getZCoOrd(), currentImageSlice.getImage().getWidth(), currentImageSlice.getImage().getHeight());
                g.drawImage(overlayImage.getScaledInstance(ImagePanel.PANEL_SIZEX, ImagePanel.PANEL_SIZEY, 0), 0, 0, null);
            }
        }
	}

    /**
     * Adds CTImageSlice to image panel and repaints panel
     * @param currentImageSlice CTImageSlice The image slice to paint in this panel
     */
    private void setImageSlice(CTImageSlice currentImageSlice) {
        this.currentImageSlice = currentImageSlice;
        repaint();
    }

    /**
     * Clears the entire image panel canvas
     */
    private void clearOverlay() {
        overlayImage = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
        repaint();
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
        return this.currentImageSlice;
    }


    /**
     * Gets size of image panel in pixels 
     * @return width of image panel 
     */
    public int getPanelSize() {
        return PANEL_SIZEX;
    }

}
