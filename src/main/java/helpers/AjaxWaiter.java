package helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AjaxWaiter {

    private final JavascriptExecutor js;
    private final WebDriver driver;
    private final String urlKeyword;

    public AjaxWaiter(WebDriver driver, String urlKeyword) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.urlKeyword = urlKeyword;
    }

    public void installTracker() {
        String script =
                "var kw = arguments[0];" +
                        "if (window.__pending === undefined) {" +
                        "  window.__pending = 0;" +
                        "  var open = XMLHttpRequest.prototype.open;" +
                        "  XMLHttpRequest.prototype.open = function(m, url) {" +
                        "    this.__track = String(url).indexOf(kw) !== -1;" +
                        "    return open.apply(this, arguments);" +
                        "  };" +
                        "  var send = XMLHttpRequest.prototype.send;" +
                        "  XMLHttpRequest.prototype.send = function() {" +
                        "    if (this.__track) {" +
                        "      window.__pending++;" +
                        "      this.addEventListener('loadend', function(){ window.__pending--; });" +
                        "    }" +
                        "    return send.apply(this, arguments);" +
                        "  };" +
                        "  if (window.fetch) {" +
                        "    var f = window.fetch;" +
                        "    window.fetch = function(u) {" +
                        "      var t = String((u && u.url) || u).indexOf(kw) !== -1;" +
                        "      if (!t) return f.apply(this, arguments);" +
                        "      window.__pending++;" +
                        "      return f.apply(this, arguments).finally(function(){ window.__pending--; });" +
                        "    };" +
                        "  }" +
                        "}";
        js.executeScript(script, urlKeyword);
    }

    public void waitForRequestsDone() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(d -> pending() > 0);
        } catch (TimeoutException ignored) {}

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> pending() == 0);
    }

    private int pending() {
        return ((Number) js.executeScript("return window.__pending;")).intValue();
    }
}