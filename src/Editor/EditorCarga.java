package Editor;

import java.io.File;

import TextEngine.Colors;
import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;

public class EditorCarga extends Menu{

    String[] Nombres;
    int pos = 0;

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
            Engine.Render();
        }
    }
    @Override
    public String Frame() {
        String res = Colors.Bold.colorize("Selecciona el archivo a cargar: \n");
        int i = 0;
        for (String file : Nombres){
            if (file.contains(".map")){
                if (i != pos){res += file + "\n";}
                else{
                    res += ">   " + Colors.Italic.colorize(file) + "\n";
                }
                i++;
            }
        }
        res += Colors.Bold.colorize("Pulsa ESC para regresar.");
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
    }
}
