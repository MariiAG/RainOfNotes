package com.example.rainofnotes.models;

import java.io.Serializable;

public class NoteModel implements Serializable {

    private String title;
    private String content;
    private String image;
    private String IdFbN;

    public NoteModel() {

    }

    public NoteModel(String title, String content, String idFbN) {
        this.title = title;
        this.content = content;
        IdFbN = idFbN;
    }

    public NoteModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "NoteModel{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", IdFbN='" + IdFbN + '\'' +
                '}';
    }

    public String getIdFbN() {
        return IdFbN;
    }

    public void setIdFbN(String idFbN) {
        IdFbN = idFbN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
