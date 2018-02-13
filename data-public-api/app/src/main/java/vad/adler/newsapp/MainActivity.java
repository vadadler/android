package vad.adler.newsapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.design.widget.Snackbar;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindDrawable(R.drawable.ic_menu_black_24px) Drawable menu;
    @BindString(R.string.toolbar_title) String toolbarTitle;
    @BindString(R.string.not_implemented) String notImplemented;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //setSupportActionBar(toolbar);
        //getSupportActionBar();
        toolbar.setNavigationIcon(menu);
        toolbar.setTitle(toolbarTitle);
    }

    @OnClick({R.id.toolbar, R.id.search})
        public void setViewOnClickEvent(View view) {
            switch(view.getId())
            {
                case R.drawable.ic_menu_black_24px:
                case R.id.search:
                    Snackbar.make(view, notImplemented, Snackbar.LENGTH_LONG).show();
                    break;
            }
        }
}
