package com.yuan.app.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.yuan.app.R;
import com.yuan.app.constants.URLs;
import com.yuan.app.entities.Themes;
import com.yuan.app.fragment.BaseFragment;
import com.yuan.app.fragment.OtherNewsFragment;
import com.yuan.app.net.ThemesService;
import com.yuan.app.other.BaseRetrofitCallBack;
import com.yuan.app.other.MyFragmentAdapter;
import com.yuan.app.utils.RetrofitUtils;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        Toolbar.OnMenuItemClickListener, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.main_pager)
    ViewPager mainPager;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.appbarlayout)
    AppBarLayout appbarlayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String date;
    private Themes themes;
//    private float lastLoacte;
//    private float currentLoacte;
//    private float screenWidth = CommonUtils.getScreenWidth();

    @Override
    protected void init() {
        toolbar.inflateMenu(R.menu.toobar_menu);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setTitle(R.string.app_name);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawer.setDrawerListener(actionBarDrawerToggle);
        navigationView.setNavigationItemSelectedListener(this);
        mainPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        //加载侧换菜单的数据
        RetrofitUtils.request(ThemesService.class, "getAllThemes", new BaseRetrofitCallBack<Themes>(URLs.THEME_LIST, Themes.class) {
            @Override
            protected void handleMessage(Themes body) {
                MainActivity.this.themes = body;
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.content_main;
    }

    //选择item之后关闭侧滑菜单，刷新数据
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        toolbar.setTitle(item.getTitle());
        switch (item.getItemId()) {
            case R.id.navigation_item_1:
                toolbar.getMenu().getItem(0).setVisible(true);
                mainPager.setCurrentItem(0);
                break;
            default:
                toolbar.getMenu().getItem(0).setVisible(false);
                mainPager.setCurrentItem(1);
                OtherNewsFragment otherNewsFragment = (OtherNewsFragment) BaseFragment.newInstance(BaseFragment.OTHER_FRAGMENT);
                if (themes != null) {
                    List<Themes.OthersBean> others = themes.getOthers();
                    int position = item.getOrder();
                    otherNewsFragment.onThemeChange(others.get(position).getId(), others.get(position));
                }
        }
        drawer.closeDrawers();
        return true;
    }

//    //滑动屏幕五分之一的时候Toolbar完全隐藏。
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                lastLoacte = event.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                currentLoacte = event.getRawY();
//                //向下滑distanc>0，向上滑distance<0
//                float distance = currentLoacte - lastLoacte;
//                if (distance == (screenWidth) / 5) {
//                    break;
//                }
//                if (distance < 0) {
//                    if (appbarlayout.getAlpha() == 0) {
//                        appbarlayout.setVisibility(View.GONE);
//                        break;
//                    }
//                    appbarlayout.setAlpha((distance / (screenWidth) * 30) + appbarlayout.getAlpha());
//                } else {
//                    appbarlayout.setVisibility(View.VISIBLE);
//                    appbarlayout.setAlpha(1);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                lastLoacte = 0;
//                currentLoacte = 0;
//                break;
//        }
//        return false;
//    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        super.dispatchTouchEvent(ev);
//        onTouchEvent(ev);
//        return false;
//    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calender:
                showTimeDialog();
                break;
        }
        return true;
    }

    private void showTimeDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String myYear = String.valueOf(year);
        String myMonth = String.valueOf(monthOfYear + 1);
        if (myMonth.length() < 2) {
            myMonth = "0" + myMonth;
        }
        String myDay = String.valueOf(dayOfMonth);
        if (myDay.length() < 2) {
            myDay = "0" + myDay;
        }
        date = myYear + myMonth + myDay;
        BaseFragment.newInstance(BaseFragment.MAIN_FRAGMENT).onToolBarChange(date);
    }
}