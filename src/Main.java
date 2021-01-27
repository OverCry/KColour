import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Main {
    private static Scanner scanner = new java.util.Scanner(System.in);
    private static ImagePixelator _imagePixel = ImagePixelator.getInstance();

    public static void main(String args[]) {
        //TODO request path
//        String path = "C:\\Users\\Wong\\Downloads\\1602345563967.jpg";
//        String path = "C:\\Users\\Wong\\Downloads\\rainbow.jpg";
        String path = "C:\\Users\\Wong\\Downloads\\1602344081046.jpg";


        //TODO request number
        int spots = 8;

        //TODO iteration
        int iteration = 10;


        _imagePixel.pixelate(path, spots,iteration);
    }
}
