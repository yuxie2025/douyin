
package com.yuxie.demo.utils.douyin.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


// refer to https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=6842888905025506568
public class ApiResult {

    @SerializedName("status_code")
    @Expose
    private Long statusCode;
    @SerializedName("item_list")
    @Expose
    private List<ItemList> itemList = null;
    @SerializedName("extra")
    @Expose
    private Extra extra;

    public Long getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Long statusCode) {
        this.statusCode = statusCode;
    }

    public List<ItemList> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemList> itemList) {
        this.itemList = itemList;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

}
