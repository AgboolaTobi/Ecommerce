package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.*;
import com.task1.ecommerce.data.repositories.BuyerRepository;
import com.task1.ecommerce.dtos.requests.*;
import com.task1.ecommerce.dtos.responses.*;
import com.task1.ecommerce.exceptions.*;
import com.task1.ecommerce.utils.Verification;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
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
    public BuyerRegistrationResponse registerBuyer(BuyerRegistrationRequest request) throws BuyerExistException, BuyerRegistrationException {

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

        for (CartItem existingCartItem : buyersItems) {
            if (existingCartItem.getProduct().getId().equals(existingProduct.getId())) {
                // Item already exists in cart, update quantity
                existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
                existingCartItem.setPrice(existingProduct.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
                cartItemService.save(existingCartItem);
                cart.setTotalQuantity(cart.getTotalQuantity() + request.getQuantity());
                cart.setTotalPrice(cart.getTotalPrice().add(existingProduct.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()))));
                cartService.save(cart);
                existingProduct.setQuantity(existingProduct.getQuantity() - request.getQuantity());
                productService.saveProduct(existingProduct);
                return buildAddToCartResponse();
            }
        }

        // Item not found in cart, create new cart item
        CartItem cartItem = new CartItem();
        cartItem.setProduct(existingProduct);
        cartItem.setQuantity(request.getQuantity());
        cartItem.setCart(cart);
        cartItem.setPrice(existingProduct.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        cartItem.setBuyer(existingBuyer);
        cartItemService.save(cartItem);

        buyersItems.add(cartItem);
        cart.setItems(buyersItems);

        // Update total price
        BigDecimal currentTotalPrice = BigDecimal.ZERO;
        for (CartItem item : buyersItems) {
            currentTotalPrice = currentTotalPrice.add(item.getPrice());
        }
        cart.setTotalPrice(currentTotalPrice);

        cart.setTotalQuantity(cart.getTotalQuantity() + request.getQuantity());
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

    @Override
    public Buyer findByBuyerId(Long buyerId) {
        return buyerRepository.findById(buyerId).orElse(null);
    }

    @Override
    public void save(Buyer existingBuyer) {
        buyerRepository.save(existingBuyer);
    }

    @Override
    public BuyerLoginResponse buyerLogin(BuyerLoginRequest request) throws InvalidCredentialsException {
        Buyer existingBuyer = buyerRepository.findByEmail(request.getEmail());
        validateCredentials(request, existingBuyer);
        existingBuyer.setLogin(true);
        buyerRepository.save(existingBuyer);
        return buildLoginResponse();

    }

    @Override
    public BuyerLogoutResponse logoutBuyer(BuyerLogoutRequest request) throws BuyerNotFoundException {
        Buyer existingBuyer = buyerRepository.findById(request.getId()).orElse(null);
        if (existingBuyer == null)throw new BuyerNotFoundException("Invalid logout credentials: " + request.getId());
        existingBuyer.setLogin(false);
        buyerRepository.save(existingBuyer);

        return buildBuyerLogoutResponse();
    }

    @Override
    public RemoveProductFromCartResponse removeProductFromCart(RemoveProductFromCartRequest request) throws BuyerNotFoundException, CartItemException {
        Buyer existingBuyer = getExistingBuyer(request);
        Cart cart = existingBuyer.getCart();
        List<CartItem> items = cart.getItems();
        List<CartItem> cartItems = cartItemService.findByProductId(request.getProductId());
        if (cartItems == null) throw new CartItemException("Cart is empty. Kindly add products to your cart");
        CartItem targetCartItem = cartItems.get(0);
        int quantityToRemove = request.getQuantity();

            Product product = productService.getProductById(request.getProductId());
            product.setQuantity(product.getQuantity() + quantityToRemove);
            productService.saveProduct(product);

            if (quantityToRemove >= targetCartItem.getQuantity())
                items.remove(targetCartItem);
            cartItemService.deleteProduct(targetCartItem);

            targetCartItem.setQuantity(targetCartItem.getQuantity() - quantityToRemove);
            cartItemService.save(targetCartItem);

            cart.setTotalQuantity(cart.getTotalQuantity() - quantityToRemove);
            cart.setTotalPrice(cart.getTotalPrice().subtract(targetCartItem.getPrice().multiply(BigDecimal.valueOf(quantityToRemove))));
            cartService.save(cart);


            RemoveProductFromCartResponse response = new RemoveProductFromCartResponse();
            response.setMessage("Product removed from cart successfully");

            return response;



    }

    private Buyer getExistingBuyer(RemoveProductFromCartRequest request) throws BuyerNotFoundException {
        Buyer existingBuyer = buyerRepository.findById(request.getBuyerId()).orElse(null);
        if (existingBuyer == null) throw new BuyerNotFoundException("Buyer not found");
        return existingBuyer;
    }

    private static BuyerLogoutResponse buildBuyerLogoutResponse() {
        BuyerLogoutResponse response = new BuyerLogoutResponse();
        response.setMessage("Logout successful");
        return response;
    }

    private static BuyerLoginResponse buildLoginResponse() {
        BuyerLoginResponse response = new BuyerLoginResponse();
        response.setMessage("Login successful");
        return response;
    }

    private static void validateCredentials(BuyerLoginRequest request, Buyer existingBuyer) throws InvalidCredentialsException {
        if (existingBuyer == null)throw new InvalidCredentialsException("Invalid email or password");
        boolean isValidPassword = BCrypt.checkpw(request.getPassword(), existingBuyer.getPassword());
        if (!isValidPassword) throw new InvalidCredentialsException("Invalid email or password");
    }


    private static BuyerRegistrationResponse buildResponse() {
        BuyerRegistrationResponse response = new BuyerRegistrationResponse();
        response.setMessage("Registration successful");
        return response;
    }

    private static Buyer createBuyer(BuyerRegistrationRequest request) throws BuyerRegistrationException {
        verifyDetails(request);

        Buyer buyer = new Buyer();

        buyer.setEmail(request.getEmail());
        buyer.setName(request.getName());
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        buyer.setPassword(hashedPassword);
        buyer.setAddress(request.getAddress());
        buyer.setPhoneNumber(request.getPhoneNumber());
        buyer.setCreatedAt(LocalDateTime.now());
        return buyer;
    }

    private static void verifyDetails(BuyerRegistrationRequest request) throws BuyerRegistrationException {
        if (!Verification.verifyRegistrationEmail(request.getEmail())) throw new BuyerRegistrationException("Invalid email format. Email should be in the format of example@gmail.com");

        if (!Verification.verifyRegistrationName(request.getName()))
            throw new BuyerRegistrationException("Invalid name format: " + request.getName() + ". Name should contain letters and spaces only");

        if (!Verification.verifyPhoneNumber(request.getPhoneNumber()))
            throw new BuyerRegistrationException("Invalid phone number format. Phone number should contain 11 digits only, without whitespaces");

        if (!Verification.verifyRegistrationPassword(request.getPassword()))
            throw new BuyerRegistrationException(""" 
            Invalid password format. Ensure password has:
            - A minimum length of 8 characters and a maximum length of 16 characters
            - No whitespaces
            - A mix of:
              - Lowercase letters (a-z)
              - Uppercase letters (A-Z)
              - Digits (0-9)
              - Special characters (@, $, !, %, *, ?, &)
        """);
    }
}
