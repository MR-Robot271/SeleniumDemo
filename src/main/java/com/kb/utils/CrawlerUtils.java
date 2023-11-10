package com.kb.utils;

import com.kb.pojo.Keyword;
import com.kb.pojo.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlerUtils {
    /**
    * @Description: 获取产品信息
    * @Param: List<WebElement>
    * @return: java.util.List<com.kb.pojo.Product>
    * @Date: 2023/10/24
    */
    public static List<Product> getInfo(List<WebElement> elements){
        List<Product> products = new ArrayList<>();
        for (WebElement element : elements) {
            Product product = new Product();

            // 获取产品名
            String pName = element.findElement(By.cssSelector("a > div.goods-name.clamp2")).getAttribute("title");
            // System.out.println(pName);
            product.setpName(pName);

            // 获取产品价格
            WebElement priceElement = element.findElement(By.cssSelector("a > div.goods-price > div.sku-price-wrap-new"));
            String regexPrice = "￥(\\d+\\.\\d+)";
            Pattern patternPrice = Pattern.compile(regexPrice);
            Matcher matcherPrice = patternPrice.matcher(priceElement.getText());
            String pPrice="";
            if (matcherPrice.find()){
                pPrice = matcherPrice.group(1);
                // System.out.println(pPrice);
            }else{
                // 表示没有数据
                pPrice="-1";
            }
            Float pPriceFloat = Float.parseFloat(pPrice);
            // System.out.println(pPriceFloat);
            product.setpPrice(pPriceFloat);

            // 获取图片地址
            String number = element.findElement(By.cssSelector("a>div.clearfix.metertitle>span")).getText();
            // 定义正则表达式
            String regex = "：(\\w+)";

            // 创建pattern
            Pattern pattern = Pattern.compile(regex);

            // 创建Matcher
            Matcher matcher = pattern.matcher(number);

            // 图片的基本地址
            String baseImgUrl = "https://private.zkh.com/PRODUCT/BIG/BIG_";
            String endImgUrl = "_01.jpg?x-oss-process=style/WEBPCOM_style_350";
            String imgUrl;
            // 获取订货编码
            if (matcher.find()) {
                // 订货编码 唯一
                String realNumber = matcher.group(1);
                // 图片地址
                imgUrl = baseImgUrl + realNumber + endImgUrl;
                // System.out.println(imgUrl);
            } else {
                imgUrl = "暂时没有照片";
            }
            product.setpImg(imgUrl);

            // 将获取到的产品放入list
            products.add(product);

        }
        return products;
    }

    /**
    * @Description: 向下随机滑动页面直到页面底部
    * @Param: [WebDriver]
    * @return: void
    * @Date: 2023/10/24
    */
    public static void slidePage(WebDriver webDriver){
        // 创建js执行器
        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        // 随机向下滑动页面
        // 定义滚动目标的高度
        long scrollHeight = (long) js.executeScript("return Math.max(document.body.scrollHeight,document.body.offsetHeight,document.documentElement.clientHeight,document.documentElement.scrollHeight,document.documentElement.offsetHeight)");

        // 缓慢滚动页面
        for (long i = 0; i < scrollHeight; i += (int) (Math.random() * 100)) {
            js.executeScript("window.scrollTo(0," + i + ")");
            try {
                // 随机等待一段时间
                Thread.sleep((int) (Math.random() * 600));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long scrollHeightTmp = scrollHeight;
        scrollHeight = (long) js.executeScript("return Math.max(document.body.scrollHeight,document.body.offsetHeight,document.documentElement.clientHeight,document.documentElement.scrollHeight,document.documentElement.offsetHeight)");
        for (long i = scrollHeightTmp; i < scrollHeight; ) {
//            int a=(int)(Math.random()*100);
            js.executeScript("window.scrollTo(" + i + "," + (i += (int) (Math.random() * 100)) + ")");
            try {
                // 随机等待一段时间
                Thread.sleep((int) (Math.random() * 500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
    * @Description: 对抓取的数据进行一些过滤，主要通过 品牌 型号
    * @Param: [List<Product> products, Keyword keyword]
    * @return: java.util.List<com.kb.pojo.Product>
    * @Date: 2023/10/24
    */
    public static List<Product> filter(List<Product> products, Keyword keyword){
        List<Product> newProducts = new ArrayList<>();
        // 确保pName里有 品牌 和 型号（可能需要用正则表达式分词）
        for (Product product : products) {
            // 确保pName里有 品牌
            if (product.getpName().contains(keyword.getBrand())) {
                String regex="[a-zA-Z0-9+]+";
                List<String> words = splitWords(regex,keyword.getModelParameters());
                // 记录型号分词击中的次数
                int count=0;
                for (String word : words) {
                    if (product.getpName().contains(word)) {
                        count++;
                    }
                }
                // 比较 分词数量 与 击中次数 的差距
                // 以5为界分为两段
                if (words.size() <= 5) {
                    // 再分为2 3 5 三段
                    if (words.size() <= 2) {
                        if (count>=1) {
                            newProducts.add(product);
                        }
                    } else if (words.size() <= 3) {
                        if (count >= 2) {
                            newProducts.add(product);
                        }
                    } else {
                        if (count >= 3) {
                            newProducts.add(product);
                        }
                    }
                }else {
                    // 以80%为界
                    if (((float)count/(float)words.size())>=0.8 ){
                        newProducts.add(product);
                    }
                }
            }
        }
        return newProducts;
    }

    /**
    * @Description: 用正则表达式进行分词
    * @Param: String
    * @return: java.util.List<java.lang.String>
    * @Date: 2023/10/24
    */
    public static List<String> splitWords(String regex,String words){
        List<String> results=new ArrayList<>();

        // 用正则表达式进行分词
        // String regex="[a-zA-Z0-9+]+";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(words);
        while (matcher.find()){
            String word=matcher.group();
            results.add(word);
        }

        return results;
    }
}
