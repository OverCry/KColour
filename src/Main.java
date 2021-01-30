import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Main {
    private static Scanner _scanner = new java.util.Scanner(System.in);
    private static ImagePixelator _imagePixel = ImagePixelator.getInstance();
    private static String[] filePath = new String[3];

    public static void main(String args[]) {

        _imagePixel.pixelate();

    }




}
