package gchfeng.rxjavaretrofitdemo.entity;

import java.util.List;

/**
 * Created by gchfeng on 2017/1/5.
 */

public class MovieEntity {


    /**
     * count : 10
     * start : 0
     * total : 250
     * subjects :
     * title : 豆瓣电影Top250
     */

    private int count;
    private int start;
    private int total;
    private String title;
    private List<SubjectsEntity> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubjectsEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsEntity> subjects) {
        this.subjects = subjects;
    }
}
