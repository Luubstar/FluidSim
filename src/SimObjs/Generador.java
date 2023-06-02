package SimObjs;

import java.awt.Point;

import TextEngine.Colors;
import TextEngine.Maps.MapObject;

public class Generador extends SimTile{

    private Fluid fluido = new Fluid("█", "Agua", new Colors(Colors.TYPE_TEXT, 52,116,235), 7);
    private int TiempoGenerador = 1;
    private int currentTick = 0;
    private boolean hasFluidOver = false;
    private Fluid FOverGenerator;
    private Colors OC;
    public Generador(int ID){super(ID);}

    public Generador(String T,int ID){super(T,ID);}

    public Generador(String T, String N,int ID){super(T, N,ID);}
    public Generador(String T, String N, Colors C,int ID){super(T,N, C,ID); OC = C;}

    public void GenerateNew(MapObject mapa){
        fluido.moved = true;
        fluido.coords = new Point((int)coords.getX(), (int)coords.getY()+1);
        fluido.setTags(new String[]{"F"});
        fluido.masa = 100;
        Fluid.setTexuraPorMasa(fluido);
        if (hasFluidOver == true){
            FOverGenerator.CheckMovement(mapa, false);
            if (!FOverGenerator.coords.equals(this.coords)){
                hasFluidOver = false;
                this.setTexture(OC.colorize(Colors.clearColor(this.getTexture())));
                FOverGenerator = null;
            }
        }
        else if(currentTick >= TiempoGenerador){
            if(mapa.isPointValid((int)coords.getX(), (int)coords.getY()+1) && Fluid.isPosMov(mapa, (int)coords.getX(), (int)coords.getY()+1, fluido)){
                mapa.setTile((int)coords.getX(), (int)coords.getY()+1, SimTile.InstanceOnCoords(fluido,(int)coords.getX(), (int)coords.getY()+1));
            }
            else if (!hasFluidOver){
                hasFluidOver = true;
                fluido.moved = false;
                int[] RGB = Colors.ReturnRGB(fluido.getColor());
                Colors f = Colors.CreateBackgroundColor(RGB[0], RGB[1], RGB[2]);
                this.setTexture( f.colorize(this.getTexture()));
                FOverGenerator = (Fluid) SimTile.InstanceOnCoords(fluido,(int)coords.getX(), (int)coords.getY());
            }
            currentTick = 0;
        }
        else{currentTick++;}
    }
}
