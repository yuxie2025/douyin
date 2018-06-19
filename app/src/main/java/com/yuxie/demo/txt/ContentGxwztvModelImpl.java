package com.yuxie.demo.txt;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ContentGxwztvModelImpl implements IWebContentModel{
    public static final String TAG = "http://www.gxwztv.com/";

    public static ContentGxwztvModelImpl getInstance() {
        return new ContentGxwztvModelImpl();
    }

    private ContentGxwztvModelImpl() {

    }
    @Override
    public String analyBookcontent(String s, String realUrl) throws Exception {
        Document doc = Jsoup.parse(s);
        List<TextNode> contentEs = doc.getElementById("txtContent").textNodes();
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < contentEs.size(); i++) {
            String temp = contentEs.get(i).text().trim();
            temp = temp.replaceAll(" ", "").replaceAll(" ", "");
            if (temp.length() > 0) {
                if (!temp.contains("\u3000\u3000")){
                    content.append("\u3000\u3000");
                }
                content.append(temp);
                if (i < contentEs.size() - 1) {
                    content.append("\r\n");
                }
            }
        }
        return content.toString();
    }

    @Override
    public List<Txt> analyBookDir(String s, String realUrl) throws Exception {
        List<Txt> data=new ArrayList<>();
        Document doc = Jsoup.parse(s);
        Elements booksE = doc.getElementById("novel-list").getElementsByClass("list-group-item clearfix");
        if (null != booksE && booksE.size() >= 2) {
            Txt txt = null;
            data.clear();
            for (int i = 1; i < booksE.size(); i++) {
                txt = new Txt();
                txt.setAuthorName(booksE.get(i).getElementsByClass("col-xs-2").get(0).text());
                txt.setType(booksE.get(i).getElementsByClass("col-xs-1").get(0).text());
                txt.setTxtName(booksE.get(i).getElementsByClass("col-xs-3").get(0).getElementsByTag("a").get(0).text());
                txt.setUpdateTime(booksE.get(i).getElementsByClass("col-xs-2").get(0).getElementsByClass("time").text());
                txt.setLatestUrl(booksE.get(i).getElementsByClass("col-xs-4").get(0).getElementsByTag("a").get(0).attr("href"));
                txt.setLatestTitle(booksE.get(i).getElementsByClass("col-xs-4").get(0).getElementsByTag("a").get(0).text());
                txt.setTag(TAG);

                //获取目录的url
                String dirUrl = txt.getLatestUrl();
                if (!TextUtils.isEmpty(dirUrl)) {
                    dirUrl = dirUrl.substring(0, dirUrl.lastIndexOf("/"));
                }
                txt.setDirUrl(dirUrl);
                data.add(txt);
            }
        }
        return data;
    }

    @Override
    public List<TxtDir> chaptersList(String s, String realUrl) throws Exception {
        List<TxtDir> list=new ArrayList<>();
        Document doc = Jsoup.parse(s);
        Elements booksE = doc.getElementById("chapters-list").getElementsByClass("list-group-item").get(0).children();
        if (null != booksE && booksE.size()>= 2) {
            TxtDir txtDir=null;
            for (int i = 0; i < booksE.size(); i++) {
                txtDir=new TxtDir();
                txtDir.setTitle(booksE.get(i).text());
                Elements href=booksE.get(i).getElementsByTag("a");
                if (href!=null&&href.size()>0){
                    txtDir.setTitleDir(href.get(0).attr("href"));
                }else{
                    continue;
                }
                list.add(txtDir);
            }
        }
        return list;
    }
}
