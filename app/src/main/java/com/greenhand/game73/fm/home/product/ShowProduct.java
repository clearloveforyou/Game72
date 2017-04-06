package com.greenhand.game73.fm.home.product;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.greenhand.game73.R;
import com.greenhand.game73.constant.Constant;
import com.greenhand.game73.okhttp.OkHttpUtils;
import com.greenhand.game73.okhttp.callback.StringCallback;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowProduct#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowProduct extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View rootView;
    private TabLayout tabs;
    private RecyclerView rvContent;
    private List<ItemBean> list;
    private HeadBean headBean = new HeadBean();
    private RecyclerViewAdapter adapter;

    public ShowProduct() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowProduct.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowProduct newInstance(String param1, String param2) {
        ShowProduct fragment = new ShowProduct();
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
        rootView = inflater.inflate(R.layout.fragment_show_product, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
    }

    private void initData() {

        //content
        OkHttpUtils
                .get()
                .url(Constant.GET_PRODUCT_CONTENT)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        list.addAll(json(response));
                        //刷新数据
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    /**
     * 解析
     *
     * @param str
     */
    private List<ItemBean> json(String str) {
        List<ItemBean> list = new ArrayList<>();
        String s = str.substring(str.indexOf("(") + 1, str.lastIndexOf(")"));
        try {
            JSONObject j = new JSONObject(s);
            JSONArray array = j.getJSONArray("commentShowDisplays");
            for (int i = 0, len = array.length(); i < len; i++) {

                JSONObject object = array.getJSONObject(i);
                if (i == 0) {
                    String remark = object.getString("remark");
                    String imgurl = object.getString("imgurl");
                    headBean.setRemark(remark);
                    headBean.setImgurl(imgurl);
                } else {
                    String id = object.getString("id");
                    String productId = object.getString("productId");
                    String nickName = object.getString("nickName");
                    String createDate = object.getString("createDate");
                    String headImg = object.getString("headImg");
                    String content = object.getString("content");
                    String commentCount = object.getString("commentCount");
//                    JSONArray commentList = object.getJSONArray("commentList");
//                    List<CommentBean> commentBeanList = new ArrayList<>();
//                    for (int h = 0; h < commentList.length(); h++) {
//                        JSONObject jsonObject = commentList.getJSONObject(h);
//                        String createDate1 = jsonObject.getString("createDate");
//                        String content1 = jsonObject.getString("content");
//                        String headImg1 = jsonObject.getString("headImg");
//                        String nickName1 = jsonObject.getString("nickName");
//                        commentBeanList.add(new CommentBean(createDate1, content1, headImg1, nickName1));
//                    }

                    JSONArray info = object.getJSONArray("productInfo");
                    List<ProductBean> productBeanList = new ArrayList<>();
                    for (int k = 0; k < info.length(); k++) {
                        JSONObject jsonObject = info.getJSONObject(k);
                        String productId1 = jsonObject.getString("productId");
                        String productImg1 = jsonObject.getString("productImg");
                        productBeanList.add(new ProductBean(productId1, productImg1));
                    }
                    list.add(new ItemBean(id, productId, nickName, createDate, headImg, content, commentCount, null, productBeanList));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void initView() {
        tabs = (TabLayout) rootView.findViewById(R.id.tab_show_product);
        setTabs();
        rvContent = (RecyclerView) rootView.findViewById(R.id.rv_content);
        setRvContent();
    }

    private void setTabs() {


        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.setSelectedTabIndicatorHeight(0);
        tabs.setTabTextColors(Color.BLACK, Color.RED);
        tabs.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabs.addTab(tabs.newTab().setText("全部"));
        tabs.addTab(tabs.newTab().setText("包袋"));
        tabs.addTab(tabs.newTab().setText("鞋服"));
        tabs.addTab(tabs.newTab().setText("首饰"));
        tabs.addTab(tabs.newTab().setText("腕表"));
        tabs.addTab(tabs.newTab().setText("配饰"));
        tabs.addTab(tabs.newTab().setText("家具"));
        tabs.addTab(tabs.newTab().setText("美妆"));
        tabs.addTab(tabs.newTab().setText("母婴"));
        tabs.addTab(tabs.newTab().setText("户外"));
        tabs.addTab(tabs.newTab().setText("营养保健"));
        tabs.addTab(tabs.newTab().setText("豪车"));
        tabs.addTab(tabs.newTab().setText("美酒与美食"));
        tabs.addTab(tabs.newTab().setText("生活方式"));
        //监听
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setRvContent() {

        //设置布局管理器--listview
        rvContent.setLayoutManager(getLinearLayoutManager());
        //设置分割线
        rvContent.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(Color.GRAY)
                .size(1)
                .build());
        //创建适配器
        list = new ArrayList<>();
        adapter = new RecyclerViewAdapter(list, headBean, getActivity());
        //关联适配器
        rvContent.setAdapter(adapter);
        //监听
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "你点了" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 布局管理器
     * listview 的水平与垂直
     *
     * @return
     */
    private LinearLayoutManager getLinearLayoutManager() {

        LinearLayoutManager llm = null;

        //相当于ListView
        llm = new LinearLayoutManager(getActivity());

//        //水平方向的ListView
//        llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//
        return llm;
    }

    /**
     * Gridview
     *
     * @return
     */
    public GridLayoutManager getGridLayoutManager() {
//        GridLayoutManager glm = new GridLayoutManager(this, 2);
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        return glm;
    }

    /**
     * 实现瀑布流,图片墙效果
     *
     * @return
     */
    public StaggeredGridLayoutManager getStaggeredGridLayoutManager() {
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        return sglm;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
