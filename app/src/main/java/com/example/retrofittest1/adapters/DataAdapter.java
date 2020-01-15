package com.example.retrofittest1.adapters;

import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofittest1.dialogs.FullScreenDialog;
import com.example.retrofittest1.managers.ImageManager;
import com.example.retrofittest1.MainActivity;
import com.example.retrofittest1.R;
import com.example.retrofittest1.managers.TimeManager;
import com.example.retrofittest1.model.Publish;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.VH>  {

    private MainActivity context;
    private List<Publish> publishes = new ArrayList<>();


    public DataAdapter(MainActivity context) {
        this.context = context;


    }


    public void setPublishes(List<Publish> publishes) {
        this.publishes = publishes;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new VH(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DataAdapter.VH holder, int position) {
        Publish publish = publishes.get(position);
        if (!publish.getThumbnail().equals("")) {
            ImageManager.fetchImage(publishes.get(position).getThumbnail(), holder.thumbnailImgV);

        }else{
            holder.thumbnailImgV.setVisibility(View.INVISIBLE);
        }

        holder.authorTxtV.setText(publish.getAuthor());
        holder.createdTxtV.setText(TimeManager.millsToDate(publish.getCreated()));
        holder.num_commentsTxtV.setText(String.valueOf(publish.getNum_comments()));
    }


    @Override
    public int getItemCount() {
        return publishes.size();
    }



    class VH extends RecyclerView.ViewHolder{
        final ImageView thumbnailImgV;
        final TextView authorTxtV;
        final TextView createdTxtV;
        final TextView num_commentsTxtV;

        public VH(@NonNull View itemView) {

            super(itemView);
            thumbnailImgV = itemView.findViewById(R.id.publish_img);
            authorTxtV = itemView.findViewById(R.id.publish_author);
            createdTxtV = itemView.findViewById(R.id.publish_created);
            num_commentsTxtV = itemView.findViewById(R.id.publish_num_comments);
            thumbnailImgV.setImageResource(R.drawable.ic_launcher_background);

            thumbnailImgV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url="";
                    ImageView img = (ImageView) view;
                    FullScreenDialog fullScreenDialog = new FullScreenDialog(publishes.get(getAdapterPosition()).getUrl());

                    fullScreenDialog.show(context.getSupportFragmentManager(),"ss");
                }
            });
        }
    }
}
