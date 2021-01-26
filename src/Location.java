public class Location {
    int _row = 0;
    int _column = 0;

    public Location(int row, int column){
        _row=row;
        _column=column;
    }

    public int getColumn() {
        return _column;
    }

    public int getRow() {
        return _row;
    }
}
