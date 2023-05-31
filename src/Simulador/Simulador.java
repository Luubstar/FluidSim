package Simulador;

import SimObjs.Fluid;
import SimObjs.Generador;
import Start.StartMenu;
import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;
import TextEngine.Maps.MapObject;
import TextEngine.Maps.Tile;

public class Simulador extends Menu{

    MapObject mapa;
    Tile[][] casillas; 
    int currentPhysicTick = 0;
    int PhysicTickPerMov = 10;

    public Simulador(MapObject mapa){
        this.mapa = mapa;
    }

    @Override
    public void Start() {
        casillas = mapa.getTiles().clone();
    }

    @Override
    public String Frame() {
        return GenerateMap();
    }

    @Override
    public void Update() {
        //Controles();
        //CheckNotification();
        /*currentframe++;
        if (currentframe >= INTERVALFRAMES){
            currentframe = 0;
            drawCursor = !drawCursor;
            map = GenerateMap();
            Engine.Render();
        }*/

        PhysicEngine();

        if (Keyboard.IsLastKeyOfType("Escape")){
            Keyboard.Clear();
            Engine.SetMenu(new StartMenu());
            Engine.Render();
        }
    }

    public void PhysicEngine(){
        if(currentPhysicTick >= PhysicTickPerMov){
            currentPhysicTick = 0;

            for (Tile[] Fila : casillas ){
                for (Tile casilla : Fila){
                    if (casilla instanceof Fluid){
                        Fluid c = (Fluid) casilla;
                        if (c.getID() > 0 && c.getNombre() != null ){c.CheckMovement(mapa);}
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
    }

    public String GenerateMap(){
        String res = "";
        
        for(int i = 0; i < casillas.length; i++){
            for(int a = 0; a < casillas[0].length; a++){
              
                res += casillas[i][a].toString();
            }
            res += "\n";
        }
        return res;
    }
    
}
