package IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import SimObjs.Fluid;
import SimObjs.Generador;
import SimObjs.SimTile;
import TextEngine.Engine;
import TextEngine.Maps.MapObject;
import TextEngine.Maps.Tile;

public class MapIO {
    
    public static MapObject Load(String filename) {
        try {
            File archivo = new File(filename);
            if (!archivo.exists()) {throw new IOException("File not found");}

            SimTile[] casillas = SimTile.LoadTiles("Tiles.db");

            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            int Width = Integer.parseInt(line.split(":")[1].split("\n")[0]);
            line = bufferedReader.readLine();
            int Height = Integer.parseInt(line.split(":")[1].split("\n")[0]);

            line = bufferedReader.readLine();

            SimTile[][] CasillasNuevas = new SimTile[Height][Width];
            int i = 0;
            int a = 0;
            for(String linea : line.split(";")){
                a = 0;
                if (linea.length() >1){
                    String[] IDs = linea.split(",");
                    for (String ID : IDs){
                        String Tipo = ID.trim().split("\\(")[1].split("\\)")[0];
                        String DatosExtra = ID.trim();
                        ID = DatosExtra.split("\\(")[0];
                        int id = Integer.parseInt(ID);

                        if (Tipo.equals("E")){
                            if(id <= 0 ){CasillasNuevas[i][a] = new SimTile(0);}
                            else{CasillasNuevas[i][a] = SimTile.InstanceOnCoords(casillas[id], a, i);}
                        }
                        else if (Tipo.equals("F")){
                            if(id <= 0 ){CasillasNuevas[i][a] = new SimTile(0);}
                            else{        
                                String DatosFluido = DatosExtra.split("\\[")[1].split("\\]")[0];
                                Fluid liquido = (Fluid) Fluid.InstanceOnCoords(casillas[Integer.parseInt(ID.replace("[" +DatosFluido+"]",""))], a, i);
                                liquido.setPropiedades(DatosFluido);
                                CasillasNuevas[i][a] = liquido;}
                        }
                        else if (Tipo.equals("G")){
                            if(id <= 0 ){CasillasNuevas[i][a] = new SimTile(0);}
                            else{CasillasNuevas[i][a] = Generador.InstanceOnCoords(casillas[id], a, i);}
                        }
                        else{
                            if(id <= 0 ){CasillasNuevas[i][a] = new SimTile(0);}
                            else{CasillasNuevas[i][a] = SimTile.InstanceOnCoords(casillas[id], a, i);}
                        }
                        a++;
                    }
                }
                i++;
            }
            MapObject mapa = new MapObject(Width, Height,CasillasNuevas );
            bufferedReader.close();
            return mapa;

        } catch (IOException e) {
            Engine.LogException(e);
            return null;
        }
    }

    public static void Save(MapObject mapa, String name){
        try{
            File archivos = new File("./Saves");

            if (!archivos.exists()){
                archivos.mkdirs();}

            String FileData = "";
            FileData += "Width:" + mapa.getWidth() + "\n";
            FileData += "Height:" +  mapa.getHeight() + "\n";
            Tile[][] tiles = mapa.getTiles();
            for(Tile[] row : tiles){
                for (Tile casilla: row){
                    if (casilla instanceof Fluid){
                        Fluid c = (Fluid) casilla;
                        String SpecialData = "["+c.getPropiedades()+"]";
                        FileData += c.getID() + "(F)"+SpecialData+",";
                    }
                    else if (casilla instanceof Generador){
                        Generador c = (Generador) casilla;
                        FileData += c.getID() + "(G),";
                    }
                    else if (casilla instanceof SimTile){
                        SimTile c = (SimTile) casilla;
                        FileData += c.getID() + "(E),";
                    }
                }
                FileData = FileData.substring(0, FileData.length()-1) +  ";";
            }
            FileWriter fileWriter = new FileWriter( name);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(FileData);
            printWriter.close();

        }
        catch(IOException e){Engine.LogException(e);}
    }

    
}

