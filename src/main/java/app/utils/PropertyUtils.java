package app.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import java.util.Map;

public final class PropertyUtils {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyUtils.class);

    private PropertyUtils() {
        throw new AssertionError();
    }

    public static BeanWrapper beanWrapper(final Object dataObj) {
        return new BeanWrapperImpl(dataObj);
    }

    /**
     * 讀取物件指定屬性名稱的值
     * @param  dataObj 要讀取值的物件
     * @param  key 屬性名稱
     **/
    public static Object readProperty(final Object dataObj, final String key) {
        if (dataObj == null || StringUtils.isBlank(key)) {
            return dataObj;
        }

        if (dataObj instanceof Map) {
            return readMap((Map<?, ?>) dataObj, key);
        }

        Object property = null;
        try {
            final BeanWrapper objectWrapper = new BeanWrapperImpl(dataObj);
            property = objectWrapper.getPropertyValue(key);
        } catch (final BeansException e) {
            LOG.warn(e.getMessage());
        }
        return property;
    }

    private static Object readMap(final Map<?, ?> map, final String key) {
        if (StringUtils.isBlank(key)) {
            return map;
        }
        final int index = key.indexOf('.');
        if (index > -1) {
            final String mapKey = key.substring(0, index);
            final Object mapValue = map.get(mapKey);
            final String subKey = key.substring(index + 1);
            return readProperty(mapValue, subKey);
        } else {
            return map.get(key);
        }
    }
}
