package com.agustinbergomi.lasmejores10apps;

//esta clase representa una entidad "entrada del feed"
public class FeedEntry {
    private String name;
    private String artist;
    private String releaseDate;
    private String summary;
    private String imageURL;

    public String getName(){
        return name;
    }
    public String getArtist(){
        return artist;
    }
    public String getReleaseDate(){
        return releaseDate;
    }
    public String getSummary(){
        return summary;
    }
    public String getImageURL(){
        return imageURL;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setArtist(String artist){
        this.artist = artist;
    }
    public void setReleaseDate(String releaseDate){
        this.releaseDate = releaseDate;
    }
    public void setSummary(String summary){
        this.summary = summary;
    }
    public void setImageURL(String imageURL){
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return  "name='" + name + '\n' +
                ", artist='" + artist + '\n' +
                ", releaseDate='" + releaseDate + '\n' +
                ", imageURL='" + imageURL + '\n';

    }
}

