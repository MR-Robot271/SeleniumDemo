package com.kb;

import com.kb.pojo.Product;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZKHCrawler {
    public static void main(String[] args) {
        // 如果出现找不到chromedriver的异常 可以使用以下代码
        // System.setProperty("webdriver.chrome.driver", "c:\\driver\\chromedriver.exe");

        // 震坤行的url
        String baseUrl = "https://www.zkh.com/search.html?keywords=";

        // 搜索的产品名
        String keyword="绿联六类线";

        ChromeOptions option=new ChromeOptions();
//        option.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        option.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36");

        // 创建driver
        WebDriver webDriver = new ChromeDriver(option);

        // 创建js执行器
        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        // 打开网页
        webDriver.get(baseUrl+keyword);

        // 随机等待一段时间
        try {
            Thread.sleep((int)(Math.random()*3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 随机向下滑动
        // 定义滚动目标的高度
        long scrollHeight = (long) js.executeScript("return Math.max(document.body.scrollHeight,document.body.offsetHeight,document.documentElement.clientHeight,document.documentElement.scrollHeight,document.documentElement.offsetHeight)");

        // 缓慢滚动页面
        for (long i = 0; i < scrollHeight; i += (int)(Math.random()*100)) {
            js.executeScript("window.scrollTo(0," + i + ")");
            try {
                // 随机等待一段时间
                Thread.sleep((int)(Math.random()*600));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long scrollHeightTmp = scrollHeight;
        scrollHeight = (long) js.executeScript("return Math.max(document.body.scrollHeight,document.body.offsetHeight,document.documentElement.clientHeight,document.documentElement.scrollHeight,document.documentElement.offsetHeight)");
        for (long i = scrollHeightTmp; i < scrollHeight;) {
//            int a=(int)(Math.random()*100);
            js.executeScript("window.scrollTo("+i+"," + (i+=(int)(Math.random()*100)) + ")");
            try {
                // 随机等待一段时间
                Thread.sleep((int)(Math.random()*500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        // 存储结果
        List<Product> products = new java.util.ArrayList<>();
        // 获取相应的内容 产品名 价格 图片
        List<WebElement> elements = webDriver.findElements(By.xpath("//*[@id=\"app\"]/div/div/div[5]/div[6]/div/div"));
        for (WebElement element : elements) {
            Product product = new Product();
            // 获取产品名
            String pName=element.findElement(By.cssSelector("a > div.goods-name.clamp2")).getAttribute("title");
            // System.out.println(pName);
            product.setpName(pName);

            // 获取产品价格
            WebElement priceElement = element.findElement(By.cssSelector("a>div.goods-price > div.sku-price-wrap-new>div.wrap-flex>div"));
            String pPrice=priceElement.findElement(By.cssSelector("span.integer")).getText()+priceElement.findElement(By.cssSelector("span.decimal")).getText();
            Float pPriceFloat=Float.parseFloat(pPrice);
            // System.out.println(pPriceFloat);
            product.setpPrice(pPriceFloat);

            // 获取图片地址
            String number = element.findElement(By.cssSelector("a>div.clearfix.metertitle>span")).getText();
            // 定义正则表达式
            String regex="：(\\w+)";

            // 创建pattern
            Pattern pattern = Pattern.compile(regex);

            // 创建Matcher
            Matcher matcher = pattern.matcher(number);

            // 图片的基本地址
            String baseImgUrl="https://private.zkh.com/PRODUCT/BIG/BIG_";
            String endImgUrl = "_01.jpg?x-oss-process=style/WEBPCOM_style_350";
            String imgUrl;
            // 获取订货编码
            if (matcher.find()) {
                // 订货编码 唯一
                String realNumber = matcher.group(1);
                // 图片地址
                imgUrl=baseImgUrl+realNumber+endImgUrl;
                // System.out.println(imgUrl);
            }else{
                imgUrl="暂时没有照片";
            }
            product.setpImg(imgUrl);

            // 将获取到的产品放入list
            products.add(product);

//            String pImg=element.findElement(By.cssSelector("a>div.goods-img>div.lazyload-wrap>img")).getAttribute("src");
//            System.out.println(pImg);
        }

        for (Product product1 : products) {
            System.out.println(product1);
        }



//        WebElement searchBox = webDriver.findElement(By.id("key"));
//
//        try {
////            System.out.println(searchBox.getText());
////            int keyLength = searchBox.getText().toCharArray().length;
////            System.out.println(keyLength);
//            int randomNum = 20+(int)(Math.random()*10);
//            System.out.println(randomNum);
//            for (int i = 0; i < randomNum; i++) {
//                // 随机等待一段时间
//                Thread.sleep((int)(Math.random()*300));
//                searchBox.sendKeys(Keys.BACK_SPACE);
//            }
//            searchBox.sendKeys("绿联");
//            // 随机等待一段时间
//            Thread.sleep((int)(Math.random()*1000));
//
//            searchBox.sendKeys("六类线");
//
//            // 随机等待一段时间
//            Thread.sleep((int)(Math.random()*10000));
//            searchBox.sendKeys(Keys.RETURN);
//
//            // 随机等待一段时间
//            Thread.sleep((int)(Math.random()*10000));
//
//            // 随机等待一段时间
//            Thread.sleep((int)(Math.random()*11100));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        // 关闭浏览器
        webDriver.quit();
    }
}
