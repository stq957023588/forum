package com.fool.demo.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    // private RedisTemplate<Object, Object> redisTemplate;
    //
    // private MongoTemplate mongoTemplate;
    //
    // @Autowired
    // public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
    //     this.redisTemplate = redisTemplate;
    // }
    //
    // @Autowired
    // public void setMongoTemplate(MongoTemplate mongoTemplate) {
    //     this.mongoTemplate = mongoTemplate;
    // }
    //
    // @RequestMapping(value = "unread-messages", method = RequestMethod.GET)
    // public List<PrivateChatMessage> getUnReadMessages(String from, String to) {
    //     Long o = (Long) redisTemplate.opsForValue().get("latest-read-message-id::" + from + "::" + to);
    //     if (o == null) {
    //         return new ArrayList<>();
    //     }
    //     Sort idSort = Sort.by("id");
    //     Criteria criteria = Criteria.where("id").gte(o).and("from").is(from).and("to").is(to);
    //     Query query = Query.query(criteria).with(idSort);
    //
    //     List<PrivateChatMessage> privateChatMessages = mongoTemplate.find(query, PrivateChatMessage.class);
    //     if (privateChatMessages.size() > 0) {
    //         PrivateChatMessage privateChatMessage = privateChatMessages.get(privateChatMessages.size() - 1);
    //         redisTemplate.opsForValue().set("latest-read-message-id::" + from + "::" + to, privateChatMessage.getId());
    //     }
    //     return privateChatMessages;
    // }

}
