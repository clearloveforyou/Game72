package com.greenhand.game73.fm.home.product;

import java.util.List;

/**
 * project: Game73
 * package: com.greenhand.game73.fm.home.product
 * author: HouShengLi
 * time: 2017/4/7 01:19
 * e-mail:13967189624@163.com
 * description:
 */

public class ItemBean {

    private String id;
    private String productId;
    private String nickName;
    private String createDate;
    private String headImg;
    private String content;

    public List<CommentBean> getCommentBeanList() {
        return commentBeanList;
    }

    public void setCommentBeanList(List<CommentBean> commentBeanList) {
        this.commentBeanList = commentBeanList;
    }

    public List<ProductBean> getProductBeanList() {
        return productBeanList;
    }

    public void setProductBeanList(List<ProductBean> productBeanList) {
        this.productBeanList = productBeanList;
    }

    private String commentCount;
    private List<CommentBean> commentBeanList;
    private List<ProductBean> productBeanList;

    public ItemBean(String id, String productId, String nickName, String createDate, String headImg, String content, String commentCount, List<CommentBean> commentBeanList, List<ProductBean> productBeanList) {
        this.id = id;
        this.productId = productId;
        this.nickName = nickName;
        this.createDate = createDate;
        this.headImg = headImg;
        this.content = content;
        this.commentCount = commentCount;
        this.commentBeanList = commentBeanList;
        this.productBeanList = productBeanList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

}
