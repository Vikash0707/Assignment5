//        System.setProperty("webdriver.chrome.driver", "/Users/superaxis/Downloads/chromedriver");

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Assignment {
    WebDriver driver;

    @BeforeMethod
    public void launcher() throws InterruptedException {
    System.setProperty("webdriver.chrome.driver", "/Users/superaxis/Downloads/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://www.booking.com");
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }


    @Test(dataProvider = "cities")
    public void booking( String depart , String going) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-decider-header=\"flights\"]")));
        driver.findElement(By.xpath("//a[@data-decider-header=\"flights\"]")).click();

        WebElement whereFrom = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"css-hm3w49\"]/parent::div")));
        whereFrom.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class=\"css-bwf0ll\"]/parent::label/parent::div")));
        driver.findElement(By.xpath("//input[@class=\"css-bwf0ll\"]")).click();

        WebElement enterWhereFrom = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder=\"Where from?\"]/parent::div[@class=\"css-526oso\"]/input")));
        enterWhereFrom.sendKeys(depart);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"css-1tli02a-autocompleteResults\"]/ul/li/div")));
        enterWhereFrom.sendKeys(Keys.ARROW_DOWN);
        driver.findElement(By.xpath("//div[@class=\"css-1tli02a-autocompleteResults\"]")).click();

        WebElement enterWhereTo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"css-526oso\"]/input[@placeholder=\"Where to?\"]")));
        enterWhereTo.sendKeys(going);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"css-1tli02a-autocompleteResults\"]")));
        enterWhereTo.sendKeys(Keys.ARROW_DOWN);
        driver.findElement(By.xpath("//div[@class=\"css-1tli02a-autocompleteResults\"]")).click();

        WebElement departDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder=\"Depart\"]")));
        departDate.click();

        WebElement selectDepartDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@aria-label=\"5 November 2022\"]/parent::td")));
        selectDepartDate.click();

        WebElement selectReturnDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@aria-label=\"6 November 2022\"]/parent::td")));
        selectReturnDate.click();

        WebElement clickSearchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=\"Search\"]")));
        clickSearchButton.click();
        Thread.sleep(5000);

        /*WebElement clickCheapest = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()=\"Cheapest\"]/parent::div")));
        Actions actions = new Actions(driver);
        actions.moveToElement(clickCheapest)
                .click()
                .build();
        actions.perform();*/
        WebElement clickCheapestButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()=\"Cheapest\"]/parent::div/parent::button")));
        Thread.sleep(3000);
        try{
            clickCheapestButton.click();
        } catch (Exception e) {
            Thread.sleep(1000);
            clickCheapestButton = wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//span[text()=\\\"Cheapest\\\"]/parent::div/parent::button"))));

            clickCheapestButton.click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id=\"flightcard-0\"]/div/div/div/div/div[2]/div[2]/div/div/div/div")));
        String cheapestFlight = driver.findElement(By.xpath("//div[@id=\"flightcard-0\"]/div/div/div/div/div[2]/div[2]/div/div/div/div")).getText();
        System.out.println("Cheapest Flight fare is " + cheapestFlight);
        Thread.sleep(5000);

        WebElement clickFastestButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()=\"Fastest\"]/parent::div/parent::button")));
        clickFastestButton.click();
        Thread.sleep(3000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id=\"flightcard-0\"]/div/div/div/div[1]/div/div[1]/div[2]/div/div/div[2]/div[@data-testid=\"flight_card_segment_duration\"]")));
        String FastestFlightFrom = driver.findElement(By.xpath("//div[@id=\"flightcard-0\"]/div/div/div/div[1]/div/div[1]/div[2]/div/div/div[2]/div[@data-testid=\"flight_card_segment_duration\"]")).getText();
        System.out.println("Fastest Flight duration from departure is " +  FastestFlightFrom);
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id=\"flightcard-0\"]/div/div/div/div[1]/div/div[4]/div[2]/div/div/div[2]/div[@data-testid=\"flight_card_segment_duration\"]")));
        String FastestFlightTo = driver.findElement(By.xpath("//div[@id=\"flightcard-0\"]/div/div/div/div[1]/div/div[4]/div[2]/div/div/div[2]/div[@data-testid=\"flight_card_segment_duration\"]")).getText();
        System.out.println("Fastest Flight duration to destination is " +  FastestFlightTo);
        Thread.sleep(5000);

    }

    @DataProvider(name = "cities")
    public Object[][] getDataFromDataprovider() {
        return new Object[][]{
                 {"Mumbai", "Delhi"},
                 {"Chennai", "Bangalore"},
                 {"Mumbai", "Bangalore"},
                 {"Delhi", "Chennai"},
                {"Mumbai", "Chennai"},
                {"Delhi", "Bangalore"},
                {"Hyderabad", "Pune"},
                {"Pune", "Mumbai"},
                {"Chennai", "Pune"},
                {"Delhi", "Hyderabad"}
        };
    }


}