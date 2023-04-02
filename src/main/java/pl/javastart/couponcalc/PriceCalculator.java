package pl.javastart.couponcalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PriceCalculator {

    private static final int ONE_COUPON = 1;

    public double calculatePrice(List<Product> products, List<Coupon> coupons) {
        double sumOfPrices = 0;
        double theMostFavorablePrice = Double.MAX_VALUE;
        if (products == null || products.isEmpty()) {
            return 0;
        } else if (coupons == null) {
            return getSumWithoutCoupon(products);
        } else if (coupons.size() >= ONE_COUPON) {
            for (int i = 0; i < coupons.size(); i++) {
                if (coupons.size() == ONE_COUPON) {
                    sumOfPrices = getSumOfPricesForMoreThanOneCoupon(products, coupons, i);
                } else {
                    double sumOfPricesForMoreThanOneCoupon = getSumOfPricesForMoreThanOneCoupon(products, coupons, i);
                    if (sumOfPricesForMoreThanOneCoupon < theMostFavorablePrice) {
                        sumOfPrices = sumOfPricesForMoreThanOneCoupon;
                    }
                }
            }
        }
        return roundedSumOfPrices(sumOfPrices);
    }

    private static double getSumOfPricesForMoreThanOneCoupon(List<Product> products, List<Coupon> coupons, int i) {
        double sumOfPrices = 0;
        for (Product prod : products) {
            double price = prod.getPrice();
            if (prod.getCategory().name().equalsIgnoreCase(coupons.get(i).getCategory().name())) {
                double discountValue = getDiscount(coupons, price);
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

    private static double getDiscount(List<Coupon> coupons, double sumOfPrices) {
        int discount = coupons.stream().mapToInt(Coupon::getDiscountValueInPercents).findFirst().orElse(0);
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