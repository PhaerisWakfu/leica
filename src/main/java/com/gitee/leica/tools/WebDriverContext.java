package com.gitee.leica.tools;

import com.gitee.leica.config.LeicaProperties;
import com.gitee.leica.exception.ExternalException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Optional;

/**
 * @author wyh
 * @since 2023/10/26
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebDriverContext {

    private static final String SCRIPT_GET_BODY_HEIGHT = "return document.body.offsetHeight";

    private static final String SCRIPT_GET_ELE_HEIGHT = "return document.querySelectorAll('%s')[0].offsetHeight";

    private static final String FORMAT = "png";


    /**
     * 截图
     *
     * @param url 页面url
     * @param os  输出流
     */
    public static void screenshot(@NonNull String url, @Nullable Integer width, @Nullable Integer height, OutputStream os) {
        screenshot(url, width, height, null, os);
    }

    /**
     * 截图
     *
     * @param url    页面url
     * @param width  宽
     * @param height 高
     * @param ele    指定元素
     * @param os     输出流
     */
    public static void screenshot(@NonNull String url, Integer width, Integer height, String ele, OutputStream os) {

        long start = System.currentTimeMillis();

        //这里每次新建driver(处理并发调用下使用同一个实例会互相影响宽高的问题)
        WebDriver driver = getDriver();

        int w = Optional.ofNullable(width).orElse(1920);
        int h = Optional.ofNullable(height).orElse(1080);

        try {

            //初始高度
            driver.manage().window().setSize(new Dimension(w, h));

            //请求页面
            driver.get(url);

            //如果滚动截图计算设置真实高度
            if (height == null) {
                String script = Optional.ofNullable(ele)
                        .map(e -> String.format(SCRIPT_GET_ELE_HEIGHT, e))
                        .orElse(SCRIPT_GET_BODY_HEIGHT);
                Long offsetHeight = (Long) ((JavascriptExecutor) driver).executeScript(script);
                h = offsetHeight != null && offsetHeight > 0 ? offsetHeight.intValue() + 100 : h;
                driver.manage().window().setSize(new Dimension(w, h));
            }

            AShot aShot = new AShot()
                    .coordsProvider(new WebDriverCoordsProvider());
            //如果滚动截图设置滚动策略
            Optional.ofNullable(height).ifPresent(x -> aShot.shootingStrategy(ShootingStrategies.viewportPasting(100)));
            BufferedImage image = Optional.ofNullable(ele)
                    .map(e -> aShot.takeScreenshot(driver, driver.findElement(By.cssSelector(e))))
                    .orElse(aShot.takeScreenshot(driver))
                    .getImage();
            //输出
            ImageIO.write(image, FORMAT, os);
            log.info("截图耗时 ==> [{}]ms", System.currentTimeMillis() - start);
        } catch (Exception e) {
            throw new ExternalException("截图失败", e);
        } finally {
            //因为是每次新建, 所以这里每次都关闭所有窗口关闭会话
            driver.quit();
        }
    }

    /**
     * 获取浏览器驱动
     *
     * @return 获取浏览器驱动
     */
    public static WebDriver getDriver() {
        ChromeOptions chromeOptions = new ChromeOptions();
        LeicaProperties.WebDriver webDriver = PropertiesContext.get().getWebDriver();
        Optional.ofNullable(webDriver.getBinaryPath()).ifPresent(chromeOptions::setBinary);
        System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, webDriver.getDriverPath());
        chromeOptions.addArguments("--headless");//设置为 headless 模式 （必须）
        chromeOptions.addArguments("--disable-gpu");//谷歌文档提到需要加上这个属性来规避bug
        chromeOptions.addArguments("--no-sandbox");//参数是让Chrome在root权限下跑
        chromeOptions.addArguments("--disable-dev-shm-usage");//禁用 Chrome 浏览器在共享内存文件系统（dev/shm）上使用临时文件
        chromeOptions.addArguments("lang=zh_CN.UTF-8");//中文
        chromeOptions.addArguments("--no-zygote");//处理僵尸进程
        chromeOptions.addArguments("--single-process");//开启单进程
        chromeOptions.addArguments("--disable-cache");//禁用缓存
        return new ChromeDriver(chromeOptions);
    }
}