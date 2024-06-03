package com.tashinimonasha.pizzacreed.service;

import com.tashinimonasha.pizzacreed.dao.Order;
import com.tashinimonasha.pizzacreed.dao.ShoppingBasket;

public interface ShoppingBasketService {
    ShoppingBasket createBasket();

    ShoppingBasket getBasketById(Long basketId);

    void addItemToBasket(Long basketId, Long pizzaId, int quantity);

    void removeItemFromBasket(Long basketId, Long itemId);

    void clearBasket(Long basketId);

    Order checkout(Long basketId);
}
