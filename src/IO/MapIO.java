package IO;

import java.io.File;
import java.io.IOException;

import Editor.EditorMainMenu;
import TextEngine.Engine;
import TextEngine.Maps.MapEngine;
import TextEngine.Maps.MapObject;

public class MapIO {
    
    public static MapObject Load(String filename) {
        try {
            File archivo = new File(filename);
            if (!archivo.exists()) {throw new IOException("File not found");}

            return MapEngine.loadMap(filename);
        } catch (IOException | ClassNotFoundException e) {
            Engine.LogException(e);
            Engine.SetMenu(new EditorMainMenu());
            return null;
        }
    }

    public static void Save(MapObject mapa, String name){
        try{
            File archivos = new File("./Saves");

            if (!archivos.exists()){
                archivos.mkdirs();}
            MapEngine.saveMap(mapa, name);
        }
        catch(IOException e){Engine.LogException(e);}
    }
}

