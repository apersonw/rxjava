package top.rxjava.apikit.tool.info;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author happy
 */
public class PackageInfo<T extends CommonClassInfo> {

    private final Multimap<String, T> multimap = Multimaps.newListMultimap(
            new ConcurrentSkipListMap<>(), CopyOnWriteArrayList::new
    );

    public void add(String packageName, T t) {
        multimap.put(packageName, t);
    }

    public Collection<T> getValues() {
        return multimap.values();
    }

    public static void main(String[] args) {
        ListMultimap<String, Object> multimap = Multimaps.newListMultimap(new ConcurrentSkipListMap<>(), CopyOnWriteArrayList::new);
        multimap.put("hello", "hi");
        multimap.put("hello", "hia");
        multimap.put("hello", "hi");
        System.out.println(multimap.values());
    }
}
