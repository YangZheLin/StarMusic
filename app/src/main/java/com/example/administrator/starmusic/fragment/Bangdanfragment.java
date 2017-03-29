package com.example.administrator.starmusic.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.starmusic.R;
import com.example.administrator.starmusic.adapter.Bangdanadapter;
import com.example.administrator.starmusic.entity.MusicPeople;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */

public class Bangdanfragment extends Fragment {
    private Context activty;
    public Bangdanfragment(){

    }
    public Bangdanfragment(Context context){
        activty=context;
    }
    private ListView lvbangdan;
    private Bangdanadapter bangdanadapter;
    private List<MusicPeople> listpeople1=new ArrayList<>();
    private List<MusicPeople> listpeople2=new ArrayList<>();
    private List<MusicPeople> listpeople3=new ArrayList<>();
    private List<MusicPeople> listpeople4=new ArrayList<>();
    private List<MusicPeople> listpeople5=new ArrayList<>();
    private List<MusicPeople> listpeople6=new ArrayList<>();
    private List<MusicPeople> listpeople7=new ArrayList<>();
    private List<MusicPeople> listpeople8=new ArrayList<>();


    private List<List<MusicPeople>> list=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bangdan_fragment,null);
        lvbangdan= (ListView) view.findViewById(R.id.lv_bangdan);
        listpeople1.add(new MusicPeople("Mc高迪","一人我饮酒醉"));
        listpeople1.add(new MusicPeople("Mc九局","梦想"));
        listpeople1.add(new MusicPeople("小右","你是我的女朋友"));

        listpeople2.add(new MusicPeople("Mc大队长","中华上下五千年"));
        listpeople2.add(new MusicPeople("Mc九局","鱼跃龙门"));
        listpeople2.add(new MusicPeople("Mc高迪","这就是爱"));

        listpeople3.add(new MusicPeople("Mc刘毅","他叫李天佑(偷歌贼）"));
        listpeople3.add(new MusicPeople("Mc阿哲","我的好兄弟"));
        listpeople3.add(new MusicPeople("Mc马克","我们的小可爱"));

        listpeople4.add(new MusicPeople("Mc寒小东","曾经的王"));
        listpeople4.add(new MusicPeople("Mc六道","惊雷"));
        listpeople4.add(new MusicPeople("Mc王有道","战火燃烧"));

        listpeople5.add(new MusicPeople("Mc小少焱","斗破苍穹"));
        listpeople5.add(new MusicPeople("MC暴徒","做我的新娘"));
        listpeople5.add(new MusicPeople("Mc蓝弟","王宝强"));

        listpeople6.add(new MusicPeople("Mc龙井","归"));
        listpeople6.add(new MusicPeople("Mc蓝弟","曾经少年梦"));
        listpeople6.add(new MusicPeople("Mc吴迪","我也想要当网红"));

        listpeople7.add(new MusicPeople("新街口组合","暗恋过 结局呢"));
        listpeople7.add(new MusicPeople("Mc谢小宇","三轮车"));
        listpeople7.add(new MusicPeople("Mc小少焱","那个女孩"));

        listpeople8.add(new MusicPeople("Mc楚新","刚好遇见你"));
        listpeople8.add(new MusicPeople("Mc小少焱","待我君临天下"));
        listpeople8.add(new MusicPeople("Mc吴迪","我也想要当网红"));

        list.add(listpeople1);
        list.add(listpeople2);
        list.add(listpeople3);
        list.add(listpeople4);
        list.add(listpeople5);
        list.add(listpeople6);
        list.add(listpeople7);
        list.add(listpeople8);
        bangdanadapter=new Bangdanadapter(activty,list);
        lvbangdan.setAdapter(bangdanadapter);
        return view;
    }
}
