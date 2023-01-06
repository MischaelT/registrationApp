package com.upscale.registration.parser;

import com.upscale.registration.model.Attendee;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


public class Parser {

    private static final String LOGIN_USERNAME_XPATH = "//*[@id=\"session_key\"]";
    private static final String LOGIN_PASSWORD_XPATH = "//*[@id=\"session_password\"]";
    private static final String LOGIN_BUTTON_XPATH = "//button[@class=\"sign-in-form__submit-button\"]";

    private static final String EVENT_ATTENDEE_LIST_XPATH = "//*[@id=\"events-top-card\"]/div[1]/div[2]/div/div[1]/div[1]/div[5]/div";
    private static final String EVENT_ATTENDEES_CLASSNAME = "reusable-search__result-container";
    private static final String EVENT_ATTENDEE_CLASSNAME = "app-aware-link";

    private static final String ATTENDEE_POSITION_COMPANY_XPATH = "//*[@id=\"main\"]/section/div[2]/div[2]/div[1]/div[2]";
    private static final String ATTENDEE_LOCATION_XPATH = "//*[@id=\"main\"]/section/div[2]/div[2]/div[2]/span[1]";

    private static final String ATTENDEE_COMPANY_XPATH = "//*[@id=\"main\"]/section/div[2]/div[2]/ul/li[1]";

    private static  final String ATTENDEE_CONTACT_INFO_ID = "top-card-text-details-contact-info";
    private static final String ATTENDEE_NAME_ID = "pv-contact-info";

    private static final String ATTENDEE_EMAIL_XPATH = "//*[@class=\"pv-contact-info__contact-type ci-email\"]/div";

    private static final String ATTENDEE_EXPERIENCE_XPATH = "//*[@id=\"experience\"]";

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

        try {
            login(basePage);
        }catch (Exception exception){
            System.out.println(exception);
            driver.quit();
        }
        List<String> attendeesLinks = collectEventAttendeesLinks(eventPage);
        List<Attendee> attendees = new ArrayList<>();

        for (String attendeeLink: attendeesLinks) {
            Attendee attendee = new Attendee();

            if (httpPresent(attendeeLink)){
                attendee.setLinkedInLink(attendeeLink);
            } else{
                attendee.setLinkedInLink("https//:" + attendeeLink);
            }
            getAttendeeInfo(attendee);
            attendees.add(attendee);
        }
        driver.quit();
        return attendees;
    }

    public Attendee getAttendeeInformation(Attendee attendee){
        try {
            login(basePage);
        }catch (Exception exception){
            System.out.println(exception);
            driver.quit();
        }
        sleep();
        Attendee updatedAttendee = getAttendeeInfo(attendee);
        driver.quit();
        return updatedAttendee;
    }

    private void login(String loginPage) {
        try {
            driver.get(loginPage);
        }catch (Exception exception){
            System.out.println(exception);
            driver.quit();
        }
        sleep();
        WebElement userName = driver.findElement(By.xpath(LOGIN_USERNAME_XPATH));
        sleep();
        WebElement password = driver.findElement(By.xpath(LOGIN_PASSWORD_XPATH));

        userName.sendKeys(linkedItnName);
        password.sendKeys(linkedInPassword);

        WebElement login_button = driver.findElement(By.xpath(LOGIN_BUTTON_XPATH));
        sleep();
        login_button.click();
    }

    private List<String> collectEventAttendeesLinks(String event_page){
        try{
            driver.get(event_page);
        }catch (Exception exception){
            System.out.println(exception);
            driver.quit();
        }
        sleep();
        WebElement userListButton = driver.findElement(By.xpath(EVENT_ATTENDEE_LIST_XPATH));
        sleep();
        userListButton.click();
        sleep();

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

        //Sometimes in the links that came to this method "https//:" can be upsant,
        // that why we should check if it is present

        if (httpPresent(attendee.getLinkedInLink())){
            driver.get(attendee.getLinkedInLink());
        } else{
            driver.get("https//:" + attendee.getLinkedInLink());
        }

        sleep();
        String companyAndPosition = driver.findElement(By.xpath(ATTENDEE_POSITION_COMPANY_XPATH)).getText();
        sleep();
        String company = driver.findElement(By.xpath(ATTENDEE_COMPANY_XPATH)).getText();
        sleep();
        String currentLocation = driver.findElement(By.xpath(ATTENDEE_LOCATION_XPATH)).getText();
        sleep();
        String currentExperience = getExperience(driver.findElement(By.xpath(ATTENDEE_EXPERIENCE_XPATH)));
        sleep();

        attendee.setCurrentPosition(companyAndPosition);
        attendee.setCurrentCompany(company);
        attendee.setLocation(currentLocation);
        attendee.setExperience(currentExperience);
        return attendee;
    }

    private Attendee getContactInfo(Attendee attendee){
        WebElement contactInfoLink = driver.findElement(By.id(ATTENDEE_CONTACT_INFO_ID));
        sleep();
        contactInfoLink.click();
        sleep();
        String name = driver.findElement(By.id(ATTENDEE_NAME_ID)).getText();
        sleep();
        String emailAddress = "";
        try {
            emailAddress = driver.findElement(By.xpath(ATTENDEE_EMAIL_XPATH)).getText();
        } catch (Exception exc){
            System.out.println(exc);
            driver.quit();
        }
        attendee.setEmailAddress(emailAddress);
        attendee.setName(name);
        return attendee;
    }

    private String getExperience(WebElement experience){

        String RELATIVE_EXP_XPATH = "following-sibling::div[2]";

        WebElement experienceBlock = experience.findElement(By.xpath(RELATIVE_EXP_XPATH));
        WebElement experiencesList = experienceBlock.findElement(By.xpath("ul"));

        boolean hasNextElement = true;
        int i = 1;
        String experiencesString = "";
        while (hasNextElement){
            try{
                WebElement experiencesListElement = experiencesList.findElement(
                                                    By.xpath("li["+i+"]/div/div[2]"));
                String experienceString = processExperienceInfo(experiencesListElement);
                experiencesString+=experienceString+"__";
                i++;
            } catch (Exception exc){
                hasNextElement=false;
            }
        }
        return experiencesString;
    }

    private String processExperienceInfo(WebElement experiencesListElement){
        WebElement mainInfo = experiencesListElement.findElement(By.xpath("div[1]/div"));
        String[] splittedMainInfo = mainInfo.getText().split("\n");
        String mainInfoString = removeDuplicates(splittedMainInfo);

        WebElement skillInfo = experiencesListElement.findElement(By.xpath("div[2]"));
        String[] splittedSkillInfo = skillInfo.getText().split("\n");
        String skillInfoString = removeDuplicates(splittedSkillInfo);

        return mainInfoString+"\n"+skillInfoString;
    }

    private String removeDuplicates(String[] arrayWithDuplicates) {
        LinkedHashSet<String> set = new LinkedHashSet<String>();
        for (int i = 0; i < arrayWithDuplicates.length; i++)
            set.add(arrayWithDuplicates[i]);
        String result = "";
        for (String element:set) {
            result+="\n"+element;
        }
        return result;
    }

    private boolean httpPresent(String link){
        String[] splittedLink = link.split("//");
        return splittedLink.length == 2;
    }

    private void sleep(){
        try {
            TimeUnit.SECONDS.sleep(generateTimeToSleep());
        } catch (InterruptedException e) {
            driver.quit();
            throw new RuntimeException(e);
        }
    }

    private int generateTimeToSleep(){
        return TIME_TO_SLEEP+ThreadLocalRandom.current().nextInt(1, 3);
    }
}
