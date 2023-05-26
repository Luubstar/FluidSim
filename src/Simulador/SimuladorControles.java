package Simulador;
import TextEngine.Engine;
import TextEngine.Keyboard;
import TextEngine.Menu;
import TextEngine.Engine.HAling;
public class SimuladorControles extends Menu {

    Simulador editor;

    SimuladorControles(Simulador lastEditor){editor = lastEditor;}

    @Override
    public String Frame() {
        String texto = """
Controles del simualdor:

Controles básicos: 

ESC  : Vuelve al simulador
?    : Abre este menú
W / ↑: Mueve el cursor arriba
S / ↓: Mueve el cursor abajo
A / ←: Mueve el cursor a la izquierda
D / →: Mueve el cursor a la derecha
Enter: Pinta una casilla

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
