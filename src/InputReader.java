import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class InputReader {
    private static InputReader instance = null;
    private Scanner _scanner = new java.util.Scanner(System.in);


    public String readFilePath() {
        String temp = _scanner.nextLine();
        try {
            File f = new File(temp);
            BufferedImage _img = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }

        return temp ;
    }

    public Integer readInt() {
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


    public static InputReader getInstance(){
        if (instance==null){
            instance= new InputReader();
        }
        return instance;
    }
}
