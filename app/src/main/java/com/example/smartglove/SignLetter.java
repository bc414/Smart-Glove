package com.example.smartglove;

public class SignLetter {

    //0-25, A=0, B=1, ... Z=25
    int letterID;

    public SignLetter(int id) {
        letterID = id;
    }

    public String getString() {
        char letter = (char)(65 + letterID); //65 is the ASCII code for capital A
        return Character.toString(letter);
    }

}
