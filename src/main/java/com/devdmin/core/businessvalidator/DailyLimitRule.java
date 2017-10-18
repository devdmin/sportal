package com.devdmin.core.businessvalidator;

import com.devdmin.core.model.SportField;
import com.devdmin.core.model.User;
import com.devdmin.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DailyLimitRule implements BusinessRule<SportField, User> {
    @Autowired
    private UserService userService;

    private final long PERMISSIBLE_DAYS_BETWEEN_LAST_ADDING = 0;
    private final long SPORTFIELDS_PER_DAY = 1;

    @Override
    public boolean validate(SportField sportField, User user) {
        User foundUser = userService.find(user.getId());
        Optional<List<SportField>> sportFields = Optional.ofNullable(foundUser.getOwnSportFields());

        if(sportFields.isPresent()) {
            Long todaysSportFields = sportFields.get()
                    .stream()
                    .filter(t -> ChronoUnit.DAYS.between(t.getAddingDate(), LocalDate.now()) == PERMISSIBLE_DAYS_BETWEEN_LAST_ADDING)
                    .count();
            if(todaysSportFields >= SPORTFIELDS_PER_DAY) {
                return false;
            }
        }else {
            return true;
        }
        return true;
    }
}
