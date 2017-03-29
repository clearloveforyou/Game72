package com.greenhand.game73.fm.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.greenhand.game73.R;
import com.greenhand.game73.fm.home.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager vpGuide;
    private LinearLayout liDots;
//    private PageIndicatorView indicator;
    private Button btnGomain;
    private List<ImageView> imgList;
    private ImageView[] imgs;
    private int currentPager;//当前页

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

        imgList = new ArrayList<>();
        int[] res = new int[]{R.mipmap.guide_bg1, R.mipmap.guide_bg2, R.mipmap.guide_bg3, R.mipmap.guide_bg4};
        for (int i = 0, len = res.length; i < len; i++) {

            ImageView img = new ImageView(this);
            img.setImageResource(res[i]);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            imgList.add(img);
        }

    }

    private void intView() {

        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        liDots = (LinearLayout) findViewById(R.id.line_dots);
        btnGomain = (Button) findViewById(R.id.btn_gomain);
//        indicator = (PageIndicatorView) findViewById(R.id.indator);
        //
        creatDots(imgList.size());
        //
        GuidePagerAdapter adapter = new GuidePagerAdapter(imgList);
        vpGuide.setAdapter(adapter);
//        //关联指示器（测试三方控件）
//        indicator.setViewPager(vpGuide);
//        //设置指示器属性
//        setIndicator();
        vpGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //
                if (currentPager == position){
                    return;
                } else {
                    imgs[currentPager].setImageResource(R.drawable.dot_default);
                    imgs[position].setImageResource(R.drawable.dot_ok);
                }
                //设一个跳转的button,只在第最后一页出现
                if (position == 3){
                    btnGomain.setVisibility(View.VISIBLE);
                } else {
                    btnGomain.setVisibility(View.GONE);
                }
                //当前位置
                currentPager = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnGomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到main
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

//    private void setIndicator() {
//
//        indicator.setCount(imgList.size());//设置点的数目,一定要对应vp数目，否则无效
//        indicator.setDynamicCount(true);//没看出效果？？？
//
//        indicator.setRadius(8);//设置圆形指示器的半径
//        indicator.setPadding(5);//设置圆形指示器间距
//        indicator.setStrokeWidth(10);//没看出效果？？？？
//
//        indicator.setUnselectedColor(Color.GRAY);//未选中颜色
//        indicator.setSelectedColor(Color.RED);//选中颜色
//
//
//        indicator.setAnimationDuration(1000);//设置动画时长，默认的不设置挺好
//        indicator.setAnimationType(AnimationType.DROP);//设置动画效果
////        indicator.setInteractiveAnimation(true);//开启动画加速
//
////        indicator.setProgress(2,6);
////        indicator.setSelection(2);
////// set orientation
////        app:orientation="{vertical||horizontal}"
//    }

    /**
     * 创建指示器
     *
     * @param n
     */
    private void creatDots(int n) {

        imgs = new ImageView[n];
        for (int i = 0;i<n;i++){

            ImageView img = new ImageView(this);
            if (i == 0){
                img.setImageResource(R.drawable.dot_ok);
            }else {
                img.setImageResource(R.drawable.dot_default);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            params.setMargins(10, 0, 10, 0);

            liDots.addView(img,params);
            imgs[i] = (ImageView) liDots.getChildAt(i);
        }

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
        private List<ImageView> lists;

        public GuidePagerAdapter(List<ImageView> lists) {
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
