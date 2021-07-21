import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.testng.Assert
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import java.io.File
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

class TestReCaptchaDemo {
    private var driver: WebDriver? = null
    private var baseUrl: String? = null
    private var acceptNextAlert = true
    private val verificationErrors = StringBuffer()

    @BeforeClass(alwaysRun = true)
    @Throws(Exception::class)
    fun setUp() {
        WebDriverManager.chromedriver().setup()
        val options = ChromeOptions()
        val userDir = System.getProperty("user.dir")
        //Extension Buster for reCaptcha
        val extension = "\\src\\main\\resources\\extension_1_2_0_0.crx"
        val pathExtension = Paths.get(userDir, extension).toAbsolutePath().toString()
        options.addExtensions(File(pathExtension))
        driver = ChromeDriver(options)
        (driver as ChromeDriver).manage().window().maximize()
        baseUrl = "https://www.google.com/recaptcha/api2/demo"
        (driver as ChromeDriver).manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
    }

    @Test
    @Throws(Exception::class)
    fun testReCaptcha() {

        driver!!.get(baseUrl)
        //This block for recognize from IBM Watson
        val iframe = driver!!.findElement(By.xpath("//*[@id=\"recaptcha-demo\"]/div/div/iframe"))
        driver!!.switchTo().frame(iframe)
        driver!!.findElement(By.id("recaptcha-anchor-label")).click()
        driver!!.switchTo().defaultContent()
        Thread.sleep(3000)
        //This block need comment if you want to use extension Buster
        val iframe2 = driver!!.findElement(By.xpath("/html/body/div[2]/div[4]/iframe"))
        driver!!.switchTo().frame(iframe2)
        driver!!.findElement(By.xpath("//*[@id=\"recaptcha-audio-button\"]")).click()
        driver!!.switchTo().defaultContent()
        Thread.sleep(3000)
        val iframe3 = driver!!.findElement(By.xpath("/html/body/div[2]/div[4]/iframe"))
        driver!!.switchTo().frame(iframe3)
        Thread.sleep(3000)
        val a = driver!!.findElement(By.xpath("/html/body/div/div/div[7]/a")).getAttribute("href")
        FileDownloader.downloadFile(a)
        val userDir = System.getProperty("user.dir")
        val recognizeString = RecognizeIBM.recognize(File("${userDir}\\downloaded.mp3"))
        Thread.sleep(5000)
        driver!!.findElement(By.xpath("//*[@id=\"audio-response\"]")).sendKeys(recognizeString)
        Thread.sleep(3000)
        driver!!.findElement(By.xpath("//*[@id=\"recaptcha-verify-button\"]")).click()

        //This block for extension Buster
//        val iframe2 = driver!!.findElement(By.xpath("/html/body/div[2]/div[4]/iframe"))
//        driver!!.switchTo().frame(iframe2)
//        driver!!.findElement(By.xpath("//*[@id=\"rc-imageselect\"]/div[3]/div[2]/div[1]/div[1]/div[4]")).click()

        driver!!.switchTo().defaultContent()
        Thread.sleep(3000)
        driver!!.findElement(By.xpath("//*[@id=\"recaptcha-demo-submit\"]")).click()
    }

    @AfterClass(alwaysRun = true)
    @Throws(Exception::class)
    fun tearDown() {
        driver!!.quit()
        val verificationErrorString = verificationErrors.toString()
        if ("" != verificationErrorString) {
            Assert.fail(verificationErrorString)
        }
    }

    private fun isElementPresent(by: By): Boolean {
        return try {
            driver!!.findElement(by)
            true
        } catch (e: NoSuchElementException) {
            false
        }
    }

    private val isAlertPresent: Boolean
        private get() = try {
            driver!!.switchTo().alert()
            true
        } catch (e: NoAlertPresentException) {
            false
        }

    private fun closeAlertAndGetItsText(): String {
        return try {
            val alert = driver!!.switchTo().alert()
            val alertText = alert.text
            if (acceptNextAlert) {
                alert.accept()
            } else {
                alert.dismiss()
            }
            alertText
        } finally {
            acceptNextAlert = true
        }
    }
}