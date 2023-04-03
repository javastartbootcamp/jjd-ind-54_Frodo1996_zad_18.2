package pl.javastart.couponcalc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculatorTest {


    @Test
    public void shouldReturnZeroForNoProducts() {
        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        // when
        double result = priceCalculator.calculatePrice(null, null);
        // then
        assertThat(result).isEqualTo(0.);
    }

    @Test
    public void shouldReturnPriceForSingleProductAndNoCoupons() {
        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        // when
        double result = priceCalculator.calculatePrice(products, null);
        // then
        assertThat(result).isEqualTo(5.99);
    }

    @Test
    public void shouldReturnPriceForTwoProductsAndNoCoupons() {
        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Płyn do spryskiwaczy", 8.99, Category.CAR));
        // when
        double result = priceCalculator.calculatePrice(products, null);
        // then
        assertThat(result).isEqualTo(14.98);
    }

    @Test
    public void shouldReturnPriceForSingleProductForCouponWithoutCategory() {
        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(null, 20));
        // when
        double result = priceCalculator.calculatePrice(products, coupons);
        // then
        assertThat(result).isEqualTo(4.79);
    }

    @Test
    public void shouldReturnPriceForTwoProductsForCouponWithoutCategory() {
        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD));
        products.add(new Product("Dżem", 6.99, Category.FOOD));
        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(null, 20));
        // when
        double result = priceCalculator.calculatePrice(products, coupons);
        // then
        assertThat(result).isEqualTo(10.38);
    }

    @Test
    public void shouldReturnPriceForSingleProductAndOneCoupon() {
        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Lampa", 5.99, Category.HOME));
        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.HOME, 20));
        // when
        double result = priceCalculator.calculatePrice(products, coupons);
        // then
        assertThat(result).isEqualTo(4.79);
    }

    @Test
    public void shouldReturnPriceForTwoProductsAndOneCoupon() {
        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD)); //1.2
        products.add(new Product("Dżem", 6.99, Category.FOOD)); //1.4
        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 20));
        // when
        double result = priceCalculator.calculatePrice(products, coupons);
        // then
        assertThat(result).isEqualTo(10.38);
    }

    @Test
    public void shouldReturnPriceForThreeProductsAndIgnoreOtherCategory() {
        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD)); //1.2
        products.add(new Product("Dżem", 6.99, Category.FOOD)); //1.4
        products.add(new Product("Jack Daniels", 79.99, Category.ENTERTAINMENT)); //1.4
        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 20));
        // when
        double result = priceCalculator.calculatePrice(products, coupons);
        // then
        assertThat(result).isEqualTo(90.37);
    }

    @Test
    public void shouldReturnTheMostFavorablePriceForMultipleCoupons() {
        // given
        PriceCalculator priceCalculator = new PriceCalculator();
        List<Product> products = new ArrayList<>();
        products.add(new Product("Masło", 5.99, Category.FOOD)); //1.2 -- 5.99 - 1.20 = 4.79zl
        products.add(new Product("Dżem", 6.99, Category.FOOD)); //1.4 -- 6.99 - 1.40 = 5.59zl
        products.add(new Product("Jack Daniels 1.5l", 169.99, Category.ENTERTAINMENT)); //169.99 -- 135,99zł
        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon(Category.FOOD, 20));
        coupons.add(new Coupon(Category.ENTERTAINMENT, 20));
        // when
        double result = priceCalculator.calculatePrice(products, coupons);
        // then
        //180,37zł  //  148,97
        assertThat(result).isEqualTo(148.97);
    }
}