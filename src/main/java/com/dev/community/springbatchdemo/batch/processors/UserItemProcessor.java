package com.dev.community.springbatchdemo.batch.processors;

import com.dev.community.springbatchdemo.dal.jpa.entities.User;
import org.springframework.batch.item.ItemProcessor;

public class UserItemProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User item) {
        item.setLastName(item.getLastName().toUpperCase()); // transformer le nom en majuscules
        return item;
    }
}