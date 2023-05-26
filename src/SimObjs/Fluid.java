package SimObjs;

import TextEngine.Colors;
import TextEngine.Debug.Debug;
import TextEngine.Maps.MapObject;

public class Fluid extends SimTile{
    public boolean moved;
    public Fluid(int ID){super(ID);}

    public Fluid(String T,int ID){super(T,ID);}

    public Fluid(String T, String N,int ID){super(T, N,ID);}
    public Fluid(String T, String N, Colors C,int ID){super(T,N, C,ID);}

    public void CheckMovement(MapObject mapa){

        int OriginX = (int) coords.getX();
        int OriginY = (int) coords.getY();

        if (mapa.isPointValid(OriginX, OriginY+1)){
            if(mapa.getTile(OriginX, OriginY+1).getTags() == null || mapa.getTile(OriginX, OriginY+1).getTags().length == 0){
                SimTile lastTile = (SimTile) mapa.getTile(OriginX, OriginY+1);
                Debug.LogMessage("Moving: " + lastTile.toString());
                mapa.setTile(OriginX, OriginY+1, this);
                mapa.setTile(OriginX, OriginY, lastTile);
            }
        }
    }
}
