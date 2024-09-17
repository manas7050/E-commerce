package com.jsp.ecommerce.helper;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.jsp.ecommerce.dto.Customer;
import com.jsp.ecommerce.dto.Item;
import com.jsp.ecommerce.dto.ShoppingOrder;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailLogic {

    @Autowired
    JavaMailSender mailSender;

    public void sendOtp(Customer customer) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("kumarmanas920@gmail.com", "E-Commerce");
            helper.setTo(customer.getEmail());
            helper.setSubject("Verify OTP");
            helper.setText("<html><body><h1>Hello " + customer.getName() + "</h1><h2>Your OTP is: " + customer.getOtp()
                    + "</h2><h3>Thanks and Regards</h3></body></html>", true);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(message);
    }

    public void sendOrderConfirmation(Customer customer, ShoppingOrder order) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        StringBuilder emailContent = new StringBuilder();
        emailContent.append("<html><body>");
        emailContent.append("<h1>Order Confirmation</h1>");
        emailContent.append("<p>Dear ").append(customer.getName()).append(",</p>");
        emailContent.append("<p>Thank you for your order! Here are your order details:</p>");
        emailContent.append("<table border='1' cellpadding='5' cellspacing='0'>");
        emailContent.append("<tr><th>Item</th><th>Quantity</th><th>Price</th></tr>");

        for (Item item : order.getItems()) {
            emailContent.append("<tr>");
            emailContent.append("<td>").append(item.getName()).append("</td>");
            emailContent.append("<td>").append(item.getQuantity()).append("</td>");
            emailContent.append("<td>").append(item.getPrice()).append("</td>");
            emailContent.append("</tr>");
        }

        emailContent.append("<tr><td colspan='2'>Total Amount:</td><td>").append(order.getAmount()).append("</td></tr>");
        emailContent.append("</table>");
        emailContent.append("<p>Order ID: ").append(order.getOrder_id()).append("</p>");
        emailContent.append("<p>We will notify you once your order is shipped.</p>");
        emailContent.append("<p>Thank you for shopping with us!</p>");
        emailContent.append("</body></html>");

        try {
            helper.setFrom("kumarmanas920@gmail.com", "E-Commerce");
            helper.setTo(customer.getEmail());
            helper.setSubject("Order Confirmation - " + order.getOrder_id());
            helper.setText(emailContent.toString(), true);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(message);
    }
    

}
