package com.abach.iptv.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abach.iptv.R;
import com.abach.iptv.api.ChannelApi;
import com.abach.iptv.api.CustomAdapter;
import com.abach.iptv.api.RetrofitClient;
import com.abach.iptv.model.Channels;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
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

public class Stream extends AppCompatActivity {

    ProgressBar progressBar;
    PlayerView playerView;
    AspectRatioFrameLayout aspectRatioFrameLayout;
    ImageButton fullscreenbtn;
    ImageButton back;
    boolean fullscreen=false;
    SimpleExoPlayer player;
    Uri mp4VideoUri;
    String uri;
    String type,name;
    RecyclerView recyclerView;
    private CustomAdapter adapter;
    TextView textView;
    private AccountHeader accountHeader;
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
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        recyclerView=findViewById(R.id.recyclerviewstream);
        textView=findViewById(R.id.channelname);
        //initialize();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adViewstream);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        player= new SimpleExoPlayer.Builder(Stream.this).build();
        Intent intent=getIntent();
        uri=intent.getStringExtra("uri");
        type=intent.getStringExtra("type");
        name=intent.getStringExtra("channelname");
        textView.setText(name);

        switch (type){
            case "1":
                getlive();
                break;
            case "2":
                getentertainment();

                break;
            case "3":
                getnews();
                break;
            case "4":
                getkids();
                break;
             default:
                 getsport();
        }
        init(uri);
        videocontrols();

    }

    private void getlive() {

        ChannelApi channelApi= RetrofitClient.getRetrofit().create(ChannelApi.class);
        Call<List<Channels>> call=channelApi.getlive();
        call.enqueue(new Callback<List<Channels>>() {
            @Override
            public void onResponse(Call<List<Channels>> call, Response<List<Channels>> response) {
                generatedatalist(response.body());

            }

            @Override
            public void onFailure(Call<List<Channels>> call, Throwable t) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Stream.this);
                alert.setTitle("Failed");
                alert.setMessage("Checked Your Internet Connection");
                alert.setCancelable(false);

                alert.setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        getlive();
                    }
                });

                alert.show();

            }
        });
    }

    private void getsport() {

        ChannelApi channelApi= RetrofitClient.getRetrofit().create(ChannelApi.class);
        Call<List<Channels>> call=channelApi.getsports();
        call.enqueue(new Callback<List<Channels>>() {
            @Override
            public void onResponse(Call<List<Channels>> call, Response<List<Channels>> response) {

                generatedatalist(response.body());
            }

            @Override
            public void onFailure(Call<List<Channels>> call, Throwable t) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Stream.this);
                alert.setTitle("Failed");
                alert.setMessage("Checked Your Internet Connection");
                alert.setCancelable(false);

                alert.setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        getlive();
                    }
                });

                alert.show();

            }
        });
    }

    private void getentertainment() {

        ChannelApi channelApi= RetrofitClient.getRetrofit().create(ChannelApi.class);
        Call<List<Channels>> call=channelApi.getentertainment();

        call.enqueue(new Callback<List<Channels>>() {
            @Override
            public void onResponse(Call<List<Channels>> call, Response<List<Channels>> response) {
                generatedatalist(response.body());

            }

            @Override
            public void onFailure(Call<List<Channels>> call, Throwable t) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Stream.this);
                alert.setTitle("Failed");
                alert.setMessage("Checked Your Internet Connection");
                alert.setCancelable(false);

                alert.setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        getlive();
                    }
                });

                alert.show();

            }
        });
    }

    private void getnews() {

        ChannelApi channelApi= RetrofitClient.getRetrofit().create(ChannelApi.class);
        Call<List<Channels>> call=channelApi.getnews();
        call.enqueue(new Callback<List<Channels>>() {
            @Override
            public void onResponse(Call<List<Channels>> call, Response<List<Channels>> response) {
                generatedatalist(response.body());

            }

            @Override
            public void onFailure(Call<List<Channels>> call, Throwable t) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Stream.this);
                alert.setTitle("Failed");
                alert.setMessage("Checked Your Internet Connection");
                alert.setCancelable(false);

                alert.setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        getlive();
                    }
                });

                alert.show();

            }
        });
    }

    private void getkids() {

        ChannelApi channelApi= RetrofitClient.getRetrofit().create(ChannelApi.class);
        Call<List<Channels>> call=channelApi.getkids();
        call.enqueue(new Callback<List<Channels>>() {
            @Override
            public void onResponse(Call<List<Channels>> call, Response<List<Channels>> response) {
                generatedatalist(response.body());

            }

            @Override
            public void onFailure(Call<List<Channels>> call, Throwable t) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Stream.this);
                alert.setTitle("Failed");
                alert.setMessage("Checked Your Internet Connection");
                alert.setCancelable(false);

                alert.setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        getlive();
                    }
                });

                alert.show();

            }
        });
    }

    private void generatedatalist(List<Channels> channels)
    {

        adapter= new CustomAdapter(Stream.this,channels);
        LinearLayoutManager layoutManager= new LinearLayoutManager(Stream.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void init(final String uri)
    {
        //Initialize the Player

        mp4VideoUri= Uri.parse(uri);
        playerView=findViewById(R.id.player);
        progressBar=findViewById(R.id.progress);
        aspectRatioFrameLayout=findViewById(R.id.aspect);
        aspectRatioFrameLayout.setAspectRatio(16f/9f);
        playerView.setPlayer(player);

        String userAgent = Util.getUserAgent(this, Util.getUserAgent(this,"AbachTV"));

// Default parameters, except allowCrossProtocolRedirects is true
        DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(
                userAgent,
                null /* listener */,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true /* allowCrossProtocolRedirects */
        );

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(
                this,
                null /* listener */,
                httpDataSourceFactory
        );

        MediaSource videoSource =
                new HlsMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(mp4VideoUri);
        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if(playbackState==ExoPlayer.STATE_BUFFERING)
                {
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.GONE);
                }

                if (playbackState == Player.STATE_IDLE || playbackState == Player.STATE_ENDED ||
                        !playWhenReady) {

                    playerView.setKeepScreenOn(false);
                } else { // STATE_IDLE, STATE_ENDED
                    // This prevents the screen from getting dim/lock
                    playerView.setKeepScreenOn(true);
                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {


                AlertDialog.Builder alert = new AlertDialog.Builder(Stream.this);
                alert.setTitle("Failed");
                alert.setMessage("The Current Stream May Not Be Working at the moment. Try the similar channels below");

                alert.setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        init(uri);
                    }
                });

                alert.show();
            }
        });
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
    }

    private void videocontrols()
    {
        //Video Controls
        fullscreenbtn=findViewById(R.id.fullscreen);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fullscreenbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fullscreen){
                    fullscreenbtn.setBackground(ContextCompat.getDrawable(Stream.this, R.drawable.baseline_fullscreen_24));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    if(getSupportActionBar() != null){
                        getSupportActionBar().show();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    fullscreen = false;
                    mAdView.setVisibility(View.VISIBLE);
                }else
                {
                    fullscreenbtn.setBackground(ContextCompat.getDrawable(Stream.this, R.drawable.baseline_fullscreen_exit_24));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    if(getSupportActionBar() != null){
                        getSupportActionBar().hide();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    fullscreen = true;
                    mAdView.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        player.stop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        init(uri);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }


}
