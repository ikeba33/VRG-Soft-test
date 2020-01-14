package com.example.retrofittest1.model;
// финальная вложенность, внутри чилдрена
public class  Publish {

    private String author;
    private long created;
    private int num_comments;
    private String thumbnail;
    private String url;


    public Publish(String author, long created, int num_comments, String thumbnail, String url) {
        this.author = author;
        this.created = created;
        this.num_comments = num_comments;
        this.thumbnail = thumbnail;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }


    public long getCreated() {
        return created;
    }


    public int getNum_comments() {
        return num_comments;
    }


    public String getThumbnail() {
        return thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
