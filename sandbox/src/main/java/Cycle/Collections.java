package Cycle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by USER on 27.07.2017.
 */
public class Collections {
    public static void main(String[] args) {
        String[] langs = {"Java", "C#", "Python", "PHP"};

        for(String l : langs) {
            System.out.println("Я хочу выучить " + l);
        }

        List<String> languages = new ArrayList<String>();
        languages.add("Java");
        for(String l : languages) {
            System.out.println("Я хочу выучить " + l);
        }

        List<String> languages1 = Arrays.asList("Java", "C#", "Python", "PHP");
        for(String l : languages1) {
            System.out.println("Я хочу выучить " + l);
        }

        for(int i = 0; i<languages1.size(); i++) {
            System.out.println("Я хочу выучить " + languages1.get(i));
        }

    }
}
