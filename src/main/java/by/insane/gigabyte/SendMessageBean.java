/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.gigabyte;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Андрій
 */
@ManagedBean
@ViewScoped
public class SendMessageBean implements Serializable{

    private Sender sender;
    private String message;
    private String email;
    private String name;
    private String topic;

    public void sendMessage() {
        sender = new Sender();
        sender.send(topic, message, "soad.awd@mail.ru");
        message = "";
        email = "";
        name = "";
        topic = "";
    }

    public String getTopic() {
        return topic;
    }

    public Sender getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

}
