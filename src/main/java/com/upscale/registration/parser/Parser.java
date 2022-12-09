package com.upscale.registration.parser;

import com.upscale.registration.model.Attendee;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class Parser {

    private String linkedItnName;
    private String linkedInPassword;
    private String basePage;
    private WebDriver driver;


    public Parser(String linkedInName, String linkedInPassword) {
        this.linkedItnName = linkedInName;
        this.linkedInPassword = linkedInPassword;

        this.basePage = "https://www.linkedin.com";

        System.setProperty("webdriver.chrome.driver", "C:\\projects\\chromedriver_win32\\chromedriver.exe");
        this.driver = new ChromeDriver();
    }

    public List<Attendee> runParsing(String eventPage){
        login(basePage);
        List<Attendee> attendeesList = getEventAttendees(eventPage);
        getAttendeesInfo(attendeesList);
        driver.quit();
        return attendeesList;
    }

    private void login(String loginPage) {

        driver.get(loginPage);
        sleep(3);

        WebElement userName = driver.findElement(By.xpath("//*[@id=\"session_key\"]"));
        WebElement password = driver.findElement(By.xpath("//*[@id=\"session_password\"]"));

        userName.sendKeys(linkedItnName);
        password.sendKeys(linkedInPassword);

        WebElement login_button = driver.findElement(By.xpath("//button[@class=\"sign-in-form__submit-button\"]"));

        sleep(3);
        login_button.click();
    }

    private List<Attendee> getEventAttendees(String event_page){
        driver.get(event_page);
        sleep(3);

        WebElement userListButton = driver.findElement(By.xpath("//*[@id=\"events-top-card\"]/div[1]/div[2]/div/div[1]/div[1]/div[4]/div"));
        userListButton.click();
        sleep(3);

        List<WebElement> attendees = driver.findElements(By.className("reusable-search__result-container"));
        List<Attendee> new_users = new ArrayList<>();

        for (WebElement element: attendees) {
            String name = element.getText().split("\n")[0];
            String link = element.findElement(By.className("app-aware-link")).getAttribute("href");
            Attendee new_user = new Attendee(name, link);
            new_users.add(new_user);
        }
        return new_users;
    }

    private void getAttendeesInfo(List<Attendee> attendees){

    }

    private void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
