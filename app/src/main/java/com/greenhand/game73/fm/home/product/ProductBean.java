package com.greenhand.game73.fm.home.product;

/**
 * project: Game73
 * package: com.greenhand.game73.fm.home.product
 * author: HouShengLi
 * time: 2017/4/7 02:57
 * e-mail:13967189624@163.com
 * description:
 */

public class ProductBean {


    private String productId;
    private String productImg;

    public ProductBean(String productId, String productImg) {
        this.productId = productId;
        this.productImg = productImg;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

}
