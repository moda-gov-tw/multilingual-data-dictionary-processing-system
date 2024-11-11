package app.comparator;

import app.enums.Direction;
import app.enums.NullHandling;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.io.Serializable;

public class Order implements Serializable {

    private static final boolean DEFAULT_IGNORE_CASE = false;
    private static final NullHandling DEFAULT_NULL_HANDLING;
    private final Direction direction;
    private final String property;
    private final boolean ignoreCase;
    private final NullHandling nullHandling;

    public Order(@Nullable Direction direction, String property) {
        this(direction, property, false, DEFAULT_NULL_HANDLING);
    }

    public Order(@Nullable Direction direction, String property, NullHandling nullHandlingHint) {
        this(direction, property, false, nullHandlingHint);
    }

    public static Order by(String property) {
        return new Order(Sort.DEFAULT_DIRECTION, property);
    }

    public static Order asc(String property) {
        return new Order(Direction.ASC, property, DEFAULT_NULL_HANDLING);
    }

    public static Order desc(String property) {
        return new Order(Direction.DESC, property, DEFAULT_NULL_HANDLING);
    }

    private Order(@Nullable Direction direction, String property, boolean ignoreCase, NullHandling nullHandling) {
        if (!StringUtils.hasText(property)) {
            throw new IllegalArgumentException("Property must not be null or empty");
        } else {
            this.direction = direction == null ? Sort.DEFAULT_DIRECTION : direction;
            this.property = property;
            this.ignoreCase = ignoreCase;
            this.nullHandling = nullHandling;
        }
    }

    public Direction getDirection() {
        return this.direction;
    }

    public String getProperty() {
        return this.property;
    }

    public boolean isAscending() {
        return Direction.isAscending(this.direction);
    }

    public boolean isDescending() {
        return Direction.isDescending(this.direction);
    }

    public boolean isIgnoreCase() {
        return this.ignoreCase;
    }

    public Order with(Direction direction) {
        return new Order(direction, this.property, this.ignoreCase, this.nullHandling);
    }

    public Order withProperty(String property) {
        return new Order(this.direction, property, this.ignoreCase, this.nullHandling);
    }

    public Sort withProperties(String... properties) {
        return Sort.by(this.direction, properties);
    }

    public Order ignoreCase() {
        return new Order(this.direction, this.property, true, this.nullHandling);
    }

    public Order with(NullHandling nullHandling) {
        return new Order(this.direction, this.property, this.ignoreCase, nullHandling);
    }

    public Order nullsFirst() {
        return this.with(NullHandling.NULLS_FIRST);
    }

    public Order nullsLast() {
        return this.with(NullHandling.NULLS_LAST);
    }

    public Order nullsNative() {
        return this.with(NullHandling.NATIVE);
    }

    public NullHandling getNullHandling() {
        return this.nullHandling;
    }

    public int hashCode() {
        int result = 17;
        result = 31 * result + this.direction.hashCode();
        result = 31 * result + this.property.hashCode();
        result = 31 * result + (this.ignoreCase ? 1 : 0);
        result = 31 * result + this.nullHandling.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Order)) {
            return false;
        } else {
            Order that = (Order)obj;
            return this.direction.equals(that.direction) && this.property.equals(that.property) && this.ignoreCase == that.ignoreCase && this.nullHandling.equals(that.nullHandling);
        }
    }

    public String toString() {
        String result = String.format("%s: %s", this.property, this.direction);
        if (!NullHandling.NATIVE.equals(this.nullHandling)) {
            result = result + ", " + this.nullHandling;
        }

        if (this.ignoreCase) {
            result = result + ", ignoring case";
        }

        return result;
    }

    static {
        DEFAULT_NULL_HANDLING = NullHandling.NATIVE;
    }
}
