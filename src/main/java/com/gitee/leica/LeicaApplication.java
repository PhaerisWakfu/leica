package com.gitee.leica;

import com.gitee.leica.tools.WebDriverContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author wyh
 * @since 2024/1/19
 */
@Slf4j
@RestController
@SpringBootApplication
@SuppressWarnings("SpellCheckingInspection")
public class LeicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeicaApplication.class, args);
    }

    @GetMapping("/screenshot")
    public void screenshot(@RequestParam String url, @RequestParam(required = false) Integer width,
                           @RequestParam(required = false) Integer height, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "image/png");
        response.setHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode("screenshot.png", "UTF-8"));
        WebDriverContext.screenshot(url, width, height, response.getOutputStream());
    }

    /**
     * 异常返回错误信息
     */
    @ExceptionHandler
    public ResponseEntity<String> external(Exception e) {
        log.error("exception catch ==>", e);
        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(e.getMessage());
    }
}
