package pl.javastart.couponcalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PriceCalculator {

    private static final int ONE_COUPON = 1;

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {
        double sumOfPrices = 0;
        Category[] values = Category.values();
        if (products == null) {
            return 0;
        } else if (coupons == null) {
            for (Product prod : products) {
                double price = prod.getPrice();
                sumOfPrices += price;
            }
            return sumOfPrices;
        } else if (coupons.size() <= ONE_COUPON) {
            for (Category value : values) {
                for (Coupon coupon : coupons) {
                    if (value.name().equalsIgnoreCase(coupon.getCategory().toString())) {
                        for (Product prod : products) {
                            double price = prod.getPrice();
                            if (prod.getCategory().name().equalsIgnoreCase(value.name())) {
                                double discountValue = getDiscount(coupons, price);
                                double priceAfterDiscount = price - discountValue;
                                sumOfPrices += priceAfterDiscount;
                            } else {
                                sumOfPrices += price;
                            }
                        }
                        BigDecimal bd = getBigDecimal(sumOfPrices);
                        return bd.doubleValue();
                    }
                }
            }
        } else {
            for (Product prod : products) {
                double price = prod.getPrice();
                sumOfPrices += price;
                for (Coupon coupon : coupons) {
                    if (coupon.getCategory() == null) {
                        return 0;
                    } else if (prod.getCategory().name().equalsIgnoreCase(coupon.getCategory().name())) {
                        sumOfPrices = prod.getPrice() * coupon.getDiscountValueInPercents();
                    }
                }
            }
        }
        return sumOfPrices;
    }

    private static double getDiscount(List<Coupon> coupons, double sumOfPrices) {
        int discount = coupons.stream().mapToInt(Coupon::getDiscountValueInPercents).findFirst().getAsInt();
        double calculatedDiscount = (sumOfPrices * discount) / 100;
        BigDecimal bd = getBigDecimal(calculatedDiscount);
        return bd.doubleValue();
    }

    private static BigDecimal getBigDecimal(double calculatedDiscount) {
        BigDecimal bd = BigDecimal.valueOf(calculatedDiscount);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd;
    }
}