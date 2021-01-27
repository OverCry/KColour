import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Location {
    private int _row = 0;
    private int _column = 0;
    private Color _colour = null;
    private Color _last = null;
    private Integer _id = null;

    private List<Color> _close = new ArrayList<>();

    public Location(int row, int column, Integer number){
        _row=row;
        _column=column;
        _id=number;
    }

    public int getColumn() {
        return _column;
    }

    public int getRow() {
        return _row;
    }

    public void setColor(Color rgb){
        _colour=rgb;
    }

    public Color getColour(){
        return _colour;
    }

    public Integer getId(){
        return _id;
    }

    public void addList(Color add){
        _close.add(add);
    }

    public void updateMean(){
        double red = 0;
        double blue = 0;
        double green = 0;
        for (Color num: _close){
            red+= num.getRed();
            blue+= num.getBlue();
            green+= num.getGreen();
        }

        //divide by amount
        red= red/_close.size();
        blue= blue/_close.size();
        green= green/_close.size();

        //store last colour
        _last = _colour;
        //set new colour
        _colour =  new Color((int) red,(int) green,(int) blue);

        //reset _close
        _close.clear();

    }

    public boolean ifSame(){
        if (_last.equals(_colour)){
            return true;
        }
        return false;
    }
}
