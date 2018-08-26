package com.sampletest.anil.testapplication.model;

import android.net.Uri;

/**
 * Created by H211060 on 8/21/2018.
 */

public class FlickerImage {
    private String id;
    private String url;
    private String title;

    public FlickerImage(String id, String url, String title) {
        this.id = id;
        this.url = url;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
