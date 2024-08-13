package com.example.test_final_module4.controller;

import com.example.test_final_module4.model.Orders;
import com.example.test_final_module4.model.Product;
import com.example.test_final_module4.service.IOrderService;
import com.example.test_final_module4.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IProductService productService;

    @GetMapping
    public String showList(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("orderDate").descending());
        Page<Orders> orders = orderService.findAll(pageable);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Orders order : orders) {
            String formattedDate = order.getOrderDate().format(formatter);
            order.setFormattedDate(formattedDate);
        }

        model.addAttribute("orders", orders);
        return "list";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        Optional<Orders> orderOptional = orderService.findById(id);
        List<Product> products = productService.findAll();
        if (orderOptional.isPresent()) {
            model.addAttribute("product", products);
            model.addAttribute("order", orderOptional.get());
            return "update";
        } else {
            model.addAttribute("errorMessage", "Order not found");
            return "error";
        }
    }

    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable Long id, Orders order, RedirectAttributes redirectAttributes) {
        try {
            Optional<Orders> existingOrder = orderService.findById(id);

            if (existingOrder.isPresent()) {
                Orders orderToUpdate = existingOrder.get();

                orderToUpdate.setQuantity(order.getQuantity());


                orderService.save(orderToUpdate);

                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật đơn hàng thành công!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy đơn hàng để cập nhật.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Đã xảy ra lỗi khi cập nhật đơn hàng.");
        }

        return "redirect:/list";
    }
}