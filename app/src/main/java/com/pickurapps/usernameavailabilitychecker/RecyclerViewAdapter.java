package com.pickurapps.usernameavailabilitychecker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Website> mData;

    public RecyclerViewAdapter(Context context, List<Website> data) {
        mContext = context;
        mData = data;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvWebsiteName;
        ImageView imgWebsiteIcon;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvWebsiteName = itemView.findViewById(R.id.website_title_id);
            imgWebsiteIcon = itemView.findViewById(R.id.website_img_id);
            cardView = itemView.findViewById(R.id.cardview_id);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_website, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).tvWebsiteName.setText(mData.get(position).getName());
        ((MyViewHolder) holder).imgWebsiteIcon.setImageResource(mData.get(position).getIcon());
        ((MyViewHolder) holder).imgWebsiteIcon.setBackgroundColor(mData.get(position).getBgColor());
        ((MyViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // What to do when a card view is clicked
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
