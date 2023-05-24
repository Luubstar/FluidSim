package Sim;
import TextEngine.Colors;
import TextEngine.Maps.Tile;

public class SimTile extends Tile {

    public SimTile(){
        super(" ", Colors.White);
    }

    public SimTile(String T){
        super(T, Colors.White);
    }
    
}
