package com.example.administrator.starmusic.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.starmusic.R;
import com.example.administrator.starmusic.entity.Music;
import com.example.administrator.starmusic.view.CircleImageView;
import com.facebook.drawee.view.SimpleDraweeView;


import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by adminwk on 2017/1/25.
 */

public class MusicFragmentAdapter extends BaseAdapter{
    private List<Music> listMusic;
    private Context context;
    private LayoutInflater inflater;
    private String musicPath;
    HashMap<Integer, Boolean> state;

    public MusicFragmentAdapter(List<Music> listMusic ,Context context) {
        if(listMusic!=null&&context!=null){
            this.listMusic=listMusic;
            inflater=LayoutInflater.from(context);
            Log.d("竟来","竟来没有2");
        }else {
            listMusic=new ArrayList<>();
        }
        state = new HashMap<Integer,Boolean>();
        musicPath="http://1p6438u002.51mypc.cn/DreamMusic/pic.do?id=";
//         bitmapUtils = new BitmapUtils(context);
    }

    @Override
    public int getCount() {
        return listMusic.size();
    }

    @Override
    public Object getItem(int position) {
        return listMusic.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Music music=listMusic.get(position);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.musicfragment_item,null);
            viewHolder=new ViewHolder();
            viewHolder.simpleDraweeView= (SimpleDraweeView) convertView.findViewById(R.id.iv_list_item_oic);
//            viewHolder.civ= (CircleImageView) convertView.findViewById(R.id.iv_list_item_oic);
            viewHolder.tvmusic_name= (TextView) convertView.findViewById(R.id.tv_music_name);
            viewHolder.tvyan_chang_zhe= (TextView) convertView.findViewById(R.id.tv_yan_chang_zhe);
            viewHolder.tvyuan_chang= (TextView) convertView.findViewById(R.id.tv_yuan_chuang);
            viewHolder.checkBox= (CheckBox) convertView.findViewById(R.id.cb_liu_liu);
            convertView.setTag(viewHolder);;
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.checkBox.setOnCheckedChangeListener(new MyCheckedListener(position));
        viewHolder.tvyuan_chang.setText(music.getAuthor());
        viewHolder.tvyan_chang_zhe.setText(music.getSinger());
        viewHolder.tvmusic_name.setText(music.getMusicName());
//        bitmapUtils.display(viewHolder.civ, parurl(music.getPicture()));
//        Log.d("url","url="+parurl(music.getPicture()));
        Uri uri = Uri.parse(parurl(music.getId()));
        viewHolder.simpleDraweeView.setImageURI(uri);
        viewHolder.checkBox.setChecked(state.get(position)==null? false : true);


        return convertView;
    }

    private String parurl(String path){

        return musicPath+path;
    }

    class MyCheckedListener implements CompoundButton.OnCheckedChangeListener{
        private int position;
        public MyCheckedListener(int position) {
            this.position=position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked)
            {
                state.put(position, isChecked);
                System.out.println("复选框以选中，选中的行数为：" + position);
            }else{
                state.remove(position);
            }
        }
    }

    class ViewHolder{
//        CircleImageView civ;
        SimpleDraweeView simpleDraweeView;
        TextView tvmusic_name;
        TextView tvyan_chang_zhe;
        TextView tvyuan_chang;
        CheckBox checkBox;
    }
}
