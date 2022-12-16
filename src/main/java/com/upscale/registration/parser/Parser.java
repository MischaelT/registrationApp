package com.upscale.registration.parser;

import com.upscale.registration.model.Attendee;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class Parser {

    private static final String LOGIN_USERNAME_XPATH = "//*[@id=\"session_key\"]";
    private static final String LOGIN_PASSWORD_XPATH = "//*[@id=\"session_password\"]";
    private static final String LOGIN_BUTTON_XPATH = "//button[@class=\"sign-in-form__submit-button\"]";

    private static final String EVENT_USER_LIST_XPATH = "//*[@id=\"events-top-card\"]/div[1]/div[2]/div/div[1]/div[1]/div[4]/div";
    private static final String EVENT_ATTENDEES_CLASSNAME = "reusable-search__result-container";
    private static final String EVENT_ATTENDEE_CLASSNAME = "app-aware-link";

 // IDs for the next two elements generates automatically, so it is not possible to use related XPATH
    private static final String ATTENDEE_POSITION_COMPANY_XPATH = "//*[@id=\"main\"]/section/div[2]/div[2]/div[1]/div[2]";
    private static final String ATTENDEE_LOCATION_XPATH = "//*[@id=\"main\"]/section/div[2]/div[2]/div[2]/span[1]";

    private static final String ATTENDEE_COMPANY_XPATH = "//*[@id=\"main\"]/section/div[2]/div[2]/ul/li[1]";

    private static  final String ATTENDEE_CONTACT_INFO_ID = "top-card-text-details-contact-info";
    private static final String ATTENDEE_NAME_ID = "pv-contact-info";

    private static final String ATTENDEE_EMAIL_XPATH = "//*[@class=\"pv-contact-info__contact-type ci-email\"]/div";

    private static final int TIME_TO_SLEEP = 3;

    private String linkedItnName;
    private String linkedInPassword;
    private String basePage;
    private WebDriver driver;


    public Parser(String linkedInName, String linkedInPassword) {
        this.linkedItnName = linkedInName;
        this.linkedInPassword = linkedInPassword;

        this.basePage = "https://www.linkedin.com";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        System.setProperty("webdriver.chrome.driver", "C:\\projects\\chromedriver_win32\\chromedriver.exe");
        this.driver = new ChromeDriver();
    }

    public List<Attendee> runEventParsing(String eventPage){
        login(basePage);
        List<String> attendeesLinks = getEventAttendeesLinks(eventPage);

        List<Attendee> attendees = new ArrayList<>();
        for (String attendeeLink: attendeesLinks) {
            Attendee attendee = new Attendee();

            httpPresent(attendeeLink, attendee);

            getAttendeeInfo(attendee);

            attendees.add(attendee);
        }

        driver.quit();
        return attendees;
    }

    public Attendee getAttendeeInformation(Attendee attendee){
        login(basePage);
        sleep(TIME_TO_SLEEP);
        Attendee updatedAttendee = getAttendeeInfo(attendee);
        driver.quit();
        return updatedAttendee;
    }

    private void login(String loginPage) {

        driver.get(loginPage);
        sleep(TIME_TO_SLEEP);

        WebElement userName = driver.findElement(By.xpath(LOGIN_USERNAME_XPATH));
        sleep(TIME_TO_SLEEP);
        WebElement password = driver.findElement(By.xpath(LOGIN_PASSWORD_XPATH));

        userName.sendKeys(linkedItnName);
        password.sendKeys(linkedInPassword);

        WebElement login_button = driver.findElement(By.xpath(LOGIN_BUTTON_XPATH));

        sleep(TIME_TO_SLEEP);
        login_button.click();
    }

    private List<String> getEventAttendeesLinks(String event_page){
        driver.get(event_page);
        sleep(3);

        WebElement userListButton = driver.findElement(By.xpath(EVENT_USER_LIST_XPATH));
        userListButton.click();
        sleep(3);

        List<WebElement> attendees = driver.findElements(By.className(EVENT_ATTENDEES_CLASSNAME));
        List<String> newAttendees = new ArrayList<>();

        for (WebElement element: attendees) {
            String link = element.findElement(By.className(EVENT_ATTENDEE_CLASSNAME)).getAttribute("href");
            newAttendees.add(link);
        }
        return newAttendees;
    }

    private Attendee getAttendeeInfo(Attendee attendee){

        getGeneralInfo(attendee);
        getContactInfo(attendee);

        return attendee;
    }

    private Attendee getGeneralInfo(Attendee attendee){

        driver.get(attendee.getLinkedInLink());
        sleep(TIME_TO_SLEEP);
        String companyAndPosition = driver.findElement(By.xpath(ATTENDEE_POSITION_COMPANY_XPATH)).getText();
        System.out.println(companyAndPosition);
        sleep(TIME_TO_SLEEP);
        String company = driver.findElement(By.xpath(ATTENDEE_COMPANY_XPATH)).getText();
        System.out.println(company);
        sleep(TIME_TO_SLEEP);
        String currentLocation = driver.findElement(By.xpath(ATTENDEE_LOCATION_XPATH)).getText();
        System.out.println(currentLocation);
        sleep(TIME_TO_SLEEP);

        attendee.setCurrentPosition(companyAndPosition);
        attendee.setCurrentCompany(company);
        attendee.setLocation(currentLocation);

        return attendee;
    }

    private Attendee getContactInfo(Attendee attendee){
        WebElement contactInfoLink = driver.findElement(By.id(ATTENDEE_CONTACT_INFO_ID));
        sleep(TIME_TO_SLEEP);
        contactInfoLink.click();
        sleep(TIME_TO_SLEEP);
        String name = driver.findElement(By.id(ATTENDEE_NAME_ID)).getText();
        sleep(TIME_TO_SLEEP);
        String emailAddress = "";
        try {
            emailAddress = driver.findElement(By.xpath(ATTENDEE_EMAIL_XPATH)).getText();
        } catch (Exception exc){
            System.out.println(exc);
        }
        attendee.setEmailAddress(emailAddress);
        attendee.setName(name);

        return attendee;
    }


    private void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Attendee httpPresent(String link, Attendee attendee){
        String[] splittedLink = link.split("//");
        boolean httpsPresent = splittedLink.length == 2;

        if (httpsPresent){
            attendee.setLinkedInLink(splittedLink[1]);
        }
        return attendee;
    }
}
