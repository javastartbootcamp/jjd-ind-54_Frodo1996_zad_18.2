package pl.javastart.couponcalc;

import java.util.List;

public class PriceCalculator {

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {
        double sumOfPrices = 0;
        double theMostFavorablePrice = Double.MAX_VALUE;
        if (products == null || products.isEmpty()) {
            return 0;
        } else if (coupons == null || coupons.size() == 0) {
            return calculateSumWithoutCoupon(products);
        }
        for (Coupon coupon : coupons) {
            sumOfPrices = calculateSumOfTheMostFavorablePrice(products, coupon);
            if (sumOfPrices < theMostFavorablePrice) {
                theMostFavorablePrice = sumOfPrices;
            }
        }
        return roundTo2DecimalPlaces(sumOfPrices);
    }

    private double calculateSumOfTheMostFavorablePrice(List<Product> products, Coupon coupon) {
        double sumOfPrices = 0;
        for (Product prod : products) {
            if (prod.getCategory() == coupon.getCategory()) {
                double price = prod.getPrice();
                sumOfPrices += calculateDiscountedPrice(coupon, price);
            } else {
                sumOfPrices += prod.getPrice();
            }
        }
        return sumOfPrices;
    }

    private double calculateSumWithoutCoupon(List<Product> products) {
        double sumOfPrices = 0;
        for (Product prod : products) {
            double price = prod.getPrice();
            sumOfPrices += price;
        }
        return sumOfPrices;
    }

    private double calculateDiscountedPrice(Coupon coupon, double value) {
        int percent = 100 - coupon.getDiscountValueInPercents();
        return value * percent / 100.;
    }

    public double roundTo2DecimalPlaces(double value) {
        return Math.round(value * 100) / 100.;
    }
}