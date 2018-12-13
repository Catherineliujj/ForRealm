package com.catherine.forrealm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Student extends RealmObject {

    //主键
    /**
     * 使用 @primarykey 来标注字段类型必须是字符串（String）或整数（byte、short、int、long）以及它们的包装类型（Byte、Short、Integer、Long）
     * 不可以使用多个主键
     */

    @PrimaryKey private String num;
    //该字段不能为空
    /**
     * 在某些情况下，有些字段是不能为 null 的，使用 @Required 属性可以强行要求其属性不能为 null
     * 只能用于 Boolean、Byte、Short、Integer、Long、Float、Double、String、byte[] 和 Date
     * 在其他类型属性上使用 @Required 会导致编译失败
     */

    @Required private String name;
    /**
     * 添加搜索索引，为字段添加 @Index 标签，插入速度变慢，查询速度变快，支持索引 String、byte、short、int、long、boolean 和 Date字段
     */
//    @Index
    private int age;
    private RealmList<Student> Students;

//    public RealmList<Student> getStudents() {
//        return Students;
//    }
//
//    public void setStudents(RealmList<Student> chatMsgs) {
//        this.Students = chatMsgs;
//    }
public String getNum() {
        return num;
    }

    void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    void setAge(int age) {
        this.age = age;
    }
}
