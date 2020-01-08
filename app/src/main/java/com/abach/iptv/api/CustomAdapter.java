package com.abach.iptv.api;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.abach.iptv.R;
import com.abach.iptv.activity.MainActivity;
import com.abach.iptv.activity.Stream;
import com.abach.iptv.model.Channels;


import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Channels> datalist;
    private Context context;


    public CustomAdapter(Context context,List<Channels> datalist)
    {
        this.context=context;
        this.datalist=datalist;
    }


    class CustomViewHolder extends ViewHolder{

        public final View mView;
        TextView textView;
        TextView textView1;
        ImageView imageView;
        ImageView imageView2;

        public CustomViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            textView=mView.findViewById(R.id.channelname);
            textView1=mView.findViewById(R.id.live);
            imageView=mView.findViewById(R.id.channelimage);
            imageView2=mView.findViewById(R.id.imageView2);

        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.channel_view,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomAdapter.CustomViewHolder holder, final int position) {

        holder.textView.setText(datalist.get(position).getName());

        switch(datalist.get(position).getType()) {
            case "1":
                holder.imageView.setImageResource(R.drawable.livefootball);
                holder.imageView2.setImageResource(R.drawable.live);
                break;
            case "2":
                holder.imageView.setImageResource(R.drawable.entertainment);
                holder.imageView2.setImageResource(R.drawable.entertain);
                break;
            case "3":
                holder.imageView.setImageResource(R.drawable.news);
                holder.imageView2.setImageResource(R.drawable.tv);
                break;
            case "4":
                holder.imageView.setImageResource(R.drawable.cartoon);
                holder.imageView2.setImageResource(R.drawable.child);
                break;
            default:
                holder.imageView.setImageResource(R.drawable.livefootball);
                holder.imageView2.setImageResource(R.drawable.sports);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Stream.class);
                intent.putExtra("uri",datalist.get(position).getLink());
                intent.putExtra("type",datalist.get(position).getType());
                intent.putExtra("channelname",datalist.get(position).getName());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

}
