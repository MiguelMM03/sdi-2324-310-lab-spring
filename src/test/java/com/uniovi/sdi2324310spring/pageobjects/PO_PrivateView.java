package com.uniovi.sdi2324310spring.pageobjects;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.uniovi.sdi2324310spring.util.SeleniumUtils;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PO_PrivateView extends PO_NavView {
    static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep)
    {
        //Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
        SeleniumUtils.waitSeconds(driver, 5);
        //Seleccionamos el alumnos userOrder
        new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
        //Rellenemos el campo de descripción
        WebElement description = driver.findElement(By.name("description"));
        description.clear();
        description.sendKeys(descriptionp);
        WebElement score = driver.findElement(By.name("score"));
        score.click();
        score.clear();
        score.sendKeys(scorep);
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }
    static public void login(WebDriver driver, String dni, String password){
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, dni, password);
    }
    static public void clickElementNumberX(WebDriver driver, String type, String text, int number){
        List<WebElement>  elements = PO_View.checkElementBy(driver, type, text);
        elements.get(number).click();
    }
    static public void checkNumberOfElements(WebDriver driver, String type, String text, int expectedNumber){
        List<WebElement> marksList = SeleniumUtils.waitLoadElementsBy(driver, type, text,
                PO_View.getTimeout());
        Assertions.assertEquals(expectedNumber, marksList.size());
    }
    static public void clickPageButton(WebDriver driver, int number){
        PO_PrivateView.clickElementNumberX(driver,"free", "//a[contains(@class, 'page-link')]",number);
    }
    static public void checkTextInPage(WebDriver driver, String text){
        List<WebElement>elements = PO_View.checkElementBy(driver, "text", text);
        Assertions.assertEquals(text, elements.get(0).getText());
    }
    static public void logout(WebDriver driver){
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }
}