package com.totwgforum.gforum.dto;

import lombok.*;

@Getter
@ToString
public class PagingDto {

    private int pageNum;
    private int pageNumStart;
    private int pageNumEnd;
    private boolean isFirst;
    private boolean isLast;
    private int nowPage;

    public PagingDto(int pageNum, int nowPage){
        this.pageNum = pageNum;
        this.nowPage = nowPage;
        int i = (pageNum - nowPage) / 15;
        pageNumStart = pageNum -15 * i;
        pageNumEnd = pageNum -15 * (i + 1) + 1 ;
        if(pageNumEnd < 1){
            pageNumEnd = 1;
        }
        isFirst = false;
        isLast = false;
        if(i == 0){
            isFirst = true;
        }
        if(pageNumEnd == 1){
            isLast = true;
        }
    }
}
