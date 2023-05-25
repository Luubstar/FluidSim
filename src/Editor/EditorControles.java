package Editor;
import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;
import TextEngine.Engine.HAling;
public class EditorControles extends Menu {

    Editor editor;

    EditorControles(Editor lastEditor){editor = lastEditor;}

    @Override
    public String Frame() {
        String texto = """
Controles del editor:

Controles básicos: 

ESC  : Vuelve al editor
?    : Abre este menú
W / ↑: Mueve el cursor arriba
S / ↓: Mueve el cursor abajo
A / ←: Mueve el cursor a la izquierda
D / →: Mueve el cursor a la derecha
Enter: Pinta una casilla
Q/E  : Cambia el pincel a otra casilla

Combinaciones:
Shift + S  : Guarda el mapa sobreescribiendolo
        """;

        return Engine.HorizontalAling(HAling.LEFT, texto);
    }
    
    @Override
    public void Update() {
        if (Keyboard.IsLastKeyOfType("Escape")) {
            Keyboard.Clear();
            Engine.SetMenu(editor);
        }
    }
}
