package com.catherine.forrealm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.catherine.forrealm.about_utils.RealmHelper;
import com.catherine.forrealm.model.SearchAdapter;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity{

    /**
     * 搜索图片
     */
    private ImageView img_search;
    /**
     * 搜索框
     */
    private EditText et_search;
    /**
     * 删除按钮
     */
    private ImageView img_del;
    /**
     * RecycleView
     */
    private RecyclerView recy_search;
    /**
     * 全部匹配的适配器
     */
    private SearchAdapter searchAdapter = null;
    /**
     * 所有数据，可以是联网获取，如果有需要可以将其储存在数据库中，我们用简单的String做演示
     */
    private List<Student> studentList = new ArrayList<>();
    /**
     * 此list用来保存符合我们规则的数据
     */
    private List<Student> studentKeyWordList = new ArrayList<>();
    /**
     * Realm 数据库
     */
    private RealmHelper realm_search = new RealmHelper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        refershUI();
        initData();
        setEditTextListener();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        et_search = findViewById(R.id.ac_et_search);
        img_search = findViewById(R.id.ac_img_search);
        img_del = findViewById(R.id.ac_img_del);
        recy_search = findViewById(R.id.ac_recyc);
        recy_search.setLayoutManager(new LinearLayoutManager(this));

        studentList = realm_search.findAllStudent();
        recy_search.setHasFixedSize(true);
        recy_search.setNestedScrollingEnabled(true);
        recy_search.setLayoutManager(new GridLayoutManager(SearchActivity.this,1));

    }


    /**
     * 刷新UI
     */
    private void refershUI() {

        if (searchAdapter == null){
            studentKeyWordList.clear();
            searchAdapter = new SearchAdapter(this,studentKeyWordList);
            Log.e("search","--------searchAdapter_refreshUI--------"+studentList.size()+"——"+studentKeyWordList.size()+"+///////////+"+searchAdapter.getItemCount());
            recy_search.setAdapter(searchAdapter);
            searchAdapter.notifyDataSetChanged();
        }
        else {
            searchAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 从数据库中获取数据，初始化数据
     */
    private void initData() {

        studentKeyWordList.addAll(studentList);
        searchAdapter.notifyDataSetChanged();
        refershUI();
    }

    /**
     * 设置EditText的监听
     */
    private void setEditTextListener() {
        //  EditText的监听
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //每次EditText内容改变时执行，控制删除按钮的显示隐藏
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0){
                    img_del.setVisibility(View.GONE);
                }else {
                    studentKeyWordList.clear();
                    Log.e("search","--------KeyWordList_setETListener-----"+studentKeyWordList);
                    searchAdapter = new SearchAdapter(getApplicationContext(),studentKeyWordList);
                    Log.e("search","--------searchAdapter_refreshUI--------"+studentList.size()+"——"+studentKeyWordList.size()+"+///////////+"+searchAdapter.getItemCount());
                    recy_search.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();
                    img_del.setVisibility(View.VISIBLE);
                    img_del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            et_search.setText("");
                        }
                    });
                    img_search.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                }

                //  匹配文字，变色
                doChangeColor(editable.toString().trim());
            }

        });
    }

    /**
     * 显示搜索的关键字颜色改变
     * @param text
     */
    private void doChangeColor(String text) {
        //  clear是必须的，不然只要改变EditText的数据，list 就会一直添加数据进来
        studentKeyWordList.clear();
        //  不需要匹配，把所有数据都传进来，不需要变色
        if (text.equals("")){
            studentKeyWordList.addAll(studentList);
            //  防止匹配过文字之后点击删除按钮，字体仍然变色的问题
            searchAdapter.setText(null);
            refershUI();
        }else {
            //  如果EditText里面有数据，则根据EditText里面的数据进行匹配，用contains判断是否包含该数据，包含的话则加入到list中
            for (Student i : studentList){
                String age = i.getAge()+"";
                if (i.getNum().contains(text) || i.getName().contains(text) || age.contains(text)){
                    studentKeyWordList.add(i);
                    Log.e("search","---------studentKeyWordList_doChangeColor--------"+studentKeyWordList.size());
                    refershUI();
                }
            }
            //  设置要变色的关键字
            searchAdapter.setText(text);
            refershUI();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm_search.close();
    }
}
