package com.devdmin.cores.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertTrue;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
public class UserServiceTest {

    @Test
    public void test(){
        LocalDate l1 = LocalDate.of(2015,12,2);
        System.out.println("XDD: " + ChronoUnit.DAYS.between(LocalDate.now(),l1));
    }
}
