package com.easybddweb.core.pages;

public interface HomePage {

    public void go(String url);

    public void lickLink(String linkSrc);
    
    public int foundPictureLink(String picLink);
    
}
