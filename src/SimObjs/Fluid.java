package SimObjs;

import java.awt.Point;

import TextEngine.Colors;
import TextEngine.Maps.MapObject;

public class Fluid extends SimTile{
    public static final String[] SizeTexture = new String[]{"▁", "▂","▃","▄","▅","▆", "▇","█"};


    public boolean moved;
    public float masa = 1000;
    public float densidad = 1000;
    public float viscosidad = 100;
    public float TensionS = 100;

    public Fluid(int ID){super(ID);}

    public Fluid(String T,int ID){super(T,ID);}

    public Fluid(String T, String N,int ID){super(T, N,ID);}
    public Fluid(String T, String N, Colors C,int ID){super(T,N, C,ID);}

    public void setPropiedades(float d, float v, float t){
        masa = d;
        densidad = d;
        viscosidad = v;
        TensionS = t;
    }

    public void setPropiedades(String p){
        String[] datos = p.split("&&");
        masa = Float.parseFloat(datos[0]);
        densidad = Float.parseFloat(datos[1]);
        viscosidad = Float.parseFloat(datos[2]);
        TensionS = Float.parseFloat(datos[3]);
    }

    public String getPropiedades(){
        return masa + "&&" + densidad + "&&" + viscosidad + "&&" + TensionS;
    }

    public static void setTexuraPorMasa(Fluid f){
        if (f.masa > 0 && f.masa <= f.densidad){
            float densidadStep = f.densidad/8;
            float masa = f.masa;

            String textura = SizeTexture[(int) Math.ceil(masa/densidadStep)-1];
            f.setTexture(textura);
        }
    }

    public void CheckMovement(MapObject mapa, boolean switches){

        int OriginX = (int) coords.getX();
        int OriginY = (int) coords.getY();

        if (!moved){
            //Si no tiene nada abajo, cae
            if (isPosMov(mapa, OriginX, OriginY+1, this)){
                MoveTo(mapa, OriginX, OriginY+1, OriginX, OriginY,switches);
            }
            //Si tiene algo abajo, intenta irse a los lados, si tiene los dos vacíos, intenta ir a uno de los dos
            else if(isPosMov(mapa, OriginX+1, OriginY, this) && !isPosMov(mapa, OriginX-1, OriginY, this)){
                MoveTo(mapa, OriginX+1, OriginY, OriginX, OriginY,switches);}
            else if (!isPosMov(mapa, OriginX+1, OriginY, this) && isPosMov(mapa, OriginX-1, OriginY, this)){
                MoveTo(mapa, OriginX-1, OriginY, OriginX, OriginY,switches);
            }

            else if(isPosMov(mapa, OriginX+1, OriginY, this) && isPosMov(mapa, OriginX-1, OriginY, this)){
                int temp = (Math.random() <= 0.5) ? 1 : 2;
                if (temp == 1){MoveTo(mapa, OriginX+1, OriginY, OriginX, OriginY,switches);}
                else{MoveTo(mapa, OriginX-1, OriginY, OriginX, OriginY,switches);}
            }
        }
    }
    

    public static boolean isPosMov(MapObject mapa,int x, int y, Fluid a){
        SimTile Tile = (SimTile) mapa.getTile(x, y);
        if (mapa.isPointValid(x,y) && (Tile.getTags() == null || Tile.getTags().length == 0)){return true;}
        if (mapa.isPointValid(x, y) && (Tile.getTileType() == "F")){
            Fluid T = (Fluid) Tile;
            if (T.masa < T.densidad && T.ID == a.ID){return true;}
        }
        return false;
    }

    public void MoveTo(MapObject mapa, int x, int y, int originX, int originY, boolean switches){
        SimTile lastTile = (SimTile) mapa.getTile(x, y);
        if (lastTile.getTileType() == "F" && lastTile.getID() == this.ID){ 
            Fluid T = (Fluid) lastTile;
            if (T.masa < T.densidad ){
                T.masa += this.masa;
                this.masa = 0;
                if (T.masa > T.densidad){
                    this.masa = T.masa-T.densidad;
                    T.masa = T.densidad;
                }
                setTexuraPorMasa(T);
                setTexuraPorMasa(this);
                if (this.masa <= 0){
                    mapa.setTile(originX, originY, new SimTile(0));
                    return;
                }
            }
        }

        SimTile tile = (SimTile) mapa.getTile(x, y);
        this.setCoords(new Point(x, y));
        mapa.setTile(x, y, this);
        if (switches){
            tile.setCoords(new Point(originX,originY));
            mapa.setTile(originX, originY, tile);}
        moved = true;
        return;
        
    }
}
