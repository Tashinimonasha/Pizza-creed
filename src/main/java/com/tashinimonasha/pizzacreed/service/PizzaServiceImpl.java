package com.tashinimonasha.pizzacreed.service;

import com.tashinimonasha.pizzacreed.dao.Pizza;
import com.tashinimonasha.pizzacreed.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Override
    public List<Pizza> getAllPizzas() {

        return pizzaRepository.findAll();
    }

    @Override
    public Pizza getPizzaById(Long pizzaId) {
        return pizzaRepository.findById(pizzaId).orElse(null);
    }

    @Override
    public Pizza savePizza(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    @Override
    public void deletePizza(Long pizzaId) {
        pizzaRepository.deleteById(pizzaId);
    }

    @Override
    public void editPizza(Long id, Pizza editedPizza) {
        Pizza pizza = pizzaRepository.findById(id).orElse(null);
        if (pizza == null) {
            return;
        }
        pizza.setName(editedPizza.getName());
        pizza.setPrice(editedPizza.getPrice());
        pizza.setSize(editedPizza.getSize());
        pizzaRepository.save(pizza);
    }

    @Override
    public int getPizzaCount() {
        return (int) pizzaRepository.count();
    }
}
