package com.aos.AOSBE.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.Authorities;
import com.aos.AOSBE.Entity.Base_products;
import com.aos.AOSBE.Entity.Cancels;
import com.aos.AOSBE.Entity.Cart_items;
import com.aos.AOSBE.Entity.Catalogs;
import com.aos.AOSBE.Entity.Categories;
import com.aos.AOSBE.Entity.Cost_histories;
import com.aos.AOSBE.Entity.Coupons;
import com.aos.AOSBE.Entity.Employees;
import com.aos.AOSBE.Entity.Extend_infors;
import com.aos.AOSBE.Entity.Extend_values;
import com.aos.AOSBE.Entity.Order_items;
import com.aos.AOSBE.Entity.Orders;
import com.aos.AOSBE.Entity.Payment_method;
import com.aos.AOSBE.Entity.Price_histories;
import com.aos.AOSBE.Entity.Product_images;
import com.aos.AOSBE.Entity.Product_items;
import com.aos.AOSBE.Entity.PromotionProduct;
import com.aos.AOSBE.Entity.Promotions;
import com.aos.AOSBE.Entity.Purchase_order_items;
import com.aos.AOSBE.Entity.Purchase_orders;
import com.aos.AOSBE.Entity.Returns;
import com.aos.AOSBE.Entity.Reviews;
import com.aos.AOSBE.Entity.Roles;
import com.aos.AOSBE.Entity.Shipping_method;
import com.aos.AOSBE.Entity.Suppliers;
import com.aos.AOSBE.Entity.Trace_xe_action_map;
import com.aos.AOSBE.Entity.Trace_xe_event_map;
import com.aos.AOSBE.Entity.User_addresses;
import com.aos.AOSBE.Entity.User_infors;
import com.aos.AOSBE.Entity.Variant_values;
import com.aos.AOSBE.Entity.Variants;
import com.aos.AOSBE.Entity.Work_histories;
import com.aos.AOSBE.Repository.AccountsRepository;
import com.aos.AOSBE.Repository.AuthoritiesRepository;
import com.aos.AOSBE.Repository.Base_productsRepository;
import com.aos.AOSBE.Repository.CancelsRepository;
import com.aos.AOSBE.Repository.Cart_itemsRepository;
import com.aos.AOSBE.Repository.CatalogsRepository;
import com.aos.AOSBE.Repository.CategoriesRepository;
import com.aos.AOSBE.Repository.Cost_historiesRepository;
import com.aos.AOSBE.Repository.CouponsRepository;
import com.aos.AOSBE.Repository.EmployeesRepository;
import com.aos.AOSBE.Repository.Extend_inforsRepository;
import com.aos.AOSBE.Repository.Extend_valuesRepository;
import com.aos.AOSBE.Repository.Order_itemsRepository;
import com.aos.AOSBE.Repository.OrdersRepository;
import com.aos.AOSBE.Repository.Payment_methodRepository;
import com.aos.AOSBE.Repository.Price_historiesRepository;
import com.aos.AOSBE.Repository.Product_imagesRepository;
import com.aos.AOSBE.Repository.Product_itemsRepository;
import com.aos.AOSBE.Repository.PromotionProductRepository;
import com.aos.AOSBE.Repository.PromotionsRepository;
import com.aos.AOSBE.Repository.Purchase_order_itemsRepository;
import com.aos.AOSBE.Repository.Purchase_ordersRepository;
import com.aos.AOSBE.Repository.ReturnsRepository;
import com.aos.AOSBE.Repository.ReviewsRepository;
import com.aos.AOSBE.Repository.RolesRepository;
import com.aos.AOSBE.Repository.Shipping_methodRepository;
import com.aos.AOSBE.Repository.SuppliersRepository;
import com.aos.AOSBE.Repository.Trace_xe_action_mapRepository;
import com.aos.AOSBE.Repository.Trace_xe_event_mapRepository;
import com.aos.AOSBE.Repository.User_addressesRepository;
import com.aos.AOSBE.Repository.User_inforsRepository;
import com.aos.AOSBE.Repository.Variant_valuesRepository;
import com.aos.AOSBE.Repository.VariantsRepository;
import com.aos.AOSBE.Repository.Work_historiesRepository;

public class Service {

    @Autowired
    private AccountsRepository accountsRepository;

    public List<Accounts> accountsFindAll() {
        return accountsRepository.findAll();
    }

    public Accounts accountsSave(Accounts accounts) {
        return accountsRepository.save(accounts);
    }

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    public List<Authorities> authoritiesFindAll() {
        return authoritiesRepository.findAll();
    }

    public Authorities authoritiesSave(Authorities authorities) {
        return authoritiesRepository.save(authorities);
    }

    @Autowired
    private Base_productsRepository base_productsRepository;

    public List<Base_products> base_productsFindAll() {
        return base_productsRepository.findAll();
    }

    public Base_products base_productsSave(Base_products base_products) {
        return base_productsRepository.save(base_products);
    }

    @Autowired
    private CancelsRepository cancelsRepository;

    public List<Cancels> cancelsFindAll() {
        return cancelsRepository.findAll();
    }

    public Cancels cancelsSave(Cancels cancels) {
        return cancelsRepository.save(cancels);
    }

    @Autowired
    private Cart_itemsRepository cart_itemsRepository;

    public List<Cart_items> cart_itemsFindAll() {
        return cart_itemsRepository.findAll();
    }

    public Cart_items cart_itemsSave(Cart_items cart_items) {
        return cart_itemsRepository.save(cart_items);
    }

    @Autowired
    private CatalogsRepository catalogsRepository;

    public List<Catalogs> catalogsFindAll() {
        return catalogsRepository.findAll();
    }

    public Catalogs catalogsSave(Catalogs catalogs) {
        return catalogsRepository.save(catalogs);
    }

    @Autowired
    private CategoriesRepository categoriesRepository;

    public List<Categories> categoriesFindAll() {
        return categoriesRepository.findAll();
    }

    public Categories categoriesSave(Categories categories) {
        return categoriesRepository.save(categories);
    }

    @Autowired
    private Cost_historiesRepository cost_historiesRepository;

    public List<Cost_histories> cost_historiesFindAll() {
        return cost_historiesRepository.findAll();
    }

    public Cost_histories cost_historiesSave(Cost_histories cost_histories) {
        return cost_historiesRepository.save(cost_histories);
    }

    @Autowired
    private CouponsRepository couponsRepository;

    public List<Coupons> couponsFindAll() {
        return couponsRepository.findAll();
    }

    public Coupons couponsSave(Coupons coupons) {
        return couponsRepository.save(coupons);
    }

    @Autowired
    private EmployeesRepository employeesRepository;

    public List<Employees> employeesFindAll() {
        return employeesRepository.findAll();
    }

    public Employees employeesSave(Employees employees) {
        return employeesRepository.save(employees);
    }

    @Autowired
    private Extend_inforsRepository extend_inforsRepository;

    public List<Extend_infors> extend_inforsFindAll() {
        return extend_inforsRepository.findAll();
    }

    public Extend_infors extend_inforsSave(Extend_infors extend_infors) {
        return extend_inforsRepository.save(extend_infors);
    }

    @Autowired
    private Extend_valuesRepository extend_valuesRepository;

    public List<Extend_values> extend_valuesFindAll() {
        return extend_valuesRepository.findAll();
    }

    public Extend_values extend_valuesSave(Extend_values extend_values) {
        return extend_valuesRepository.save(extend_values);
    }

    @Autowired
    private Order_itemsRepository order_itemsRepository;

    public List<Order_items> order_itemsFindAll() {
        return order_itemsRepository.findAll();
    }

    public Order_items order_itemsSave(Order_items order_items) {
        return order_itemsRepository.save(order_items);
    }

    @Autowired
    private OrdersRepository ordersRepository;

    public List<Orders> ordersFindAll() {
        return ordersRepository.findAll();
    }

    public Orders ordersSave(Orders orders) {
        return ordersRepository.save(orders);
    }

    @Autowired
    private Payment_methodRepository payment_methodRepository;

    public List<Payment_method> payment_methodFindAll() {
        return payment_methodRepository.findAll();
    }

    public Payment_method payment_methodSave(Payment_method payment_method) {
        return payment_methodRepository.save(payment_method);
    }

    @Autowired
    private Price_historiesRepository price_historiesRepository;

    public List<Price_histories> price_historiesFindAll() {
        return price_historiesRepository.findAll();
    }

    public Price_histories price_historiesSave(Price_histories price_histories) {
        return price_historiesRepository.save(price_histories);
    }

    @Autowired
    private Product_imagesRepository product_imagesRepository;

    public List<Product_images> product_imagesFindAll() {
        return product_imagesRepository.findAll();
    }

    public Product_images product_imagesSave(Product_images product_images) {
        return product_imagesRepository.save(product_images);
    }

    @Autowired
    private Product_itemsRepository product_itemsRepository;

    public List<Product_items> product_itemsFindAll() {
        return product_itemsRepository.findAll();
    }

    public Product_items product_itemsSave(Product_items product_items) {
        return product_itemsRepository.save(product_items);
    }

    @Autowired
    private PromotionProductRepository promotionProductRepository;

    public List<PromotionProduct> promotionProductFindAll() {
        return promotionProductRepository.findAll();
    }

    public PromotionProduct promotionProductSave(PromotionProduct promotionProduct) {
        return promotionProductRepository.save(promotionProduct);
    }

    @Autowired
    private PromotionsRepository promotionsRepository;

    public List<Promotions> promotionsFindAll() {
        return promotionsRepository.findAll();
    }

    public Promotions promotionsSave(Promotions promotions) {
        return promotionsRepository.save(promotions);
    }

    @Autowired
    private Purchase_order_itemsRepository purchase_order_itemsRepository;

    public List<Purchase_order_items> purchase_order_itemsFindAll() {
        return purchase_order_itemsRepository.findAll();
    }

    public Purchase_order_items purchase_order_itemsSave(Purchase_order_items purchase_order_items) {
        return purchase_order_itemsRepository.save(purchase_order_items);
    }

    @Autowired
    private Purchase_ordersRepository purchase_ordersRepository;

    public List<Purchase_orders> purchase_ordersFindAll() {
        return purchase_ordersRepository.findAll();
    }

    public Purchase_orders purchase_ordersSave(Purchase_orders purchase_orders) {
        return purchase_ordersRepository.save(purchase_orders);
    }

    @Autowired
    private ReturnsRepository returnsRepository;

    public List<Returns> returnsFindAll() {
        return returnsRepository.findAll();
    }

    public Returns returnsSave(Returns returns) {
        return returnsRepository.save(returns);
    }

    @Autowired
    private ReviewsRepository reviewsRepository;

    public List<Reviews> reviewsFindAll() {
        return reviewsRepository.findAll();
    }

    public Reviews reviewsSave(Reviews reviews) {
        return reviewsRepository.save(reviews);
    }

    @Autowired
    private RolesRepository rolesRepository;

    public List<Roles> rolesFindAll() {
        return rolesRepository.findAll();
    }

    public Roles rolesSave(Roles roles) {
        return rolesRepository.save(roles);
    }

    @Autowired
    private Shipping_methodRepository shipping_methodRepository;

    public List<Shipping_method> shipping_methodFindAll() {
        return shipping_methodRepository.findAll();
    }

    public Shipping_method shipping_methodSave(Shipping_method shipping_method) {
        return shipping_methodRepository.save(shipping_method);
    }

    @Autowired
    private SuppliersRepository suppliersRepository;

    public List<Suppliers> suppliersFindAll() {
        return suppliersRepository.findAll();
    }

    public Suppliers suppliersSave(Suppliers suppliers) {
        return suppliersRepository.save(suppliers);
    }

    @Autowired
    private User_addressesRepository user_addressesRepository;

    public List<User_addresses> user_addressesFindAll() {
        return user_addressesRepository.findAll();
    }

    public User_addresses user_addressesSave(User_addresses user_addresses) {
        return user_addressesRepository.save(user_addresses);
    }

    @Autowired
    private User_inforsRepository user_inforsRepository;

    public List<User_infors> user_inforsFindAll() {
        return user_inforsRepository.findAll();
    }

    public User_infors user_inforsSave(User_infors user_infors) {
        return user_inforsRepository.save(user_infors);
    }

    @Autowired
    private Variant_valuesRepository variant_valuesRepository;

    public List<Variant_values> variant_valuesFindAll() {
        return variant_valuesRepository.findAll();
    }

    public Variant_values variant_valuesSave(Variant_values variant_values) {
        return variant_valuesRepository.save(variant_values);
    }

    @Autowired
    private VariantsRepository variantsRepository;

    public List<Variants> variantsFindAll() {
        return variantsRepository.findAll();
    }

    public Variants variantsSave(Variants variants) {
        return variantsRepository.save(variants);
    }

    @Autowired
    private Work_historiesRepository work_historiesRepository;

    public List<Work_histories> work_historiesFindAll() {
        return work_historiesRepository.findAll();
    }

    public Work_histories work_historiesSave(Work_histories work_histories) {
        return work_historiesRepository.save(work_histories);
    }

    @Autowired
    private Trace_xe_action_mapRepository trace_xe_action_mapRepository;

    public List<Trace_xe_action_map> trace_xe_action_mapFindAll() {
        return trace_xe_action_mapRepository.findAll();
    }

    public Trace_xe_action_map trace_xe_action_mapSave(Trace_xe_action_map trace_xe_action_map) {
        return trace_xe_action_mapRepository.save(trace_xe_action_map);
    }

    @Autowired
    private Trace_xe_event_mapRepository trace_xe_event_mapRepository;

    public List<Trace_xe_event_map> trace_xe_event_mapFindAll() {
        return trace_xe_event_mapRepository.findAll();
    }

    public Trace_xe_event_map trace_xe_event_mapSave(Trace_xe_event_map trace_xe_event_map) {
        return trace_xe_event_mapRepository.save(trace_xe_event_map);
    }

}
