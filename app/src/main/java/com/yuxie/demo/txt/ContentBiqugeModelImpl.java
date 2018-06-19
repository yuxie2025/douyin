package com.yuxie.demo.txt;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ContentBiqugeModelImpl implements IWebContentModel{
    public static final String TAG = "http://www.biquge5200.com/";
    public static final String bqgUrl="http://zhannei.baidu.com/";

    public static ContentBiqugeModelImpl getInstance() {
        return new ContentBiqugeModelImpl();
    }

    private ContentBiqugeModelImpl() {

    }
    @Override
    public String analyBookcontent(String s, String realUrl) throws Exception {
        Document doc = Jsoup.parse(s);
        List<TextNode> contentEs = doc.getElementById("content").textNodes();
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
        Elements booksE = doc.getElementsByClass("grid").get(0).getElementsByTag("tr");
        if (null != booksE && booksE.size() >= 2) {
            Txt txt = null;
            for (int i = 1; i < booksE.size(); i++) {
                txt = new Txt();
                txt.setAuthorName(booksE.get(i).getElementsByTag("td").get(2).text());
                txt.setType(booksE.get(i).getElementsByTag("td").get(5).text());
                txt.setTxtName(booksE.get(i).getElementsByTag("td").get(0).getElementsByTag("a").text());
                txt.setUpdateTime(booksE.get(i).getElementsByTag("td").get(4).text());
                txt.setLatestUrl(booksE.get(i).getElementsByTag("td").get(1).getElementsByTag("a").attr("href"));
                txt.setLatestTitle(booksE.get(i).getElementsByTag("td").get(1).getElementsByTag("a").text());
//                txt.setPhoto(booksE.get(i).getElementsByClass("result-game-item-pic").get(0).getElementsByClass("result-game-item-pic-link-img").get(0).attr("src"));

                //获取目录的url
                String dirUrl = txt.getLatestUrl();

                String tag=dirUrl.substring(0,dirUrl.indexOf("com/")+4);
                txt.setTag(tag);

                String latestUrl=dirUrl.substring(dirUrl.indexOf("com/")+4,dirUrl.length());
                if (!TextUtils.isEmpty(latestUrl)) {
                    dirUrl = latestUrl.substring(0, latestUrl.lastIndexOf("/"));
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
        Elements booksE = doc.getElementById("list").getElementsByTag("dl").get(0).getElementsByTag("dd");
        if (null != booksE && booksE.size()>= 2) {
            TxtDir txtDir=null;
            for (int i = 9; i < booksE.size(); i++) {
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
