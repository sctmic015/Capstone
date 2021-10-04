package VFDS;
import javax.swing.*;

public class XYImageFrame extends JFrame{
    private static final int PANEL_SIZE = 600;
    // Instance variables 
    private CTImageStack imageStack;
    private int xyStyle; // 0=x-axis 1=y-axis
    // Instance variables for frame 
    private JLabel iconViewLabel;
    private JPanel iconViewPanel;
    private XImagePanel imagePanel;
    private JSlider imageSlider;



    public XYImageFrame(int xyStyle) {
        this.xyStyle = xyStyle==0 ? 0 : 1;
        initComponents();
        this.setVisible(true);
    }
    
    public XYImageFrame(int xyStyle,CTImageStack imageStack) {
        this.xyStyle = xyStyle==0 ? 0 : 1;
        this.imageStack=imageStack;
        initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        
        imagePanel = new XImagePanel(xyStyle);
        imageSlider = new JSlider();
        iconViewPanel = new JPanel();
        ImageIcon icon = new ImageIcon(xyStyle==0 ?
            "/Users/david/Desktop/cubes/right-X.jpg" :
            "/Users/david/Desktop/cubes/left-Z.jpg"
        );
        iconViewLabel = new JLabel(icon);
        iconViewPanel.add(iconViewLabel);
        iconViewPanel.setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        imagePanel.setBackground(new java.awt.Color(0, 0, 204));

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
        imageSlider.setMaximum(imageStack == null ? 0 : imageStack.getSize());
        imageSlider.setValue(0);
        imageSlider.setEnabled(false);
        imageSlider.addChangeListener(e -> 
        {
            int sliceSelected = imageSlider.getValue();
            System.out.println("X slice: "+sliceSelected);
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

    public void refresh(int xCoOrd) {
        this.imageSlider.setEnabled(true);
        this.imagePanel.displaySlice(xCoOrd);
    }

    public void setImageStack(CTImageStack imageStack) {
        this.imageStack=imageStack;
        imageSlider.setMaximum(imageStack == null ? 0 : imageStack.getSize());
        imagePanel.setImageStack(imageStack);
    }
}
