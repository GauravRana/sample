package com.dev.poplify.projectsample.model;

import android.os.Parcel;
import android.os.Parcelable;


public class TuneModel implements Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public String getArtworkUrl30() {
        return artworkUrl30;
    }

    public void setArtworkUrl30(String artworkUrl30) {
        this.artworkUrl30 = artworkUrl30;
    }

    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    public void setArtworkUrl60(String artworkUrl60) {
        this.artworkUrl60 = artworkUrl60;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public String artworkUrl30;
    public String artworkUrl60;
    public String artworkUrl100;

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String trackName;

    public static final Creator<TuneModel> CREATOR = new Creator<TuneModel>() {

        @Override
        public TuneModel[] newArray(int size) {
            return new TuneModel[size];
        }

        @Override
        public TuneModel createFromParcel(Parcel source) {
            return new TuneModel();
        }
    };

}
