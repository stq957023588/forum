package com.fool.demo.spider.pageprocessor;

import com.fool.demo.utils.DateTimeUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author fool
 * @date 2021/8/26 13:26
 */
public class WeChatPageProcessor implements PageProcessor {
    private final Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        Html html = page.getHtml();

        System.out.println(html);

        String title = html.xpath("//h1[@id='activity-name']/text()").toString();
        String author = html.xpath("//span[@id='profileBt']/a/text()").toString();
        String article = html.xpath("//div[@id='js_content']/tidyText()").toString();
        String releaseTimeStr = html.regex("t=\"([0-9]+)\",n=\"([0-9]+)\",i=\"([0-9,-]+)\"", 2).get();


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

        List<String> all = html.links().all();
        System.out.println(all);
        Selectable links = html.links().regex("http[s]{0,1}://mp.weixin.qq.com/s?__biz=.*==&mid=.*&idx=.*&sn=.*&chksm=.*&scene=.*#wechat_redirect");
        page.addTargetRequests(links.all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new WeChatPageProcessor())
                .addUrl("https://mp.weixin.qq.com/s/t8zLtcE8xeQQqFaB7ogzvQ")
                .addPipeline(new ConsolePipeline())
                .thread(2).run();
    }
}
