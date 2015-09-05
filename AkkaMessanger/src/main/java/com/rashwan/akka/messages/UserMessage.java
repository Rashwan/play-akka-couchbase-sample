package com.rashwan.akka.messages;

import java.io.Serializable;

/**
 * Created by rashwan on 9/4/15.
 */

public class UserMessage implements Serializable {
    private String name;
    private String job;
    private String email;

    public UserMessage() {
    }

    public UserMessage(String email, String job, String name) {
        this.email = email;
        this.job = job;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public void setMessage(String email, String job, String name) {
        this.email = email;
        this.job = job;
        this.name = name;
    }
}
