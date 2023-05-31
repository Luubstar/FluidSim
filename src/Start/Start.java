package Start;
import TextEngine.Colors;
import TextEngine.Engine;
import TextEngine.Debug.ProfileMeasure;
import TextEngine.Debug.Profiler;
public class Start {
    public static void main(String[] args){
        Engine.SetFrameTime(20);
        Engine.setDebugMode(true);
        Engine.setProfilerMode(false);
        Profiler.setFilter("Fisicas");
        Profiler.AddProfile(new ProfileMeasure("Fisicas", "Fisicas", new Colors(Colors.TYPE_TEXT, 52, 116, 235)));
        Engine.Start(new StartMenu());
    }
}