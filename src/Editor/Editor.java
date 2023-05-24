package Editor;

import java.io.File;
import java.io.IOException;

import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;
import TextEngine.Maps.MapEngine;
import TextEngine.Maps.MapObject;

public class Editor extends Menu {

    private String mode;
    private String filename;
    private MapObject mapaCargado;

    public Editor(String mode) {
        this.mode = mode;
    }

    public Editor(String mode, String filename) {
        this.mode = mode;
        this.filename = filename;
    }

    public void CreateNew() {
        try {
            String name = Keyboard.Scanner("Name of the map: ");
            int WIDTH = Integer.parseInt(Keyboard.Scanner("Set Width: "));
            int HEIGHT = Integer.parseInt(Keyboard.Scanner("Set Height: "));

            MapObject mapa = new MapObject(WIDTH, HEIGHT);
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
            if (!archivo.exists()) {
                throw new IOException("File not found");
            }
            mapaCargado = MapEngine.loadMap(filename);
        } catch (IOException | ClassNotFoundException e) {
            Engine.LogException(e);
            Engine.SetMenu(new EditorMainMenu());
        }
    }

    @Override
    public String Frame() {
        if (mapaCargado != null) {
            return MapEngine.printMap(mapaCargado);
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
        if (Keyboard.IsLastKeyOfType("Escape")) {
            Keyboard.Clear();
            Engine.SetMenu(new EditorMainMenu());
        }
    }

}
