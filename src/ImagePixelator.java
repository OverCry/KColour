import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.util.Scanner;

public class ImagePixelator {
    private static ImagePixelator instance = null;


    private BufferedImage _img = null;
    private File _f = null;
    private Integer _width = null;
    private Integer _height = null;
    private List<Location> _coOrdinates = new ArrayList<>();
    private Integer[][] _assignment = null;

    public BufferedImage pixelate(BufferedImage image, int numbers, int iteration){

        _img =image;
        _width=image.getWidth();
        _height=image.getHeight();


        SelectKRandomPoints(numbers);

        KMeanRGB(iteration);

        //write image
        //overwrite original pixels
        BufferedImage output = new BufferedImage(_width,_height,_img.getType());
        for (int width=0;width<_width;width++){
            for (int height=0;height<_height;height++){
                //get pixel colour value
                int id =_assignment[height][width];
//                _coOrdinates.get(id).getColour()
                output.setRGB(width,height,_coOrdinates.get(id).getColour().getRGB());
            }
        }

        return output;

    }

    /**
     * randomly select a number of unique locations
     * @param numbers
     */
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
                _coOrdinates.add(new Location(sRow,sColumn,_coOrdinates.size()));
            }
        }

        //automatically set colours of first set
        setRGBColours();
    }

    /**
     * randomly generator a number from 0 to max-1
     * @param max
     * @return
     */
    private int randOfTop(int max){
        return (int) (Math.random() * max);
    }

    /**
     * requires the location coordinates to change before it can be set
     *
     */
    private void setRGBColours(){
        for (Location pos : _coOrdinates){
            //get pixel
            int pixel = _img.getRGB(pos.getColumn(),pos.getRow());
            pos.setColor(new Color(pixel, true));
        }
    }


    private double squareDistance(Location point, Color imageColour){
        double blue = Math.pow((imageColour.getBlue()-point.getColour().getBlue()),2);
        double green = Math.pow((imageColour.getGreen()-point.getColour().getGreen()),2);
        double red = Math.pow((imageColour.getRed()-point.getColour().getRed()),2);
        return Math.sqrt(blue+green+red);
    }

    private void assignToCluster(){
        //generate assignment array
        _assignment = new Integer[_height][_width];
        //using the data of the image, compare which randomly selected point it is closest to
        for (int width=0; width<_width;width++){
            for (int height=0; height<_height;height++){
                Double current = null;
                Color pixel = null;
                for (Location point: _coOrdinates){
                    pixel = new Color(_img.getRGB(width,height), true);
                    double distance = squareDistance(point,pixel );
                    if (current==null || distance<current){
                        _assignment[height][width]=point.getId();
//                        point.addList(pixel);
                        current = distance;
                    }
                }

                //assign to appropriate point
                //find point
                int id = _assignment[height][width];
                for (Location point: _coOrdinates) {
                    if (point.getId()==id){
                        point.addList(pixel);
                        break;
                    }
                }
            }
        }
    }

    private void updateMeans(){
        for (Location pos: _coOrdinates){
            pos.updateMean();
        }
    }

    private void KMeanRGB(int iteration){
        int runs = 0;
        while (runs<iteration){

            assignToCluster();
            updateMeans();

            if (sameMean()){
                System.out.println("Colours Converged" );
                return;
            }

            runs++;
            System.out.println("Completed Iteration: " + runs );
        }
        System.out.println("Did not converge");

    }

    private boolean sameMean(){
        for (Location loc : _coOrdinates){
            if (!loc.ifSame()){
                return false;
            }
        }
        return true;
    }



    public static ImagePixelator getInstance(){
        if (instance==null){
            instance= new ImagePixelator();
        }
        return instance;
    }
}


