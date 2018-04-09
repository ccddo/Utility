package uod.gla.util;

import java.lang.reflect.*;
import java.util.*;

/**
 * This class provides utility methods, such as element (object) searching, for
 * collections and arrays. See the method listing for more details.
 *
 * @author Chi Onyekaba [c.onyekaba@dundee.ac.uk]
 * @version 1.2
 * @since April 4, 2018
 */
public class CollectionUtils {

    /**
     * This method provides string search capabilities to objects. It is used to
     * search an array of objects for the presence of a string key. The search
     * is not case-sensitive. When searching an object, the method(s) specified
     * by the memberNames argument are invoked and the key string is searched
     * for in the value that is returned from the invocation of the method. If
     * it is a field, the key string is searched for in value of the field.
     * Please note that only public members can be searched and any method(s)
     * specified must not require a parameter to execute otherwise it will be
     * ignored. If no member name is specified, the key string will be searched
     * for in the value that is returned from the invocation of the toString()
     * method of the object.
     *
     * @param <T> The array type.
     * @param key The string to search for.
     * @param objects The array of objects to be searched.
     * @param memberNames The name(s) of the object's methods or fields which
     * are to be searched.
     * @return An array of objects that contain the search key.
     */
    public static <T> T[] search(String key, T[] objects, String... memberNames) {
        return search(key, objects, false, memberNames);
    }

    /**
     * This method provides string search capabilities to objects. It is used to
     * search an array of objects for the presence of a string key. When
     * searching an object, the method(s) specified by the memberNames argument
     * is invoked and the key string is searched for in the value that is
     * returned from the invocation of the method. If it is a field, the key
     * string is searched for in value of the field. Please note that only
     * public members can be searched and any method(s) specified must not
     * require a parameter to execute otherwise it will be ignored. If no member
     * name is specified, the key string will be searched for in the value that
     * is returned from the invocation of the toString() method of the object.
     *
     * @param <T> The array type.
     * @param key The string to search for.
     * @param objects The array of objects to be searched.
     * @param caseSensitive The case sensitivity of the search. If a case
     * sensitive search is required, this parameter should be set to true,
     * otherwise, it should be set to false.
     * @param memberNames The name(s) of the object methods or fields which are
     * to be searched.
     * @return An array of objects that contain the search key.
     */
    public static <T> T[] search(String key, T[] objects,
            boolean caseSensitive, String... memberNames) {
        return search(key, Arrays.asList(objects), caseSensitive, memberNames)
                .toArray(Arrays.copyOf(objects, 0));
    }

    /**
     * This method provides object search capabilities to objects. It is used to
     * search an array of objects for the presence of an object in its members.
     * When searching an object, the method(s) specified by the memberNames
     * argument is invoked and the object key is searched for in the value that
     * is returned from the invocation of the method. The search is done by
     * checking both objects for equality i.e. the object key and the object
     * returned from the invocation of the member method. If it is a field, the
     * object key is compared with the value of the field for equality. Please
     * note that only public members can be searched and any method(s) specified
     * must not require a parameter to execute otherwise it will be ignored. If
     * no member name is specified, an empty array is returned.
     *
     * @param <T> The array type.
     * @param key The object to search for.
     * @param objects The array of objects to be searched.
     * @param memberNames The name(s) of the object methods or fields which are
     * to be searched.
     * @return An array of objects that match the object key.
     */
    public static <T> T[] search(Object key, T[] objects, String... memberNames) {
        return search((Object) key, Arrays.asList(objects), true, memberNames)
                .toArray(Arrays.copyOf(objects, 0));
    }

    /**
     * This method provides string search capabilities to objects. It is used to
     * search a collection of objects for the presence of a string key. The
     * search is not case-sensitive. When searching an object, the method(s)
     * specified by the memberNames argument are invoked and the key string is
     * searched for in the value that is returned from the invocation of the
     * method. If it is a field, the key string is searched for in value of the
     * field. Please note that only public members can be searched and any
     * method(s) specified must not require a parameter to execute otherwise it
     * will be ignored. If no member name is specified, the key string will be
     * searched for in the value that is returned from the invocation of the
     * toString() method of the object.
     *
     * @param <T> The collection type.
     * @param key The string to search for.
     * @param objects The collection of objects to be searched.
     * @param memberNames The name(s) of the object's methods or fields which
     * are to be searched.
     * @return A collection of objects that contain the search key.
     */
    public static <T> Collection<T> search(String key, Collection<T> objects,
            String... memberNames) {
        return search(key, objects, false, memberNames);
    }

    /**
     * This method provides string search capabilities to objects. It is used to
     * search a collection of objects for the presence of a string key. When
     * searching an object, the method(s) specified by the memberNames argument
     * is invoked and the key string is searched for in the value that is
     * returned from the invocation of the method. If it is a field, the key
     * string is searched for in value of the field. Please note that only
     * public members can be searched and any method(s) specified must not
     * require a parameter to execute otherwise it will be ignored. If no member
     * name is specified, the key string will be searched for in the value that
     * is returned from the invocation of the toString() method of the object.
     *
     * @param <T> The collection type.
     * @param key The string to search for.
     * @param objects The collection of objects to be searched.
     * @param caseSensitive The case sensitivity of the search. If a case
     * sensitive search is required, this parameter should be set to true,
     * otherwise, it should be set to false.
     * @param memberNames The name(s) of the object methods or fields which are
     * to be searched.
     * @return A collection of objects that contain the search key.
     */
    public static <T> Collection<T> search(String key, Collection<T> objects,
            boolean caseSensitive, String... memberNames) {
        return search((Object) key, objects, caseSensitive, memberNames);
    }

    /**
     * This method provides object search capabilities to objects. It is used to
     * search a collection of objects for the presence of an object in its
     * members. When searching an object, the method(s) specified by the
     * memberNames argument is invoked and the object key is searched for in the
     * value that is returned from the invocation of the method. The search is
     * done by checking both objects for equality i.e. the object key and the
     * object returned from the invocation of the member method. If it is a
     * field, the object key is compared with the value of the field for
     * equality. Please note that only public members can be searched and any
     * method(s) specified must not require a parameter to execute otherwise it
     * will be ignored. If no member name is specified, an empty collection is
     * returned.
     *
     * @param <T> The collection type.
     * @param key The object to search for.
     * @param objects The collection of objects to be searched.
     * @param memberNames The name(s) of the object methods or fields which are
     * to be searched.
     * @return A collection of objects that match the object key.
     */
    public static <T> Collection<T> search(Object key, Collection<T> objects,
            String... memberNames) {
        return search((Object) key, objects, true, memberNames);
    }

    private static <T> Collection<T> search(Object key, Collection<T> objects,
            boolean caseSensitive, String... memberNames) {
        Collection<T> results = new ArrayList<>();
        if (key instanceof String && memberNames.length == 0) {
            memberNames = new String[]{"toString"};
        } else if (memberNames.length == 0) {
            return results;
        }
        List<Member> members = new ArrayList<>();
        search:
        for (T obj : objects) {
            members.clear();
            members.addAll(Arrays.asList(obj.getClass().getFields()));
            members.addAll(Arrays.asList(obj.getClass().getMethods()));
            for (Member member : members) {
                String name = member.getName();
                for (String memberName : memberNames) {
                    if (name.equals(memberName)
                            && matches(key, obj, member, caseSensitive)) {
                        results.add(obj);
                        continue search;
                    }
                }
            }
        }
        return results;
    }

    private static boolean matches(Object key, Object obj,
            Member member, boolean caseSensitive) {
        if (obj == null || key == null) {
            return false;
        }
        Object data = null;
        if (member instanceof Method) {
            Method method = (Method) member;
            if (method.getParameterCount() != 0) {
                return false;
            }
            try {
                data = method.invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                return false;
            }
        } else if (member instanceof Field) {
            Field field = (Field) member;
            try {
                data = field.get(obj);
            } catch (IllegalAccessException ex) {
                return false;
            }
        }
        if (data == null) {
            return false;
        }
        if (key instanceof String) {
            String text;
            String k = (String) key;
            if (caseSensitive) {
                text = data.toString();
            } else {
                text = data.toString().toLowerCase();
                k = k.toLowerCase();
            }
            return text.contains(k);
        } else {
            return data.equals(key);
        }
    }

    /**
     * This method is used to provide an intersection of two or more arrays. An
     * intersection of two or more arrays is an array which contains the
     * elements that are present in all the arrays being intersected.
     *
     * @param <T> The array type
     * @param arrays The arrays to be intersected
     * @return The intersection of the arrays
     * @throws IllegalArgumentException if no array argument is provided
     */
    public static <T> T[] intersect(T[]... arrays)
            throws IllegalArgumentException {
        if (arrays.length == 0) {
            throw new IllegalArgumentException("No arguments provided!");
        } else if (arrays.length == 1) {
            return arrays[0];
        }
        List<T> list = new ArrayList<>(Arrays.asList(arrays[0]));
        for (T[] array : arrays) {
            list.retainAll(Arrays.asList(array));
        }
        return list.toArray(Arrays.copyOf(arrays[0], 0));
    }

    /**
     * This method is used to provide an intersection of two or more
     * collections. An intersection of two or more collections is a collection
     * which contains the elements that are present in all the collections being
     * intersected.
     *
     * @param <T> The collection type
     * @param collections The collections to be intersected
     * @return The intersection of the collections
     * @throws IllegalArgumentException if no collection argument is provided
     */
    public static <T> Collection<T> intersect(Collection<T>... collections)
            throws IllegalArgumentException {
        if (collections.length == 0) {
            throw new IllegalArgumentException("No arguments provided!");
        } else if (collections.length == 1) {
            return collections[0];
        }
        Collection<T> collection = new ArrayList<>(collections[0]);
        for (Collection<T> c : collections) {
            collection.retainAll(c);
        }
        return collection;
    }

}
