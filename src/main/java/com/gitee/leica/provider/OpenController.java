package com.gitee.leica.provider;

import com.gitee.leica.object.ScreenshotDTO;
import com.gitee.leica.tools.WebDriverContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;


/**
 * @author wyh
 * @since 2023/12/21
 */
@RestController
@RequestMapping("/screenshot")
public class OpenController {

    @PostMapping
    public void screenshot(@RequestBody ScreenshotDTO screenshotDTO, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/force-download");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + URLEncoder.encode("screenshot.png", "UTF-8"));
        WebDriverContext.screenshot(screenshotDTO.getUrl(), response.getOutputStream());
    }
}
