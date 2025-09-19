package com.example.mechat.adapter;

//package com.example.mechat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.mechat.R;
import com.example.mechat.model.Friend;

import java.util.List;

public class FriendListAdapter extends BaseAdapter {
    private Context context;
    private List<Friend> friends;

    public FriendListAdapter(Context context, List<Friend> friends) {
        this.context = context;
        this.friends = friends;
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);
            holder = new ViewHolder();
            holder.ivAvatar = convertView.findViewById(R.id.ivFriendAvatar);
            holder.tvName = convertView.findViewById(R.id.tvFriendName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Friend friend = friends.get(position);
        if (friend.getFriendAvatar() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(friend.getFriendAvatar(), 0, friend.getFriendAvatar().length);
            holder.ivAvatar.setImageBitmap(bitmap);
        }
        holder.tvName.setText(friend.getFriendName());

        return convertView;
    }

    static class ViewHolder {
        ImageView ivAvatar;
        TextView tvName;
    }
}
