package com.example.smartglove;

public class SignLetter {

    //0-25, A=0, B=1, ... Z=25
    int letterID;
    public int imageID;

    public SignLetter(int id, int imageID) {
        letterID = id;
        this.imageID = imageID;
    }

    public String getString() {
        char letter = (char)(65 + letterID); //65 is the ASCII code for capital A
        return Character.toString(letter);
    }

}
