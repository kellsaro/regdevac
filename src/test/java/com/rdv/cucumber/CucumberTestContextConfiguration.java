package com.rdv.cucumber;

import com.rdv.RdvApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = RdvApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
