package app.comparator;

import app.enums.Direction;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Sort implements Streamable<Order>, Serializable {

    public static final Direction DEFAULT_DIRECTION;
    private final List<Order> orders;

    protected Sort(List<Order> orders) {
        this.orders = orders;
    }

    private Sort(Direction direction, List<String> properties) {
        if (properties != null && !properties.isEmpty()) {
            this.orders = (List)properties.stream().map((it) -> {
                return new Order(direction, it);
            }).collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("You have to provide at least one property to sort by");
        }
    }

    public static Sort by(String... properties) {
        return new Sort(DEFAULT_DIRECTION, Arrays.asList(properties));
    }

    public static Sort by(List<Order> orders) {
        return new Sort(orders);
    }

    public static Sort by(Order... orders) {
        return new Sort(Arrays.asList(orders));
    }

    public static Sort by(Direction direction, String... properties) {
        Assert.notNull(direction, "Direction must not be null");
        Assert.notNull(properties, "Properties must not be null");
        Assert.isTrue(properties.length > 0, "At least one property must be given");
        return by((List)Arrays.stream(properties).map((it) -> {
            return new Order(direction, it);
        }).collect(Collectors.toList()));
    }

    public Sort descending() {
        return this.withDirection(Direction.DESC);
    }

    public Sort ascending() {
        return this.withDirection(Direction.ASC);
    }

    public boolean isSorted() {
        return !this.isEmpty();
    }

    public boolean isEmpty() {
        return this.orders.isEmpty();
    }

    public boolean isUnsorted() {
        return !this.isSorted();
    }

    public Sort and(Sort sort) {
        Assert.notNull(sort, "Sort must not be null");
        ArrayList<Order> these = new ArrayList(this.toList());
        Iterator var3 = sort.iterator();

        while(var3.hasNext()) {
            Order order = (Order)var3.next();
            these.add(order);
        }

        return by((List)these);
    }

    @Nullable
    public Order getOrderFor(String property) {
        Iterator var2 = this.iterator();

        Order order;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            order = (Order)var2.next();
        } while(!order.getProperty().equals(property));

        return order;
    }

    public Iterator<Order> iterator() {
        return this.orders.iterator();
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Sort)) {
            return false;
        } else {
            Sort that = (Sort)obj;
            return this.toList().equals(that.toList());
        }
    }

    public int hashCode() {
        int result = 17;
        result = 31 * result + this.orders.hashCode();
        return result;
    }

    public String toString() {
        return this.isEmpty() ? "UNSORTED" : StringUtils.collectionToCommaDelimitedString(this.orders);
    }

    private Sort withDirection(Direction direction) {
        return by((List)this.stream().map((it) -> {
            return it.with(direction);
        }).collect(Collectors.toList()));
    }

    static {
        DEFAULT_DIRECTION = Direction.ASC;
    }
}
