package org.robot;

import org.maintask.Dump;

public class Scientist {

    private String name;
    private Dump dump;

    public Scientist(String name, Dump dump) {
        this.name = name;
        this.dump = dump;
    }

    public Assistant getAssistant() {
        return new Assistant(name);
    }


    public class Assistant implements Runnable {

        private String name;

        public Assistant(String name) {
            this.name = name + " -> Assistant";
        }

        @Override
        public void run() {
            while (true) {

            }
        }
    }
}
