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

//        C:\\Users\\Wong\\Downloads\\1602345563967.jpg
//        C:\\Users\\Wong\\Downloads\\1602344081046.jpg
//        String asd = "C:\Users\Wong\Downloads\1602344081046.jpg";

        Integer spots = null;
        Integer iteration = null;
        BufferedImage image = null;

        //request file path
        while (image == null) {
            System.out.println("Please provide file path");
            image = readFile();
        }

        //request number of colours
        while (spots == null) {
            System.out.println("How many colours would you like your image to be made of");
            spots = readInt();
            if (spots != null &&  (spots > image.getWidth() * image.getHeight() || spots<3)) {
                System.out.println("Please provide a number larger than 2, but smaller than "+(image.getWidth() * image.getHeight()));
                spots = null;
            }
        }

        //request number of max iterations
        while (iteration == null) {
            System.out.println("How many rounds of iterations would you like to process through");
            iteration = readInt();
        }

        //create directory
        createFolder();


        for (int num = 2; num <= spots; num++) {
            BufferedImage out = _imagePixel.pixelate(image, num, iteration);

            writeImage(out,num);
            System.out.println("Finished Writing colour "+num +"\n");
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

    private static void createFolder(){
        File dir = new File(path.substring(0,path.lastIndexOf("\\"))+"Output\\");
        boolean bool = dir.mkdir();
        if (bool){
            System.out.println("Output Directory has been made");
        }
    }

    private static void writeImage(BufferedImage output, int colours){
        try {
            File f = new File(path.substring(0,path.lastIndexOf("\\")+1)+"Output\\"+path.substring(path.lastIndexOf("\\"),path.lastIndexOf("."))+"-"+colours+".jpg");
//                File f = new File(path.replace());

            ImageIO.write(output, "png", f);
        } catch (IOException e) {
            System.out.println(e);
        }
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
