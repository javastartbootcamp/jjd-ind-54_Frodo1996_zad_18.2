package pl.javastart.couponcalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PriceCalculator {

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {
        double sumOfPrices = 0;
        double theMostFavorablePrice = Double.MAX_VALUE;
        if (products == null || products.isEmpty()) {
            return 0;
        } else if (coupons == null || coupons.size() == 0) {
            return getSumWithoutCoupon(products);
        }
        for (Coupon coupon : coupons) {
            sumOfPrices = getSumOfTheMostFavorablePrice(products, coupon);
            if (sumOfPrices < theMostFavorablePrice) {
                theMostFavorablePrice = sumOfPrices;
            }
        }
        return roundedSumOfPrices(sumOfPrices);
    }

    private static double getSumOfTheMostFavorablePrice(List<Product> products, Coupon coupon) {
        double sumOfPrices = 0;
        for (Product prod : products) {
            double price = prod.getPrice();
            if (coupon.getCategory() == null || prod.getCategory().name().equalsIgnoreCase(coupon.getCategory().name())) {
                double discountValue = getDiscount(coupon, price);
                double priceAfterDiscount = price - discountValue;
                sumOfPrices += priceAfterDiscount;
            } else {
                sumOfPrices += price;
            }
        }
        return sumOfPrices;
    }

    private static double getSumWithoutCoupon(List<Product> products) {
        double sumOfPrices = 0;
        for (Product prod : products) {
            double price = prod.getPrice();
            sumOfPrices += price;
        }
        return sumOfPrices;
    }

    private static double getDiscount(Coupon coupon, double sumOfPrices) {
        int discount = coupon.getDiscountValueInPercents();
        double calculatedDiscount = (sumOfPrices * discount) / 100;
        return roundedSumOfPrices(calculatedDiscount);
    }

    private static BigDecimal getBigDecimal(double calculatedDiscount) {
        BigDecimal bd = BigDecimal.valueOf(calculatedDiscount);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd;
    }

    private static double roundedSumOfPrices(double sumOfPrices) {
        BigDecimal bd = getBigDecimal(sumOfPrices);
        return bd.doubleValue();
    }
}