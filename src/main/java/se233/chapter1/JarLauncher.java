package se233.chapter1;

import se233.chapter1.JarLauncher; // สมมติว่านี่คือคลาส JavaFX Application ของคุณ
import javafx.application.Application;
import se233.chapter1.Launcher;

public class JarLauncher {
    public static void main(String[] args) {
        // ให้ Helper Class นี้เรียกใช้คลาส JavaFX Application จริงๆ
        // โดยใช้ HelloApplication แทน Launcher ตาม pom.xml
        Application.launch(Launcher.class, args);
    }
}
