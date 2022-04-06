package org.me.gcu.labstuff.androidcwk;



public class trafficInfo {
    public String title, description, link,  pubDate;
    public double georsslat, georsslon;

    public trafficInfo(String title, String description, String link, double georsslat, double georsslon, String pubDate) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.georsslat = 36;
        this.georsslon = 37;
        this.pubDate = pubDate;
    }

    public trafficInfo() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getGeorsslat() {
        return georsslat;
    }

    public void setGeorsslat(double georsslat) {
        this.georsslat = georsslat;
    }

    public double getGeorsslon() {
        return georsslon;
    }

    public void setGeorsslon(double georsslon) {
        this.georsslon = georsslon;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return "trafficInfo{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", georss lat='" + georsslat + '\'' +
                ", georss lon='" + georsslon + '\'' +
                ", pubDate='" + pubDate + '\'' +
                '}';
    }
}

