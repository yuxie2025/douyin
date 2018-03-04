package com.yuxie.myapp.txt;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/08/24.
 */

public class Txt implements Serializable {
    String tag;//网站
    String type;//小说类型
    String txtName;//小说名称
    String dirUrl;//小说目录地址
    String updateTime;//小说最后更新时间
    String authorName;//作者名称
    String latestUrl;//最新章节地址
    String latestTitle;//最新章节,名称
    String photo;//小说封面

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public String getDirUrl() {
        return dirUrl;
    }

    public void setDirUrl(String dirUrl) {
        this.dirUrl = dirUrl;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getLatestUrl() {
        return latestUrl;
    }

    public void setLatestUrl(String latestUrl) {
        this.latestUrl = latestUrl;
    }

    public String getLatestTitle() {
        return latestTitle;
    }

    public void setLatestTitle(String latestTitle) {
        this.latestTitle = latestTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Txt)) return false;

        Txt txt = (Txt) o;

        if (getTag() != null ? !getTag().equals(txt.getTag()) : txt.getTag() != null) return false;
        if (getType() != null ? !getType().equals(txt.getType()) : txt.getType() != null)
            return false;
        if (getTxtName() != null ? !getTxtName().equals(txt.getTxtName()) : txt.getTxtName() != null)
            return false;
        if (getDirUrl() != null ? !getDirUrl().equals(txt.getDirUrl()) : txt.getDirUrl() != null)
            return false;
        if (getUpdateTime() != null ? !getUpdateTime().equals(txt.getUpdateTime()) : txt.getUpdateTime() != null)
            return false;
        if (getAuthorName() != null ? !getAuthorName().equals(txt.getAuthorName()) : txt.getAuthorName() != null)
            return false;
        if (getLatestUrl() != null ? !getLatestUrl().equals(txt.getLatestUrl()) : txt.getLatestUrl() != null)
            return false;
        if (getLatestTitle() != null ? !getLatestTitle().equals(txt.getLatestTitle()) : txt.getLatestTitle() != null)
            return false;
        return getPhoto() != null ? getPhoto().equals(txt.getPhoto()) : txt.getPhoto() == null;

    }

    @Override
    public int hashCode() {
        int result = getTag() != null ? getTag().hashCode() : 0;
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getTxtName() != null ? getTxtName().hashCode() : 0);
        result = 31 * result + (getDirUrl() != null ? getDirUrl().hashCode() : 0);
        result = 31 * result + (getUpdateTime() != null ? getUpdateTime().hashCode() : 0);
        result = 31 * result + (getAuthorName() != null ? getAuthorName().hashCode() : 0);
        result = 31 * result + (getLatestUrl() != null ? getLatestUrl().hashCode() : 0);
        result = 31 * result + (getLatestTitle() != null ? getLatestTitle().hashCode() : 0);
        result = 31 * result + (getPhoto() != null ? getPhoto().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Txt{" +
                "tag='" + tag + '\'' +
                ", type='" + type + '\'' +
                ", txtName='" + txtName + '\'' +
                ", dirUrl='" + dirUrl + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", authorName='" + authorName + '\'' +
                ", latestUrl='" + latestUrl + '\'' +
                ", latestTitle='" + latestTitle + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
