package com.hy.happyword.model;

/**
 * Created by huyu on 2017/4/6.
 */
public class Word {

    private String ID;
    private String meanning;
    private String spelling;
    private String phonetic_alphabet;
    private String list;

    public Word(){}

    public Word(String ID,String meanning,String spelling,String phonetic_alphabet,String list){
        this.ID = ID;
        this.meanning = meanning;
        this.spelling = spelling;
        this.phonetic_alphabet = phonetic_alphabet;
        this.list = list;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMeanning() {
        return meanning;
    }

    public void setMeanning(String meanning) {
        this.meanning = meanning;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public String getPhonetic_alphabet() {
        return phonetic_alphabet;
    }

    public void setPhonetic_alphabet(String phonetic_alphabet) {
        this.phonetic_alphabet = phonetic_alphabet;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }
}
