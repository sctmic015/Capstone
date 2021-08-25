import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel{
    private CTImageSlice imageSlice;

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
                g.drawImage(imageSlice.getImage(), 0, 0, null);
            }
            // TODO: paint fractures over image, using colour assigned to them
            /*
            fractures.deriveImage();
            if (fractures.getImage() != null) {
                g.drawImage(fractures.getImage(), 0, 0, null);
            }
            */
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

    public void paintFractures() {
        ArrayList<FractureVoxel> fractureVoxels = Dectection.findFractureVoxels(imageSlice);
    }
}
