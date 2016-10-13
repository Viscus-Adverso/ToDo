package com.theironyard;

/**
 * Created by Viscus on 9/19/16.
 */
public class Item {
    int id;
    String text;
    boolean isDone;
    String author;

    public Item(int id, String text, boolean isDone) {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
    }

    public Item(int id, String text, boolean isDone, String author) {
        this.id = id;
        this.text = text;
        this.isDone = isDone;
        this.author = author;
    }
}
