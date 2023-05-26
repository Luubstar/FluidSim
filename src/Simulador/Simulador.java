package Simulador;

import SimObjs.Fluid;
import Start.StartMenu;
import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;
import TextEngine.Debug.Debug;
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
        casillas = mapa.getTiles();
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
                    Debug.LogMessage(casilla.getTexture());
                    if (casilla instanceof Fluid){
                        Fluid c = (Fluid) casilla;
                        Debug.LogMessage("Ticking");
                        c.CheckMovement(mapa);
                    }
                }
            }
            Engine.Render();

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
