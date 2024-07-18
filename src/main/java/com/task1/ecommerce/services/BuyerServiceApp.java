package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.*;
import com.task1.ecommerce.data.repositories.BuyerRepository;
import com.task1.ecommerce.dtos.requests.AddToCartRequest;
import com.task1.ecommerce.dtos.requests.BuyerRegistrationRequest;
import com.task1.ecommerce.dtos.requests.OrderRequest;
import com.task1.ecommerce.dtos.responses.AddToCartResponse;
import com.task1.ecommerce.dtos.responses.BuyerRegistrationResponse;
import com.task1.ecommerce.dtos.responses.OrderResponse;
import com.task1.ecommerce.exceptions.BuyerExistException;
import com.task1.ecommerce.exceptions.BuyerNotFoundException;
import com.task1.ecommerce.exceptions.EmptyCartException;
import com.task1.ecommerce.exceptions.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BuyerServiceApp implements BuyerService{
    private final BuyerRepository buyerRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final BuyerOrderService buyerOrderService;

    @Override
    public BuyerRegistrationResponse registerBuyer(BuyerRegistrationRequest request) throws BuyerExistException {

        boolean isRegistered = buyerRepository.findByEmail(request.getEmail())!=null;
        if (isRegistered) throw new BuyerExistException("Registration details already taken");
        Buyer buyer = createBuyer(request);
        Cart cart = new Cart();
        cart.setTotalQuantity(0);
        cart.setTotalPrice(BigDecimal.valueOf(0));
        List<BuyerOder> buyerOrders = new ArrayList<>();
        buyerRepository.save(buyer);
        cart.setBuyerId(buyer.getId());


        cartService.save(cart);
        buyer.setBuyerOrders(buyerOrders);
        buyer.setCart(cart);
        buyerRepository.save(buyer);

        return buildResponse();



    }

    @Override
    public AddToCartResponse addProductToCart(AddToCartRequest request) throws BuyerNotFoundException, ProductNotFoundException {
        Buyer existingBuyer = existingBuyer(request);
        Product existingProduct = existingProduct(request);
        checkQuantity(request, existingProduct);
        Cart cart = existingBuyer.getCart();


        List<CartItem> buyersItems = cart.getItems();

        List<CartItem> existingCartItems = cartItemService.findByProductId(existingProduct.getId());

        CartItem cartItem;
        if (!existingCartItems.isEmpty()) {

            cartItem = existingCartItems.get(0);
            int newQuantity = cartItem.getQuantity() + request.getQuantity();
            BigDecimal newPrice = cartItem.getPrice().add(existingProduct.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItem.setQuantity(newQuantity);
            cartItem.setPrice(newPrice);
            cartItemService.save(cartItem);
        } else {

            cartItem = new CartItem();
            cartItem.setBuyerId(existingBuyer.getId());
            cartItem.setProduct(existingProduct);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setCart(cart);
            cartItem.setPrice(existingProduct.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemService.save(cartItem);
        }

        buyersItems.add(cartItem);

        // Update cart totals
        cart.setItems(buyersItems);
        cart.setTotalQuantity(cart.getTotalQuantity() + request.getQuantity());
        cart.setTotalPrice(cart.getTotalPrice().add(existingProduct.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()))));

        cartService.save(cart);


        existingProduct.setQuantity(existingProduct.getQuantity() - request.getQuantity());
        productService.saveProduct(existingProduct);

        existingBuyer.setCart(cart);
        buyerRepository.save(existingBuyer);

        return buildAddToCartResponse();
    }



    private static AddToCartResponse buildAddToCartResponse() {
        AddToCartResponse response = new AddToCartResponse();
        response.setResponse("Product successfully added to the cart");
        return response;
    }

    private static void checkQuantity(AddToCartRequest request, Product existingProduct) throws ProductNotFoundException {
        if (existingProduct.getQuantity() < request.getQuantity()) throw new ProductNotFoundException("The required quantity of product is unavailable at the moment. You can only request for "
                + existingProduct.getQuantity()
                + " ,or less at the moment. Thanks for your understanding");
    }

    private Product existingProduct(AddToCartRequest request) throws ProductNotFoundException {
        Product existingProduct = productService.getProductById(request.getProductId());
        if (existingProduct==null) throw new ProductNotFoundException("Product is unavailable at the moment");
        return existingProduct;
    }

    private Buyer existingBuyer(AddToCartRequest request) throws BuyerNotFoundException {
        Buyer existingBuyer = buyerRepository.findById(request.getBuyerId()).orElse(null);
        if (existingBuyer==null) throw new BuyerNotFoundException("Invalid user details");
        return existingBuyer;
    }

    @Override
    public OrderResponse makeOrder(OrderRequest request) throws BuyerNotFoundException, EmptyCartException {
        Buyer existingBuyer = buyerRepository.findById(request.getBuyerId()).orElse(null);
        if (existingBuyer == null) {
            throw new BuyerNotFoundException("Invalid buyer details");
        }

        Cart cart = existingBuyer.getCart();
        List<CartItem> items = cart.getItems();

        if (items.isEmpty()) throw new EmptyCartException("Cart is empty");

        BuyerOder buyerOrder = new BuyerOder();
        buyerOrder.setBuyerId(request.getBuyerId());
        buyerOrder.setAmount(cart.getTotalPrice());
        buyerOrder.setDeliveryAddress(request.getDeliveryAddress());
        buyerOrder.setPhoneNumber(request.getPhoneNumber());
        buyerOrder.setPreferredDeliveryDate(request.getPreferredDeliveryDate());
        buyerOrder.setOrderDate(LocalDateTime.now());

        buyerOrderService.save(buyerOrder);

        List<BuyerOder> buyerOrders = existingBuyer.getBuyerOrders();
        buyerOrders.add(buyerOrder);
        existingBuyer.setBuyerOrders(buyerOrders);
        buyerRepository.save(existingBuyer);

        existingBuyer.setCart(cart);
        buyerRepository.save(existingBuyer);

        OrderResponse response = new OrderResponse();
        response.setMessage("Order successfully made");
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
        buyer.setPassword(request.getPassword());
        buyer.setAddress(request.getAddress());
        buyer.setPhoneNumber(request.getPhoneNumber());
        return buyer;
    }
}
