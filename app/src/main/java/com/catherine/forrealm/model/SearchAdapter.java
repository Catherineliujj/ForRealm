package com.catherine.forrealm.model;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.catherine.forrealm.R;
import com.catherine.forrealm.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Written by LiuJiajia on 12-11-2018
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;

    /**
     * adapter传递过来的数据集合
     */
    private List<Student> list ;

    /**
     * 需要改变颜色的文字
     */
    private String text;

    /**
     * 在SearchActivity中设置文字
     */
    public void setText(String text){this.text = text;}

    public SearchAdapter(Context context,List<Student> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_realm, parent,false));
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        String student_num = list.get(position).getNum();
        String student_name = list.get(position).getName();
        String student_age = list.get(position).getAge()+"";
        /**
         * 如果没有进行搜索操作或者搜索之后点击了删除按钮，我们会在SearchActivity中把text质控并传递过来
         */
        if (text!=null){
            //  设置span
            SpannableString num = matcherSearchText(Color.rgb(39,213,207),student_num,text);
            SpannableString name = matcherSearchText(Color.rgb(39,213,207),student_name,text);
            SpannableString age = matcherSearchText(Color.rgb(39,213,207),student_age,text);
            holder.tv_num.setText(num);
            holder.tv_name.setText(name);
            holder.tv_age.setText(age);
        }else {
            holder.tv_num.setText(student_num);
            holder.tv_name.setText(student_name);
            holder.tv_age.setText(student_age);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    /**
//     * RecyclerView的点击监听接口
//     */
//    public interface onItemClickListener{
//        void onClick(View view,int position);
//    }
//
//    private onItemClickListener onItemClickListener;
//
//    public void setOnItemClickListener(onItemClickListener onItemClickListener){
//        this.onItemClickListener = onItemClickListener;
//    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_num;
        private TextView tv_name;
        private TextView tv_age;

        private ViewHolder(View itemView) {
            super(itemView);
            tv_num = itemView.findViewById(R.id.item_tv_realm_num);
            tv_name = itemView.findViewById(R.id.item_tv_realm_name);
            tv_age = itemView.findViewById(R.id.item_tv_realm_age);
        }
    }

    /**
     * 正则匹配，返回值是一个SpannableString，即经过变色处理的数据
     */
    private SpannableString matcherSearchText(int color, String text, String keyword) {
        SpannableString spannableString = new SpannableString(text);
        //  条件  keyword
        Pattern pattern = Pattern.compile(keyword);
        //  匹配
        Matcher matcher = pattern.matcher(spannableString);
        while (matcher.find()){
            int start = matcher.start();
            int end = matcher.end();
            //ForegroundColorSpan 需要new 不然则是部分变色
            spannableString.setSpan(new ForegroundColorSpan(color),start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        //返回变色处理的结果
        return spannableString;
    }

}