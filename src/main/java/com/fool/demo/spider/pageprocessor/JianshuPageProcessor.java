package com.fool.demo.spider.pageprocessor;

import com.fool.demo.utils.DateTimeUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import javax.management.JMException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author fool
 * @date 2021/8/26 9:21
 */
public class JianshuPageProcessor implements PageProcessor {
    private final Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        Html html = page.getHtml();

        String title = html.xpath("//h1[@class='_1RuRku']/text()").toString();
        String author = html.xpath("//div[@class='_26qd_C']/a/span/text()").toString();
        String article = html.xpath("//article/tidyText()").toString();
        String releaseTimeStr = html.regex("\"first_shared_at\":([0-9]*)", 1).get();


        page.putField("title", title);
        page.putField("author", author);
        page.putField("article", article);
        if (releaseTimeStr != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(Long.parseLong(releaseTimeStr) * 1000L);
            Date releaseDate = instance.getTime();
            page.putField("releaseTime", DateTimeUtils.format(releaseDate, DateTimeUtils.DATE_TIME_PATTERN));
        }
        if (title == null) {
            page.setSkip(true);
        }

        Selectable links = html.links().regex("https://www\\.jianshu.com/p/.*");
        page.addTargetRequests(links.all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws JMException {
        Spider spider = Spider.create(new JianshuPageProcessor())
                .addUrl("https://www.jianshu.com/")
                .addPipeline(new ConsolePipeline())
                .thread(2);

        SpiderMonitor.instance().register(spider);
        spider.start();
    }
}
