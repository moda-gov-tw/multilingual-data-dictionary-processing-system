package app.comparator;

import app.enums.Direction;
import app.utils.PropertyUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public final class ChineseComparator implements Comparator<Object> {

    private final Collator collator = Collator.getInstance(Locale.TRADITIONAL_CHINESE);

    private final Sort sort;

    private final Direction direction;

    private ChineseComparator(final Sort sort, final Direction direction) {
        this.sort = sort;
        this.direction = direction;
    }

    public static ChineseComparator asc() {
        return new ChineseComparator(null, Direction.ASC);
    }

    public static ChineseComparator desc() {
        return new ChineseComparator(null, Direction.DESC);
    }

    public static ChineseComparator sort(final Sort sort) {
        return new ChineseComparator(sort, null);
    }

    @Override
    public int compare(final Object o1, final Object o2) {
        if (sort == null) {
            return this.sortByDirection(o1, o2);
        }
        return this.sort(o1, o2);
    }

    private int sortByDirection(final Object o1, final Object o2) {
        final CompareToBuilder builder = new CompareToBuilder();
        if (Direction.ASC == this.direction) {
            builder.append(o1, o2, collator);
        } else {
            builder.append(o2, o1, collator);
        }
        return builder.toComparison();
    }

    private int sort(final Object o1, final Object o2) {
        final CompareToBuilder builder = new CompareToBuilder();
        sort.stream().forEach(order -> {
            final String property = order.getProperty();
            final Direction direction = order.getDirection();
            final Object obj1 = PropertyUtils.readProperty(o1, property);
            final Object obj2 = PropertyUtils.readProperty(o2, property);

            if (Direction.ASC == direction) {
                if (obj1 instanceof String && obj2 instanceof String) {
                    builder.append(obj1, obj2, collator);
                } else {
                    builder.append(obj1, obj2);
                }
            } else {
                if (obj1 instanceof String && obj2 instanceof String) {
                    builder.append(obj2, obj1, collator);
                } else {
                    builder.append(obj2, obj1);
                }
            }
        });
        return builder.toComparison();
    }
}
