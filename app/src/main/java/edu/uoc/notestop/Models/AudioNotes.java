package edu.uoc.notestop.Models;

public class AudioNotes {


    String route;
    String name;

    public AudioNotes(String route, String name) {
        this.route = route;
        this.name = name;
    }

    public String getRoute() {
        return route;
    }
    public AudioNotes() {

    }

    public void setRoute(String route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "AudioNotes{" +
                "route='" + route + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
