package ru.gerasimov.springmvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gerasimov.springmvc.dao.PersonDAO;
import ru.gerasimov.springmvc.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;//Spring внедрит объект класса PersonDAO в наш контроллер

    //но лучше это делать через конструктор и можем @Autowired писать над конструктором
    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    @GetMapping()
    public String index(Model model) {
        //Получим всех людей из DAO и передадим на отображение в представление
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")//можем поместить любое число и оно
    public String show(@PathVariable("id") int id, Model model) { //поместится в аргументы этого метода
        // Получим одного человека по id из DAO и передадим на отображение в представление
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    //    @GetMapping("/new")
//    public String newPerson(Model model) {
//        model.addAttribute("person", new Person());
//        return "people/new";
//    }
    //или так
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        //она автоматически создаст объект класса person и положит сама в модель
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person) {

        personDAO.save(person);
        return "redirect:/people";//те когда человек будет добавлен в базу данных то мы отправим браузеру эту строку
        //и браузер перейдет на страницу со всеми людьми
    }

    //при GET запросе по адресу /people/{id}/edit мв попадем в этот метод. Он возвращвет HTML страницу для редактирования стр
    @GetMapping("/{id}/edit")
            public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update (@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }
}
