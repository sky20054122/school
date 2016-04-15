
package com.brxy.school.actuator.health;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.brxy.school.model.User;
import com.brxy.school.service.UserService;

@Component
public class UserHealthIndicator implements HealthIndicator {

    @Autowired
    private UserService userService;

    @Override
    public Health health() {
        Collection<User> users = userService.findAll();

        if (users == null || users.size() == 0) {
            return Health.down().withDetail("count", 0).build();
        }

        return Health.up().withDetail("count", users.size()).build();
    }

}
