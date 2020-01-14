package com.example.retrofittest1;
// финальная вложенность, внутри чилдрена
class Publish {

    private String author;
    private long created;
    private int num_comments;
    private String thumbnail;


    public Publish(String author, long created, int num_comments, String thumbnail) {
        this.author = author;
        this.created = created;
        this.num_comments = num_comments;
        this.thumbnail = thumbnail;
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
}
