import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;

public class ImagePanel extends JPanel{
    private CTImageSlice imageSlice;
    private ArrayList<FractureVoxel> fractureVoxels; // TODO: change to just fractures not fracture voxels
    private BufferedImage overlayImage;

    public ImagePanel() {
        setupPanelBasics();
    }

    public ImagePanel(CTImageSlice imageSlice) {
        setupPanelBasics();
        this.imageSlice = imageSlice;
    }

    private void setupPanelBasics() {
        // Set background colour
        // this.setBackground(new java.awt.Color(0, 0, 204));
        // Setup layout
        javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(this);
        this.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
    }
    
    /**
     * Paints image slice onto panel
     */
	@Override
    protected void paintComponent(Graphics g) {
		// int width = getWidth();
		// int height = getHeight();
		super.paintComponent(g);
        if (imageSlice != null) {
            // draw the image slice in greyscale as an image
            if (imageSlice.getImage() != null){
                g.drawImage(imageSlice.getImage().getScaledInstance(600, 600, 0), 0, 0, null);
            }
            // TODO: paint fractures over image, using colour assigned to them
            // change to use fractures not fractureVoxels
            if (overlayImage != null) {
                g.drawImage(overlayImage.getScaledInstance(600, 600, 0), 0, 0, null);
            }
        }
	}

    /**
     * Adds CTImageSlice to image panel and repaints panel
     * @param imageSlice CTImageSlice The image slice to paint in this panel
     */
    public void setImageSlice(CTImageSlice imageSlice) {
        this.imageSlice = imageSlice;
        repaint();
    }

    /**
     * Dectects fractures on CTImageSlice and paints them red
     */
    public void findFractures() {
        if (imageSlice != null) {
            ArrayList<FractureVoxel> fractureVoxels = Dectection.findFractureVoxels(imageSlice); // detect fractures
            overlayImage = new BufferedImage(imageSlice.getImage().getWidth(), imageSlice.getImage().getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (FractureVoxel fractureVoxel : fractureVoxels) {
                int r = 255;
                int g = 0;
                int b = 0;
                int a = 255;
                Color color = new Color(r,g,b,a);
                overlayImage.setRGB(fractureVoxel.getX(), fractureVoxel.getY(), color.getRGB()); // update
            }
            repaint();
        }
    }
}
