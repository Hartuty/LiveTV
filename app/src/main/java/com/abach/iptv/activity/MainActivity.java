package com.abach.iptv.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.abach.iptv.R;
import com.abach.iptv.api.ChannelApi;
import com.abach.iptv.api.CustomAdapter;
import com.abach.iptv.api.RetrofitClient;
import com.abach.iptv.model.Channels;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private AccountHeader accountHeader;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private ProgressBar progressBar;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.recyclerview);
        progressBar=findViewById(R.id.progressbarhome);
        init();
        viewchannels();
        load();
    }

    public void load()
    {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7080541101979840/2258636481");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void viewchannels()
    {

        ChannelApi channelApi= RetrofitClient.getRetrofit().create(ChannelApi.class);
        Call<List<Channels>> call=channelApi.getChannels();
        call.enqueue(new Callback<List<Channels>>() {
            @Override
            public void onResponse(Call<List<Channels>> call, Response<List<Channels>> response) {
                generatedatalist(response.body());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Channels>> call, Throwable t) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Failed");
                alert.setMessage("Checked Your Internet Connection");
                alert.setCancelable(false);

                alert.setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        viewchannels();
                    }
                });

                alert.show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void generatedatalist(List<Channels> channels)
    {
        adapter= new CustomAdapter(MainActivity.this,channels);
        LinearLayoutManager layoutManager= new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void init()
    {
        new DrawerBuilder().withActivity(this).build();
        accountHeader=new AccountHeaderBuilder().withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .build();
        item1=new PrimaryDrawerItem().withName(R.string.drawer_item_football).withIdentifier(6).withIcon(R.drawable.sports);
        item2=new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home).withIcon(R.drawable.home);
        item3=new PrimaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_live).withIcon(R.drawable.live);
        item4=new PrimaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_news).withIcon(R.drawable.tv);
        item5=new PrimaryDrawerItem().withIdentifier(4).withName(R.string.drawer_item_entertainment).withIcon(R.drawable.entertain);
        item6=new PrimaryDrawerItem().withIdentifier(5).withName(R.string.drawer_item_kid).withIcon(R.drawable.child);
        item8=new SecondaryDrawerItem().withIdentifier(7).withName(R.string.drawer_item_Disclaimer).withIcon(R.drawable.warning);
        item9=new SecondaryDrawerItem().withIdentifier(8).withName(R.string.drawer_item_exit).withIcon(R.drawable.exit);

        result=new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        item2,
                        item3,
                        item1,
                        item4,
                        item5,
                        item6,
                        new DividerDrawerItem(),
                        item8,
                        item9
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(@Nullable View view, int i, @NotNull IDrawerItem<?> iDrawerItem) {
                        if (iDrawerItem.getIdentifier()==7) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                            alert.setTitle("Disclaimer");
                            alert.setMessage("We only load Freely Available links from the Internet. Quality and Buffering of the Links " +
                                    "Are not Controlled by us. NOTE!! Any Copyright issues that maybe raised to be directed to the individual stream link. " +
                                    "We DO NOT provide this content.");
                            alert.setCancelable(false);

                            alert.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    viewchannels();
                                }
                            });

                            alert.show();
                        }else if(iDrawerItem.getIdentifier()==8)
                        {
                            finishAndRemoveTask();
                        }else if(iDrawerItem.getIdentifier()==3)
                        {

                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                                Intent intent = new Intent(MainActivity.this, News.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(MainActivity.this, News.class);
                                startActivity(intent);
                            }
                        }else if(iDrawerItem.getIdentifier()==4)
                        {
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                                Intent intent = new Intent(MainActivity.this,Entertainment.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(MainActivity.this, Entertainment.class);
                                startActivity(intent);
                            }
                        }else if(iDrawerItem.getIdentifier()==2)
                        {

                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                                Intent intent = new Intent(MainActivity.this, Live.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(MainActivity.this, Live.class);
                                startActivity(intent);
                            }
                        }else if(iDrawerItem.getIdentifier()==5)
                        {

                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                                Intent intent = new Intent(MainActivity.this, Kids.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(MainActivity.this, Kids.class);
                                startActivity(intent);
                            }
                        }else if(iDrawerItem.getIdentifier()==6)
                        {

                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                                Intent intent = new Intent(MainActivity.this, sports.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(MainActivity.this, sports.class);
                                startActivity(intent);
                            }
                        }
                        return false;
                    }
                })
                .build();
    }

    public void search(View view) {
        Intent intent=new Intent(this,Search.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        load();
    }
}
