package com.fool.demo.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PersonDao {

    // MongoTemplate template;
    //
    // @Autowired
    // public void setTemplate(MongoTemplate template) {
    //     this.template = template;
    // }
    //
    // public void save(Person person) {
    //     template.save(person);
    // }
    //
    // public void insert(Person person) {
    //     template.insert(person);
    // }
    //
    // public List<Person> findByName(String name) {
    //     Query query = new Query(Criteria.where("name").regex(".*?" + name + ".*"));
    //     return template.find(query, Person.class);
    // }
    //
    // public void update(Person person) {
    //     Query query = new Query(Criteria.where("id").is(person.getId()));
    //
    //     Update update = new Update().set("age", person.getAge());
    //     UpdateResult updateResult = template.updateMulti(query, update, Person.class);
    //     long matchedCount = updateResult.getMatchedCount();
    //     log.info("Update count:{}", matchedCount);
    // }
    //
    // public void delete(String id) {
    //     Query query = new Query(Criteria.where("id").is(id));
    //     DeleteResult remove = template.remove(query, Person.class);
    //     long deletedCount = remove.getDeletedCount();
    //     log.info("Delete count:{}", deletedCount);
    // }

}
