package com.example.murali.alpha.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.murali.alpha.R;
import com.example.murali.alpha.cache.SmartImageView;
import com.example.murali.alpha.pojo.AlphaData;

import java.util.List;

/**
 * Created by murali on 12/8/2016.
 */
public class AlphaAdapter extends ArrayAdapter<AlphaData> {

    Context context;
    private List<AlphaData> alphaDataList;

    public AlphaAdapter(Context context, List<AlphaData> alphaDataList) {
        super(context, -1, alphaDataList);
        this.context = context;
        this.alphaDataList = alphaDataList;
    }

    @Override
    public int getCount() {
        return alphaDataList.size();
    }

    private static class ViewHolder {

        SmartImageView userImage;
        TextView userName;
        TextView timeStamp;
        TextView userPost;

        /*
           Initialize the views
         */

        public ViewHolder(View view) {
            this.userImage = (SmartImageView) view.findViewById(R.id.user_image);
            this.userName = (TextView) view.findViewById(R.id.user_name);
            this.timeStamp = (TextView) view.findViewById(R.id.time_stamp);
            this.userPost = (TextView) view.findViewById(R.id.user_posts_text);
        }

    }

    /*
       fetch the data to the appropriate views
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.activity_single_row_data, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        AlphaData data = alphaDataList.get(position);

        if (data.url != null) {
            viewHolder.userImage.setImageUrl(data.url);
        }

        viewHolder.userName.setText(data.posterName);
        viewHolder.timeStamp.setText(data.date);
        viewHolder.userPost.setText(data.posterText);

        return view;

    }
}

