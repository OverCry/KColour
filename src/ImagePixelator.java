import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImagePixelator {
    private static ImagePixelator instance = null;

    private BufferedImage _img = null;
    private File _f = null;
    private Integer _width = null;
    private Integer _height = null;
    private List<Location> _coOrdinates = new ArrayList<>();

    public void pixelate(String path, int numbers,int iteration){


        //read image and TODO ensure a valid image is provided
        try {
            _f = new File(path);
            _img = ImageIO.read(_f);
        } catch (IOException e) {
            System.out.println(e);
        }

        //get image width and height
        _width = _img.getWidth();
        _height = _img.getHeight();

        //ensure number provided is less than width*height TODO maybe change effect
        if (numbers> _width * _height){
            return;
        }

        SelectKRandomPoints(numbers);



//        int p = _img.getRGB(0, 0);
//        // get alpha
//        int a = (p >> 24) & 0xff;
//        // get red
//        int r = (p >> 16) & 0xff;
//        // get green
//        int g = (p >> 8) & 0xff;
//        // get blue
//        int b = p & 0xff;

    /*
    for simplicity we will set the ARGB
    value to 255, 100, 150 and 200 respectively.
    */
//        a = 255;
//        r = 100;
//        g = 150;
//        b = 200;


//        //set the pixel value
//        p = (a << 24) | (r << 16) | (g << 8) | b;
//        _img.setRGB(0, 0, p);
//
//        //write image
//        try {
//            _f = new File("C:\\Users\\Wong\\Downloads\\Out.png");
//            ImageIO.write(_img, "png", _f);
//        } catch (IOException e) {
//            System.out.println(e);
//        }
    }


    private void SelectKRandomPoints(int numbers){
        while (_coOrdinates.size()<numbers){
            //randomly generate a width and height
            int sColumn = randOfTop(_width);
            int sRow = randOfTop(_height);

            // check if the position has existed
            boolean different = true;
            for (Location points:_coOrdinates){
                if (points.getColumn()==sColumn && points.getRow()==sRow){
                    different = false;
                    break;
                }
            }
            if (different){
                _coOrdinates.add(new Location(sRow,sColumn));
            }
        }
    }

    private int randOfTop(int max){
        return (int) Math.random() * max;
    }










    public static ImagePixelator getInstance(){
        if (instance==null){
            instance= new ImagePixelator();
        }
        return instance;
    }
}


