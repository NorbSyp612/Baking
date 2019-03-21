package com.example.baking.Items;

public class RecipeSteps {
    private String id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public RecipeSteps() { }

    public void setDescription(String DescriptionFromJson) {
        description = DescriptionFromJson;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String IdFromJson) {
        id = IdFromJson;
    }

    public String getId() {
        return id;
    }

    public void setShortDescription(String ShortDescriptionFromJson) {
        shortDescription = ShortDescriptionFromJson;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setVideoURL(String VideoURLfromJson) {
        videoURL = VideoURLfromJson;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setThumbnailURL(String ThumbnailURLFromJSON){
        thumbnailURL = ThumbnailURLFromJSON;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
}
