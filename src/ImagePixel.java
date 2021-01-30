import java.awt.*;

public class ImagePixel {
    private Integer _row = null;
    private Integer _column = null;
    private Color _colour = null;
    private Integer _belong = null;

    public ImagePixel(int row, int column, Color colour){
        _row=row;
        _colour=colour;
        _column=column;
    }

    public int getRow(){
        return _row;
    }

    public int getColumn(){
        return  _column;
    }

    public int getId(){
        return  _belong;
    }

    public void setId(int newId){
        _belong=newId;
    }

    public Color getColour(){
        return _colour;
    }

}
