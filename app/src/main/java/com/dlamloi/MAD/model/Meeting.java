package com.dlamloi.MAD.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Don on 8/05/2018.
 */

public class Meeting implements Parcelable {

    private String mId;
    private String mCreatorEmail;
    private String mMeetingTitle;
    private String mMeetingDate; //In the form of DD-MONTH-YYYY
    private String mMeetingTime; //In the form of HH:MM am/pm
    private String mMeetingLocation;
    private String mMeetingSuburb;
    private String mAgenda; //Agenda info (if needed)

    public Meeting() {

    }


    public Meeting(String id, String creatorEmail, String meetingTitle, String meetingDate, String meetingTime, String meetingLocation, String meetingSuburb, String agenda) {
        this.mId = id;
        this.mCreatorEmail = creatorEmail;
        this.mMeetingTitle = meetingTitle;
        this.mMeetingDate = meetingDate;
        this.mMeetingTime = meetingTime;
        this.mMeetingLocation = meetingLocation;
        this.mMeetingSuburb = meetingSuburb;
        this.mAgenda = agenda;
    }

    protected Meeting(Parcel in) {
        mId = in.readString();
        mCreatorEmail = in.readString();
        mMeetingTitle = in.readString();
        mMeetingDate = in.readString();
        mMeetingTime = in.readString();
        mMeetingLocation = in.readString();
        mMeetingSuburb = in.readString();
        mAgenda = in.readString();
    }

    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mCreatorEmail);
        dest.writeString(mMeetingTitle);
        dest.writeString(mMeetingDate);
        dest.writeString(mMeetingTime);
        dest.writeString(mMeetingLocation);
        dest.writeString(mMeetingSuburb);
        dest.writeString(mAgenda);
    }

    public String getId() {

        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getCreatorEmail() {
        return mCreatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.mCreatorEmail = creatorEmail;
    }

    public String getMeetingTitle() {
        return mMeetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.mMeetingTitle = meetingTitle;
    }

    public String getMeetingDate() {
        return mMeetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.mMeetingDate = meetingDate;
    }

    public String getMeetingTime() {
        return mMeetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.mMeetingTime = meetingTime;
    }

    public String getMeetingSuburb() {
        return this.mMeetingSuburb;
    }

    public void setMeetingSuburb(String suburb) {
        this.mMeetingSuburb = suburb;
    }

    public String getAgenda() {
        return mAgenda;
    }

    public void setAgenda(String agenda) {
        this.mAgenda = agenda;
    }

    public static Creator<Meeting> getCREATOR() {
        return CREATOR;
    }
}