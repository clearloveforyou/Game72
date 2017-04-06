package com.greenhand.game73.fm.home;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.greenhand.game73.R;
import com.greenhand.game73.fm.home.product.ProductFragment;
import com.greenhand.game73.okhttp.OkHttpUtils;

/**
 * 加载Fragment
 * 解决Fragment show和hide时的重叠问题
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup rgMain;
    private static final String STATE_FRAGMENT_SHOW = "state";//内存不足重启时的当前fragment
    private Fragment homeFragment, productFragment, categoryFragment, carFragment, mineFragment;
    private Fragment currentFragment = new Fragment();//当前Fragment

    private Fragment menuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        if (savedInstanceState == null) {
            //正常的加载，初始化tab
            initTab();
        } else {
            //否则就是内存重启的情况：
            //先获取“内存重启”时保存的fragment名字
            String saveName = savedInstanceState.getString(STATE_FRAGMENT_SHOW);
            //由Tag取出Fragment
            homeFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
            productFragment = getSupportFragmentManager().findFragmentByTag(ProductFragment.class.getName());
            categoryFragment = getSupportFragmentManager().findFragmentByTag(CategoryFragment.class.getName());
            carFragment = getSupportFragmentManager().findFragmentByTag(CarFragment.class.getName());
            mineFragment = getSupportFragmentManager().findFragmentByTag(MineFragment.class.getName());
            //创建数组，便于判断
            Fragment[] fragments = new Fragment[]{homeFragment, productFragment, categoryFragment, carFragment, mineFragment};
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            for (int i = 0, len = fragments.length; i < len; i++) {
                Fragment fragment = fragments[i];
                if (fragment != null) {
                    //如果与保存的fragment名字一样，设为show
                    if (TextUtils.equals(saveName, fragment.getClass().getName())) {
                        transaction.show(fragment);
                        //记录当前fragment
                        currentFragment = fragment;
                        RadioButton rb = (RadioButton) rgMain.getChildAt(i);
                        rb.setChecked(true);
                    } else {
                        //否则隐藏
                        transaction.hide(fragment);
                    }
                }
            }
            transaction.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //在activity销毁时，保存当前fragment的名字，以便恢复
        outState.putString(STATE_FRAGMENT_SHOW, currentFragment.getClass().getName());
        super.onSaveInstanceState(outState);
    }

    /**
     * 初始化tab
     */
    private void initTab() {

        RadioButton rbtHome = (RadioButton) findViewById(R.id.rbt_main_home);
        rbtHome.setChecked(true);
    }

    private void initView() {

        rgMain = (RadioGroup) findViewById(R.id.rg_mian);
        rgMain.setOnCheckedChangeListener(this);

        //初始化侧滑菜单的Fragment
        menuFragment = MenuFragment.newInstance("", "");
        initMenuFragment();

    }

    private void initMenuFragment() {

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_draw_container, menuFragment)
                .commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {

            case R.id.rbt_main_home:
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance("", "");
                }
                addOrShowFragment(getSupportFragmentManager(), homeFragment);
                break;
            case R.id.rbt_main_product:
                if (productFragment == null) {
                    productFragment = ProductFragment.newInstance("", "");
                }
                addOrShowFragment(getSupportFragmentManager(), productFragment);
                break;
            case R.id.rbt_main_category:
                if (categoryFragment == null) {
                    categoryFragment = CategoryFragment.newInstance("", "");
                }
                addOrShowFragment(getSupportFragmentManager(), categoryFragment);
                break;
            case R.id.rbt_main_car:
                if (carFragment == null) {
                    carFragment = CarFragment.newInstance("", "");
                }
                addOrShowFragment(getSupportFragmentManager(), carFragment);
                break;
            case R.id.rbt_main_mine:
                if (mineFragment == null) {
                    mineFragment = MineFragment.newInstance("", "");
                }
                addOrShowFragment(getSupportFragmentManager(), mineFragment);
                break;
        }

    }


    /**
     * fragment的切换
     *
     * @param manager
     * @param fragment
     */
    private void addOrShowFragment(FragmentManager manager, Fragment fragment) {

        FragmentTransaction transaction = manager.beginTransaction();
        if (currentFragment == fragment)
            return;

        /**
         * 如果当前fragment未被添加，则添加到Fragment管理器中,隐藏上一个fragment
         * 否则隐藏当前，显示传进来的fragment
         */
        if (!fragment.isAdded()) {
            transaction
                    .hide(currentFragment)//给Fragment设置tag
                    .add(R.id.main_fragment_container, fragment, fragment.getClass().getName());
        } else {
            transaction
                    .hide(currentFragment)
                    .show(fragment);
        }
        //重新给当前fragment赋值
        currentFragment = fragment;
        //提交事务
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当前Activity页面所有的请求以Activity对象作为tag，可以在onDestory里面统一取消。
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
