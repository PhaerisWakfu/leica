package com.gitee.leica.provider;

import com.gitee.leica.tools.WebDriverContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public void screenshot(@RequestParam String url, @RequestParam(required = false) Integer width,
                           @RequestParam(required = false) Integer height, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "image/png");
        response.setHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode("screenshot.png", "UTF-8"));
        WebDriverContext.screenshot(url, width, height, response.getOutputStream());
    }
}
