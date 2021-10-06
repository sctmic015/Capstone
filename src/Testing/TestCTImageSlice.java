package Testing;

import VFDS.*;
// junit
import static org.junit.Assert.assertNotNull;
import java.io.File;
import org.junit.Test;

/**
 * Unit Tests for Testing CTImage Slice
 * @author SCTMIC015, SMTJUL022, BLRDAV002
 */

public class TestCTImageSlice {

    public TestCTImageSlice() {
    }

    @Test
    public void testImageRender() {
        int[][] imageData = FileHandler.readPGM(new File("/Users/david/Google Drive/Varsity/*Work/CSC 3003S/Capstone/capstone/data/1/cross77.pgm"));
        CTImageSlice imageSlice = new CTImageSlice(0, imageData);
        assertNotNull(imageSlice.getImage());
    }

    public static void main(String[] args) {
        
    }
}
