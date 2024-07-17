package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.*;
import com.task1.ecommerce.data.repositories.BuyerRepository;
import com.task1.ecommerce.dtos.requests.AddToCartRequest;
import com.task1.ecommerce.dtos.requests.BuyerRegistrationRequest;
import com.task1.ecommerce.dtos.responses.AddToCartResponse;
import com.task1.ecommerce.dtos.responses.BuyerRegistrationResponse;
import com.task1.ecommerce.exceptions.BuyerExistException;
import com.task1.ecommerce.exceptions.BuyerNotFoundException;
import com.task1.ecommerce.exceptions.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BuyerServiceApp implements BuyerService{
    private final BuyerRepository buyerRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final CartItemService cartItemService;

    @Override
    public BuyerRegistrationResponse registerBuyer(BuyerRegistrationRequest request) throws BuyerExistException {

        boolean isRegistered = buyerRepository.findByEmail(request.getEmail())!=null;
        if (isRegistered) throw new BuyerExistException("Registration details already taken");
        Buyer buyer = createBuyer(request);
        Cart cart = new Cart();
        List<BuyerOder> buyerOrders = new ArrayList<>();
        cart.setBuyerId(buyer.getId());
        buyerRepository.save(buyer);
        cartService.save(cart);
        buyer.setBuyerOrders(buyerOrders);
        buyer.setCart(cart);
        buyerRepository.save(buyer);

        return buildResponse();
    }


    @Override
    public AddToCartResponse addProductToCart(AddToCartRequest request) throws BuyerNotFoundException, ProductNotFoundException {
        Buyer existingBuyer = buyerRepository.findById(request.getBuyerId()).orElse(null);
        if (existingBuyer==null) throw new BuyerNotFoundException("Invalid user details");

        Product existingProduct = productService.getProductById(request.getProductId());
        if (existingProduct==null) throw new ProductNotFoundException("Product is unavailable at the moment");

        if (existingProduct.getQuantity() < request.getQuantity()) throw new ProductNotFoundException("The required quantity of product is unavailable at the moment. You can only request for " + existingProduct.getQuantity()
                + " ,or less at the moment. Thanks for your understanding");
        Cart cart = existingBuyer.getCart();
        CartItem cartItem = cartItemService.findByProductIdAndId(existingProduct.getId(), cart.getId());




        if (cartItem != null)
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());

        else cartItem = new CartItem();
        cartItem.setProduct(existingProduct);
        cartItem.setQuantity(request.getQuantity());
        cartItem.setCart(cart);
        cartItem.setPrice(existingProduct.getPrice());
        cart.getItems().add(cartItem);
        cart.setProductId(request.getProductId());
        cart.setBuyerId(existingBuyer.getId());

        cartItemService.save(cartItem);
        cartService.save(cart);


        existingProduct.setQuantity(existingProduct.getQuantity() - request.getQuantity());
        productService.saveProduct(existingProduct);

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart.getItems()) {
            total = total.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        cart.setTotal(total);
        cartService.save(cart);

        AddToCartResponse response = new AddToCartResponse();
        response.setResponse("Product successfully added to the cart");
        return response;
    }

    private static BuyerRegistrationResponse buildResponse() {
        BuyerRegistrationResponse response = new BuyerRegistrationResponse();
        response.setMessage("Registration successful");
        return response;
    }

    private static Buyer createBuyer(BuyerRegistrationRequest request) {
        Buyer buyer = new Buyer();
        buyer.setEmail(request.getEmail());
        buyer.setName(request.getName());
        buyer.setAddress(request.getAddress());
        buyer.setPhoneNumber(request.getPhoneNumber());
        return buyer;
    }
}
