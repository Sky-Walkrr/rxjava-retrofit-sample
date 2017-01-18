package gchfeng.rxjavaretrofitdemo.entity;

import android.databinding.BaseObservable;

/**
 * Created by walker on 2017/1/17.
 */
public class User extends BaseObservable{
    private int age;
    private int sex;
    private String name;
    private String lastName;

    public User(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
