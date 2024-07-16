### 说明

- 使用无头浏览器实现输入网址获取实时截图

### 使用

#### 下载chrome浏览器及其相对应的chromedriver
一定要版本对应，不然启动时会提示你版本不符的
```
https://googlechromelabs.github.io/chrome-for-testing/#stable
```

#### 添加配置
```yaml
leica:
  web-driver:
    #下载的浏览器驱动所在路径
    driver-path: /xxx
    #浏览器所在路径
    binary-path: /xxx
```

#### 截图
```
GET http://localhost:8080/screenshot?url=https://www.baidu.com
```