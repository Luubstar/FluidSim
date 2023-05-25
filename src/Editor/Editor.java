package Editor;

import java.io.File;
import java.io.IOException;
import Sim.SimTile;
import TextEngine.Colors;
import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;
import TextEngine.Maps.MapObject;
import TextEngine.Maps.Tile;
import IO.MapIO;

public class Editor extends Menu {

    private String mode;
    private String filename;
    private MapObject mapaCargado;

    private String Notification = "Pulsa ? para ver los controles";
    private String tutorial = "Pulsa ? para ver los controles";
    private int NotifactionChangerSteps = 300;
    private int NotificationChangerCurrentSteps = 0;

    
    Tile[][] casillas;
    SimTile[] pinceles;
    int pincelSeleccionado;

    int PosX = 0;
    int PosY = 0;
    int LastX = -1;
    int LastY = -1;
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
            String res = Notification + "\nPincel actual: \n" + pinceles[pincelSeleccionado].Nombre + "("+ pinceles[pincelSeleccionado].getTexture()+")\n";
            res +=  Colors.CreateTextColor(130,130,130).colorize("━".repeat(Engine.getWidth()));
            return res + "\n" + map;
            
        } else {
            return "Loading...";
        }   
    }

    @Override
    public void Start() {
        if (mode == "Crear") {
            CreateNew();
        } else if (mode == "Cargar") {
            mapaCargado = MapIO.Load(filename);
            casillas = mapaCargado.getTiles();
        }
        pinceles = SimTile.LoadTiles("Tiles.db");
    }

    @Override
    public void Update() {
        Controles();
        CheckNotification();
        currentframe++;
        if (currentframe >= INTERVALFRAMES){
            currentframe = 0;
            drawCursor = !drawCursor;
            map = GenerateMap();
            Engine.Render();
        }
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
            MapIO.Save(mapa, "./Saves/"+name+".map");
            filename = name + ".map";
            mapaCargado = MapIO.Load("./Saves/" + name + ".map");
            casillas = mapaCargado.getTiles();

        } catch (InterruptedException | IOException e) {
            Engine.LogException(new IOException("Error with the input of the Keyboard "));
            Engine.SetMenu(new EditorMainMenu());
        } catch (NumberFormatException e) {
            Engine.LogException(new NumberFormatException("The given value isn't an int "));
            Engine.SetMenu(new EditorMainMenu());
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

    public void CheckNotification(){
        if (Notification != tutorial){
            if(NotificationChangerCurrentSteps < NotifactionChangerSteps){
                NotificationChangerCurrentSteps++;
            }
            else{
                NotificationChangerCurrentSteps = 0;
                Notification = tutorial;
            }
        }
    }

    public void Controles(){
        if (Keyboard.IsLastKeyOfType("Escape")) {
            Keyboard.Clear();
            Engine.SetMenu(new EditorMainMenu());
        }
        else if (Keyboard.IsLastKeyValue("?")){
            Keyboard.Clear();
            Engine.SetMenu(new EditorControles(this));
        }

        if (mapaCargado != null){
            if(Keyboard.IsLastKeyValue("W") || Keyboard.IsLastKeyOfType("ArrowUp")){
                Keyboard.Clear();
                PosY--;
                if (PosY < 0){PosY = 0;}
                else{ Engine.Render(); }
            }
            else if(Keyboard.IsLastKeyValue("S") || Keyboard.IsLastKeyOfType("ArrowDown")){
                
                if (Keyboard.getKeyCharacter() == 'S' && !Keyboard.isControlPressed()){
                    Keyboard.Clear();
                    MapIO.Save(mapaCargado, filename);
                    filename = filename.replace("./Saves/", "Saves/");
                    File archivo = new File(filename);
                    Notification = "Mapa guardado como "+archivo.getAbsolutePath();
                    NotificationChangerCurrentSteps = 0;
                }
                else{
                    Keyboard.Clear();
                    PosY++;
                    if (PosY > mapaCargado.getHeight()-1){PosY = mapaCargado.getHeight()-1;}
                    else{
                        Engine.Render();
                    }
                }
            }
            else if(Keyboard.IsLastKeyValue("A") || Keyboard.IsLastKeyOfType("ArrowLeft")){
                Keyboard.Clear();
                PosX--;
                if (PosX < 0){PosX = 0;}
                else{ Engine.Render(); }
            }
            else if(Keyboard.IsLastKeyValue("D") || Keyboard.IsLastKeyOfType("ArrowRight")){
                Keyboard.Clear();
                PosX++;
                if (PosX > mapaCargado.getWidth()-1){PosX = mapaCargado.getWidth()-1;}
                else{ Engine.Render(); }
            }

            else if (Keyboard.IsLastKeyValue("E")){
                Keyboard.Clear();
                pincelSeleccionado++;
                if (pincelSeleccionado > pinceles.length-1){pincelSeleccionado = 0;}
                Engine.Render();
            }
            else if (Keyboard.IsLastKeyValue("Q")){
                Keyboard.Clear();
                pincelSeleccionado--;
                if (pincelSeleccionado < 0){pincelSeleccionado = pinceles.length-1;}
                Engine.Render();
            }
            else if (Keyboard.getKeyCharacter() == 'G')
                {
                    try{
                        Keyboard.Clear();
                        Engine.clearConsole();
                        String newname ="./Saves/" + Keyboard.Scanner("Introduzca el nombre para el archivo: ") + ".map";
                        MapIO.Save(mapaCargado, newname);
                        File archivo = new File(newname.replace("./Saves/", "Saves/"));
                        Notification = "Mapa guardado en " +archivo.getAbsolutePath();
                        NotificationChangerCurrentSteps = 0;
                    }
                    catch(IOException | InterruptedException e){
                        Engine.LogException(e);
                        Notification = "Error al intentar guardar";
                    }
                }

            else if (Keyboard.IsLastKeyOfType("Enter"))
            {
                Keyboard.Clear();
                mapaCargado.setTile(PosX, PosY, pinceles[pincelSeleccionado].asTile());
                drawCursor = true;
                currentframe = 0;
                LastX = PosX;
                LastY = PosY;
                map = GenerateMap();
                Engine.Render();
            }

        }
    }
}