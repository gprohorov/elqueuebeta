package com.med.model;


/*
  @author   george
  @project   elqueuebeta
  @class  InitCall
  @version  1.0.0 
  @since 21/10/2026 - 17.08
*/

public class InitCall {
    private String key = "754a66-7732552";
    private String secret = "1c6ab9-cb7866-545522-8e4ba4-ab9c863e";
    private String internalNumber;
    private String externalNumber;
    private String pbxNumber = "";
    private boolean playbackWaiting = false;

    public InitCall(String key, String secret, String internalNumber, String externalNumber, String pbxNumber, boolean playbackWaiting) {
        this.key = key;
        this.secret = secret;
        this.internalNumber = internalNumber;
        this.externalNumber = externalNumber;
        this.pbxNumber = pbxNumber;
        this.playbackWaiting = playbackWaiting;
    }

    public InitCall() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getInternalNumber() {
        return internalNumber;
    }

    public void setInternalNumber(String internalNumber) {
        this.internalNumber = internalNumber;
    }

    public String getExternalNumber() {
        return externalNumber;
    }

    public void setExternalNumber(String externalNumber) {
        this.externalNumber = externalNumber;
    }

    public String getPbxNumber() {
        return pbxNumber;
    }

    public void setPbxNumber(String pbxNumber) {
        this.pbxNumber = pbxNumber;
    }

    public boolean isPlaybackWaiting() {
        return playbackWaiting;
    }

    public void setPlaybackWaiting(boolean playbackWaiting) {
        this.playbackWaiting = playbackWaiting;
    }

    @Override
    public String toString() {
        return "InitCall{" + '\'' +
                "key='" + key + '\'' +
                "secret='" + secret + '\'' +
                "internalNumber='" + internalNumber + '\'' +
                "externalNumber='" + externalNumber + '\'' +
                "pbxNumber='" + pbxNumber + '\'' +
                "playbackWaiting=" + playbackWaiting + '\'' +
                '}';
    }
}
