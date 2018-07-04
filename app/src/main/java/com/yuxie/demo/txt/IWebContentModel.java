package com.yuxie.demo.txt;

import java.util.List;

public interface IWebContentModel {

    public String analyBookcontent(String s, String realUrl) throws Exception;

    public List<Txt> analyBookDir(String s, String realUrl) throws Exception;

    public List<TxtDir> chaptersList(String s, String realUrl) throws Exception;



}