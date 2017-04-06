package com.greenhand.game73.fm.home.product;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greenhand.game73.R;
import com.greenhand.game73.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * "尖货"
 */
public class ProductFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TabLayout tabTop;
    private ViewPager vp;
    private View rootView;
    private List<String> tabList;
    private List<Fragment> fragments;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_product, container, false);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initView();

    }

    private void initData() {

        tabList = new ArrayList<>();
        fragments = new ArrayList<>();
        //初始tab
        tabList.add("全球晒货");
        tabList.add("库大师");
        //初始化Fragment
        fragments.add(ShowProduct.newInstance("",""));
        fragments.add(MasterFragment.newInstance("",""));
    }

    private void initView() {

        tabTop = (TabLayout) rootView.findViewById(R.id.tab_product);
        //设置tab属性
        setTab();
        vp = (ViewPager) rootView.findViewById(R.id.vp_product);
        //关联vp
        bindVp();
    }

    private void bindVp() {

        //创建适配器
        MyFragmentPagerAdaper pagerAdaper = new MyFragmentPagerAdaper(getChildFragmentManager(), tabList, fragments);
        vp.setAdapter(pagerAdaper);
        //关联
        tabTop.setupWithViewPager(vp);
    }

    private void setTab() {

        tabTop.setTabMode(TabLayout.MODE_FIXED);
        //tab居中显示
        tabTop.setTabGravity(TabLayout.GRAVITY_FILL);
        //tab的字体选择器,默认黑色,选择时红色
        tabTop.setTabTextColors(Color.WHITE, Color.RED);
        //tab的下划线颜色,默认是粉红色
        tabTop.setSelectedTabIndicatorColor(Color.RED);
    }


    private class MyFragmentPagerAdaper extends FragmentPagerAdapter{

        private List<String> tabs;
        private List<Fragment> fragments;

        public MyFragmentPagerAdaper(FragmentManager fm, List<String> tabs, List<Fragment> fragments) {
            super(fm);
            this.tabs = tabs;
            this.fragments = fragments;
        }

        /**
         * 返回也卡标题
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }


    }
}
