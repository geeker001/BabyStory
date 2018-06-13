package com.lofter.story.entity;

/**
 * PACKAGE_NAME：com.lofter.story.entity
 * DATE：2018/1/5 17:09
 * USER: xiantao.jiang
 * DESCRIBE:
 */

public class StoryVO {

    private int id;
    private String title;
    private String content;
    private String count;

    public StoryVO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
