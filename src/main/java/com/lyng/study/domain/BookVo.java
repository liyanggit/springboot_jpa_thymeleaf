package com.lyng.study.domain;

/**
 * Created by Administrator on 2017/11/16.
 */
public class BookVo extends Book{

    public BookVo(){}

    public BookVo(long id,String author){
        this.setId(id);
        this.setAuthor(author);
    }
}
