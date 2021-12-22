package com.fool.demo.dao;

import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonDaoTest {

    // private PersonDao personDao;
    //
    // @Autowired
    // public void setPersonDao(PersonDao personDao) {
    //     this.personDao = personDao;
    // }
    //
    // @Test
    // void save() {
    //     Person person = new Person();
    //     person.setAge(53);
    //     person.setName("Tom");
    //     person.setPhone("1315796840");
    //     personDao.save(person);
    // }
    //
    // @Test
    // void insert() {
    // }
    //
    // @Test
    // void findByName() {
    //     List<Person> persons = personDao.findByName("jack");
    //     persons.forEach(System.out::println);
    // }
    //
    // @Test
    // void update() {
    // }
    //
    // @Test
    // void delete() {
    // }
}