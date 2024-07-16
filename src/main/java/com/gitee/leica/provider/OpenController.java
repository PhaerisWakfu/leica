package com.gitee.leica.provider;

import com.gitee.leica.exception.ExternalException;
import com.gitee.leica.object.ResultBase;
import com.gitee.leica.tools.WebDriverContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;


/**
 * @author wyh
 * @since 2023/12/21
 */
@Slf4j
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

    /**
     * 外部异常返回错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResultBase<String> external(ExternalException e) {
        log.error("external exception catch ==>", e);
        return ResultBase.fail(e.getMessage());
    }
}
