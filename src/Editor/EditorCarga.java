package Editor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import TextEngine.Colors;
import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;

public class EditorCarga extends Menu{

    String[] Nombres;
    int pos = 0;
    Colors bckSelected =  new Colors(Colors.TYPE_TEXT, 80,80,80).and(new Colors(Colors.TYPE_BACKGROUND, 180,180,180)).and(Colors.Italic);

    @Override
    public void Start() {
        File archivos = new File("./Saves");

        if (!archivos.exists()){
            archivos.mkdirs();
            Nombres = new String[0];
            Engine.Render();
        }
        else{
            Nombres = archivos.list();
            List<String> nombresClear = new ArrayList<>();
            for (String n : Nombres){
                if ( n.contains(".map")){nombresClear.add(n);}
            }
            Nombres = nombresClear.toArray(new String[0]);
            Engine.Render();
        }
    }
    @Override
    public String Frame() {
        String res = Colors.Bold.colorize("Selecciona el archivo a cargar: \n");
        int i = 0;
        for (String file : Nombres){
            if (i != pos){res += file + "\n";}
            else{res += bckSelected.colorize( "▶ " + Colors.Italic.colorize(file))+ "\n";}
            i++;
        }
        res += Colors.Bold.colorize("Pulsa ESC para regresar y x para eliminar archivos");
        return res;
    }

    @Override
    public void Update() {

        if(Keyboard.IsLastKeyValue("S") || Keyboard.IsLastKeyOfType("ArrowDown")){
            pos++;
            if (pos >= Nombres.length){pos = 0;}
            Engine.Render();
            Keyboard.Clear();
        }
        else if(Keyboard.IsLastKeyValue("W") || Keyboard.IsLastKeyOfType("ArrowUp")){
            pos--;
            if(pos < 0){pos = Nombres.length-1;}
            Engine.Render();
            Keyboard.Clear();
        }
        else if (Keyboard.IsLastKeyOfType("Enter")){
            Keyboard.Clear();
            Engine.SetMenu(new Editor("Cargar", "./Saves/" + Nombres[pos]));
        }
        else if (Keyboard.IsLastKeyOfType("Escape")){
            Keyboard.Clear();
            Engine.SetMenu(new EditorMainMenu());
        }
        else if (Keyboard.IsLastKeyValue("x")){
            Keyboard.Clear();
            Engine.clearConsole();

            boolean elejido = false;
            boolean accion = false;
            String lastMensaje = "";
            while (!elejido){
                try{
                    String opcion = Keyboard.Scanner(lastMensaje + "¿Deseas eliminar el fichero " + Nombres[pos] + "? (s/n)" );
                    if (opcion.toLowerCase().equals("s")){accion = true; elejido = true;}
                    else if (opcion.toLowerCase().equals("n")) {accion = false; elejido = true;}
                    else{throw new IllegalArgumentException(opcion.toLowerCase() + " no es una entrada válida");}
                }
                catch(IOException | InterruptedException e){elejido = true;}
                catch(IllegalArgumentException e){lastMensaje = e.getMessage() + "\n";}
            }

            if(accion){
                File archivo = new File("./Saves/" + Nombres[pos]);
                archivo.delete();
                Start();
                pos = 0;
            }
        }
    }
}
