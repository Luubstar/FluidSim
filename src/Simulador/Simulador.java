package Simulador;

import SimObjs.Fluid;
import SimObjs.Generador;
import SimObjs.SimTile;
import Start.StartMenu;
import TextEngine.Colors;
import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;
import TextEngine.Debug.Profiler;
import TextEngine.Maps.MapObject;
import TextEngine.Maps.Tile;

public class Simulador extends Menu{

    private MapObject mapa;
    private Tile[][] casillas; 
    private byte currentPhysicTick = 0;
    private byte PhysicTickPerMov = 5;
    private byte currentframe = 0;
    private final byte INTERVALFRAMES = 40;
    private boolean drawCursor;
    private String mapaS;
    private String cursor = Colors.Red.colorize("X");
    private SimTile backgroundTile;
    private SimTile LastbackgroundTile;

    int PosX = 0;
    int PosY = 0;
    int LastX = -1;
    int LastY = -1;

    public Simulador(MapObject mapa){
        this.mapa = mapa;
    }

    @Override
    public void Start() {
        casillas = mapa.getTiles().clone();
    }

    @Override
    public String Frame() {
        String res = "";
        if (mapa != null) {

            backgroundTile =  (SimTile) mapa.getTile(PosX, PosY);
            if (LastX != PosX || LastY != PosY || !LastbackgroundTile.equals(backgroundTile)){
                LastbackgroundTile = backgroundTile;
                if (backgroundTile.getID() > 0){
                    int[] RGB = Colors.ReturnRGB(backgroundTile.getColor());
                    cursor = Colors.CreateBackgroundColor(RGB[0], RGB[1], RGB[2]).and(Colors.Red).colorize(Colors.clearColor(cursor));
                }
                else{
                    if (cursor != Colors.Red.colorize(cursor)){
                    cursor = Colors.Red.colorize(Colors.clearColor(cursor));}
                }
                drawCursor = true;
                currentframe = 0;
                LastX = PosX;
                LastY = PosY;
                mapaS = GenerateMap();
            }   

            if (backgroundTile != null && backgroundTile.getNombre() != null){
                res = "Datos de la casilla: " + backgroundTile.getNombre()  + "\n";
                if (backgroundTile instanceof Fluid){
                    Fluid c = (Fluid) backgroundTile;
                    res += "Densidad: " +c.densidad + "\n";
                }
            }
            res +=  Colors.CreateTextColor(130,130,130).colorize("━".repeat(Engine.getWidth()));

            return res + "\n" + mapaS;
        }
        else{return "Loading...";}
    }

    @Override
    public void Update() {
        PhysicEngine();
        Controles();
        currentframe++;
        if (currentframe >= INTERVALFRAMES){
            currentframe = 0;
            drawCursor = !drawCursor;
            Engine.Render();
        }
        mapaS = GenerateMap();
    }

    public void PhysicEngine(){
        Profiler.StartMeasure("Fisicas");
        if(currentPhysicTick >= PhysicTickPerMov){
            currentPhysicTick = 0;

            for (Tile[] Fila : casillas ){
                for (Tile casilla : Fila){
                    if (casilla instanceof Fluid){
                        Fluid c = (Fluid) casilla;
                        if (c.getID() > 0 && c.getNombre() != null ){c.CheckMovement(mapa,true);}
                    }
                    else if(casilla instanceof Generador){
                        Generador c = (Generador) casilla;
                        if (c.getID() > 0 && c.getNombre() != null ){c.GenerateNew(mapa);}
                    }
                }
            }

            for (Tile[] Fila : casillas ){
                for (Tile casilla : Fila){
                    if (casilla instanceof Fluid){
                        Fluid c = (Fluid) casilla;
                        if (c.getID() > 0 && c.getNombre() != null ){c.moved=false;}
                    }
                }
            }

            Engine.Render();
            casillas = mapa.getTiles().clone();
        }
        else{currentPhysicTick++;}
        Profiler.EndMeasure("Fisicas");
    }

    public String GenerateMap(){
        String res = "";
        
        for(int i = 0; i < casillas.length; i++){
            for(int a = 0; a < casillas[0].length; a++){
                if (i == PosY && a == PosX && drawCursor){res += cursor;}
                else{res += casillas[i][a].toString();}
            }
            res += "\n";
        }
        return res;
    }

    public void Controles(){
        if (Keyboard.IsLastKeyOfType("Escape")) {
            Keyboard.Clear();
            Engine.SetMenu(new StartMenu());
        }
        else if (Keyboard.IsLastKeyValue("?")){
            Keyboard.Clear();
            Engine.SetMenu(new SimuladorControles(this));
        }

        if (mapa != null){
            if(Keyboard.IsLastKeyValue("W") || Keyboard.IsLastKeyOfType("ArrowUp")){
                Keyboard.Clear();
                PosY--;
                if (PosY < 0){PosY = 0;}
                else{ Engine.Render(); }
            }
            else if(Keyboard.IsLastKeyValue("S") || Keyboard.IsLastKeyOfType("ArrowDown")){
                Keyboard.Clear();
                PosY++;
                if (PosY > mapa.getHeight()-1){PosY = mapa.getHeight()-1;}
                else{
                    Engine.Render();
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
                if (PosX > mapa.getWidth()-1){PosX = mapa.getWidth()-1;}
                else{ Engine.Render(); }
            }
        }
    }
    
}
