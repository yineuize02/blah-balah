package com.fml.blah.common.utils;

import cn.hutool.core.lang.Pair;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class BlahCollectors {

  static final Set<Collector.Characteristics> CH_ID =
      Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));

  private static <I, R> Function<I, R> castingIdentity() {
    return i -> (R) i;
  }

  static class CollectorImpl<T, A, R> implements Collector<T, A, R> {
    private final Supplier<A> supplier;
    private final BiConsumer<A, T> accumulator;
    private final BinaryOperator<A> combiner;
    private final Function<A, R> finisher;
    private final Set<Characteristics> characteristics;

    CollectorImpl(
        Supplier<A> supplier,
        BiConsumer<A, T> accumulator,
        BinaryOperator<A> combiner,
        Function<A, R> finisher,
        Set<Characteristics> characteristics) {
      this.supplier = supplier;
      this.accumulator = accumulator;
      this.combiner = combiner;
      this.finisher = finisher;
      this.characteristics = characteristics;
    }

    CollectorImpl(
        Supplier<A> supplier,
        BiConsumer<A, T> accumulator,
        BinaryOperator<A> combiner,
        Set<Characteristics> characteristics) {
      this(supplier, accumulator, combiner, castingIdentity(), characteristics);
    }

    @Override
    public BiConsumer<A, T> accumulator() {
      return accumulator;
    }

    @Override
    public Supplier<A> supplier() {
      return supplier;
    }

    @Override
    public BinaryOperator<A> combiner() {
      return combiner;
    }

    @Override
    public Function<A, R> finisher() {
      return finisher;
    }

    @Override
    public Set<Characteristics> characteristics() {
      return characteristics;
    }
  }

  public static <T, K, U> Collector<T, ?, Map<K, U>> toMap(
      Function<? super T, ? extends K> keyMapper,
      Function<? super T, ? extends U> valueMapper,
      U defaultValue) {
    return new CollectorImpl<>(
        HashMap::new,
        uniqKeysMapAccumulator(keyMapper, valueMapper, defaultValue),
        uniqKeysMapMerger(defaultValue),
        CH_ID);
  }

  /**
   * {@code BiConsumer<Map, T>} that accumulates (key, value) pairs extracted from elements into the
   * map, throwing {@code IllegalStateException} if duplicate keys are encountered.
   *
   * @param keyMapper a function that maps an element into a key
   * @param valueMapper a function that maps an element into a value
   * @param <T> type of elements
   * @param <K> type of map keys
   * @param <V> type of map values
   * @return an accumulating consumer
   */
  private static <T, K, V> BiConsumer<Map<K, V>, T> uniqKeysMapAccumulator(
      Function<? super T, ? extends K> keyMapper,
      Function<? super T, ? extends V> valueMapper,
      V defaultValue) {
    return (map, element) -> {
      K k = keyMapper.apply(element);
      V v = valueMapper.apply(element);
      if (v == null) {
        v = defaultValue;
      }
      V u = map.putIfAbsent(k, v);
      if (u != null) throw duplicateKeyException(k, u, v);
    };
  }

  private static IllegalStateException duplicateKeyException(Object k, Object u, Object v) {
    return new IllegalStateException(
        String.format("Duplicate key %s (attempted merging values %s and %s)", k, u, v));
  }

  /**
   * {@code BinaryOperator<Map>} that merges the contents of its right argument into its left
   * argument, throwing {@code IllegalStateException} if duplicate keys are encountered.
   *
   * @param <K> type of the map keys
   * @param <V> type of the map values
   * @param <M> type of the map
   * @return a merge function for two maps
   */
  private static <K, V, M extends Map<K, V>> BinaryOperator<M> uniqKeysMapMerger(V defaultValue) {
    return (m1, m2) -> {
      for (Map.Entry<K, V> e : m2.entrySet()) {
        K k = e.getKey();
        V v = e.getValue();
        if (v == null) {
          v = defaultValue;
        }
        V u = m1.putIfAbsent(k, v);
        if (u != null) throw duplicateKeyException(k, u, v);
      }
      return m1;
    };
  }

  public static void main(String[] args) {
    var l1 = List.of(new Pair<Integer, Integer>(1, 1), new Pair<>(2, null), new Pair<>(3, 3));
    var map = l1.stream().collect(BlahCollectors.toMap(Pair::getKey, Pair::getValue, 0));
    System.out.println(map);
    var map2 = l1.parallelStream().collect(BlahCollectors.toMap(Pair::getKey, Pair::getValue, 0));
    System.out.println(map2);
  }
}
