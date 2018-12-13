package com.catherine.forrealm.about_utils;

import android.util.Log;

import com.catherine.forrealm.Student;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {
    static final String DB_NAME = "myStudentRealm.realm";
    private Realm myStudentRealm;
    public RealmHelper(){
        myStudentRealm = Realm.getDefaultInstance();
    }
    public void close(){
        myStudentRealm.close();
    }


    //  add
    public void addStudent(Student student){
        myStudentRealm.beginTransaction();
        //进来几次就添加几个
//        myStudentRealm.copyToRealm(student);
        //有主键的情况下使用，添加更新
        /**
         * 判断主键（num）是否为空
         */
        if (student.getNum().length()!=0 && !student.getName().equals("") || student.getName() == null)
            myStudentRealm.copyToRealmOrUpdate(student);
        Log.e("stu","-----num-----"+student.getNum()+"----name---"+student.getName()+"---age----"+student.getAge());
        myStudentRealm.commitTransaction();
    }

    //  del
    public void delStudent() {
        final RealmResults<Student> studentsList = myStudentRealm.where(Student.class).findAll();
        if (studentsList != null){
            myStudentRealm.beginTransaction();
            studentsList.deleteLastFromRealm();
            myStudentRealm.commitTransaction();
        }
    }

    //  clear
    public void clearStudent(){
        final RealmResults<Student> studentsList = myStudentRealm.where(Student.class).findAll();
        if (studentsList != null) {
            myStudentRealm.beginTransaction();
            studentsList.deleteAllFromRealm();
            myStudentRealm.commitTransaction();
        }

    }

    //  find all
    public List<Student> findAllStudent(){
        RealmResults<Student> studentsList = myStudentRealm.where(Student.class).findAll();
        if(studentsList != null) {
            Log.e("stu", "----findAllStudent------" + studentsList.size());
            studentsList = studentsList.sort("num");
            return myStudentRealm.copyFromRealm(studentsList);
        }
        else
            return null;
    }

    //  find student by primary key(StuNum)
    public List<Student> findByNumStudent(String priNum){
        RealmResults<Student> studentsList = myStudentRealm.where(Student.class).equalTo("num",priNum).findAll();
        if(studentsList != null) {
            return studentsList;
        }
        else
            return null;
    }

    //  find student by StuName
    public List<Student> findByNameStudent(String priName){
        RealmResults<Student> studentsList = myStudentRealm.where(Student.class).equalTo("name",priName).findAll();
        if(studentsList != null) {
            return studentsList;
        }
        else
            return null;
    }

    //  find student by StuAge
    public List<Student> findByAgeStudent(String priAge){
        RealmResults<Student> studentsList = myStudentRealm.where(Student.class).equalTo("age",priAge).findAll();
        if(studentsList != null) {
            return studentsList;
        }
        else
            return null;
    }

    //  delete student by primary key(StuNum)
    public void delByStudent(String priNum) {
        RealmResults<Student> studentsList = myStudentRealm.where(Student.class).equalTo("num", priNum).findAll();
        myStudentRealm.beginTransaction();
        for (int i = 0; i < studentsList.size(); i++) {
            studentsList.deleteLastFromRealm();
            Log.e("stu", "-------delet_by-------" + studentsList);
        }
        myStudentRealm.commitTransaction();
    }



}
