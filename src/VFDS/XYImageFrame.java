package VFDS;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;


/**
 *  Class sets up the infrastructure for the alternative views thoruhg the creation of frames.
 *  @author SCTMIC015, SMTJUL022, BLRDAV002
 */
public class XYImageFrame extends JFrame{
    // Instance variables 
    private CTImageStack imageStack;
    private int xyStyle; // 0=x-axis 1=y-axis
    // Instance variables for frame 
    private JLabel iconViewLabel;
    private JPanel iconViewPanel;
    private XYImagePanel imagePanel;
    private JSlider imageSlider;


    /**
     * Constructor
     * @param int xyStle
     */
    public XYImageFrame(int xyStyle) {
        this.xyStyle = xyStyle==0 ? 0 : 1;
        initComponents();
        this.setVisible(true);
    }
    
    /**
     * Constructor
     * @param int xyStle
     * @param CTImageStack
     */
    public XYImageFrame(int xyStyle,CTImageStack imageStack) {
        this.xyStyle = xyStyle==0 ? 0 : 1;
        this.imageStack=imageStack;
        initComponents();
        this.setVisible(true);
    }

    /**
     * Method intializes frame components
     */
    private void initComponents() {
        setTitle(xyStyle==0 ? "X axis slice" : "Y axis slice");
        imagePanel = new XYImagePanel(xyStyle);
        imageSlider = new JSlider();
        iconViewPanel = new JPanel();
        iconViewLabel = new JLabel("IMAGE NOT FOUND");
        // set icon 
        try {
            URL url;
            if (xyStyle==0) {
                url = new URL("https://i.imgur.com/k5mDPj6.jpg");
            }else{
                url = new URL("https://i.imgur.com/1wLL1bV.jpg");
            }
            Image image = ImageIO.read(url);
            ImageIcon icon = new ImageIcon(image);
            iconViewLabel = new JLabel(icon);
            iconViewPanel.add(iconViewLabel);
            iconViewPanel.setVisible(true);
        } catch (Exception e1) {
            System.out.println("Couldt fectch images for icons");
        }

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        GroupLayout imagePanelLayout = new GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 384, Short.MAX_VALUE)
        );

        // Image slider
        imageSlider.setMinimum(0);
        int numSlices = imageStack == null ? 0 : imageStack.getImageSlice(0).getXDimension()-1;
        imageSlider.setMaximum(numSlices);
        imageSlider.setValue(0);
        imageSlider.setEnabled(false);
        imageSlider.addChangeListener(e -> 
        {
            int sliceSelected = imageSlider.getValue();
            String sliceString = xyStyle==0?"X slice: ":"Y slice: ";
            System.out.println(sliceString + sliceSelected);
            if (imageStack != null) {
                imageSlider.setEnabled(true);
                imagePanel.displaySlice(sliceSelected);
            }
        });

        // Layout 
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

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(imageSlider, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                    .addComponent(imagePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(iconViewPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(imagePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconViewPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(imageSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    /**
     * Method Repaints the frame
     */
    public void refresh() {
        this.imageSlider.setEnabled(true);
        this.imagePanel.repaint();
    }

    /**
     * Method sets the local image stack so that it can be used to display data on the frame
     */
    public void setImageStack(CTImageStack imageStack) {
        this.imageStack=imageStack;
        int numSlices = imageStack == null ? 0 : imageStack.getImageSlice(0).getXDimension()-1;
        imageSlider.setMaximum(numSlices);
        imagePanel.setImageStack(imageStack);
    }
}
