import org.junit.jupiter.api.Test;
import ru.hse.fmcs.DictionaryImpl;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTest {
    private static final int sizeOfTestInput = 100;

    /* testing size() */

    @Test
    public void testSizeAfterCreating() {
        assertEquals((new DictionaryImpl<Integer, Integer>()).size(), 0);
    }

    @Test
    public void testSizeAfterSimplePut() {
        DictionaryImpl<Integer, Integer> dict = new DictionaryImpl<>();
        dict.put(1, 2);
        assertEquals(dict.size(), 1);
    }

    @Test
    public void testSizeAfterPuttingNewKeys() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 1; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
            assertEquals(d.size(), i);
        }
    }

    @Test
    public void testSizeAfterPuttingSameKey() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 1; i < sizeOfTestInput; i++) {
            d.put(0, i);
            assertEquals(d.size(), 1);
        }
    }

    @Test
    public void testSizeAfterRemove() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 0; i < sizeOfTestInput; i++) {
            d.put(i, i);
            assertEquals(d.size(), i + 1);
        }
        for (int i = 0; i < sizeOfTestInput; i++) {
            d.remove(i);
            assertEquals(d.size(), sizeOfTestInput - i - 1);
        }
    }

    @Test
    public void testSizeAfterClear() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 0; i < sizeOfTestInput; i++) {
            d.put(i, i);
            assertEquals(d.size(), i + 1);
        }
        d.clear();
        assertEquals(d.size(), 0);
    }

    /* testing containsKey() */
    @Test
    public void testContainsKeyForExisted() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 1; i < sizeOfTestInput; i++) {
            d.put(i, i);
            assertTrue(d.containsKey(i));
        }
    }

    @Test
    public void testContainsKeyForNonExisted() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 1; i < sizeOfTestInput; i++) {
            d.put(i, i);
            assertTrue(d.containsKey(i));
            assertFalse(d.containsKey(i + 1));
        }
    }

    @Test
    public void testContainsKeyInEmptyDictionary() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        d.put(0, 0);
        d.remove(0);
        for (int i = 1; i < sizeOfTestInput; i++) {
            assertFalse(d.containsKey(i));
        }
    }

    @Test
    public void testContainsKeyAfterRemove() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 0; i < sizeOfTestInput; i++) {
            d.put(i, i);
        }
        for (int i = 0; i < sizeOfTestInput; i++) {
            d.remove(i);
            assertFalse(d.containsKey(i));
        }
    }

    /* test get */

    @Test
    public void testGetAfterPuttingNewKeys() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 1; i < sizeOfTestInput; i++) {
            d.put(i, i);
            assertEquals(d.get(i), i);
        }
    }

    @Test
    public void testGetAfterUpdatingValue() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 1; i < sizeOfTestInput; i++) {
            d.put(0, i);
            assertEquals(d.get(0), i);
        }
    }

    @Test
    public void testGetAfterResize() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>(0.3, sizeOfTestInput);
        for (int i = 1; i < sizeOfTestInput; i++) {
            d.put(i, i);
            assertEquals(d.get(i), i);
        }
    }

    @Test
    public void testGetAfterRemove() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 0; i < sizeOfTestInput; i++) {
            d.put(i, i);
        }
        for (int i = 0; i < sizeOfTestInput; i++) {
            d.remove(i);
            assertNull(d.get(i));
        }
    }

    @Test
    public void testGetInEmptyDictionary() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        d.put(0, 0);
        d.remove(0);
        for (int i = 1; i < sizeOfTestInput; i++) {
            assertNull(d.get(i));
        }
    }

    @Test
    public void testGetForNonExistedKeys() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 1; i < sizeOfTestInput; i++) {
            d.put(i, i);
            assertNull(d.get(i + 1));
        }
    }

    /* test put */

    @Test
    public void testPutNewKeysSimple() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        assertNull(d.put(0, 2));
        assertNull(d.put(-1, 2));
        assertNull(d.put(30248923, 2));
        assertEquals(d.size(), 3);

        DictionaryImpl<String, Integer> d2 = new DictionaryImpl<>();
        assertNull(d2.put("one", 2));
        assertNull(d2.put("two", 2));
        assertNull(d2.put("three", 2));
        assertEquals(d2.size(), 3);

        DictionaryImpl<ArrayList<Integer>, Integer> d3 = new DictionaryImpl<>();
        var v1 = new ArrayList<Integer>();
        v1.add(0);
        var v2 = new ArrayList<Integer>();
        v2.add(1);
        var v3 = new ArrayList<Integer>();
        v3.add(2);
        assertNull(d3.put(v1, 2));
        assertNull(d3.put(v2, 2));
        assertNull(d3.put(v3, 2));
        assertEquals(d2.size(), 3);
    }

    @Test
    public void testPutSameKeySimple() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        assertNull(d.put(0, 2));
        assertNull(d.put(-1, 2));
        assertNull(d.put(30248923, 2));
        assertEquals(d.put(0, 3), 2);
        assertEquals(d.size(), 3);

        DictionaryImpl<String, Integer> d2 = new DictionaryImpl<>();
        String s1 = "string";
        String s2 = "string"; // кеширование
        String s3 = "pip";
        assertNull(d2.put(s1, 2));
        assertEquals(d2.put(s2, 3), 2);
        assertNull(d2.put(s3, 2));
    }

    @Test
    public void testPutAfterRemove() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
            assertEquals(d.remove(i), i);
            assertNull(d.put(i, i));
        }
    }

    @Test
    public void testPutAfterClear() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
        }
        d.clear();
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
        }
    }

    @Test
    public void testPutAfterResize() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>(0.2, sizeOfTestInput);
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
        }
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertEquals(d.put(i, i), i);
        }
    }

    @Test
    public void testPutStress() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
        }
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertEquals(d.put(i, i + 1), i);
        }

        DictionaryImpl<String, Integer> d2 = new DictionaryImpl<>();
        String s = "a".repeat(sizeOfTestInput);
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d2.put(s.substring(i), i));
        }
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertEquals(d2.put(s.substring(i), i + 1), i);
        }
    }

    /* test remove */
    @Test
    public void testRemoveNewKeysSimple() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        assertNull(d.put(0, 2));
        assertNull(d.put(-1, 2));
        assertNull(d.put(30248923, 2));
        assertEquals(d.remove(0), 2);
        assertEquals(d.remove(-1), 2);
        assertEquals(d.remove(30248923), 2);
        assertEquals(d.size(), 0);

        DictionaryImpl<String, Integer> d2 = new DictionaryImpl<>();
        assertNull(d2.put("one", 2));
        assertNull(d2.put("two", 2));
        assertNull(d2.put("three", 2));
        assertEquals(d2.remove("one"), 2);
        assertEquals(d2.remove("two"), 2);
        assertEquals(d2.remove("three"), 2);
        assertEquals(d2.size(), 0);

        DictionaryImpl<ArrayList<Integer>, Integer> d3 = new DictionaryImpl<>();
        var v1 = new ArrayList<Integer>();
        v1.add(0);
        var v2 = new ArrayList<Integer>();
        v2.add(1);
        var v3 = new ArrayList<Integer>();
        v3.add(2);
        assertNull(d3.put(v1, 2));
        assertNull(d3.put(v2, 2));
        assertNull(d3.put(v3, 2));
        assertEquals(d3.remove(v1), 2);
        assertEquals(d3.remove(v2), 2);
        assertEquals(d3.remove(v3), 2);
        assertEquals(d2.size(), 0);
    }

    @Test
    public void testRemoveNonExisted() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        assertNull(d.put(-1, 0));
        assertNull(d.remove(0));
        assertEquals(d.size(), 1);
        assertEquals(d.remove(-1), 0);
        assertEquals(d.size(), 0);
        assertNull(d.remove(-1));
        assertEquals(d.size(), 0);
    }

    @Test
    public void testRemoveAfterResize() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>(sizeOfTestInput / 10);
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
        }
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertEquals(d.remove(i), i);
            assertEquals(d.size(), sizeOfTestInput - i - 1);
        }
    }

    @Test
    public void testRemoveAfterClear() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>(sizeOfTestInput);
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
        }
        d.clear();
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.remove(i));
            assertEquals(d.size(), 0);
        }
    }

    @Test
    public void testRemoveCheckValue() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>(sizeOfTestInput);
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
            assertEquals(d.remove(i), i);
            assertNull(d.put(i, i));
            assertEquals(d.put(i, i + 1), i);
            assertEquals(d.remove(i), i + 1);
        }
    }

    @Test
    public void testClear() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>(sizeOfTestInput);
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
        }
        assertEquals(d.size(), sizeOfTestInput);
        d.clear();
        assertEquals(d.size(), 0);
    }

    @Test
    public void testKeySetElements() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>(sizeOfTestInput);
        Set<Integer> s = new HashSet<>();
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
            s.add(i);
        }
        assertEquals(s, d.keySet());
    }

    @Test
    public void testKeySetIteratorNext() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>(sizeOfTestInput);
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
        }
        for (Integer key : d.keySet()) {
            assertTrue(d.containsKey(key));
        }
    }

    @Test
    public void testKeySetIteratorRemove() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
        }
        for (var iterator = d.keySet().iterator(); iterator.hasNext(); ) {
            var key = iterator.next();
            iterator.remove();
            assertNull(d.get(key));
        }
        assertEquals(d.size(), 0);
    }

    @Test
    public void testValueCollectionElements() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>(sizeOfTestInput);
        Collection<Integer> c = new ArrayList<>();
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
            c.add(i);
        }
        assertTrue(c.containsAll(d.values()));
        assertEquals(c.size(), d.size());
    }

    @Test
    public void testValueCollectionIteratorNext() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>(sizeOfTestInput);
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
        }
        for (Integer val : d.values()) {
            assertTrue(d.containsKey(val));
        }
    }

    @Test
    public void testValueCollectionIteratorRemove() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
        }
        for (var iterator = d.values().iterator(); iterator.hasNext(); ) {
            iterator.next();
            iterator.remove();
        }
        assertEquals(d.size(), 0);
    }

    @Test
    public void testEntrySetElements() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>(sizeOfTestInput);
        Set<Map.Entry<Integer, Integer>> s = new HashSet<>();
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
            s.add(new AbstractMap.SimpleEntry<>(i, i));
        }
        assertEquals(s, d.entrySet());
    }

    @Test
    public void testEntrySetIteratorNext() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>(sizeOfTestInput);
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
        }
        for (Map.Entry<Integer, Integer> entry : d.entrySet()) {
            assertEquals(d.get(entry.getKey()), entry.getValue());
        }
    }

    @Test
    public void testEntrySetIteratorRemove() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(i, i));
        }
        for (var iterator = d.entrySet().iterator(); iterator.hasNext(); ) {
            var entry = iterator.next();
            iterator.remove();
            assertNull(d.get(entry.getKey()));
        }
        assertEquals(d.size(), 0);
    }

    @Test
    public void testNegativeHash() {
        DictionaryImpl<Integer, Integer> d = new DictionaryImpl<>();
        for (int i = 0; i < sizeOfTestInput; i++) {
            assertNull(d.put(-i, i));
            assertEquals(d.get(-i), i);
        }
    }
}
