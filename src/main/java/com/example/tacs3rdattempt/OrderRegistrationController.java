package com.example.tacs3rdattempt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Controller
@ResponseBody
public class OrderRegistrationController {

    @Autowired
    ProductRepository pr;

    @Autowired
    SoldProductRepository spr;

    //http://localhost:4000/registerOrder?productName=taco1&price=45.5&date=2000-10-31T01:30:00.000-05:00 for testing

@GetMapping("/registerOrder")
    public String registerOrder(
            @RequestParam(name="productName", defaultValue = "NO_PRODUCT")
            String productName,
            @RequestParam(name= "price", defaultValue = "-1.0")
            double price
            /*@RequestParam(name= "dateTime", defaultValue = "NO_DATE")
            @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime dateTime*/
){

    Product toCheckIfExists= pr.findByProductName(productName);
    Product toRegister;
    if(toCheckIfExists==null){
        toRegister = new Product ();
        toRegister.setProductName(productName);
        toRegister.setPrice(price);
        pr.save(toRegister);
    }else{
        toRegister=toCheckIfExists;
    }


    Date dateTime = new Date();
    SoldProduct soldProduct = new SoldProduct();
    soldProduct.setLocalDateTime(dateTime);
    soldProduct.setProduct(toRegister);
    spr.save(soldProduct);

    return "SavedProducts";
}


    //example of recieved request: http://localhost:63342/quantitiesReport?startdate=2017-11-08&finishdate=2017-11-17
@GetMapping("/quantitiesReport")
    public ModelAndView getQuantityReport(
            @RequestParam(name= "startdate", defaultValue = "NO_DATE")
            @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime startDate,
            @RequestParam(name= "finishdate", defaultValue = "NO_DATE")
            @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime finishDate
){
    System.out.println(startDate.toString()+finishDate.toString());

    return null;
}

}