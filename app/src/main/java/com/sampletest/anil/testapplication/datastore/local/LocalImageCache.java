package com.sampletest.anil.testapplication.datastore.local;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by H211060 on 8/26/2018.
 */

public class LocalImageCache extends LruCache<String, Bitmap> {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public LocalImageCache(int maxSize) {
        super(maxSize);
    }


    @Override
    protected int sizeOf(String key, Bitmap value) {
        int kbOfBitmap = value.getByteCount() / 1024;
        return kbOfBitmap;
    }
}
