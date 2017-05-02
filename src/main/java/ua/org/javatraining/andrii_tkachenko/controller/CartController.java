package ua.org.javatraining.andrii_tkachenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.org.javatraining.andrii_tkachenko.data.model.CustomOrder;
import ua.org.javatraining.andrii_tkachenko.data.model.Customer;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.data.model.enumeration.OrderType;
import ua.org.javatraining.andrii_tkachenko.data.model.enumeration.VisualizationType;
import ua.org.javatraining.andrii_tkachenko.data.session.Cart;
import ua.org.javatraining.andrii_tkachenko.service.CustomerService;
import ua.org.javatraining.andrii_tkachenko.service.OrderItemService;
import ua.org.javatraining.andrii_tkachenko.service.OrderService;
import ua.org.javatraining.andrii_tkachenko.service.ProductService;
import ua.org.javatraining.andrii_tkachenko.view.CustomerForm;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tkaczenko on 17.04.17.
 */
@Controller
public class CartController {
    private final Cart cart;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @Autowired
    public CartController(Cart cart, CustomerService customerService, ProductService productService,
                          OrderService orderService, OrderItemService orderItemService) {
        this.cart = cart;
        this.customerService = customerService;
        this.productService = productService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @PostMapping("/cart/buyByOne")
    public String buyByOne(@ModelAttribute("customerForm") @Valid CustomerForm customerForm,
                           BindingResult binding, RedirectAttributes attributes, HttpSession session) {
        // Validate form
        boolean isEmpty = cart.getItems().size() == 0;
        if (binding.hasErrors() || isEmpty) {
            if (isEmpty) {
                attributes.addFlashAttribute("mess", "Корзина пуста");
            }
            attributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.customerForm", binding
            );
            attributes.addFlashAttribute("customerForm", customerForm);
            return "redirect:/cart";
        }

        // Check amount and quantity of the product from cart
        for (Map.Entry<Product, Integer> item : cart.getItems().entrySet()) {
            if (item.getKey().getAmount() < item.getValue()) {
                attributes.addFlashAttribute("mess", "Невозможно столько купить");
                attributes.addFlashAttribute("customerForm", customerForm);
                return "redirect:/cart";
            }
        }

        // Save customer
        Customer customer = new Customer();
        customer.setEmail(customerForm.getEmail());
        customer.setPhone(customerForm.getPhone());
        customer.setFirstName(customerForm.getName());
        customerService.save(customer);

        // Save order
        double subTotal = cart.calculateSubTotal();
        double total = cart.calculateTotal(subTotal);
        CustomOrder order = orderService.save(customer, OrderType.IN_PROGRESS.getCode(), customerForm.getAddress());

        // Save ordered products
        Map<Product, Integer> itemMap = new HashMap<>(cart.getItems());
        orderItemService.save(order, itemMap);

        // Submit
        itemMap.entrySet().parallelStream().forEach(e -> {
            Product key = e.getKey();
            key.setAmount(key.getAmount() - e.getValue());
        });
        productService.save(itemMap.keySet());
        order.setStatus(OrderType.SUBMITTED.getCode());
        orderService.save(order);

        attributes.addFlashAttribute("orderId", order.getId());
        cart.clear();

        session.setAttribute("cartSize", cart.sumQuantity());

        return "redirect:/order";
    }

    @GetMapping("/order")
    public String order(Model model) {
        return "order";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        Map<Product, Integer> itemMap = cart.getItems();
        double subTotal = cart.calculateSubTotal();
        double total = cart.calculateTotal(subTotal);
        model.addAttribute("itemMap", itemMap)
                .addAttribute("subTotal", subTotal)
                .addAttribute("total", subTotal == 0 ? 0 : total)
                .addAttribute("estimated", cart.getEstimated());
        if (!model.containsAttribute("customerForm")) {
            model.addAttribute("customerForm", new CustomerForm());
        }
        return "cart";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("sku") String sku,
                            @RequestHeader("referer") String referer, HttpSession session) {
        Product product = productService.findByIdWithVisualization(sku, VisualizationType.ORIGINAL_PICTURE.getCode());
        cart.addItem(product);
        session.setAttribute("cartSize", cart.sumQuantity());
        return "redirect:" + referer;
    }

    @PostMapping("/clearCart")
    public String clearCart(HttpSession session) {
        cart.clear();
        session.setAttribute("cartSize", cart.sumQuantity());
        return "redirect:/cart";
    }

    @PostMapping("/updateCart")
    public String updateCart(@RequestParam("sku") String sku,
                             @RequestParam("quantity") Integer quantity, HttpSession session) {
        cart.updateItem(sku, quantity);
        session.setAttribute("cartSize", cart.sumQuantity());
        return "redirect:/cart";
    }

    @PostMapping("/deleteFromCart")
    public String deleteFromCart(@RequestParam("sku") String sku, HttpSession session) {
        cart.removeItem(sku);
        session.setAttribute("cartSize", cart.sumQuantity());
        return "redirect:/cart";
    }
}
