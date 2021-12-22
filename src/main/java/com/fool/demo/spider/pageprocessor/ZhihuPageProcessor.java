package com.fool.demo.spider.pageprocessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * @author fool
 * @date 2021/8/25 16:57
 */
public class ZhihuPageProcessor implements PageProcessor {

    private final Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String title = html.xpath("//h1[@class='QuestionHeader-title']/text()").toString();
        String answer = page.getHtml().xpath("//div[@class='QuestionAnswer-content']/tidyText()").toString();

        page.putField("title", title);
        page.putField("answer", answer);
        if (title == null) {
            // 如果是列表页，跳过此页，pipeline不进行后续处理
            page.setSkip(true);
        }

        page.addTargetRequests(page.getHtml().links().regex("https://www\\.zhihu\\.com/question/\\d+/answer/\\d+.*").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new ZhihuPageProcessor())
                .addUrl("https://www.zhihu.com/explore")
                .addPipeline(new ConsolePipeline())
                .thread(5).start();
    }
}