package Editor;

import java.io.IOException;

import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;
import TextEngine.Maps.MapEngine;
import TextEngine.Maps.MapObject;

public class Editor extends Menu{

    private String mode;
    private String filename;

    public Editor(String mode){
        this.mode = mode;
    }

    public Editor(String mode, String filename){
        this.mode = mode;
        this.filename = filename;
    }

    public static void CreateNew(){
        try{
            String name = Keyboard.Scanner("Name of the map: ");
            int WIDTH = Integer.parseInt(Keyboard.Scanner("Set Width: "));
            int HEIGHT = Integer.parseInt(Keyboard.Scanner("Set Height: "));

            MapObject mapa = new MapObject(WIDTH, HEIGHT);
            MapEngine.saveMap(mapa, "./Saves/"+name+".map");
            Load("./Saves/"+name+".map");
        }
        catch(InterruptedException | IOException e){
            Engine.LogException(new IOException("Error with the input of the Keyboard "));
        }
        catch (NumberFormatException e){
            Engine.LogException(new NumberFormatException("The given value isn't an int "));
        }
    }
    public static void Load(String filename){

    }

    @Override
    public void Start() {
        if (mode == "Crear"){
            CreateNew();
        }
        else if (mode == "Cargar"){
            Load(filename);
        }
    }

}
