package net.slimevoid.library.util.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Example Usage:
 * 
 * Object privateVariable = ReflectionHelper.getInstance([ClassName].class).getPrivateFieldAtIndex(0);
 * 
 * if (privateVariable instanceof Block) {
 *      Block block = (Block) privateVariable;
 * }
 * 
 * @author Gregory Jones
 * 
 */
public class ReflectionHelper {

    public Class _clazz;

    public ReflectionHelper(Class clazz) {
        this._clazz = clazz;
    }

    public static ReflectionHelper getInstance(Class clazz) {
        if (clazz != null) {
            return new ReflectionHelper(clazz);
        }
        return new ReflectionHelper(ReflectionHelper.class);
    }

    public boolean setFinalStaticFieldAtIndex(int declaredIndex, Object newValue) {
        try {
            Field declaredField = this._clazz.getDeclaredFields()[declaredIndex];
            this.setFinalStatic(declaredField,
                                newValue);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field,
                              field.getModifiers() & ~Modifier.FINAL);

        field.set(null,
                  newValue);
    }

    public Object getPrivateFieldAtIndex(int declaredIndex) {
        try {
            Field declaredField = this._clazz.getDeclaredFields()[declaredIndex];
            return this.getPrivate(declaredField);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object getPrivate(Field field) throws Exception {
        boolean flag = field.isAccessible();
        field.setAccessible(true);
        Object result = field.get(field);
        field.setAccessible(flag);
        return result;
    }

}
