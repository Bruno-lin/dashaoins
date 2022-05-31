package com.dsmh.common.trouble;

import com.dsmh.common.trouble.annotation.HttpTrouble;
import com.dsmh.common.trouble.annotation.TroubleCodePrefix;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TeamScorpio
 * @since 2021/01/01
 */
public class TroubleHolder {


    private static final Map<Object, Trouble> CODE_MAP = new ConcurrentHashMap<>();

    public static Trouble get(Object object) {
        if (CODE_MAP.containsKey(object)) {
            return CODE_MAP.get(object);
        }
        try {
            final Enum<?> codeEnum = (Enum<?>) object;
            final Class<?> objectClass = object.getClass();
            final Field field = objectClass.getField(codeEnum.name());
            TroubleCodePrefix troubleCodePrefix = objectClass.getAnnotation(TroubleCodePrefix.class);

            com.dsmh.common.trouble.annotation.Trouble trouble = field
                    .getAnnotation(com.dsmh.common.trouble.annotation.Trouble.class);
            String code = trouble != null ? trouble.code() : String.valueOf(codeEnum.ordinal());
            String codePrefix = troubleCodePrefix != null ? troubleCodePrefix.value() : "";
            String completeCode = codePrefix + code;
            String completeMessage = codeEnum.name();
            if (trouble != null) {
                completeMessage = trouble.message();
            }
            // http support
            HttpTrouble httpTrouble = field.isAnnotationPresent(HttpTrouble.class) ? field.getAnnotation(HttpTrouble.class) : objectClass.getAnnotation(HttpTrouble.class);
            Trouble troubleException = new Trouble(httpTrouble == null ? null : httpTrouble.status(), completeCode, completeMessage);
            CODE_MAP.put(object, troubleException);
            return troubleException;
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }
}
