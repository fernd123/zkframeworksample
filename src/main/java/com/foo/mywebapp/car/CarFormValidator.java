package com.foo.mywebapp.car;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
 
public class CarFormValidator extends AbstractValidator {
     
    public void validate(ValidationContext ctx) {
        //all the bean properties
        Map<String,Property> beanProps = ctx.getProperties(ctx.getProperty().getBase());
         
        validateModel(ctx, (String)beanProps.get("model").getValue());
        validateMake(ctx, (String)beanProps.get("make").getValue());
        validateDescription(ctx, (String)beanProps.get("description").getValue());
        validatePrice(ctx, (Integer)beanProps.get("price").getValue());
    }
     
    private void validateModel(ValidationContext ctx, String model) { 
        if(StringUtils.isEmpty(model)) {
            this.addInvalidMessage(ctx, "model", "Value required");
        }
    }
     
    private void validateMake(ValidationContext ctx, String make) {
        if(StringUtils.isEmpty(make)) {
            this.addInvalidMessage(ctx, "make", "Value required");
        }
    }
     
    private void validateDescription(ValidationContext ctx, String description) {
        if(StringUtils.isEmpty(description)) {
            this.addInvalidMessage(ctx, "description", "Must be exist a description");
        }
    }   
     
    private void validatePrice(ValidationContext ctx, Integer price) {
    	if(price == null || price < 0) {
            this.addInvalidMessage(ctx, "price", "The price should be > 0!");
        }
    }     
}