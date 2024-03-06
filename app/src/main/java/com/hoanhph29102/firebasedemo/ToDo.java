package com.hoanhph29102.firebasedemo;

import java.util.HashMap;
import java.util.Objects;

public class ToDo {
    private String id, title, content;

    public ToDo(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public ToDo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public HashMap<String, Object> convertToHashMap(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id",id);
        hashMap.put("title", title);
        hashMap.put("content",content);

        return hashMap;
    }
}
