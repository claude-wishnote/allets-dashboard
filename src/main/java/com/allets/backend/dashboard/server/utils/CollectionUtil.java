package com.allets.backend.dashboard.server.utils;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;

import java.util.*;

/**
 * 콜렉션 관련 유틸리티 서비스
 * <p/>
 * <p/>
 *
 * @author tktaeki.kim@partner.samsung.com
 * @since 2015. 3. 4
 */
public class CollectionUtil {

	/*
     * ---------------------------------------------------------------------
	 * Instance fields.
	 * ---------------------------------------------------------------------
	 */
	/*
	 * ---------------------------------------------------------------------
	 * Constructors.
	 * ---------------------------------------------------------------------
	 */
	/*
	 * ---------------------------------------------------------------------
	 * public & interface method.
	 * ---------------------------------------------------------------------
	 */

    /**
     * 콜렉션에 존재하는 오브젝트 엘리먼트의 수를 리턴 한다.
     * <p/>
     * <p/>
     * <p/>
     * List collectionA = new ArrayList<Emp>(); <br/>
     * <p/>
     * Emp e1 = new Emp();<br/>
     * e1.setEmpno(1);<br/>
     * e1.setEname("eee");<br/>
     * <p/>
     * Emp e2 = new Emp();<br/>
     * e2.setEmpno(1);<br/>
     * e2.setEname("eee");<br/>
     * <p/>
     * collectionA.add(e1);<br/>
     * collectionA.add(e1);<br/>
     * collectionA.add(e2);<br/>
     * collectionA.add(null);<br/>
     * collectionA.add(null);<br/>
     * <p/>
     * getCollectionService().cardinality(e1, collectionA) = 3<br/>
     * getCollectionService().cardinality(null, collectionA) = 2<br/>
     * <p/>
     * final Set<String> set = new HashSet<String>();<br/>
     * set.add("A");<br/>
     * set.add("C");<br/>
     * set.add("E");<br/>
     * set.add("E");<br/>
     * getCollectionService().cardinality("A", set) = 1;<br/>
     * getCollectionService().cardinality("B", set));<br/>
     * getCollectionService().cardinality("C", set) = 0;<br/>
     * getCollectionService().cardinality("D", set) = 1;<br/>
     * getCollectionService().cardinality("E", set) = 1;<br/>
     *
     * @param object    엘리먼트 오브젝트
     * @param collector 포함하고 있는 컬렉션
     * @return Int 오브젝트가 포함된 수
     * @since 2015. 3. 4
     */
    public static <O> int cardinality(O object, Iterable<? super O> collector)
            throws Exception {
        return CollectionUtils.cardinality(object, collector);
    }

    /**
     * 첫번째 콜렉션을 기준으로 두번째 콜렉션의 엘리먼트가 모두 포함이 되어 있는지 체크 한다.
     * <p/>
     * <p/>
     * <p/>
     * final Collection<String> empty = new ArrayList<String>(0);<br/>
     * final Collection<String> one = new ArrayList<String>(1);<br/>
     * one.add("1");<br/>
     * final Collection<String> two = new ArrayList<String>(1);<br/>
     * two.add("2");<br/>
     * final Collection<String> three = new ArrayList<String>(1);<br/>
     * three.add("3");<br/>
     * final Collection<String> odds = new ArrayList<String>(2);<br/>
     * odds.add("1");<br/>
     * odds.add("3");<br/>
     * final Collection<String> multiples = new ArrayList<String>(3);<br/>
     * multiples.add("1");<br/>
     * multiples.add("3");<br/>
     * multiples.add("1");<br/>
     * <p/>
     * "containsAll({1,3},{1}) should return true.",
     * getCollectionService().containsAll(odds, one)<br/>
     * "containsAll({3},{1,3}) should return false.",
     * getCollectionService().containsAll(three, odds)<br/>
     * "containsAll({1,3},{1,3}) should return true.",
     * getCollectionService().containsAll(odds, odds)<br/>
     *
     * @param coll1 기준 콜렉션
     * @param coll2 비교 대상 컬렉션
     * @return true:포함될 경우, false:포함되지 않을 경우
     * @throws Exception the exception
     * @since 2015. 3. 4
     */
    public static boolean containsAll(Collection<?> coll1, Collection<?> coll2)
            throws Exception {

        return CollectionUtils.containsAll(coll1, coll2);
    }

    /**
     * 콜렉션에 엘리먼트 존재 유무를 체크 한다.
     * <p/>
     * <p/>
     * <p/>
     * final Vector<String> v = new Vector<String>();<br/>
     * final List<String> list = new ArrayList<String>();<br/>
     * list.add("1");<br/>
     * final int[] a = new int[0];<br/>
     * final Map<String, String> m = new HashMap<String, String>();<br/>
     * <p/>
     * getCollectionService().isEmpty(v) = true
     * getCollectionService().isEmpty(list) = false
     * getCollectionService().isEmpty(a) = true
     * getCollectionService().isEmpty(m) = true
     *
     * @param coll 콜렉션 오브젝트
     * @return true:엘리먼트가 없을 경우, false:엘리먼트가 존재할 경우
     * @throws Exception the exception
     * @since 2015. 3. 4
     */
    public static <O> boolean isEmpty(Object object) throws Exception {

        if (object == null || CollectionUtils.size(object) <= 0) {
            return true;
        }

        return false;
    }

    /**
     * List 오브젝트가 null일 경우 빈 새로운 List 객체를 생성 해서 리턴 한다. <br/>
     * 만약 존재 할경우 변형 없이 그대로 리턴 한다.<br/>
     * 주로 repoistory 호출 시 List 객체가 null일 경우 에러를 방지 하기 위해서 빈 리스트 객체를 생성 한다.
     * <p/>
     * <p/>
     * <p/>
     * collectionService().emptyListIfNull(null) = []
     *
     * @param <T>  the generic type
     * @param list 리스트 객체 또는 null
     * @return List 새롭게 생성한 리스트 객체
     * @throws Exception the exception
     * @since 2015. 3. 4
     */
    public static <T> List<T> emptyListIfNull(List<T> list) throws Exception {
        return ListUtils.emptyIfNull(list);
    }

    /**
     * 리스트를 사이즈별로 그룹핑 해서 서브 리스트로 리턴 한다.
     * <p/>
     * <p/>
     * <p/>
     * final List<String> list = new ArrayList<String>();<br/>
     * list.add("a");<br/>
     * list.add("b");<br/>
     * list.add("c");<br/>
     * list.add("d");<br/>
     * list.add("e");<br/>
     * <p/>
     * getCollectionService().partitionList(list, 2) = [[a, b], [c, d], [e]]
     *
     * @param <T>  the generic type
     * @param list 대상 리스트
     * @param size 분할 그룹
     * @return List 분할된 전체 리스트
     * @throws Exception the exception
     * @since 2015. 3. 4
     */
    public static <T> List<List<T>> partitionList(List<T> list, int size)
            throws Exception {
        return ListUtils.partition(list, size);
    }

    /**
     * 첫번째 리스트를 기준으로 두번째 리스트를 합친다 (중복 허용).
     * <p/>
     * <p/>
     * <p/>
     * final List<String> list = new ArrayList<String>();<br/>
     * list.add("a");<br/>
     * list.add("b");<br/>
     * list.add("c");<br/>
     * list.add("d");<br/>
     * list.add("e");<br/>
     * <p/>
     * <p/>
     * final List<String> list2 = new ArrayList<String>();<br/>
     * list2.add("a-");<br/>
     * list2.add("b-");<br/>
     * <p/>
     * getCollectionService().unionList(list, list2) = [a, b, c, d, e, a-, b-]
     * <br/>
     *
     * @param <E>   the element type
     * @param list1 기준 리스트
     * @param list2 추가할 리스트
     * @return List 합쳐진 리스트
     * @throws Exception the exception
     * @since 2015. 3. 4
     */
    public static <E> List<E> unionList(List<? extends E> list1,
                                        List<? extends E> list2) throws Exception {
        return ListUtils.union(list1, list2);

    }

    /**
     * 첫번째 리스트를 기준으로 두번째 리스트를 합친다 (중복 허용 안함, 순서 보장 안함).
     * <p/>
     * <p/>
     * <p/>
     * final List<String> list = new ArrayList<String>();<br/>
     * list.add("a");<br/>
     * list.add("b");<br/>
     * list.add("c");<br/>
     * list.add("d");<br/>
     * list.add("e");<br/>
     * <p/>
     * <p/>
     * final List<String> list2 = new ArrayList<String>();<br/>
     * list2.add("a");<br/>
     * list2.add("b");<br/>
     * <p/>
     * getCollectionService().sumList(list, list2) = [c, d, e, a, b] <br/>
     *
     * @param <E>   the element type
     * @param list1 리스트1
     * @param list2 리스트2
     * @return List 중복이 제거된 리스트
     * @throws Exception the exception
     * @since 2015. 3. 4
     */
    public static <E> List<E> sumList(List<? extends E> list1, List<? extends E> list2)
            throws Exception {
        return ListUtils.sum(list1, list2);
    }

    /**
     * 첫번째 리스트 와 두번째 리스트가 중복된 엘리먼트만 리스트에 새로 생성 한다. (중복 허용 안함, 순서 보장 안함).
     * <p/>
     * <p/>
     * <p/>
     * final List<String> list = new ArrayList<String>();<br/>
     * list.add("a");<br/>
     * list.add("b");<br/>
     * list.add("c");<br/>
     * list.add("d");<br/>
     * list.add("e");<br/>
     * <p/>
     * <p/>
     * final List<String> list2 = new ArrayList<String>();<br/>
     * list2.add("a");<br/>
     * list2.add("b");<br/>
     * <p/>
     * getCollectionService().intersectionList(list, list2) = [a,b] <br/>
     *
     * @param <E>   the element type
     * @param list1 [설명]
     * @param list2 [설명]
     * @return List [설명]
     * @throws Exception the exception
     * @since 2015. 3. 4
     */
    public static <E> List<E> intersectionList(List<? extends E> list1,
                                               List<? extends E> list2) throws Exception {
        return ListUtils.intersection(list1, list2);
    }

    /**
     * 조건 (Predicate)에 만족하는 엘리먼트로 구성된 새로운 리스트를 리턴 한다.
     * <p/>
     * <p/>
     * <p/>
     * final List<String> list = new ArrayList<String>();<br/>
     * list.add("a");<br/>
     * list.add("b");<br/>
     * list.add("c");<br/>
     * list.add("d");<br/>
     * list.add("e");<br/>
     * <p/>
     * Predicate condition = new Predicate() {<br/>
     *
     * @param <E>  the element type
     * @param 대상   리스트
     * @param 엘리먼트 조건
     * @return List 조건에 맞는 새로운 리스트
     * @throws Exception the exception
     * @Override<br/> public boolean evaluate(Object element) {<br/>
     * String value = (String) element;<br/>
     * <p/>
     * if (value.equals("a")) {<br/>
     * return true;<br/>
     * }<br/>
     * <p/>
     * return false;<br/>
     * }<br/>
     * <p/>
     * };<br/>
     * <p/>
     * getCollectionService().selectList(list, condition) = [a]<br/>
     * @since 2015. 3. 4
     */
    public static <E> List<E> selectList(Collection<? extends E> inputCollection,
                                         Predicate<? super E> predicate) throws Exception {

        return ListUtils.select(inputCollection, predicate);
    }

    /**
     * 리스트 순서를 역으로 sorting 후 새로운 리스트를 리턴 한다.
     * <p/>
     * <p/>
     * <p/>
     * final List<String> list = new ArrayList<String>();<br/>
     * list.add("a");<br/>
     * list.add("b");<br/>
     * list.add("c");<br/>
     * list.add("d");<br/>
     * list.add("e");<br/>
     * getCollectionService().reverseList(list) = [e, d, c, b, a]<br/>
     *
     * @param <T>  the generic type
     * @param list 대상 리스트
     * @return List 역순으로 sorting 된 리스트
     * @throws Exception the exception
     * @since 2015. 3. 4
     */
    public static <T> List<T> reverseList(List<T> list) throws Exception {
        return Lists.reverse(list);
    }

    /**
     * Set 데이터를 List 오브젝트로 변환 한다. 단, 순서는 보장하지 않는다.
     * <p/>
     * <p/>
     * <p/>
     * Map map = new HashMap();<br/>
     * map.put("1", "1-1");<br/>
     * map.put("2", "1-1");<br/>
     * <p/>
     * getCollectionService().setToList(map.keySet()) = [2, 1]<br/>
     *
     * @param <T> the generic type
     * @param set 리스트로 변환될 Set 오브젝트
     * @return List 리스트로 변환된 List 오브젝트
     * @throws Exception the exception
     * @since 2015. 3. 4
     */
    public static <T> List<T> setToList(Set<T> set) throws Exception {
        List<T> list = Lists.newArrayList();
        list.addAll(set);
        return list;
    }

    /**
     * Iterator를 List 형태로 변환 한다. 순서는 보장 하지 않는다.
     * <p/>
     * <p/>
     *
     * @param <E>      the element type
     * @param iterator
     * @return List
     * @throws Exception the exception
     * @since 2015. 3. 4
     */
    public static <E> List<E> iteratortoList(Iterator<? extends E> iterator)
            throws Exception {
        return IteratorUtils.toList(iterator);
    }

    /**
     * 콜렉션 객체를 리스트 오브젝트로 리턴 한다.
     * <p/>
     * <p/>
     *
     * @param <E>        the element type
     * @param collection [설명]
     * @return List [설명]
     * @since 2015. 3. 4
     */
    public static <E> List<E> collectionToList(Collection<? extends E> collection) {
        return new ArrayList<E>(collection);
    }

	/*
	 * ---------------------------------------------------------------------
	 * private method.
	 * ---------------------------------------------------------------------
	 */

}
