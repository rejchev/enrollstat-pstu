package ru.rejchev.pstu.enrollstat.domain.base;

public interface ICastable<T> {
    default <S extends T> S cast(Class<S> clazz) {
        try {
            return clazz.cast(this);
        } catch (ClassCastException ignored) {}
        return null;
    }
}
