package com.tashinimonasha.pizzacreed.controller;

import com.tashinimonasha.pizzacreed.util.DTOConverter;
import com.tashinimonasha.pizzacreed.dao.BasketItem;
import com.tashinimonasha.pizzacreed.dao.Order;
import com.tashinimonasha.pizzacreed.dao.ShoppingBasket;
import com.tashinimonasha.pizzacreed.dto.ShoppingBasketDTO;
import com.tashinimonasha.pizzacreed.exception.BasketAlreadyCheckedOutException;
import com.tashinimonasha.pizzacreed.service.ShoppingBasketService;
import com.tashinimonasha.pizzacreed.util.ExceptionHandlingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class ShoppingBasketRestController {

    @Autowired
    private ShoppingBasketService shoppingBasketService;

    @PostMapping("/api/baskets")
    public ShoppingBasket createBasket() {
        return shoppingBasketService.createBasket();
    }

    @GetMapping("/api/baskets/{basketId}")
    public ResponseEntity<Object> getBasketById(@PathVariable Long basketId) {
        try {
            ShoppingBasket basket = shoppingBasketService.getBasketById(basketId);
            ShoppingBasketDTO basketDTO = DTOConverter.convertShoppingBasketToDTO(basket);
            return new ResponseEntity<>(basketDTO, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return ExceptionHandlingService.handleRestException(ex, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return ExceptionHandlingService.handleRestException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/baskets/{basketId}")
    public ResponseEntity<Object> addProductToBasket(@PathVariable Long basketId, @RequestBody BasketItem basketItem) {
        try {
            shoppingBasketService.addItemToBasket(basketId, basketItem.getPizza().getId(), basketItem.getQuantity());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Item added successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return ExceptionHandlingService.handleRestException(ex, HttpStatus.NOT_FOUND);
        } catch (BasketAlreadyCheckedOutException ex) {
            return ExceptionHandlingService.handleRestException(ex, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return ExceptionHandlingService.handleRestException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/baskets/{basketId}/items/{itemId}")
    public ResponseEntity<Object> removeItemFromBasket(@PathVariable Long basketId, @PathVariable Long itemId) {
        try {
            shoppingBasketService.removeItemFromBasket(basketId, itemId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Item removed successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return ExceptionHandlingService.handleRestException(ex, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return ExceptionHandlingService.handleRestException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/api/baskets/{basketId}/items")
    public ResponseEntity<Object> clearItemsFromBasket(@PathVariable Long basketId) {
        try {
            shoppingBasketService.clearBasket(basketId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Items cleared successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return ExceptionHandlingService.handleRestException(ex, HttpStatus.NOT_FOUND);
        } catch (BasketAlreadyCheckedOutException ex) {
            return ExceptionHandlingService.handleRestException(ex, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return ExceptionHandlingService.handleRestException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/api/baskets/{basketId}/checkout")
    public ResponseEntity<Object> checkout(@PathVariable Long basketId) {

        try {
            Order order = shoppingBasketService.checkout(basketId);

            double totalAmount = order.getTotalAmount();

            // You can customize the response message and structure as needed
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Checkout successful");
            response.put("orderId", order.getId());
            response.put("totalAmount", totalAmount);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return ExceptionHandlingService.handleRestException(ex, HttpStatus.NOT_FOUND);
        } catch (BasketAlreadyCheckedOutException ex) {
            return ExceptionHandlingService.handleRestException(ex, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return ExceptionHandlingService.handleRestException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




    /*
    private ShoppingBasketDTO convertToDTO(ShoppingBasket basket) {
        List<BasketItemDTO> itemDTOs = basket.getItems().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ShoppingBasketDTO.builder()
                .id(basket.getId())
                .status(basket.getStatus())
                .totalAmount(basket.getTotalAmount())
                .items(itemDTOs)
                .build();
    }

    private BasketItemDTO convertToDTO(BasketItem item) {
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
    }*/

}
