package Start;
import TextEngine.Engine;
public class Start {
    public static void main(String[] args){
        Engine.SetFrameTime(20);
        Engine.Start(new StartMenu());
    }
}