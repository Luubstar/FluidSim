package Sim;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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

    public SimTile(){
        super(" ", Colors.White);
    }

    public SimTile(String T){
        super(T, Colors.White);
    }

    public SimTile(String T, String N){
        super(T, Colors.White);
        Nombre = N;
    }

    public Tile asTile(){
        return new Tile(this.getTexture(), this.getColor());
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
                    tiles.add( new SimTile(resultados.getString("Textura"), resultados.getString("Nombre")));
                }
                con.close();
                cur.close();
                resultados.close();
                return tiles.toArray(new SimTile[0]);
            }
            return null;
        }
        catch(SQLException e){
            Engine.LogException(e);
            return null;
        }
    }
    
}
