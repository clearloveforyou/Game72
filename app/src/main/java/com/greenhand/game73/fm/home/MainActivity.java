package com.greenhand.game73.fm.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.greenhand.game73.R;
import com.greenhand.game73.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_FRAGMENT_SHOW = "state";//内存不足重启时的当前fragment
    private Fragment homeFragment, productFragment, categoryFragment, carFragment, mineFragment;
    private Fragment currentFragment;//当前Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        initView();
        //增加fragment
        chooseFragment(savedInstanceState);

    }

    private void initView() {


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        //“内存重启”时保存当前的fragment名字
        if (currentFragment != null){
            outState.putString(STATE_FRAGMENT_SHOW, currentFragment.getClass().getName());
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * 这个方法主用用于解决由于“内存重启”造成的Fragment重叠的问题，以及保存了重启后fragment是之前的状态：
     * 1.判断savedInstanceState是否为空
     * 不为空：正常的启动，那就正常初始化fragment：即调用：initTab();
     * 为空：判断savename是否为空
     * 为空：取出FragmentManager自动保存的fragment，
     * （注意判断fragment是否为空，因为没点击的tab,我们没加载fragment，也就没设Tag）
     * 显示首页fragment，隐藏其他页
     * 不为空： 设为tag为savename的fragment显示，
     * 其他不为空的fragment隐藏。
     *
     * @param savedInstanceState
     */
    private void chooseFragment(Bundle savedInstanceState) {

        //内存重启时的判断：
        if (savedInstanceState != null) {

            //获取“内存重启”时保存的fragment名字
            String saveName = savedInstanceState.getString(STATE_FRAGMENT_SHOW);
            //由Tag取出Fragment
            homeFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
            productFragment = getSupportFragmentManager().findFragmentByTag(ProductFragment.class.getName());
            categoryFragment = getSupportFragmentManager().findFragmentByTag(CategoryFragment.class.getName());
            carFragment = getSupportFragmentManager().findFragmentByTag(CarFragment.class.getName());
            mineFragment = getSupportFragmentManager().findFragmentByTag(MineFragment.class.getName());
            //放入list(便于判断)
            List<Fragment> fragments = new ArrayList<>();
            fragments.add(homeFragment);
            fragments.add(productFragment);
            fragments.add(categoryFragment);
            fragments.add(carFragment);
            fragments.add(mineFragment);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //如果为空，只解决内存重启时的重叠问题
            if (TextUtils.isEmpty(saveName)) {
                //首页比不为空，显示
                transaction.show(homeFragment);
                for (int i = 1, len = fragments.size(); i < len; i++) {
                    if (fragments.get(i) != null) {
                        //隐藏不为空的fragment（为空即为：从来没有add过）
                        transaction.hide(fragments.get(i));
                    }
                }
                currentFragment = homeFragment;//记录当前fragment
                transaction.commit();
            } else {

                for (int i = 0, len = fragments.size(); i < len; i++) {
                    Fragment fragment = fragments.get(i);
                    if (fragment != null) {
                        if (TextUtils.equals(saveName, fragment.getClass().getName())) {
                            transaction.show(fragment);
                            //记录当前fragment
                            currentFragment = fragment;
                        } else {
                            transaction.hide(fragment);
                        }
                    }
                }
                transaction.commit();

            }

        } else {
            //正常启动
            //TODO
        }
    }



    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)//给Fragment设置tag
                    .add(R.id.main_fragment_container, fragment, fragment.getClass().getName()).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当前Activity页面所有的请求以Activity对象作为tag，可以在onDestory里面统一取消。
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
