package Testing;

import VFDS.*;
import java.io.File;
// junit
import static org.junit.Assert.assertNotNull;
import org.junit.*;
import org.junit.Assert.*;

public class TestCTImageSlice {

    public TestCTImageSlice() {
    }

    @Test
    public void testImageRender() {
        int[][] imageData = FileHandler.readPGM(new File("../data/1/cross77.pgm"));
        CTImageSlice imageSlice = new CTImageSlice(0, imageData);
        assertNotNull(imageSlice.getImage());
    }
}
