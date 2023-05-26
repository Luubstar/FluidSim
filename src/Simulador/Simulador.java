package Simulador;

import SimObjs.Fluid;
import TextEngine.Menu;
import TextEngine.Maps.MapObject;
import TextEngine.Maps.Tile;

public class Simulador extends Menu{

    MapObject mapa;
    Tile[][] casillas; 

    public Simulador(MapObject mapa){
        this.mapa = mapa;
    }

    @Override
    public void Start() {
        casillas = mapa.getTiles();
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

        int x = 0;
        int y = 0;
        for (Tile[] Fila : casillas ){
            x = 0;
            for (Tile casilla : Fila){
                if (casilla instanceof Fluid){
                    casilla.CheckMovement(mapa);
                }
                x++;
            }
            y++;
        }
    }
    
}
