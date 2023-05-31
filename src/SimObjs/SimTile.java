package SimObjs;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import TextEngine.Colors;
import TextEngine.Engine;
import TextEngine.Maps.Tile;

public class SimTile extends Tile {

    public String Nombre;
    public Point coords;
    public int ID;

    public SimTile(int ID){
        super(" ", Colors.White);
        this.ID = ID;
    }

    public SimTile(String T,int ID){
        super(T, Colors.White);
        this.ID = ID;
    }

    public SimTile(String T, String N,int ID){
        super(T, Colors.White);
        Nombre = N;
        this.ID = ID;
    }
    public SimTile(String T, String N, Colors C,int ID){
        super(T, C);
        Nombre = N;
        this.ID = ID;
    }

    public Tile asTile(){
        return new Tile(this.getTexture(), this.getColor());
    }

    public String getTileType(){
        try{return this.getTags()[0];}
        catch(Exception e){return "";}
        }

    public static SimTile InstanceOnCoords(SimTile tile, int x, int y){
        if (tile.getTags()[0] == "F"){
            Fluid copy = new Fluid(tile.getTexture(), tile.getNombre(), tile.getColor(), tile.getID());
            Fluid c = (Fluid) tile;
            copy.setTags(tile.getTags());
            copy.setCoords(new Point(x, y));
            copy.moved = c.moved;
            return copy;
        }
        else if (tile.getTags()[0] == "G"){
            Generador copy = new Generador(tile.getTexture(), tile.getNombre(), tile.getColor(), tile.getID());
            copy.setTags(tile.getTags());
            copy.setCoords(new Point(x, y));
            return copy;
        }
        else{
            SimTile copy = new SimTile(tile.getTexture(), tile.getNombre(), tile.getColor(), tile.getID());
            copy.setCoords(new Point(x, y));
            copy.setTags(tile.getTags());
            return copy;
        }
    }

    public static SimTile[] LoadTiles(String dbFilepath){
        try{
            if (new File(dbFilepath).exists()){
                String url = "jdbc:sqlite:"+dbFilepath;
                Connection con = DriverManager.getConnection(url);
                Statement cur = con.createStatement();
                ResultSet resultados = cur.executeQuery("SELECT * FROM Tiles");
                List<SimTile> tiles = new ArrayList<>();
                while(resultados.next()){
                    String[] colores = resultados.getString("RGB").split(",");
                    Colors c = new Colors(1, Integer.parseInt(colores[0].trim()),Integer.parseInt(colores[1].trim()),Integer.parseInt(colores[2].trim()));
                    if (resultados.getString("Tipo").equals("Estatico")){
                        SimTile t =  new SimTile(resultados.getString("Textura"), resultados.getString("Nombre"), c, resultados.getInt("ID"));
                        t.setTags(new String[]{"E"});
                        tiles.add(t);
                    }
                    else if (resultados.getString("Tipo").equals("Generador")){
                        Generador t =  new Generador(resultados.getString("Textura"), resultados.getString("Nombre"), c, resultados.getInt("ID"));
                        t.setTags(new String[]{"G"});
                        tiles.add(t);
                    }
                    else if (resultados.getString("Tipo").equals("Fluido")){
                        Fluid f = new Fluid(resultados.getString("Textura"), resultados.getString("Nombre"), c,resultados.getInt("ID"));
                        f.setTags(new String[]{"F"});
                        tiles.add(f);
                    }
                }
                con.close();
                cur.close();
                resultados.close();
                return tiles.toArray(new SimTile[0]);
            }
            throw new SQLException("Database not found");
        }
        catch(SQLException e){
            Engine.LogException(e);
            return null;
        }
    }
    

    public Point getCoords() {
        return this.coords;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public String getNombre() {
        return this.Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
}
