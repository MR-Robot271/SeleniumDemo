package com.kb;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;

public class FirefoxTestCrawler {
    public static void main(String[] args) {
        // 如果出现找不到Firefox和driver的异常 可以使用以下代码
        System.setProperty("webdriver.gecko.driver", "c:\\driver\\geckodriver.exe");
        System.setProperty("webdriver.firefox.bin", "D:\\Mozilla Firefox\\firefox.exe");

        // 创建FirefoxOptions以配置Firefox WebDriver
        FirefoxOptions options = new FirefoxOptions();

        // 京东工业的url
        String baseUrl = "https://i-search.jd.com/Search?keyword=";

        // 搜索的产品名
        String keyword = "螺丝刀";

        // 京东搜索的结尾
        String endUrl = "&enc=utf-8&wq=";

        // 创建driver
        WebDriver webDriver = new FirefoxDriver(options);

        // 创建js执行器
        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        // 使window.navigator.webdriver失效
        js.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");

        // 最大化窗口
        webDriver.manage().window().maximize();

        webDriver.get("https://i-search.jd.com");
        WebElement searchBox = webDriver.findElement(By.name("keyword"));



        try {
            searchBox.sendKeys("六角螺丝");
            Thread.sleep(5000);
            searchBox.sendKeys(Keys.RETURN);
            Thread.sleep(10000);
            System.out.println(webDriver.getCurrentUrl());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // 打开网页
//        webDriver.get(baseUrl+keyword+endUrl+keyword);
//        System.out.println(webDriver.getTitle());

        // 创建js执行器
//        JavascriptExecutor js = (JavascriptExecutor) webDriver;
//
//        // 定义滚动目标的高度
//        long scrollHeight = (long) js.executeScript("return Math.max(document.body.scrollHeight,document.body.offsetHeight,document.documentElement.clientHeight,document.documentElement.scrollHeight,document.documentElement.offsetHeight)");
//        // 定义滚动步长
//        int scrollStep = 100;
//        // 定义滚动延迟时间
//        int scrollDelay = 1000;
//
//        WebElement imgUrl = webDriver.findElement(By.xpath("//*[@id=\"J_goodsList\"]/ul/li"));
//        System.out.println(imgUrl.getText());
//
//        // 缓慢滚动页面
//        for (long i = 0; i < scrollHeight; i += scrollStep) {
//            js.executeScript("window.scrollTo(0," + i + ")");
//            try {
//                Thread.sleep(scrollDelay);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        long scrollHeightTmp = scrollHeight;
//        scrollHeight = (long) js.executeScript("return Math.max(document.body.scrollHeight,document.body.offsetHeight,document.documentElement.clientHeight,document.documentElement.scrollHeight,document.documentElement.offsetHeight)");
//        for (long i = scrollHeightTmp; i >= scrollHeight; i += scrollStep) {
//            js.executeScript("window.scrollTo(0," + i + ")");
//            try {
//                Thread.sleep(scrollDelay);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // 往页面头部缓慢滚动
//        for (long i = scrollHeight; i >0; i -= scrollStep) {
//            js.executeScript("window.scrollTo("+i+"," + (i-scrollStep) + ")");
//            try {
//                Thread.sleep(scrollDelay);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // 等待一会
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        String source = webDriver.getPageSource();
//        System.out.println(source);
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        List<WebElement> lis = webDriver.findElements(By.xpath("//*[@id=\"J_goodsList\"]/ul/li"));
//        System.out.println(lis.size());
//        for (WebElement li : lis) {
//            System.out.println(li.getText());
//        }
//
//        // 关闭浏览器
//        webDriver.quit();
    }
}
