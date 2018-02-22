package vad.adler.newsapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import vad.adler.newsapp.data.Article;
import vad.adler.newsapp.news.NewsContract;
import vad.adler.newsapp.news.NewsPresenter;

public class MainActivity extends DaggerAppCompatActivity implements NewsContract.View {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindDrawable(R.drawable.ic_menu_black_24px) Drawable ic_menu;
    @BindString(R.string.toolbar_title) String toolbarTitle;
    @BindString(R.string.not_implemented) String notImplemented;
    @BindView(R.id.nvView) NavigationView nvView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;

    @Inject
    NewsPresenter mNewsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        toolbar.setNavigationIcon(ic_menu);
        toolbar.setTitle(toolbarTitle);

        setupDrawerContent(nvView);
        ActionBarDrawerToggle drawerToggle = setupDrawerToggle();
        drawer.addDrawerListener(drawerToggle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mNewsPresenter.subscribe();
    }

    @OnClick({R.id.search})
    public void setViewOnClickEvent(View view) {
        switch(view.getId())
        {
            case R.id.search:
                Snackbar.make(view, notImplemented, Snackbar.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void showNews(List<Article> articles) {

    }

    /**
     * Set a listener that will be notified when a menu item is selected.
     *
     * @param navigationView Represents a standard navigation menu for the application.
     *                       The menu contents is populated by menu resource file.
     */
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    /**
     * Handle selected item of the navigation menu.
     *
     * @param menuItem selected menu item.
     */
    public void selectDrawerItem(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.category_general:
            case R.id.category_business:
            case R.id.category_technology:
            case R.id.category_entertainment:
            case R.id.category_sports:
            case R.id.category_health:
            case R.id.category_science:
            default:
                break;

        }
        Snackbar.make(toolbar, notImplemented, Snackbar.LENGTH_LONG).show();
        drawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }
}
