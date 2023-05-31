package SimObjs;

import java.awt.Point;

import TextEngine.Colors;
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

        if (!moved){
            //Si no tiene nada abajo, cae
            if (isPosMov(mapa, OriginX, OriginY+1)){
                MoveTo(mapa, OriginX, OriginY+1, OriginX, OriginY);
            }
            //Si tiene algo abajo, intenta irse a los lados, si tiene los dos vacíos, intenta ir a uno de los dos
            else if(isPosMov(mapa, OriginX+1, OriginY) && !isPosMov(mapa, OriginX-1, OriginY)){
                MoveTo(mapa, OriginX+1, OriginY, OriginX, OriginY);}
            else if (!isPosMov(mapa, OriginX+1, OriginY) && isPosMov(mapa, OriginX-1, OriginY)){
                    MoveTo(mapa, OriginX-1, OriginY, OriginX, OriginY);
            }
            else if(isPosMov(mapa, OriginX+1, OriginY) && isPosMov(mapa, OriginX-1, OriginY)){
                    int temp = (Math.random() <= 0.5) ? 1 : 2;
                    if (temp == 1){MoveTo(mapa, OriginX+1, OriginY, OriginX, OriginY);}
                    else{MoveTo(mapa, OriginX-1, OriginY, OriginX, OriginY);}
            }

        }
    }
    

    public static boolean isPosMov(MapObject mapa,int x, int y){
        return (mapa.isPointValid(x,y)&& mapa.getTile(x, y).getTags() == null ||  mapa.getTile(x, y).getTags().length == 0);
    }

    public void MoveTo(MapObject mapa, int x, int y, int originX, int originY){
        SimTile lastTile = (SimTile) mapa.getTile(x, y);

        if(mapa.getTile(x, y).getTags() == null || mapa.getTile(x, y).getTags().length == 0){
            if (lastTile.getTileType() == "F"){/* TODO: Se ha de bloquear porque ahora no se mezclan
                Fluid tile = (Fluid) mapa.getTile(OriginX, OriginY+1);
                Debug.LogMessage("Moving: " + tile.toString());
                this.setCoords(new Point(OriginX, OriginY+1));
                tile.setCoords(new Point(OriginX, OriginY));
                mapa.setTile(OriginX, OriginY+1, this);
                mapa.setTile(OriginX, OriginY, tile);
                moved = true;*/
            }
            else{
                SimTile tile = (SimTile) mapa.getTile(x, y);
                this.setCoords(new Point(x, y));
                tile.setCoords(new Point(originX,originY));
                mapa.setTile(x, y, this);
                mapa.setTile(originX, originY, tile);
                moved = true;
            }
        }
    }
}
