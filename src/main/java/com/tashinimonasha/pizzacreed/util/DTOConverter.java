package com.tashinimonasha.pizzacreed.util;

import com.tashinimonasha.pizzacreed.dao.BasketItem;
import com.tashinimonasha.pizzacreed.dao.Pizza;
import com.tashinimonasha.pizzacreed.dao.ShoppingBasket;
import com.tashinimonasha.pizzacreed.dto.BasketItemDTO;
import com.tashinimonasha.pizzacreed.dto.ShoppingBasketDTO;

import java.util.List;
import java.util.stream.Collectors;

public class DTOConverter {

    public static ShoppingBasketDTO convertShoppingBasketToDTO(ShoppingBasket basket) {
        List<BasketItemDTO> itemDTOs = basket.getItems().stream()
                .map(DTOConverter::convertBasketItemToDTO)
                .collect(Collectors.toList());

        return ShoppingBasketDTO.builder()
                .id(basket.getId())
                .status(basket.getStatus())
                .totalAmount(basket.getTotalAmount())
                .items(itemDTOs)
                .build();
    }

    public static BasketItemDTO convertBasketItemToDTO(BasketItem item) {
        Pizza pizza = item.getPizza();

        return BasketItemDTO.builder()
                .id(item.getId())
                .pizzaId(pizza != null ? pizza.getId() : null)
                .pizzaName(pizza != null ? pizza.getName() : null)
                .pizzaSize(pizza != null ? pizza.getSize() : null)
                .pizzaPrice(pizza != null ? pizza.getPrice() : null)
                .quantity(item.getQuantity())
                .total(pizza != null ? pizza.getPrice() * item.getQuantity() : null)
                .build();
    }
}

