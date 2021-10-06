package VFDS;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

/**
* Class sets up the image panels for the alternative views.
* @author SCTMIC015, SMTJUL022, BLRDAV002
*/
public class XYImagePanel extends JPanel{
    private static final int PANEL_SIZE = 384;
    // Instance variables 
    private CTImageStack imageStack;
    private int coOrd;
    private BufferedImage overlayImage;
    private int xyStyle; // 0=x-axis 1=y-axis

    /**
     * Basic constructor that intializes empty image panel
     */
    public XYImagePanel(int xyStyle) {
        this.xyStyle = xyStyle==0 ? 0 : 1;
        setupPanelBasics();
    }

    /**
     * Constructor for image panel that takes in an image stacl
     * This image stack is stored and rendered (through swing)
     * @param imageStack 
     */
    public XYImagePanel(int xyStyle, CTImageStack imageStack) {
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
            .addGap(0, XYImagePanel.PANEL_SIZE, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, XYImagePanel.PANEL_SIZE, Short.MAX_VALUE)
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
            if (imageStack.getXviewImage(coOrd) != null){
                Image img;
                if (xyStyle==0) {
                    img = imageStack.getXviewImage(coOrd).getScaledInstance(XYImagePanel.PANEL_SIZE, XYImagePanel.PANEL_SIZE, 0);
                }else{
                    img = imageStack.getYviewImage(coOrd).getScaledInstance(XYImagePanel.PANEL_SIZE, XYImagePanel.PANEL_SIZE, 0);
                }
                g.drawImage(img, 0, 0, null);
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
     * Sets the imageStack which this XYImagePanel will represent 
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
    public void displaySlice(int coOrd) {
        this.clearOverlay();
        this.coOrd = coOrd;
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
