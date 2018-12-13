package com.catherine.forrealm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.catherine.forrealm.about_utils.RealmHelper;
import com.catherine.forrealm.model.StudentAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyc_realm_show;
    Button btn_realm_add;
    Button btn_realm_del;
    Button btn_realm_clear;
    Button btn_realm_show;
    Button btn_realm_find_by;
    Button btn_realm_del_by;
    Button btn_realm_search;
    EditText et_realm_num;
    EditText et_realm_name;
    EditText et_realm_age;
    RealmHelper mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyc_realm_show = findViewById(R.id.ac_recyc);
        btn_realm_add = findViewById(R.id.ac_btn_add);
        btn_realm_del = findViewById(R.id.ac_btn_del);
        btn_realm_clear = findViewById(R.id.ac_btn_clear);
        btn_realm_show = findViewById(R.id.ac_btn_findall);
        btn_realm_find_by = findViewById(R.id.ac_btn_add_by);
        btn_realm_del_by = findViewById(R.id.ac_btn_query);
        btn_realm_search = findViewById(R.id.ac_btn_search);
        et_realm_num = findViewById(R.id.ac_et_num);
        et_realm_name = findViewById(R.id.ac_et_name);
        et_realm_age = findViewById(R.id.ac_et_age);

        mRealm = new RealmHelper();
        recyc_realm_show.setHasFixedSize(true);
        recyc_realm_show.setNestedScrollingEnabled(true);
        recyc_realm_show.setLayoutManager(new GridLayoutManager(MainActivity.this,1));

        operateRealm();
    }

    List<Student> mRealmList = new ArrayList<>();
    StudentAdapter realmAdapter = null;
    private void operateRealm() {

        List<Student> studentList = mRealm.findAllStudent();
        mRealmList.clear();
        mRealmList.addAll(studentList);

        btn_realm_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Student student = new Student();
                    student.setNum(et_realm_num.getText().toString());
                    student.setName(et_realm_name.getText().toString());
                    student.setAge(Integer.parseInt(et_realm_age.getText().toString()));
                    mRealm.addStudent(student);
                    List<Student> studentList = mRealm.findAllStudent();
                    mRealmList.clear();
                    mRealmList.addAll(studentList);
                    realmAdapter.notifyDataSetChanged();
                    Log.e("bendi", "------add--------" + mRealmList.size());
                }
                catch (Exception ignored){

                }
            }
        });

        btn_realm_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRealm.delStudent();
                List<Student> studentList = mRealm.findAllStudent();
                mRealmList.clear();
                mRealmList.addAll(studentList);
                realmAdapter.notifyDataSetChanged();
                Log.e("bendi","-------del-------"+mRealmList.size());
            }
        });

        btn_realm_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRealm.clearStudent();
                List<Student> studentList = mRealm.findAllStudent();
                mRealmList.clear();
                mRealmList.addAll(studentList);
                realmAdapter.notifyDataSetChanged();
                Log.e("bendi","------clear--------"+mRealmList.size());
            }
        });

        btn_realm_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Student> studentList = mRealm.findAllStudent();
                mRealmList.clear();
                mRealmList.addAll(studentList);
                realmAdapter.notifyDataSetChanged();
                Log.e("bendi","------all--------"+mRealmList.size());
            }
        });

        btn_realm_find_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Student> studentList = mRealm.findByNumStudent(et_realm_num.getText().toString());
                mRealmList.clear();
                mRealmList.addAll(studentList);
                realmAdapter.notifyDataSetChanged();
                Log.e("bendi", "--------find_by--------" + mRealmList.size());
            }
        });

        btn_realm_del_by.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRealm.delByStudent(et_realm_num.getText().toString());
                List<Student> studentList;
                mRealmList.clear();
                studentList = mRealm.findAllStudent();
                mRealmList.addAll(studentList);
                realmAdapter.notifyDataSetChanged();
                Log.e("bendi", "--------delete_by--------" + mRealmList.size());
            }
        });

        btn_realm_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        realmAdapter = new StudentAdapter(mRealmList,MainActivity.this);
        recyc_realm_show.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyc_realm_show.setAdapter(realmAdapter);
        realmAdapter.notifyDataSetChanged();
        Log.e("bendi","------final--------"+mRealmList.size());

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
