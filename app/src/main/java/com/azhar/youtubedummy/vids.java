package com.azhar.youtubedummy;

public class vids {
    private String Title;
    private String Likes;

    public vids(String title, String likes, String image) {
        Title = title;
        Likes = likes;
        this.image = image;
    }

    private String image;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLikes() {
        return Likes;
    }

    public void setLikes(String likes) {
        Likes = likes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
