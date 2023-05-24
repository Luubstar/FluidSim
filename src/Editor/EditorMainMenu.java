package Editor;

import Start.StartMenu;
import TextEngine.Colors;
import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;
import TextEngine.Engine.HAling;

public class EditorMainMenu extends Menu {

    private static final String Titulo = """

███████╗██████╗ ██╗████████╗ ██████╗ ██████╗ 
 ██╔════╝██╔══██╗██║╚══██╔══╝██╔═══██╗██╔══██╗
 █████╗  ██║  ██║██║   ██║   ██║   ██║██████╔╝
 ██╔══╝  ██║  ██║██║   ██║   ██║   ██║██╔══██╗
 ███████╗██████╔╝██║   ██║   ╚██████╔╝██║  ██║
 ╚══════╝╚═════╝ ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝
    """;

    String[] opciones = {"Crear", "Cargar", "Volver"};

    private static final Colors TitleColor = Colors.Cyan;
    private static final Colors SelectedColor = Colors.Italic;
    private static final Colors fontColor = Colors.White;

    int pos = 0;

    @Override
    public String Frame() {
        String res = "";
        for( int i = 0; i < opciones.length; i++){
            if (i== pos){res += "> " +SelectedColor.colorize(opciones[i] + " \n");}
            else{res += fontColor.colorize(opciones[i] + " \n"); }
        }
        return  Engine.VerticalAling(null, Engine.HorizontalAling(HAling.CENTER,   TitleColor.colorize(Titulo) + "\n" +res));
    }

    @Override
    public void Update() {

        if (Keyboard.IsLastKeyOfType("Character") && Character.isDigit( Keyboard.getKeyCharacter())){
            int val = Integer.parseInt(Keyboard.getKeyValue()) - 1;
            if(val >= 0 && val <= opciones.length - 1){
                pos = val;
                Keyboard.Clear();
                Engine.Render();
            }
        }
        else if(Keyboard.IsLastKeyValue("S") || Keyboard.IsLastKeyOfType("ArrowDown")){
            pos++;
            if (pos >= opciones.length){pos = 0;}
            Engine.Render();
            Keyboard.Clear();
        }
        else if(Keyboard.IsLastKeyValue("W") || Keyboard.IsLastKeyOfType("ArrowUp")){
            pos--;
            if(pos < 0){pos = opciones.length-1;}
            Engine.Render();
            Keyboard.Clear();
        }
        else if (Keyboard.IsLastKeyOfType("Enter")){
            Keyboard.Clear();
            if (pos == 2){Engine.SetMenu(new StartMenu());}
            else if (pos == 0){
                Engine.SetMenu(new Editor("Crear"));
            }
            else if (pos == 1){Engine.SetMenu(new EditorCarga());}
        }
    }
    
    

}
