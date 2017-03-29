package com.example.administrator.starmusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.starmusic.R;
import com.example.administrator.starmusic.entity.Music;
import com.example.administrator.starmusic.entity.MusicPeople;

import java.util.List;

import static android.R.id.list;

/**
 * Created by Administrator on 2016/11/16.
 */

public class Bangdanadapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int[] ids={R.mipmap.baohongbang,R.mipmap.wanghongbang,R.mipmap.mcbang,R.mipmap.lingleibang,R.mipmap.yuanchuangbang,R.mipmap.renqibang,R.mipmap.fengxiangbang,R.mipmap.fanchangbang};
    private List<List<MusicPeople>> list;
    private List<MusicPeople> listpeople;
    public Bangdanadapter(Context context,List<List<MusicPeople>> list){
        inflater=LayoutInflater.from(context);
        this.list=list;
    }
    @Override
    public int getCount() {
        return ids.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int id=ids[position];
        listpeople=list.get(position);
        ViewHolder viewHolder;
        if(convertView==null){
       convertView= inflater.inflate(R.layout.bangdan_list_item,null);
            viewHolder=new ViewHolder();
//            viewHolder.civ= (CircleImageView) convertView.findViewById(R.id.iv_head);
            viewHolder.iv= (ImageView) convertView.findViewById(R.id.iv_head);

            viewHolder.tv1= (TextView) convertView.findViewById(R.id.tv1);
            viewHolder.tv2= (TextView) convertView.findViewById(R.id.tv2);
            viewHolder.tv3= (TextView) convertView.findViewById(R.id.tv3);

            viewHolder.tv_wh= (TextView) convertView.findViewById(R.id.tv_wh);
            viewHolder.tv_whrz= (TextView) convertView.findViewById(R.id.tv_whrz);
            viewHolder.tv_dj= (TextView) convertView.findViewById(R.id.tv_dj);

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
//        viewHolder.civ.setImageResource(id);
        viewHolder.iv.setImageResource(id);

        viewHolder.tv1.setText(listpeople.get(0).getMusicName());
        viewHolder.tv2.setText(listpeople.get(1).getMusicName());
        viewHolder.tv3.setText(listpeople.get(2).getMusicName());

        viewHolder.tv_wh.setText(listpeople.get(0).getPeopleName());
        viewHolder.tv_whrz.setText(listpeople.get(1).getPeopleName());
        viewHolder.tv_dj.setText(listpeople.get(2).getPeopleName());


        return convertView;
    }
    class ViewHolder{
//        CircleImageView civ;
        ImageView iv;
        TextView tv_wh;
        TextView tv_whrz;
        TextView tv_dj;
        TextView tv1;
        TextView tv2;
        TextView tv3;
    }
}
