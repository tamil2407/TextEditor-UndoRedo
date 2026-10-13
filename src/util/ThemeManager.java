package util;

import java.awt.Color;

/** Central color palettes for light and dark themes. */
public final class ThemeManager {
    private ThemeManager() {}
    public static final class Palette {
        public final Color background, foreground, editorBackground, editorForeground;
        public final Color toolbarBackground, statusBackground, caret;
        private Palette(Color bg, Color fg, Color ebg, Color efg, Color toolbar, Color status, Color caret) {
            this.background = bg; this.foreground = fg; this.editorBackground = ebg;
            this.editorForeground = efg; this.toolbarBackground = toolbar;
            this.statusBackground = status; this.caret = caret;
        }
    }
    private static final Palette LIGHT = new Palette(
        new Color(245,247,250), new Color(35,43,55), Color.WHITE, new Color(35,43,55),
        new Color(232,237,244), new Color(232,237,244), new Color(30,90,180));
    private static final Palette DARK = new Palette(
        new Color(35,39,46), new Color(230,233,238), new Color(25,28,34), new Color(230,233,238),
        new Color(45,50,59), new Color(45,50,59), new Color(120,170,255));
    public static Palette light() { return LIGHT; }
    public static Palette dark() { return DARK; }
}
