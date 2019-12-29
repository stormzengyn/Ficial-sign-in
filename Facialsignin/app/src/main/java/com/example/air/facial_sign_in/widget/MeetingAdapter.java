package com.example.air.facial_sign_in.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.air.facial_sign_in.R;
import com.example.air.facial_sign_in.model.Meeting;

import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ItemHolder> {

    private List<Meeting> meetings;
    private OnItemClickListener listener;
    private int[] imgResource = {R.drawable.meeting,R.drawable.meeting2,R.drawable.db};

    public MeetingAdapter(List<Meeting> items) {
        meetings = items;
    }
    public MeetingAdapter(List<Meeting> items , OnItemClickListener listener) {
        meetings = items;
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return meetings.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
        //设置Item图片
        holder.image.setImageResource(imgResource[position%3]);
        //设置Item文字
        holder.title.setText(meetings.get(position).getMname());
        //setting sum of people joining the meeting.
        holder.num.setText(""+meetings.get(position).getSum());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });

    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.meeting_item, parent, false));
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView num;

        ItemHolder(View item) {
            super(item);
            image = item.findViewById(R.id.meeting_cover);
            title = item.findViewById(R.id.meeting_title);
            num = item.findViewById(R.id.meeting_num);
        }
    }
    public interface OnItemClickListener{

        void onClick(int pos);
    }

}