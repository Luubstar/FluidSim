package SimObjs;

import java.awt.Point;

import TextEngine.Colors;
import TextEngine.Maps.MapObject;

public class Generador extends SimTile{

    private Fluid fluido = new Fluid("█", "Agua", new Colors(Colors.TYPE_TEXT, 52,116,235), 7);
    private int TiempoGenerador = 1;
    private int currentTick = 0;
    public Generador(int ID){super(ID);}

    public Generador(String T,int ID){super(T,ID);}

    public Generador(String T, String N,int ID){super(T, N,ID);}
    public Generador(String T, String N, Colors C,int ID){super(T,N, C,ID);}

    public void GenerateNew(MapObject mapa){
        
        if(currentTick >= TiempoGenerador){
            if(mapa.isPointValid((int)coords.getX(), (int)coords.getY()+1) && Fluid.isPosMov(mapa, (int)coords.getX(), (int)coords.getY()+1)){
                if (Fluid.isPosMov(mapa, (int)coords.getX(), (int)coords.getY()+1)){
                    fluido.moved = true;
                    fluido.coords = new Point((int)coords.getX(), (int)coords.getY()+1);
                    fluido.setTags(new String[]{"F"});
                    mapa.setTile((int)coords.getX(), (int)coords.getY()+1, SimTile.InstanceOnCoords(fluido,(int)coords.getX(), (int)coords.getY()+1));
                }
            }
            currentTick = 0;
        }
        else{currentTick++;}
    }

}
