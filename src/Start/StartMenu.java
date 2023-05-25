package Start;

import Editor.EditorMainMenu;
import TextEngine.Colors;
import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;
import TextEngine.Engine.HAling;
import TextEngine.Engine.VAling;

public class StartMenu extends Menu {
    private static final String Title = """

   ███████╗██╗     ██╗   ██╗██╗██████╗     ███████╗██╗███╗   ███╗██╗   ██╗██╗      █████╗ ████████╗ ██████╗ ██████╗ 
    ██╔════╝██║     ██║   ██║██║██╔══██╗    ██╔════╝██║████╗ ████║██║   ██║██║     ██╔══██╗╚══██╔══╝██╔═══██╗██╔══██╗
    █████╗  ██║     ██║   ██║██║██║  ██║    ███████╗██║██╔████╔██║██║   ██║██║     ███████║   ██║   ██║   ██║██████╔╝
    ██╔══╝  ██║     ██║   ██║██║██║  ██║    ╚════██║██║██║╚██╔╝██║██║   ██║██║     ██╔══██║   ██║   ██║   ██║██╔══██╗
    ██║     ███████╗╚██████╔╝██║██████╔╝    ███████║██║██║ ╚═╝ ██║╚██████╔╝███████╗██║  ██║   ██║   ╚██████╔╝██║  ██║
    ╚═╝     ╚══════╝ ╚═════╝ ╚═╝╚═════╝     ╚══════╝╚═╝╚═╝     ╚═╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝
    
    
    """;

    String[] opciones = {"Cargar", "Editor", "Salir"};

    private static final Colors TitleColor = Colors.Cyan;
    private static final Colors fontColor = new Colors(Colors.TYPE_TEXT, 114, 159, 232);
    Colors bckSelected =  new Colors(Colors.TYPE_TEXT, 80,80,80).and(Colors.Italic);

    int pos = 0;

    @Override
    public String Frame() {
        String res = "";
        for( int i = 0; i < opciones.length; i++){
            if (i== pos){res += bckSelected.colorize("▶ " +opciones[i]) + " \n";}
            else{res += fontColor.colorize(opciones[i] + " \n"); }
        }
        return  Engine.VerticalAling(VAling.CENTER, Engine.HorizontalAling(HAling.CENTER,   TitleColor.colorize(Title) + "\n" +res));
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
            if (pos == 2){ System.exit(1);}
            else if (pos == 1){Engine.SetMenu(new EditorMainMenu());}
        }
        if (Keyboard.IsLastKeyOfType("Escape")) {
            Keyboard.Clear();
            System.exit(1);
        }
    }
    
    
}
