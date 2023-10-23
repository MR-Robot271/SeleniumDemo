package com.kb;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumCrawler {
    public static void main(String[] args) {
        // 如果出现找不到chromedriver的异常 可以使用以下代码
        // System.setProperty("webdriver.chrome.driver", "c:\\driver\\chromedriver.exe");

        // 京东工业的url
        String baseUrl = "https://i-search.jd.com/Search?keyword=";

        // 搜索的产品名
        String keyword="发电薄膜";

        // 京东搜索的结尾
        String endUrl = "&enc=utf-8&wq=";

        ChromeOptions option=new ChromeOptions();
        option.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        option.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36");

        // 创建driver
        WebDriver webDriver = new ChromeDriver(option);




//        // 最大化窗口
//        webDriver.manage().window().maximize();
//
//        // 打开网页
//        webDriver.get("https://i-search.jd.com/Search?keyword=%E8%9E%BA%E4%B8%9D%E5%88%80&enc=utf-8");
//        System.out.println(webDriver.getTitle());
//
        // 创建js执行器
        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //webDriver.get(baseUrl+keyword+endUrl+keyword);

        WebElement searchBox = webDriver.findElement(By.id("key"));

        try {
//            System.out.println(searchBox.getText());
//            int keyLength = searchBox.getText().toCharArray().length;
//            System.out.println(keyLength);
            int randomNum = 20+(int)(Math.random()*10);
            System.out.println(randomNum);
            for (int i = 0; i < randomNum; i++) {
                // 随机等待一段时间
                Thread.sleep((int)(Math.random()*300));
                searchBox.sendKeys(Keys.BACK_SPACE);
            }
            searchBox.sendKeys("绿联");
            // 随机等待一段时间
            Thread.sleep((int)(Math.random()*1000));

            searchBox.sendKeys("六类线");

            // 随机等待一段时间
            Thread.sleep((int)(Math.random()*10000));
            searchBox.sendKeys(Keys.RETURN);

            // 随机等待一段时间
            Thread.sleep((int)(Math.random()*10000));

            // 随机等待一段时间
            Thread.sleep((int)(Math.random()*11100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 暂时可行方案
//        try {
//            Thread.sleep(20000);
//            js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
//            Thread.sleep(10000);
//            WebElement imgUrl = webDriver.findElement(By.xpath("//*[@id=\"J_goodsList\"]/ul/li"));
//            System.out.println(imgUrl.getText());
//            String source = webDriver.getPageSource();
//            System.out.println(source);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }



        // 定义滚动目标的高度
        long scrollHeight = (long) js.executeScript("return Math.max(document.body.scrollHeight,document.body.offsetHeight,document.documentElement.clientHeight,document.documentElement.scrollHeight,document.documentElement.offsetHeight)");
        // 定义滚动步长
        int scrollStep = 100;
        // 定义滚动延迟时间
        int scrollDelay = 1000;

        int a=(int)(Math.random()*100);

        WebElement imgUrl = webDriver.findElement(By.xpath("//*[@id=\"J_goodsList\"]/ul/li"));
        System.out.println(imgUrl.getText());

        // 缓慢滚动页面
        for (long i = 0; i < scrollHeight; i += (int)(Math.random()*100)) {
            js.executeScript("window.scrollTo(0," + i + ")");
            try {
                // 随机等待一段时间
                Thread.sleep((int)(Math.random()*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long scrollHeightTmp = scrollHeight;
        scrollHeight = (long) js.executeScript("return Math.max(document.body.scrollHeight,document.body.offsetHeight,document.documentElement.clientHeight,document.documentElement.scrollHeight,document.documentElement.offsetHeight)");
        for (long i = scrollHeightTmp; i < scrollHeight; i += (int)(Math.random()*100)) {
            js.executeScript("window.scrollTo("+i+"," + (i+scrollStep) + ")");
            try {
                // 随机等待一段时间
                Thread.sleep((int)(Math.random()*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        String source = webDriver.getPageSource();
//        System.out.println(source);

        // 获取网页源代码
        //WebElement imgUrl = webDriver.findElement(By.xpath("//*[@id=\"J_goodsList\"]/ul/li"));
        //System.out.println(imgUrl.getText());
//        String source = webDriver.getPageSource();
//        System.out.println(source);
        // 获取价格 用jsoup
        // 获取产品名称

        System.out.println(webDriver.getPageSource());

        // 关闭浏览器
//        webDriver.quit();
    }
}
