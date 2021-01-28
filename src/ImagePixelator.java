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
    private InputReader _reader = InputReader.getInstance();
    private BufferedImage _img = null;
    private String _path = null;
    private Integer _width = null;
    private Integer _height = null;
    private List<Location> _coOrdinates = new ArrayList<>();
    private Integer[][] _assignment = null;
    private Integer _iteration = null;
    private Integer _colours = null;

    public void pixelate() {

        String[] filePath = new String[3];

        //request file path
        while (_path == null) {
            System.out.println("Please provide file path");
            _path = _reader.readFilePath();
        }

        filePath[0] = _path.substring(0, _path.lastIndexOf("\\")) + "\\Output\\";
        filePath[1] = _path.substring(_path.lastIndexOf("\\") + 1, _path.lastIndexOf("."));
        filePath[2] = _path.substring(_path.lastIndexOf("."));

        // create buffered image
        _img = setImg(_path);

        _width = _img.getWidth();
        _height = _img.getHeight();

        //request number of colours
        while (_colours == null) {
            System.out.println("How many colours would you like your image to be made of");
            _colours = _reader.readInt();
            if (_colours != null && (_colours > _width * _height || _colours < 3)) {
                System.out.println("Please provide a number larger than 2, but smaller than " + (_width * _height));
                _colours = null;
            }
        }

        //request number of max iterations
        while (_iteration == null) {
            System.out.println("How many rounds of iterations would you like to process through");
            _iteration = _reader.readInt();
        }





        //create directory the output will go to
        createFolder(filePath[0]);


//                 pixelate and write
        for (int num = 2; num <= _colours; num++) {


            SelectKRandomPoints(num);

            KMeanRGB(_iteration);

            //write image
            BufferedImage output = new BufferedImage(_width, _height, _img.getType());
            for (int width = 0; width < _width; width++) {
                for (int height = 0; height < _height; height++) {
                    //get pixel colour value
                    int id = _assignment[height][width];
                    output.setRGB(width, height, _coOrdinates.get(id).getColour().getRGB());
                }
            }


            writeImage(output, num, filePath);

            System.out.println("Finished Writing colour " + num + "\n");
        }
    }

    private void KMeanRGB(int iteration) {
        int runs = 0;
        while (runs < iteration) {

            assignToCluster();
            updateMeans();

            if (sameMean()) {
                System.out.println("Colours Converged");
                return;
            }

            runs++;
            System.out.println("Completed Iteration: " + runs);
        }
        System.out.println("Did not converge");
    }

    private void assignToCluster() {
        //generate assignment array
        _assignment = new Integer[_height][_width];
        //using the data of the image, compare which randomly selected point it is closest to
        for (int width = 0; width < _width; width++) {
            for (int height = 0; height < _height; height++) {
                Double current = null;
                Color pixel = null;
                for (Location point : _coOrdinates) {
                    pixel = new Color(_img.getRGB(width, height), true);
                    double distance = squareDistance(point, pixel);
                    if (current == null || distance < current) {
                        _assignment[height][width] = point.getId();
                        current = distance;
                        if (current == 0) {
                            break;
                        }
                    }
                }

                //assign to appropriate point
                //find point
                int id = _assignment[height][width];
                for (Location point : _coOrdinates) {
                    if (point.getId() == id) {
                        point.addList(pixel);
                        break;
                    }
                }
            }
        }
    }


    /**
     * randomly select a number of unique locations
     * find the colours at the selected locations
     *
     * @param numbers
     */
    private void SelectKRandomPoints(int numbers) {
        while (_coOrdinates.size() < numbers) {
            //randomly generate a width and height
            int sColumn = randOfTop(_width);
            int sRow = randOfTop(_height);

            // check if the position has existed
            boolean different = true;
            for (Location points : _coOrdinates) {
                if (points.getColumn() == sColumn && points.getRow() == sRow) {
                    different = false;
                    break;
                }
            }
            if (different) {
                _coOrdinates.add(new Location(sRow, sColumn, _coOrdinates.size()));
            }
        }

        for (Location pos : _coOrdinates) {
            //get pixel
            int pixel = _img.getRGB(pos.getColumn(), pos.getRow());
            pos.setColor(new Color(pixel, true));
        }
    }

    /**
     * randomly generator a number from 0 to max-1
     *
     * @param max
     * @return
     */
    private int randOfTop(int max) {
        return (int) (Math.random() * max);
    }

    /**
     * calculating the squared distance between the colours of a mean location,
     * and the mean of a specified pixel
     *
     * @param point
     * @param imageColour
     * @return
     */
    private double squareDistance(Location point, Color imageColour) {
        double blue = Math.pow((imageColour.getBlue() - point.getColour().getBlue()), 2);
        double green = Math.pow((imageColour.getGreen() - point.getColour().getGreen()), 2);
        double red = Math.pow((imageColour.getRed() - point.getColour().getRed()), 2);
        return Math.sqrt(blue + green + red);
    }

    /**
     * updates locations with mean colours
     */
    private void updateMeans() {
        for (Location pos : _coOrdinates) {
            pos.updateMean();
        }
    }

    /**
     * helper method to see if colour values have changed
     *
     * @return
     */
    private boolean sameMean() {
        for (Location loc : _coOrdinates) {
            if (!loc.ifSame()) {
                return false;
            }
        }
        return true;
    }

    /*** methods for reading files and input ***/

    private void createFolder(String path) {
        File dir = new File(path);
        boolean bool = dir.mkdir();
        if (bool) {
            System.out.println("Output Directory has been made (" + path + ")");
        } else {
            System.out.println("Using Directory at " + path);
        }
    }

    private void writeImage(BufferedImage output, int colours, String[] filePath) {
        try {
            File f = new File(filePath[0] + filePath[1] + "-" + colours + filePath[2]);
            ImageIO.write(output, filePath[2].substring(1), f);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private BufferedImage setImg(String path) {
        BufferedImage buff;
        try {
            buff = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
        return buff;
    }


    /*** getting instance ***/

    /**
     * instance method to get the singleton
     *
     * @return
     */
    public static ImagePixelator getInstance() {
        if (instance == null) {
            instance = new ImagePixelator();
        }
        return instance;
    }
}


