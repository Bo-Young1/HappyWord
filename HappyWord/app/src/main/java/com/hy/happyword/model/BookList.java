package com.hy.happyword.model;

/**
 * Created by huyu on 2017/4/6.
 */
public class BookList {

    private String ID;
    private String name;
    private String generateTime;
    private String numOfList;
    private int numOfWord;

    public BookList(){}

    public BookList(String ID,String name,String generateTime,String numOfList){
        this.ID = ID;
        this.name = name;
        this.generateTime = generateTime;
        this.numOfList = numOfList;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(String generateTime) {
        this.generateTime = generateTime;
    }

    public String getNumOfList() {
        return numOfList;
    }

    public void setNumOfList(String numOfList) {
        this.numOfList = numOfList;
    }

    public int getNumOfWord() {
        return numOfWord;
    }

    public void setNumOfWord(int numOfWord) {
        this.numOfWord = numOfWord;
    }
}
