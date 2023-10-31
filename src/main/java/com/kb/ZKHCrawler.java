package com.kb;

import com.kb.pojo.Keyword;
import com.kb.pojo.Product;
import com.kb.utils.CrawlerUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class ZKHCrawler {
    public static List<Product> crawler(Keyword Keyword){
        // 如果出现找不到chromedriver的异常 可以使用以下代码
        // System.setProperty("webdriver.chrome.driver", "c:\\driver\\chromedriver.exe");

        // 震坤行的url
        String baseUrl = "https://www.zkh.com/search.html?keywords=";

        // keyword测试
//        Keyword keyword1 = new Keyword("德力西", "漏电保护器", "DZ47sLE 3P+n 63A");
        String keyword=Keyword.toString();

        // 搜索的产品名
//        String keyword = "绿联六类线";
        ChromeOptions option = new ChromeOptions();
//        option.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        option.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36");

        // 创建driver
        WebDriver webDriver = new ChromeDriver(option);



        // 打开网页
        webDriver.get(baseUrl + keyword);

        // 随机等待一段时间
        try {
            Thread.sleep((int) (Math.random() * 3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CrawlerUtils.slidePage(webDriver);


        // 存储结果
        List<Product> products = new java.util.ArrayList<>();
        // 获取相应的内容 产品名 价格 图片
        List<WebElement> elements = webDriver.findElements(By.xpath("//*[@id=\"app\"]/div/div/div[5]/div[6]/div/div"));
        List<WebElement> elementsInfinite = webDriver.findElements(By.cssSelector("#app>div.content>div.catalog-wrap>div.list-inner>div.infiniteScroll>div.goods-wrap>div>div.goods-item-box-new>div.goods-item-wrap-new"));
//        List<WebElement> elements=webDriver.findElements(By.cssSelector("#app>div.content>div.catalog-wrap>div.list-inner>div.infiniteScroll>div.goods-wrap>div>div.goods-item-box-new>div.goods-item-wrap-new"));
        products= CrawlerUtils.getInfo(elements);
        if (elementsInfinite.size()>0) {
            products.addAll(CrawlerUtils.getInfo(elementsInfinite));
        }

        // 输出产品数量
//        System.out.println(products.size());
//        for (Product product1 : products) {
//            System.out.println(product1);
//        }

        // 用品牌 型号 过滤数据
        products=CrawlerUtils.filter(products,Keyword);
//        System.out.println(products.size());
//        for (Product product1 : products) {
//            System.out.println(product1);
//        }

        // 关闭浏览器
        webDriver.quit();

        return products;
    }

//    public static void main(String[] args) {
//        // 如果出现找不到chromedriver的异常 可以使用以下代码
//        // System.setProperty("webdriver.chrome.driver", "c:\\driver\\chromedriver.exe");
//
//        // 震坤行的url
//        String baseUrl = "https://www.zkh.com/search.html?keywords=";
//
//        // keyword测试
//        Keyword keyword1 = new Keyword("德力西", "漏电保护器", "DZ47sLE 3P+n 63A");
//        String keyword=keyword1.toString();
//
//        // 搜索的产品名
////        String keyword = "绿联六类线";
//        ChromeOptions option = new ChromeOptions();
////        option.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
//        option.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36");
//
//        // 创建driver
//        WebDriver webDriver = new ChromeDriver(option);
//
//
//
//        // 打开网页
//        webDriver.get(baseUrl + keyword);
//
//        // 随机等待一段时间
//        try {
//            Thread.sleep((int) (Math.random() * 3000));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
////        // 创建js执行器
////        JavascriptExecutor js = (JavascriptExecutor) webDriver;
////
////        // 随机向下滑动页面
////        // 定义滚动目标的高度
////        long scrollHeight = (long) js.executeScript("return Math.max(document.body.scrollHeight,document.body.offsetHeight,document.documentElement.clientHeight,document.documentElement.scrollHeight,document.documentElement.offsetHeight)");
////
////        // 缓慢滚动页面
////        for (long i = 0; i < scrollHeight; i += (int) (Math.random() * 100)) {
////            js.executeScript("window.scrollTo(0," + i + ")");
////            try {
////                // 随机等待一段时间
////                Thread.sleep((int) (Math.random() * 600));
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////        }
////        long scrollHeightTmp = scrollHeight;
////        scrollHeight = (long) js.executeScript("return Math.max(document.body.scrollHeight,document.body.offsetHeight,document.documentElement.clientHeight,document.documentElement.scrollHeight,document.documentElement.offsetHeight)");
////        for (long i = scrollHeightTmp; i < scrollHeight; ) {
//////            int a=(int)(Math.random()*100);
////            js.executeScript("window.scrollTo(" + i + "," + (i += (int) (Math.random() * 100)) + ")");
////            try {
////                // 随机等待一段时间
////                Thread.sleep((int) (Math.random() * 500));
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////        }
//        CrawlerUtils.slidePage(webDriver);
//
//
//        // 存储结果
//        List<Product> products = new java.util.ArrayList<>();
//        // 获取相应的内容 产品名 价格 图片
//        List<WebElement> elements = webDriver.findElements(By.xpath("//*[@id=\"app\"]/div/div/div[5]/div[6]/div/div"));
//        List<WebElement> elementsInfinite = webDriver.findElements(By.cssSelector("#app>div.content>div.catalog-wrap>div.list-inner>div.infiniteScroll>div.goods-wrap>div>div.goods-item-box-new>div.goods-item-wrap-new"));
////        List<WebElement> elements=webDriver.findElements(By.cssSelector("#app>div.content>div.catalog-wrap>div.list-inner>div.infiniteScroll>div.goods-wrap>div>div.goods-item-box-new>div.goods-item-wrap-new"));
//        products= CrawlerUtils.getInfo(elements);
//        if (elementsInfinite.size()>0) {
//            products.addAll(CrawlerUtils.getInfo(elementsInfinite));
//        }
//
//        // 输出产品数量
//        System.out.println(products.size());
//        for (Product product1 : products) {
//            System.out.println(product1);
//        }
//
//        // 用品牌 型号 过滤数据
//        products=CrawlerUtils.filter(products,keyword1);
//        System.out.println(products.size());
//        for (Product product1 : products) {
//            System.out.println(product1);
//        }
//
//
////        WebElement searchBox = webDriver.findElement(By.id("key"));
////
////        try {
//////            System.out.println(searchBox.getText());
//////            int keyLength = searchBox.getText().toCharArray().length;
//////            System.out.println(keyLength);
////            int randomNum = 20+(int)(Math.random()*10);
////            System.out.println(randomNum);
////            for (int i = 0; i < randomNum; i++) {
////                // 随机等待一段时间
////                Thread.sleep((int)(Math.random()*300));
////                searchBox.sendKeys(Keys.BACK_SPACE);
////            }
////            searchBox.sendKeys("绿联");
////            // 随机等待一段时间
////            Thread.sleep((int)(Math.random()*1000));
////
////            searchBox.sendKeys("六类线");
////
////            // 随机等待一段时间
////            Thread.sleep((int)(Math.random()*10000));
////            searchBox.sendKeys(Keys.RETURN);
////
////            // 随机等待一段时间
////            Thread.sleep((int)(Math.random()*10000));
////
////            // 随机等待一段时间
////            Thread.sleep((int)(Math.random()*11100));
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//
//
//        // 关闭浏览器
//        webDriver.quit();
//    }
}
