package ru.ivmiit;

public class HelloMessage implements Message{

    private String text;

    public HelloMessage(String text){
        this.text = "hello " + text;
    }

    public String getText() {
        return text;
    }
}
