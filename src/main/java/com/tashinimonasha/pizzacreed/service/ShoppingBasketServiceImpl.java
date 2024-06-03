package com.tashinimonasha.pizzacreed.service;

import com.tashinimonasha.pizzacreed.dao.BasketItem;
import com.tashinimonasha.pizzacreed.dao.Order;
import com.tashinimonasha.pizzacreed.dao.Pizza;
import com.tashinimonasha.pizzacreed.dao.ShoppingBasket;
import com.tashinimonasha.pizzacreed.exception.BasketAlreadyCheckedOutException;
import com.tashinimonasha.pizzacreed.repository.BasketItemRepository;
import com.tashinimonasha.pizzacreed.repository.OrderRepository;
import com.tashinimonasha.pizzacreed.repository.PizzaRepository;
import com.tashinimonasha.pizzacreed.repository.ShoppingBasketRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingBasketServiceImpl implements ShoppingBasketService {

    @Autowired
    private ShoppingBasketRepository basketRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private BasketItemRepository basketItemRepository;

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public ShoppingBasket createBasket() {
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.setStatus("EMPTY");
        return basketRepository.save(shoppingBasket);
    }

    @Override
    public ShoppingBasket getBasketById(Long basketId) {
        // return basketRepository.findById(basketId).orElse(null);
        Optional<ShoppingBasket> basketOptional = basketRepository.findById(basketId);

        if (basketOptional.isPresent()) {
            return basketOptional.get();
        } else {
            throw new EntityNotFoundException("Shopping basket not found with id: " + basketId);
        }
    }

    @Override
    public void addItemToBasket(Long basketId, Long pizzaId, int quantity) {
        Optional<ShoppingBasket> basketOptional = basketRepository.findById(basketId);

        if (basketOptional.isPresent()) {
            ShoppingBasket basket = basketOptional.get();
            if (basket.getStatus().equals("COMPLETE")) {
                throw new BasketAlreadyCheckedOutException("Basket is already checked out");
            }
            Optional<Pizza> pizzaOptional = pizzaRepository.findById(pizzaId);
            if (pizzaOptional.isEmpty()) {
                throw new EntityNotFoundException("Pizza not found with id: " + pizzaId);
            }
            Pizza pizza = pizzaOptional.get();

            BasketItem item = new BasketItem();
            item.setQuantity(quantity);
            item.setPizza(pizza);
            item.setShoppingBasket(basket);

            basket.getItems().add(item);
            //basket.setTotalAmount(basket.getTotalAmount() + (pizza.getPrice() * quantity));
            basket.setTotalAmount(basket.getItems().stream()
                    .mapToDouble(basketItem -> basketItem.getPizza().getPrice() * basketItem.getQuantity())
                    .sum());
            basket.setStatus("IN_PROGRESS");

            basketRepository.save(basket);
        } else {
            throw new EntityNotFoundException("Shopping basket not found with id: " + basketId);
        }
    }

    @Override
    public void removeItemFromBasket(Long basketId, Long itemId) {
        Optional<ShoppingBasket> basketOptional = basketRepository.findById(basketId);

        if (basketOptional.isPresent()) {
            ShoppingBasket basket = basketOptional.get();
            if (basket.getStatus().equals("COMPLETE")) {
                throw new BasketAlreadyCheckedOutException("Basket is already checked out");
            }

            //basket.getItems().removeIf(item -> item.getId().equals(itemId));
            // Retrieve the item to be removed
            BasketItem itemToRemove = basket.getItems()
                    .stream()
                    .filter(item -> item.getId().equals(itemId))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Basket item not found with id: " + itemId));

            // Remove the item from the collection
            basket.getItems().remove(itemToRemove);

            // Remove the item from the database
            basketItemRepository.deleteById(itemId);


            basket.setTotalAmount(basket.getItems().stream()
                    .mapToDouble(item -> item.getPizza().getPrice() * item.getQuantity())
                    .sum());

            if (basket.getItems().isEmpty()) {
                basket.setStatus("EMPTY");
            }

            basketRepository.save(basket);
        } else {
            throw new EntityNotFoundException("Shopping basket not found with id: " + basketId);
        }

    }

    @Override
    @Transactional
    public void clearBasket(Long basketId) {
        // ShoppingBasket basket = basketRepository.findById(basketId).orElseThrow(() -> new RuntimeException("Basket not found"));

        Optional<ShoppingBasket> basket = basketRepository.findById(basketId);

        if (basket.isPresent()) {
            if (basket.get().getStatus().equals("COMPLETE")) {
                throw new BasketAlreadyCheckedOutException("Basket is already checked out");
            }
            basket.get().getItems().clear();

            // Remove all items from the database
            basketItemRepository.deleteAllByShoppingBasketId(basketId);

            basket.get().setTotalAmount(0);
            basket.get().setStatus("EMPTY");
            basketRepository.save(basket.get());
        } else {
            throw new EntityNotFoundException("Shopping basket not found with id: " + basketId);
        }
    }

    @Override
    public Order checkout(Long basketId) {
        //ShoppingBasket basket = basketRepository.findById(basketId).orElseThrow(() -> new RuntimeException("Basket not found"));

        Optional<ShoppingBasket> basketOptional = basketRepository.findById(basketId);

        if (basketOptional.isPresent()) {
            ShoppingBasket basket = basketOptional.get();
            if (basket.getStatus().equals("COMPLETE")) {
                throw new BasketAlreadyCheckedOutException("Basket is already checked out");
            }
            //get the total amount of the basket
            double totalAmount = basket.getItems().stream()
                    .mapToDouble(item -> item.getPizza().getPrice() * item.getQuantity())
                    .sum();

            Order order = new Order();
            order.setShoppingBasket(basket);
            order.setTotalAmount(totalAmount);

            basket.setStatus("COMPLETE");

            return orderRepository.save(order);
        } else {
            throw new EntityNotFoundException("Shopping basket not found with id: " + basketId);
        }


    }
}
