package SimObjs;

import TextEngine.Colors;
import TextEngine.Maps.MapObject;

public class Fluid extends SimTile{
    public Fluid(int ID){super(ID);}

    public Fluid(String T,int ID){super(T,ID);}

    public Fluid(String T, String N,int ID){super(T, N,ID);}
    public Fluid(String T, String N, Colors C,int ID){super(T,N, C,ID);}

    public void CheckMovement(MapObject mapa){
        int OriginX = (int) coords.getX();
        int OriginY = (int) coords.getY();
        if(mapa.getTile(OriginX, OriginY-1).getTags().length == 0){
            SimTile lastTile = (SimTile) mapa.getTile(OriginX, OriginY-1);
            mapa.setTile(OriginX, OriginY-1, mapa.getTile(OriginX, OriginY));
            mapa.setTile(OriginX, OriginY, lastTile);
        }
    }
}
