package cz.instance.gtt.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class GenericType<T> {

	private final Type type;
	private volatile Constructor<T> constructor;

	protected GenericType() {
		Type superClass = getClass().getGenericSuperclass();
		if (superClass instanceof Class<?>) {
			throw new IllegalArgumentException("GenericType constructed without generic type parameter");
		}
		type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
	}

	public Type getType() {
		return type;
	}

	public T newInstance() throws Exception {
		return getConstructor().newInstance();
	}

	public Constructor<T> getConstructor() throws SecurityException, NoSuchMethodException {
		constructor = constructor != null ? constructor : getClazz().getConstructor();
		return constructor;
	}

	@SuppressWarnings("unchecked")
	public Class<T> getClazz() {
		Class<?> clazz = isParameterized() ? (Class<?>) ((ParameterizedType) type).getRawType() : (Class<?>) type;
		return (Class<T>) clazz;
	}
	
	public Type getRawType() {
		if(isParameterized())
			return ((ParameterizedType) type).getActualTypeArguments()[0];
		else
			throw new RuntimeException("Cannot get RawType of non ParameterizedType !");
	}
	
	public boolean isParameterized() {
		return type instanceof Class<?> ? false : true;
	}
}
