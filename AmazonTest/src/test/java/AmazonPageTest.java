import com.sun.nio.sctp.SctpSocketOption;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AmazonPageTest {

    public WebDriver driver;
    protected static String url = "https://www.amazon.com/";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Emree\\IdeaProjects\\AmazonTest\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void AmazonWebPageTest()
    {
        driver.get(url);
        //1. Adım - Ana sayfanın açıldığının onaylanması
        Assert.assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");
        //Assert.assertEquals(driver.getCurrentUrl(), "https://www.amazon.com/");

        //2. Adım - Login Olma (Login olurken bazen güvenlik için OTP-One Time Password çıkmaktadır.Sebebini anlamadım.
        // Tekrar çıkarsa o adımı bir şekilde geçmeniz gerekmektedir)
        WebElement clickLoginPage = driver.findElement(By.cssSelector("#nav-link-accountList"));
        clickLoginPage.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Kullanıcı adı
        WebElement username = driver.findElement(By.id("ap_email"));
        username.sendKeys("emreaycicek1@gmail.com");
        WebElement contbutton = driver.findElement(By.id("continue"));
        contbutton.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Şifre
        WebElement password = driver.findElement(By.id("ap_password"));
        password.sendKeys("2132145");
        WebElement signInButton = driver.findElement(By.id("signInSubmit"));
        signInButton.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //3. Adım - Arama kutusuna samsung yazılıp aranacak
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("samsung");
        WebElement searchButton = driver.findElement(By.cssSelector("#nav-search > form > div.nav-right > div > input"));
        searchButton.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //4. Adım - samsung için sonuçların listelendiğinin onayı
        Assert.assertTrue(driver.getTitle().contains("samsung"));
        //Assert.assertEquals(driver.getTitle(),"Amazon.com : samsung");

        //5. Adım - Arama sonuçlarından 2. sayfaya tıklanacak
        WebElement secondPage = driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/div/span[8]/div/div/span/div/div/ul/li[3]/a"));
        secondPage.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Açılan sayfanın 2. sayfa olduğunun onaylanması
        String selectedPage = driver.findElement(By.className("a-selected")).getText();
        Assert.assertEquals(selectedPage,"2");

        //6. Adım - Üstten 3. ürüne tıklanacak ve detaylarına girilecek
        WebElement thirdProduct = driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/div/span[4]/div[1]/div[3]/div/span/div/div/div[2]/div[2]/div/div[1]/div/div/div[1]/h2/a/span"));
        thirdProduct.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Add to List düğmesine tıklanacak
        WebElement addToList = driver.findElement(By.xpath("//*[@id=\"add-to-wishlist-button-submit\"]"));
        addToList.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //7. Adım - Açılan bilgi ekranından View your list düğmesine tıklanacak ve wish list sayfası görüntülenecek.
        WebElement viewYourList = driver.findElement(By.xpath("//*[@id=\"WLHUC_viewlist\"]/span/span"));
        //Sekizinci madde için ürünün ismi alınıyor.
        String thirdProductName = driver.findElement(By.xpath("//*[@id=\"WLHUC_info\"]/div[1]/ul/li[2]/table/tbody/tr/td/a")).getText();
        viewYourList.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //8. Adım - Açılan sayfada bir önceki maddede listeye eklenmiş ürünün bulunduğu onaylanacak.
        //Bütün ürünler for döngüsüne sokularak eklenen ürün aranmaktadır.
        List<WebElement> options = driver.findElements(By.id("g-items"));
        for (WebElement option : options){
            if (option.getText().contains(thirdProductName))
            {
                Assert.assertTrue(true);
            }
            else
            {
                Assert.assertTrue(false);
            }
        }

        //9. Adım - Listeye eklenmiş ürünün yanında bulunan delete butonuna basılacak.
        WebElement deleteItem = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[1]/div/div/div/div[2]/div[7]/div[3]/div/ul/li[1]/span/div/div/div/div[2]/div[3]/div/div[2]/div[1]/div/div/span/a"));
        deleteItem.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //10. Adım - Ürünün listede olmadığı onaylanacak
        driver.navigate().refresh();
        List<WebElement> options2 = driver.findElements(By.id("g-items"));
        for (WebElement option : options2){
            if (!option.getText().contains(thirdProductName))
            {
                Assert.assertTrue(true);
            }
            else
            {
                Assert.assertTrue(false);
            }
        }


    }


    @After
    public void tearDown() {
        driver.quit();
    }

}
