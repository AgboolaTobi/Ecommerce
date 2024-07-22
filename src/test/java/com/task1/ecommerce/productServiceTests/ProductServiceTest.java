package com.task1.ecommerce.productServiceTests;

import com.task1.ecommerce.data.models.Category;
import com.task1.ecommerce.dtos.requests.RemoveProductFromStoreRequest;
import com.task1.ecommerce.dtos.requests.AddProductRequest;
import com.task1.ecommerce.dtos.requests.SearchForProductByCategoryRequest;
import com.task1.ecommerce.dtos.requests.SearchForProductRequest;
import com.task1.ecommerce.dtos.responses.AddProductResponse;
import com.task1.ecommerce.dtos.responses.RemoveProductFromStoreResponse;
import com.task1.ecommerce.dtos.responses.SearchForProductByCategoryResponse;
import com.task1.ecommerce.dtos.responses.SearchForProductResponse;
import com.task1.ecommerce.exceptions.ExistingProductException;
import com.task1.ecommerce.exceptions.ProductNotFoundException;
import com.task1.ecommerce.exceptions.SellerNotFoundException;
import com.task1.ecommerce.exceptions.StoreNotFoundException;
import com.task1.ecommerce.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    public void testThatProductCanBeAddedToSellerStore() throws StoreNotFoundException,
            SellerNotFoundException, ExistingProductException {
        AddProductRequest request = new AddProductRequest();
//        request.setSellerEmail("tobi9tee@gmail.com");
        request.setSellerId(1L);
        request.setStoreId(1L);
        request.setCategory(Category.CANDIES);
        request.setName("Sweets");
        request.setDescription("Creamy sweets for all ages...");
        request.setPrice(new BigDecimal(100));
        request.setQuantity(25);

        AddProductResponse response = productService.addProduct(request);
        System.out.println(response);
        assertThat(response).isNotNull();
    }

    @Test
    public void testThatASellerCanAddMultipleProducts() throws StoreNotFoundException,
            SellerNotFoundException, ExistingProductException {
        AddProductRequest request = new AddProductRequest();
        request.setSellerEmail("tobi9tee@gmail.com");
        request.setStoreId(3L);
        request.setCategory(Category.PHARMACY_ITEMS);
        request.setName("Antibiotics");
        request.setDescription("Antibiotics for all age group. Different brands in stock");
        request.setPrice(new BigDecimal(500));
        request.setQuantity(55);
        AddProductResponse response = productService.addProduct(request);
        System.out.println(response);
        assertThat(response).isNotNull();
    }


    @Test
    public void testThatASellerCanAddMultipleProducts2() throws StoreNotFoundException,
            SellerNotFoundException, ExistingProductException {
        AddProductRequest request = new AddProductRequest();
        request.setSellerEmail("tobi9tee@gmail.com");
        request.setSellerId(1L);
        request.setStoreId(3L);
        request.setCategory(Category.FOODS_ITEMS);
        request.setName("White Garri");
        request.setDescription("Your place for different range and varieties of food items...");
        request.setPrice(new BigDecimal(300));
        request.setQuantity(30);
        AddProductResponse response = productService.addProduct(request);
        System.out.println(response);
        assertThat(response).isNotNull();
    }

    @Test
    public void testThatMultipleSellersCanAddProductWithSameNameToTheirStore() throws StoreNotFoundException, ExistingProductException, SellerNotFoundException {
        AddProductRequest request = new AddProductRequest();
        request.setSellerId(2L);
        request.setStoreId(2L);
        request.setCategory(Category.CANDIES);
        request.setName("Brown biscuits");
        request.setDescription("Your place for different range and varieties of candies...");
        request.setPrice(new BigDecimal(200));
        request.setQuantity(42);
        AddProductResponse response = productService.addProduct(request);
        System.out.println(response);
        assertThat(response).isNotNull();
    }

    @Test
    public void testThatAProductCanBeFoundByProductName(){

        SearchForProductRequest request = new SearchForProductRequest();

        request.setName("Antibiotics");
        SearchForProductResponse response = productService.getProductByProductName(request);

        assertThat(response).isNotNull();

    }

    @Test
    public void testThatProductsCanBeFoundByCategory(){

        SearchForProductByCategoryRequest request = new SearchForProductByCategoryRequest();
        request.setProductCategory(Category.CANDIES);

        SearchForProductByCategoryResponse response = productService.getProductByCategory(request);
        assertThat(response).isNotNull();

    }

    @Test
    public void testThatAProductCanBeDeletedFromStore() throws ProductNotFoundException, SellerNotFoundException {
        RemoveProductFromStoreRequest request = new RemoveProductFromStoreRequest();
        request.setSellerId(1L);
        request.setStoreId(1L);
        request.setProductId(1L);

        RemoveProductFromStoreResponse response = productService.removeProductFromStore(request);
        assertThat(response).isNotNull();

    }

}
