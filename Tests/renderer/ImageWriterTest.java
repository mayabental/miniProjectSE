package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * testing class ImageWriterTest
 */
class ImageWriterTest {

    /**
     * testing Write To Image
     */
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter= new ImageWriter("blueNet",800,500);
        for(int i=0;i<imageWriter.getNx();i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if((i%50==0)||(j%50==0)){
                    imageWriter.writePixel(i, j, new Color(0,0,0));
                }
                else imageWriter.writePixel(i, j, new Color(51,51,255));
            }
        }
        imageWriter.writeToImage();
    }
//
//    @Test
//    void testWritePixel() {
//    }
}