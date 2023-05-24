package Editor;

import java.io.File;
import java.io.IOException;

import Sim.SimTile;
import TextEngine.Colors;
import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;
import TextEngine.Maps.MapEngine;
import TextEngine.Maps.MapObject;
import TextEngine.Maps.Tile;

public class Editor extends Menu {

    private String mode;
    private String filename;
    private MapObject mapaCargado;

    Tile[][] casillas;

    long PosX = 0;
    long PosY = 0;
    long LastX = -1;
    long LastY = -1;
    byte currentframe = 0;
    byte INTERVALFRAMES = 40;
    boolean drawCursor = true;
    String cursor = Colors.Red.colorize("X");

    String map = "";

    public Editor(String mode) {this.mode = mode;}

    public Editor(String mode, String filename) {
        this.mode = mode;
        this.filename = filename;
    }

    public void CreateNew() {
        try {
            String name = Keyboard.Scanner("Name of the map: ");
            int WIDTH = Integer.parseInt(Keyboard.Scanner("Set Width: "));
            int HEIGHT = Integer.parseInt(Keyboard.Scanner("Set Height: "));

            Tile[][] tiles = new Tile[HEIGHT][WIDTH];
            for(int i = 0; i < HEIGHT; i++){
                for(int a = 0; a < WIDTH; a++){
                    if(i == 0 || a == WIDTH - 1 || a == 0 || i == HEIGHT -1){tiles[i][a] = new SimTile("█");}
                    else{tiles[i][a] = new SimTile();}
                }}

            MapObject mapa = new MapObject(WIDTH, HEIGHT, tiles);
            MapEngine.saveMap(mapa, "./Saves/" + name + ".map");

            Load("./Saves/" + name + ".map");
        } catch (InterruptedException | IOException e) {
            Engine.LogException(new IOException("Error with the input of the Keyboard "));
            Engine.SetMenu(new EditorMainMenu());
        } catch (NumberFormatException e) {
            Engine.LogException(new NumberFormatException("The given value isn't an int "));
            Engine.SetMenu(new EditorMainMenu());
        }
    }

    public void Load(String filename) {
        try {
            File archivo = new File(filename);
            if (!archivo.exists()) {throw new IOException("File not found");}

            mapaCargado = MapEngine.loadMap(filename);
            casillas = mapaCargado.getTiles();
        } catch (IOException | ClassNotFoundException e) {
            Engine.LogException(e);
            Engine.SetMenu(new EditorMainMenu());
        }
    }

    @Override
    public String Frame() {
        if (mapaCargado != null) {

            if (LastX != PosX || LastY != PosY){
                drawCursor = true;
                currentframe = 0;
                LastX = PosX;
                LastY = PosY;
                map = GenerateMap();
            }
            return map;
            
        } else {
            return "Loading...";
        }
    }

    @Override
    public void Start() {
        if (mode == "Crear") {
            CreateNew();
        } else if (mode == "Cargar") {
            Load(filename);
        }
    }

    @Override
    public void Update() {
        try{Controles();}
        catch(IOException e){Engine.LogException(e);}
        currentframe++;
        if (currentframe >= INTERVALFRAMES){
            currentframe = 0;
            drawCursor = !drawCursor;
            map = GenerateMap();
            Engine.Render();
        }
    }


    public String GenerateMap(){
        String res = "";
        for(int i = 0; i < casillas.length; i++){
            for(int a = 0; a < casillas[0].length; a++){
                if (i == PosY && a == PosX && drawCursor){res += cursor;}
                else{res += casillas[i][a].getTexture();}
            }
            res += "\n";
        }
        return res;
    }

    public void Controles() throws IOException{
        if (Keyboard.IsLastKeyOfType("Escape")) {
            Keyboard.Clear();
            Engine.SetMenu(new EditorMainMenu());
        }
        if (mapaCargado != null){
            if(Keyboard.IsLastKeyValue("W") || Keyboard.IsLastKeyOfType("ArrowUp")){
                Keyboard.Clear();
                PosY--;
                if (PosY < 0){PosY = 0;}
                else{
                    Engine.Render();
                    Keyboard.DetectInput();
                }
            }
            if(Keyboard.IsLastKeyValue("S") || Keyboard.IsLastKeyOfType("ArrowDown")){
                Keyboard.Clear();
                PosY++;
                if (PosY > mapaCargado.getHeight()-1){PosY = mapaCargado.getHeight()-1;}
                else{
                    Engine.Render();
                    Keyboard.DetectInput();
                }
            }
            if(Keyboard.IsLastKeyValue("A") || Keyboard.IsLastKeyOfType("ArrowLeft")){
                Keyboard.Clear();
                PosX--;
                if (PosX < 0){PosX = 0;}
                else{
                    Engine.Render();
                    Keyboard.DetectInput();
                }
            }
            if(Keyboard.IsLastKeyValue("D") || Keyboard.IsLastKeyOfType("ArrowRight")){
                Keyboard.Clear();
                PosX++;
                if (PosX > mapaCargado.getWidth()-1){PosX = mapaCargado.getWidth()-1;}
                else{
                    Engine.Render();
                    Keyboard.DetectInput();
                }
            }
        }
    }
}
