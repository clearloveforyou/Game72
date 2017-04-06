package com.greenhand.game73.fm.home.product;

/**
 * project: Game73
 * package: com.greenhand.game73.fm.home.product
 * author: HouShengLi
 * time: 2017/4/7 02:56
 * e-mail:13967189624@163.com
 * description:
 */

public class CommentBean {


    //   "createDate": 1491496303000,
//                "id": "3484",
//                "shortUid": "462440254545",
//                "content": "1775760266",
//                "headImg": "http:\/\/pic12.secooimg.com\/thumb\/120\/120\/pic1.secoo.com\/headImage\/17\/4\/2cc9a7155ad94a45be3d0f8f632dbe00.jpg",
//                "type": "0",
//                "nickName": "奢侈品1775760266品牌",
//                "source": 0,
//                "userName": "4***5"

    private String createDate;
    private String content;
    private String headImg;
    private String nickName;

    public CommentBean() {
    }

    public CommentBean(String createDate, String content, String headImg, String nickName) {
        this.createDate = createDate;
        this.content = content;
        this.headImg = headImg;
        this.nickName = nickName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

}
