package com.catherine.forrealm.model;

import android.content.Context;

import com.catherine.forrealm.R;
import com.catherine.forrealm.Student;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class StudentAdapter extends BaseQuickAdapter<Student,BaseViewHolder> {

    private Context context;
    public StudentAdapter(List<Student> data, Context context) {
        super(R.layout.item_realm, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Student item) {
        helper.setText(R.id.item_tv_realm_num,item.getNum());
        helper.setText(R.id.item_tv_realm_name,item.getName());
        helper.setText(R.id.item_tv_realm_age,item.getAge()+"");
    }
}
