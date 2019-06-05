package org.rxjava.apikit.tool.info;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author happy
 */
public class PackageInfo<T extends ClassInfo> {

    private Multimap<String, T> multimap = Multimaps.newListMultimap(
            new ConcurrentSkipListMap<>(), CopyOnWriteArrayList::new
    );

    public void add(String pack, T t) {
        multimap.put(pack, t);
    }

    public Collection<T> getValues() {
        return multimap.values();
    }
}
