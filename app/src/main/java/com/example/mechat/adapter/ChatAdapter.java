package com.example.mechat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mechat.R;
import com.example.mechat.model.Message; // 确保正确导入

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends BaseAdapter {
    private Context context;
    private List<Message> messages;
    private String currentUser;

    public ChatAdapter(Context context, List<Message> messages, String currentUser) {
        this.context = context;
        this.messages = new ArrayList<>(messages);
        this.currentUser = currentUser;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Message getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);
        boolean isMe = message.getSender().equals(currentUser);

        if (convertView == null) {
            int layoutRes = isMe ? R.layout.item_message_sent : R.layout.item_message_received;
            convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
        }

        TextView tvContent = convertView.findViewById(R.id.tvMessageContent);
        TextView tvTime = convertView.findViewById(R.id.tvMessageTime);

        tvContent.setText(message.getContent());
        tvTime.setText(formatTime(message.getTimestamp()));

        return convertView;
    }

    public void addMessage(Message message) {
        messages.add(message);
        notifyDataSetChanged();
    }

    private String formatTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
}
