import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Main {
    private static Scanner _scanner = new java.util.Scanner(System.in);
    private static ImagePixelator _imagePixel = ImagePixelator.getInstance();
    private static String path = "";

    public static void main(String args[]) {
        //TODO request path
//        String path = "C:\\Users\\Wong\\Downloads\\1602345563967.jpg";
//        String path = "C:\\Users\\Wong\\Downloads\\rainbow.jpg";
//        String path = "C:\\Users\\Wong\\Downloads\\1602344081046.jpg";

        Integer spots = null;
        Integer iteration = null;
        BufferedImage image = null;

        while (image == null) {
            System.out.println("Please provide file path");
            image = readFile();
        }

        while (spots == null) {
            System.out.println("How many colours would you like your image to be made of");
            spots = readInt();
            if (spots != null && spots > image.getWidth() * image.getHeight()) {
                spots = null;
            }
        }

        while (iteration == null) {
            System.out.println("How many rounds of iterations would you like to process through");
            iteration = readInt();
        }

        //create directory
        //check if directory already exists

        for (int num = 1; num <= spots; num++) {
            BufferedImage out = _imagePixel.pixelate(image, num, iteration);

            try {
                //make path for output

            File f = new File("C:\\Users\\Wong\\Downloads\\Out"+num+".png");
//                File f = new File(path.replace());

                ImageIO.write(out, "png", f);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    private static BufferedImage readFile() {
        BufferedImage _img = null;

        String temp = _scanner.nextLine();
        try {
            File f = new File(temp);
            _img = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
        path = temp;
        return _img;
    }

    private static Integer readInt() {
        Integer number = null;
        try {
            String temp = _scanner.nextLine();
            number = Integer.parseInt(temp);
        } catch (NumberFormatException e) {
            System.out.println("Please provide a whole number");
            return null;
        }

        return number;
    }
}
