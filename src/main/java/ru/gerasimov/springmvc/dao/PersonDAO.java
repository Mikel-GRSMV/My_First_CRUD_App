package ru.gerasimov.springmvc.dao;
//этот класс будет общаться со списком и будет извлекать людей из списка находить какого то конертного человека по id,
//добавлять человека в список и удалять

import org.springframework.stereotype.Component;
import ru.gerasimov.springmvc.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT; //для назначения в поле id автоматически в начале = 0
    private List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Tom"));
        people.add(new Person(++PEOPLE_COUNT, "Mike"));
        people.add(new Person(++PEOPLE_COUNT, "Bob"));
        people.add(new Person(++PEOPLE_COUNT, "Katy"));
    }

    public List<Person> index() {
        //возвращает список из объектов класса Person
        return people;//возвращаем наш список people и при помощи Thymeleaf отобразим в браузере
    }

    public Person show(int id) {
        //возвращает одного человек
        //принимает на вход id этого человека и должен найти человека с этим id из нашей условной БД(из списка people)
        //здесь мы можем использовать лямбду выражение которые появились в 8 java-это сократит наш код
        //но можно использовать и цикл for, просто пройти по всему списку people и если на найдет id то вернет null
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person updatePerson){
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(updatePerson.getName());
    }

    public void delete(int id){
        people.removeIf(p -> p.getId() == id);
    }
}
