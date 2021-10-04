package VFDS;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class XImagePanel extends JPanel{
    private static final int PANEL_SIZE = 384;
    // Instance variables 
    private CTImageStack imageStack;
    private int xCoOrd;
    private BufferedImage overlayImage;
    private int xyStyle; // 0=x-axis 1=y-axis

    /**
     * Basic constructor that intializes empty image panel
     */
    public XImagePanel(int xyStyle) {
        this.xyStyle = xyStyle==0 ? 0 : 1;
        setupPanelBasics();
    }

    /**
     * Constructor for image panel that takes in an image stacl
     * This image stack is stored and rendered (through swing)
     * @param imageStack 
     */
    public XImagePanel(int xyStyle, CTImageStack imageStack) {
        this.xyStyle = xyStyle==0 ? 0 : 1;
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
            .addGap(0, XImagePanel.PANEL_SIZE, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, XImagePanel.PANEL_SIZE, Short.MAX_VALUE)
        );
    }
    
    /**
     * Paints image slice onto panel
     */
	@Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
        if (imageStack != null) {
            // draw the image slice in greyscale as an image
            if (imageStack.getXviewImage(xCoOrd) != null){
                Image img;
                if (xyStyle==0) {
                    img = imageStack.getXviewImage(xCoOrd).getScaledInstance(XImagePanel.PANEL_SIZE, XImagePanel.PANEL_SIZE, 0);
                }else{
                    img = imageStack.getYviewImage(xCoOrd).getScaledInstance(XImagePanel.PANEL_SIZE, XImagePanel.PANEL_SIZE, 0);
                }
                g.drawImage(img, 0, 0, null);
            }
            // draw the overlay image to show fracture colours 
            if (imageStack.getFractures() != null) {
                // TODO: fractures 
                // overlayImage = imageStack.getFractures().getImage(currentImageSlice.getZCoOrd(), currentImageSlice.getImage().getWidth(), currentImageSlice.getImage().getHeight());
                // g.drawImage(overlayImage.getScaledInstance(XImagePanel.PANEL_SIZE, XImagePanel.PANEL_SIZE, 0), 0, 0, null);
            }
        }
	}


    /**
     * Clears the entire image panel canvas
     */
    private void clearOverlay() {
        overlayImage = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
        repaint();
    }

    /**
     * Sets the imageStack which this XImagePanel will represent 
     * @param imageStack
     */
    public void setImageStack(CTImageStack imageStack) {
        this.imageStack = imageStack;
        repaint();
    }

    /**
     * Displays CTImageSlice in CTImageStack given the zCoOrd / layer number 
     * @param zCoOrd zCoOrd of CTImageSlice 
     */
    public void displaySlice(int xCoOrd) {
        this.clearOverlay();
        this.xCoOrd = xCoOrd;
        repaint();
    }
    
    /**
     * Gets size of image panel in pixels 
     * @return width of image panel 
     */
    public int getPanelSize() {
        return PANEL_SIZE;
    }

}
