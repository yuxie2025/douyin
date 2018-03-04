package com.yuxie.myapp.txt;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ContentBiquziModelImpl implements IWebContentModel{
    public static final String TAG = "http://www.biquzi.com/";
    public static final String bqgUrl="http://zhannei.baidu.com/";

    public static ContentBiquziModelImpl getInstance() {
        return new ContentBiquziModelImpl();
    }

    private ContentBiquziModelImpl() {

    }
    @Override
    public String analyBookcontent(String s, String realUrl) throws Exception {
        Document doc = Jsoup.parse(s);
        List<TextNode> contentEs = doc.getElementById("content").textNodes();
        StringBuilder content = new StringBuilder();
        Log.i("TAG", "analyBookcontent: "+contentEs.size());
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
        Elements booksE = doc.getElementsByClass("result-item result-game-item");
        if (null != booksE && booksE.size() >= 2) {
            Txt txt = null;
            for (int i = 0; i < booksE.size(); i++) {
                txt = new Txt();
                txt.setAuthorName(booksE.get(i).getElementsByClass("result-game-item-detail").get(0).getElementsByClass("result-game-item-info").get(0).getElementsByClass("result-game-item-info-tag").get(0).child(1).text());
                txt.setType(booksE.get(i).getElementsByClass("result-game-item-detail").get(0).getElementsByClass("result-game-item-info").get(0).getElementsByClass("result-game-item-info-tag").get(1).getElementsByClass("result-game-item-info-tag-title").get(1).text());
                txt.setTxtName(booksE.get(i).getElementsByClass("result-game-item-detail").get(0).getElementsByClass("result-item-title result-game-item-title").get(0).getElementsByTag("a").get(0).attr("title"));
                txt.setUpdateTime(booksE.get(i).getElementsByClass("result-game-item-detail").get(0).getElementsByClass("result-game-item-info").get(0).getElementsByClass("result-game-item-info-tag").get(2).getElementsByClass("result-game-item-info-tag-title").get(0).text());
                txt.setLatestUrl(booksE.get(i).getElementsByClass("result-game-item-detail").get(0).getElementsByClass("result-game-item-info").get(0).getElementsByClass("result-game-item-info-tag").get(3).getElementsByClass("result-game-item-info-tag-item").get(0).attr("href"));
                txt.setLatestTitle(booksE.get(i).getElementsByClass("result-game-item-detail").get(0).getElementsByClass("result-game-item-info").get(0).getElementsByClass("result-game-item-info-tag").get(3).getElementsByClass("result-game-item-info-tag-item").text());
                txt.setPhoto(booksE.get(i).getElementsByClass("result-game-item-pic").get(0).getElementsByClass("result-game-item-pic-link-img").get(0).attr("src"));

                //获取目录的url
                String dirUrl = txt.getLatestUrl();

                String tag=dirUrl.substring(0,dirUrl.indexOf("com/")+4);
                txt.setTag(tag);

                String latestUrl=dirUrl.substring(dirUrl.indexOf("com/")+4,dirUrl.length());
                txt.setLatestUrl(latestUrl);
                if (!TextUtils.isEmpty(latestUrl)) {
                    dirUrl = latestUrl.substring(0, latestUrl.lastIndexOf("/"));
                }
                txt.setDirUrl(dirUrl);
//                Log.i("TAG", "analyBookDir: "+txt.toString());
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
            Log.i("TAG","booksE.size():"+booksE.size());
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
