package com.greenhand.game73.fm.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.greenhand.game73.R;
import com.greenhand.game73.fm.home.MainActivity;
import com.rd.PageIndicatorView;
import com.rd.animation.AnimationType;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager vpGuide;
    private PageIndicatorView indicator;
    private List<View> lists;
    private ImageView imgWatch, imgStar, imgGoes, imgWater, imgLock, imgWhite, imgOpen, imgClose, imgHand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 不显示系统的标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);

        initData();
        intView();

    }

    private void initData() {

        creatImg();

    }

    /**
     * 导航图片
     */
    private void creatImg() {

//        imgList = new ArrayList<>();
//        int[] res = new int[]{R.mipmap.guide_bg1, R.mipmap.guide_bg2, R.mipmap.guide_bg3, R.mipmap.guide_bg4};
//        for (int i = 0, len = res.length; i < len; i++) {
//
//            ImageView img = new ImageView(this);
//            img.setImageResource(res[i]);
//            img.setScaleType(ImageView.ScaleType.FIT_XY);
//            imgList.add(img);
//        }

        lists = new ArrayList<>();

        //加载第一个View
        View view = LayoutInflater.from(this).inflate(R.layout.view_guide1, null);
        imgWatch = (ImageView) view.findViewById(R.id.img_guide_watch);
        imgStar = (ImageView) view.findViewById(R.id.img__guide_star);

        View view2 = LayoutInflater.from(this).inflate(R.layout.view_guide2, null);
        imgGoes = (ImageView) view2.findViewById(R.id.img__guide_goes);
        imgWater = (ImageView) view2.findViewById(R.id.img__guide_water);

        View view3 = LayoutInflater.from(this).inflate(R.layout.view_guide3, null);
        imgLock = (ImageView) view3.findViewById(R.id.img__guide_lock);
        imgWhite = (ImageView) view3.findViewById(R.id.img__guide_white);

        View view4 = LayoutInflater.from(this).inflate(R.layout.view_guide4, null);
        imgOpen = (ImageView) view4.findViewById(R.id.img__guide_second);
        imgClose = (ImageView) view4.findViewById(R.id.img__guide_normal);
        imgHand = (ImageView) view4.findViewById(R.id.img__guide_thumb);

        imgHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator4();
            }
        });

        lists.add(view);
        lists.add(view2);
        lists.add(view3);
        lists.add(view4);


    }

    private void intView() {

        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        indicator = (PageIndicatorView) findViewById(R.id.indator);
        GuidePagerAdapter adapter = new GuidePagerAdapter(lists);
        vpGuide.setAdapter(adapter);
        //关联指示器（测试三方控件）
        indicator.setViewPager(vpGuide);
        //设置指示器属性
        setIndicator();
        vpGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position){

                    case 0:
                        animator1();
                        break;
                    case 1:
                        animator2();
                        break;
                    case 2:
                        animator3();
                        break;
                    case 3:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setIndicator() {

        indicator.setCount(lists.size());//设置点的数目,一定要对应vp数目，否则无效
        indicator.setDynamicCount(true);//没看出效果？？？

        indicator.setRadius(5);//设置圆形指示器的半径
        indicator.setPadding(4);//设置圆形指示器间距
        indicator.setStrokeWidth(10);//没看出效果？？？？

        indicator.setUnselectedColor(Color.GRAY);//未选中颜色
        indicator.setSelectedColor(Color.WHITE);//选中颜色


//        indicator.setAnimationDuration(1000);//设置动画时长，默认的不设置挺好
        indicator.setAnimationType(AnimationType.DROP);//设置动画效果
//        indicator.setInteractiveAnimation(true);//开启动画加速

//        indicator.setProgress(2,6);
//        indicator.setSelection(2);
//// set orientation
//        app:orientation="{vertical||horizontal}"
    }

//    /**
//     * 创建指示器
//     *
//     * @param n
//     */
//    private void creatDots(int n) {
//
//        imgs = new ImageView[n];
//        for (int i = 0;i<n;i++){
//
//            ImageView img = new ImageView(this);
//            if (i == 0){
//                img.setImageResource(R.drawable.dot_ok);
//            }else {
//                img.setImageResource(R.drawable.dot_default);
//            }
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
//            params.setMargins(10, 0, 10, 0);
//
//            liDots.addView(img,params);
//            imgs[i] = (ImageView) liDots.getChildAt(i);
//        }
//
//    }

    private void animator1(){
        ObjectAnimator.ofFloat(imgWatch,"translationX",-5f).setDuration(1500).start();
        ObjectAnimator.ofFloat(imgStar,"translationX",10f).setDuration(1500).start();
    }

    private void animator2(){

        ObjectAnimator.ofFloat(imgGoes,"translationY",-5f).setDuration(1500).start();
        ObjectAnimator.ofFloat(imgWater,"translationY",10f).setDuration(1500).start();
    }

    private void animator3(){

        ObjectAnimator.ofFloat(imgLock,"translationY",-5f).setDuration(1500).start();
        ObjectAnimator.ofFloat(imgWhite,"translationY",10f).setDuration(1500).start();
    }

    private void animator4(){

        AnimatorSet set = new AnimatorSet();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imgClose, "alpha", 1f, 0f);
        ObjectAnimator x = ObjectAnimator.ofFloat(imgHand, "translationX", -imgClose.getWidth());
        set.setDuration(1500).playTogether(alpha,x);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //跳转到main
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * vp适配器
     */
    private class GuidePagerAdapter extends PagerAdapter {

        /**
         * 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
         *
         * @return
         */
        private List<View> lists;

        public GuidePagerAdapter(List<View> lists) {
            this.lists = lists;
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        /**
         * 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
         *
         * @param view
         * @param object
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，
         * 我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(lists.get(position));
            return lists.get(position);
        }

        /**
         * PagerAdapter只缓存三张要显示的图片，
         * 如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(lists.get(position));
        }
    }
}
