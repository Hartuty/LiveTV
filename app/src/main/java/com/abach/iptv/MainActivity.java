package com.abach.iptv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialize.holder.ColorHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity {

    private PrimaryDrawerItem item1;
    private PrimaryDrawerItem item2;
    private PrimaryDrawerItem item3;
    private PrimaryDrawerItem item4;
    private PrimaryDrawerItem item5;
    private PrimaryDrawerItem item6;
    private SecondaryDrawerItem item7;
    private SecondaryDrawerItem item8;
    private SecondaryDrawerItem item9;
    private Drawer result;
    private MaterialToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        init();

    }

    private void init()
    {
        new DrawerBuilder().withActivity(this).build();
        item1=new PrimaryDrawerItem().withName(R.string.app_name);
        item2=new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home).withIcon(R.drawable.home);
        item3=new PrimaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_live).withIcon(R.drawable.live);
        item4=new PrimaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_news).withIcon(R.drawable.video);
        item5=new PrimaryDrawerItem().withIdentifier(4).withName(R.string.drawer_item_entertainment).withIcon(R.drawable.entertain);
        item6=new PrimaryDrawerItem().withIdentifier(5).withName(R.string.drawer_item_kid).withIcon(R.drawable.child);
        item7=new SecondaryDrawerItem().withIdentifier(6).withName(R.string.drawer_item_settings).withIcon(R.drawable.settings);
        item8=new SecondaryDrawerItem().withIdentifier(7).withName(R.string.drawer_item_Disclaimer).withIcon(R.drawable.disclaimer);
        item9=new SecondaryDrawerItem().withIdentifier(8).withName(R.string.drawer_item_exit).withIcon(R.drawable.exit);

        result=new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        item3,
                        item4,
                        item5,
                        item6,
                        new DividerDrawerItem(),
                        item7,
                        item8,
                        item9,
                        new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(@Nullable View view, int i, @NotNull IDrawerItem<?> iDrawerItem) {
                        if(iDrawerItem.getIdentifier()==1)
                        {
                            Context context = getApplicationContext();
                            CharSequence text = "Hello toast!";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                        return false;
                    }
                })
                .build();
    }
}
