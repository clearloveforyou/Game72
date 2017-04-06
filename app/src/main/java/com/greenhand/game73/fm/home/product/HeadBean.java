package com.greenhand.game73.fm.home.product;

/**
 * project: Game73
 * package: com.greenhand.game73.fm.home.product
 * author: HouShengLi
 * time: 2017/4/7 01:14
 * e-mail:13967189624@163.com
 * description:
 */

public class HeadBean {
//    {
//        "sort": 1,
//            "link": "shzt_0330list",
//            "id": 1,
//            "productFirstCategoryId": 464,
//            "remark": "那些难以抗拒的口红色号",
//            "imgurl": "http:\/\/pic12.secooimg.com\/comment\/17\/3\/4bad67e3f7944a0e993bb1fb16dd5c88.jpg",
//            "linkType": 0
//    }


    private String remark;
    private String imgurl;


    public HeadBean() {
    }

    public HeadBean(String remark, String imgurl) {
        this.remark = remark;
        this.imgurl = imgurl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
