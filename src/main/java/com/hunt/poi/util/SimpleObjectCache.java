package com.hunt.poi.util;


import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;


public class SimpleObjectCache<K, T> {

    private Map<K, SoftReference<T>> cacheMap ;
    private IGenerateObject<K, T> iGenerateObject;

    /**
     * Instantiates a new Simple object cache.
     *
     * @param iGenerateObject the generate object
     */
    public SimpleObjectCache(IGenerateObject<K, T> iGenerateObject){
        cacheMap = new HashMap<K, SoftReference<T>>();
        this.iGenerateObject = iGenerateObject;
    }

    /**
     * Get object t.
     *
     * @param key the key
     * @return the t
     */
    public T getObject(K key){
        SoftReference<T> softReference = cacheMap.get(key);
        T retObj = null;

        if(softReference == null){
            retObj = this.iGenerateObject.create(key);
            cacheMap.put(key, new SoftReference<T>(retObj));
            return retObj;
        }

        retObj = softReference.get();

        if(retObj == null){
            retObj = this.iGenerateObject.create(key);
            softReference = new SoftReference<T>(retObj);
            cacheMap.put(key, softReference);
            return retObj;
        }

        return retObj;
    }

    /**
     * The interface Generate object.
     *
     * @param <K> the type parameter
     * @param <T> the type parameter
     */
    public interface IGenerateObject<K, T> {
        /**
         * Create t.
         *
         * @param key the key
         * @return the t
         */
        public T create(K key);
    }

}
