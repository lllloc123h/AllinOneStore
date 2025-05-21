package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class Service {



  @Autowired
    private AccountsRepository accountsRepository;

    public List<Accounts> accountsFindAll() {
        return accountsRepository.findAll();
    }

    public Accounts accountsSave(Accounts accounts) {
        return accountsRepository.save(accounts);
    }

    public Optional<Accounts> accountsFindById(String id) {
        return accountsRepository.findById(id);
    }

    public void accountsDeleteById(String id) {
        accountsRepository.deleteById(id);
    }


  @Autowired
    private AuthoritiesRepository authoritiesRepository;

    public List<Authorities> authoritiesFindAll() {
        return authoritiesRepository.findAll();
    }

    public Authorities authoritiesSave(Authorities authorities) {
        return authoritiesRepository.save(authorities);
    }

    public Optional<Authorities> authoritiesFindById(String id) {
        return authoritiesRepository.findById(id);
    }

    public void authoritiesDeleteById(String id) {
        authoritiesRepository.deleteById(id);
    }


  @Autowired
    private Base_productsRepository base_productsRepository;

    public List<Base_products> base_productsFindAll() {
        return base_productsRepository.findAll();
    }

    public Base_products base_productsSave(Base_products base_products) {
        return base_productsRepository.save(base_products);
    }

    public Optional<Base_products> base_productsFindById(int id) {
        return base_productsRepository.findById(id);
    }

    public void base_productsDeleteById(int id) {
        base_productsRepository.deleteById(id);
    }


  @Autowired
    private CancelsRepository cancelsRepository;

    public List<Cancels> cancelsFindAll() {
        return cancelsRepository.findAll();
    }

    public Cancels cancelsSave(Cancels cancels) {
        return cancelsRepository.save(cancels);
    }

    public Optional<Cancels> cancelsFindById(int id) {
        return cancelsRepository.findById(id);
    }

    public void cancelsDeleteById(int id) {
        cancelsRepository.deleteById(id);
    }


  @Autowired
    private Cart_itemsRepository cart_itemsRepository;

    public List<Cart_items> cart_itemsFindAll() {
        return cart_itemsRepository.findAll();
    }

    public Cart_items cart_itemsSave(Cart_items cart_items) {
        return cart_itemsRepository.save(cart_items);
    }

    public Optional<Cart_items> cart_itemsFindById(int id) {
        return cart_itemsRepository.findById(id);
    }

    public void cart_itemsDeleteById(int id) {
        cart_itemsRepository.deleteById(id);
    }


  @Autowired
    private CatalogsRepository catalogsRepository;

    public List<Catalogs> catalogsFindAll() {
        return catalogsRepository.findAll();
    }

    public Catalogs catalogsSave(Catalogs catalogs) {
        return catalogsRepository.save(catalogs);
    }

    public Optional<Catalogs> catalogsFindById(String id) {
        return catalogsRepository.findById(id);
    }

    public void catalogsDeleteById(String id) {
        catalogsRepository.deleteById(id);
    }


  @Autowired
    private CategoriesRepository categoriesRepository;

    public List<Categories> categoriesFindAll() {
        return categoriesRepository.findAll();
    }

    public Categories categoriesSave(Categories categories) {
        return categoriesRepository.save(categories);
    }

    public Optional<Categories> categoriesFindById(String id) {
        return categoriesRepository.findById(id);
    }

    public void categoriesDeleteById(String id) {
        categoriesRepository.deleteById(id);
    }


  @Autowired
    private Cost_historiesRepository cost_historiesRepository;

    public List<Cost_histories> cost_historiesFindAll() {
        return cost_historiesRepository.findAll();
    }

    public Cost_histories cost_historiesSave(Cost_histories cost_histories) {
        return cost_historiesRepository.save(cost_histories);
    }

    public Optional<Cost_histories> cost_historiesFindById(int id) {
        return cost_historiesRepository.findById(id);
    }

    public void cost_historiesDeleteById(int id) {
        cost_historiesRepository.deleteById(id);
    }


  @Autowired
    private CouponsRepository couponsRepository;

    public List<Coupons> couponsFindAll() {
        return couponsRepository.findAll();
    }

    public Coupons couponsSave(Coupons coupons) {
        return couponsRepository.save(coupons);
    }

    public Optional<Coupons> couponsFindById(int id) {
        return couponsRepository.findById(id);
    }

    public void couponsDeleteById(int id) {
        couponsRepository.deleteById(id);
    }


  @Autowired
    private EmployeesRepository employeesRepository;

    public List<Employees> employeesFindAll() {
        return employeesRepository.findAll();
    }

    public Employees employeesSave(Employees employees) {
        return employeesRepository.save(employees);
    }

    public Optional<Employees> employeesFindById(String id) {
        return employeesRepository.findById(id);
    }

    public void employeesDeleteById(String id) {
        employeesRepository.deleteById(id);
    }


  @Autowired
    private Extend_inforsRepository extend_inforsRepository;

    public List<Extend_infors> extend_inforsFindAll() {
        return extend_inforsRepository.findAll();
    }

    public Extend_infors extend_inforsSave(Extend_infors extend_infors) {
        return extend_inforsRepository.save(extend_infors);
    }

    public Optional<Extend_infors> extend_inforsFindById(String id) {
        return extend_inforsRepository.findById(id);
    }

    public void extend_inforsDeleteById(String id) {
        extend_inforsRepository.deleteById(id);
    }


  @Autowired
    private Extend_valuesRepository extend_valuesRepository;

    public List<Extend_values> extend_valuesFindAll() {
        return extend_valuesRepository.findAll();
    }

    public Extend_values extend_valuesSave(Extend_values extend_values) {
        return extend_valuesRepository.save(extend_values);
    }

    public Optional<Extend_values> extend_valuesFindById(int id) {
        return extend_valuesRepository.findById(id);
    }

    public void extend_valuesDeleteById(int id) {
        extend_valuesRepository.deleteById(id);
    }


  @Autowired
    private Order_itemsRepository order_itemsRepository;

    public List<Order_items> order_itemsFindAll() {
        return order_itemsRepository.findAll();
    }

    public Order_items order_itemsSave(Order_items order_items) {
        return order_itemsRepository.save(order_items);
    }

    public Optional<Order_items> order_itemsFindById(int id) {
        return order_itemsRepository.findById(id);
    }

    public void order_itemsDeleteById(int id) {
        order_itemsRepository.deleteById(id);
    }


  @Autowired
    private OrdersRepository ordersRepository;

    public List<Orders> ordersFindAll() {
        return ordersRepository.findAll();
    }

    public Orders ordersSave(Orders orders) {
        return ordersRepository.save(orders);
    }

    public Optional<Orders> ordersFindById(int id) {
        return ordersRepository.findById(id);
    }

    public void ordersDeleteById(int id) {
        ordersRepository.deleteById(id);
    }


  @Autowired
    private Payment_methodRepository payment_methodRepository;

    public List<Payment_method> payment_methodFindAll() {
        return payment_methodRepository.findAll();
    }

    public Payment_method payment_methodSave(Payment_method payment_method) {
        return payment_methodRepository.save(payment_method);
    }

    public Optional<Payment_method> payment_methodFindById(int id) {
        return payment_methodRepository.findById(id);
    }

    public void payment_methodDeleteById(int id) {
        payment_methodRepository.deleteById(id);
    }


  @Autowired
    private Price_historiesRepository price_historiesRepository;

    public List<Price_histories> price_historiesFindAll() {
        return price_historiesRepository.findAll();
    }

    public Price_histories price_historiesSave(Price_histories price_histories) {
        return price_historiesRepository.save(price_histories);
    }

    public Optional<Price_histories> price_historiesFindById(int id) {
        return price_historiesRepository.findById(id);
    }

    public void price_historiesDeleteById(int id) {
        price_historiesRepository.deleteById(id);
    }


  @Autowired
    private Product_imagesRepository product_imagesRepository;

    public List<Product_images> product_imagesFindAll() {
        return product_imagesRepository.findAll();
    }

    public Product_images product_imagesSave(Product_images product_images) {
        return product_imagesRepository.save(product_images);
    }

    public Optional<Product_images> product_imagesFindById(int id) {
        return product_imagesRepository.findById(id);
    }

    public void product_imagesDeleteById(int id) {
        product_imagesRepository.deleteById(id);
    }


  @Autowired
    private Product_itemsRepository product_itemsRepository;

    public List<Product_items> product_itemsFindAll() {
        return product_itemsRepository.findAll();
    }

    public Product_items product_itemsSave(Product_items product_items) {
        return product_itemsRepository.save(product_items);
    }

    public Optional<Product_items> product_itemsFindById(int id) {
        return product_itemsRepository.findById(id);
    }

    public void product_itemsDeleteById(int id) {
        product_itemsRepository.deleteById(id);
    }


  @Autowired
    private PromotionProductRepository promotionProductRepository;

    public List<PromotionProduct> promotionProductFindAll() {
        return promotionProductRepository.findAll();
    }

    public PromotionProduct promotionProductSave(PromotionProduct promotionProduct) {
        return promotionProductRepository.save(promotionProduct);
    }

    public Optional<PromotionProduct> promotionProductFindById(int id) {
        return promotionProductRepository.findById(id);
    }

    public void promotionProductDeleteById(int id) {
        promotionProductRepository.deleteById(id);
    }


  @Autowired
    private PromotionsRepository promotionsRepository;

    public List<Promotions> promotionsFindAll() {
        return promotionsRepository.findAll();
    }

    public Promotions promotionsSave(Promotions promotions) {
        return promotionsRepository.save(promotions);
    }

    public Optional<Promotions> promotionsFindById(int id) {
        return promotionsRepository.findById(id);
    }

    public void promotionsDeleteById(int id) {
        promotionsRepository.deleteById(id);
    }


  @Autowired
    private Purchase_order_itemsRepository purchase_order_itemsRepository;

    public List<Purchase_order_items> purchase_order_itemsFindAll() {
        return purchase_order_itemsRepository.findAll();
    }

    public Purchase_order_items purchase_order_itemsSave(Purchase_order_items purchase_order_items) {
        return purchase_order_itemsRepository.save(purchase_order_items);
    }

    public Optional<Purchase_order_items> purchase_order_itemsFindById(int id) {
        return purchase_order_itemsRepository.findById(id);
    }

    public void purchase_order_itemsDeleteById(int id) {
        purchase_order_itemsRepository.deleteById(id);
    }


  @Autowired
    private Purchase_ordersRepository purchase_ordersRepository;

    public List<Purchase_orders> purchase_ordersFindAll() {
        return purchase_ordersRepository.findAll();
    }

    public Purchase_orders purchase_ordersSave(Purchase_orders purchase_orders) {
        return purchase_ordersRepository.save(purchase_orders);
    }

    public Optional<Purchase_orders> purchase_ordersFindById(int id) {
        return purchase_ordersRepository.findById(id);
    }

    public void purchase_ordersDeleteById(int id) {
        purchase_ordersRepository.deleteById(id);
    }


  @Autowired
    private ReturnsRepository returnsRepository;

    public List<Returns> returnsFindAll() {
        return returnsRepository.findAll();
    }

    public Returns returnsSave(Returns returns) {
        return returnsRepository.save(returns);
    }

    public Optional<Returns> returnsFindById(int id) {
        return returnsRepository.findById(id);
    }

    public void returnsDeleteById(int id) {
        returnsRepository.deleteById(id);
    }


  @Autowired
    private ReviewsRepository reviewsRepository;

    public List<Reviews> reviewsFindAll() {
        return reviewsRepository.findAll();
    }

    public Reviews reviewsSave(Reviews reviews) {
        return reviewsRepository.save(reviews);
    }

    public Optional<Reviews> reviewsFindById(int id) {
        return reviewsRepository.findById(id);
    }

    public void reviewsDeleteById(int id) {
        reviewsRepository.deleteById(id);
    }


  @Autowired
    private RolesRepository rolesRepository;

    public List<Roles> rolesFindAll() {
        return rolesRepository.findAll();
    }

    public Roles rolesSave(Roles roles) {
        return rolesRepository.save(roles);
    }

    public Optional<Roles> rolesFindById(int id) {
        return rolesRepository.findById(id);
    }

    public void rolesDeleteById(int id) {
        rolesRepository.deleteById(id);
    }


  @Autowired
    private Shipping_methodRepository shipping_methodRepository;

    public List<Shipping_method> shipping_methodFindAll() {
        return shipping_methodRepository.findAll();
    }

    public Shipping_method shipping_methodSave(Shipping_method shipping_method) {
        return shipping_methodRepository.save(shipping_method);
    }

    public Optional<Shipping_method> shipping_methodFindById(int id) {
        return shipping_methodRepository.findById(id);
    }

    public void shipping_methodDeleteById(int id) {
        shipping_methodRepository.deleteById(id);
    }


  @Autowired
    private SuppliersRepository suppliersRepository;

    public List<Suppliers> suppliersFindAll() {
        return suppliersRepository.findAll();
    }

    public Suppliers suppliersSave(Suppliers suppliers) {
        return suppliersRepository.save(suppliers);
    }

    public Optional<Suppliers> suppliersFindById(int id) {
        return suppliersRepository.findById(id);
    }

    public void suppliersDeleteById(int id) {
        suppliersRepository.deleteById(id);
    }


  @Autowired
    private User_addressesRepository user_addressesRepository;

    public List<User_addresses> user_addressesFindAll() {
        return user_addressesRepository.findAll();
    }

    public User_addresses user_addressesSave(User_addresses user_addresses) {
        return user_addressesRepository.save(user_addresses);
    }

    public Optional<User_addresses> user_addressesFindById(int id) {
        return user_addressesRepository.findById(id);
    }

    public void user_addressesDeleteById(int id) {
        user_addressesRepository.deleteById(id);
    }


  @Autowired
    private User_inforsRepository user_inforsRepository;

    public List<User_infors> user_inforsFindAll() {
        return user_inforsRepository.findAll();
    }

    public User_infors user_inforsSave(User_infors user_infors) {
        return user_inforsRepository.save(user_infors);
    }

    public Optional<User_infors> user_inforsFindById(String id) {
        return user_inforsRepository.findById(id);
    }

    public void user_inforsDeleteById(String id) {
        user_inforsRepository.deleteById(id);
    }


  @Autowired
    private Variant_valuesRepository variant_valuesRepository;

    public List<Variant_values> variant_valuesFindAll() {
        return variant_valuesRepository.findAll();
    }

    public Variant_values variant_valuesSave(Variant_values variant_values) {
        return variant_valuesRepository.save(variant_values);
    }

    public Optional<Variant_values> variant_valuesFindById(int id) {
        return variant_valuesRepository.findById(id);
    }

    public void variant_valuesDeleteById(int id) {
        variant_valuesRepository.deleteById(id);
    }


  @Autowired
    private VariantsRepository variantsRepository;

    public List<Variants> variantsFindAll() {
        return variantsRepository.findAll();
    }

    public Variants variantsSave(Variants variants) {
        return variantsRepository.save(variants);
    }

    public Optional<Variants> variantsFindById(String id) {
        return variantsRepository.findById(id);
    }

    public void variantsDeleteById(String id) {
        variantsRepository.deleteById(id);
    }


  @Autowired
    private Work_historiesRepository work_historiesRepository;

    public List<Work_histories> work_historiesFindAll() {
        return work_historiesRepository.findAll();
    }

    public Work_histories work_historiesSave(Work_histories work_histories) {
        return work_historiesRepository.save(work_histories);
    }

    public Optional<Work_histories> work_historiesFindById(int id) {
        return work_historiesRepository.findById(id);
    }

    public void work_historiesDeleteById(int id) {
        work_historiesRepository.deleteById(id);
    }


  @Autowired
    private Trace_xe_action_mapRepository trace_xe_action_mapRepository;

    public List<Trace_xe_action_map> trace_xe_action_mapFindAll() {
        return trace_xe_action_mapRepository.findAll();
    }

    public Trace_xe_action_map trace_xe_action_mapSave(Trace_xe_action_map trace_xe_action_map) {
        return trace_xe_action_mapRepository.save(trace_xe_action_map);
    }

    public Optional<Trace_xe_action_map> trace_xe_action_mapFindById(String id) {
        return trace_xe_action_mapRepository.findById(id);
    }

    public void trace_xe_action_mapDeleteById(String id) {
        trace_xe_action_mapRepository.deleteById(id);
    }


  @Autowired
    private Trace_xe_event_mapRepository trace_xe_event_mapRepository;

    public List<Trace_xe_event_map> trace_xe_event_mapFindAll() {
        return trace_xe_event_mapRepository.findAll();
    }

    public Trace_xe_event_map trace_xe_event_mapSave(Trace_xe_event_map trace_xe_event_map) {
        return trace_xe_event_mapRepository.save(trace_xe_event_map);
    }

    public Optional<Trace_xe_event_map> trace_xe_event_mapFindById(String id) {
        return trace_xe_event_mapRepository.findById(id);
    }

    public void trace_xe_event_mapDeleteById(String id) {
        trace_xe_event_mapRepository.deleteById(id);
    }

}
