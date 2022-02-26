package mybatis;

import com.baomidou.mybatisplus.core.conditions.AbstractLambdaWrapper;
import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import mybatis.wrapper.interfaces.BlahLambdaInterface;

public class BlahLambdaQueryWrapper<T> extends AbstractLambdaWrapper<T, BlahLambdaQueryWrapper<T>>
    implements Query<BlahLambdaQueryWrapper<T>, T, SFunction<T, ?>>,
        BlahLambdaInterface<BlahLambdaQueryWrapper<T>, SFunction<T, ?>> {
  private SharedString sqlSelect = new SharedString();

  /** 不建议直接 new 该实例，使用 Wrappers.lambdaQuery(entity) */
  public BlahLambdaQueryWrapper() {
    this((T) null);
  }

  /** 不建议直接 new 该实例，使用 Wrappers.lambdaQuery(entity) */
  public BlahLambdaQueryWrapper(T entity) {
    super.setEntity(entity);
    super.initNeed();
  }

  /** 不建议直接 new 该实例，使用 Wrappers.lambdaQuery(entity) */
  public BlahLambdaQueryWrapper(Class<T> entityClass) {
    super.setEntityClass(entityClass);
    super.initNeed();
  }

  BlahLambdaQueryWrapper(
      T entity,
      Class<T> entityClass,
      SharedString sqlSelect,
      AtomicInteger paramNameSeq,
      Map<String, Object> paramNameValuePairs,
      MergeSegments mergeSegments,
      SharedString lastSql,
      SharedString sqlComment,
      SharedString sqlFirst) {
    super.setEntity(entity);
    super.setEntityClass(entityClass);
    this.paramNameSeq = paramNameSeq;
    this.paramNameValuePairs = paramNameValuePairs;
    this.expression = mergeSegments;
    this.lastSql = lastSql;
    this.sqlComment = sqlComment;
    this.sqlFirst = sqlFirst;
    this.sqlSelect = sqlSelect;
  }

  @Override
  protected BlahLambdaQueryWrapper<T> instance() {
    return getInstance();
  }

  private BlahLambdaQueryWrapper<T> getInstance() {
    return new BlahLambdaQueryWrapper<>(
        getEntity(),
        getEntityClass(),
        null,
        paramNameSeq,
        paramNameValuePairs,
        new MergeSegments(),
        SharedString.emptyString(),
        SharedString.emptyString(),
        SharedString.emptyString());
  }

  @SafeVarargs
  @Override
  public final BlahLambdaQueryWrapper<T> select(SFunction<T, ?>... columns) {
    if (ArrayUtils.isNotEmpty(columns)) {
      this.sqlSelect.setStringValue(columnsToString(false, columns));
    }
    return typedThis;
  }

  @Override
  public BlahLambdaQueryWrapper<T> select(
      Class<T> entityClass, Predicate<TableFieldInfo> predicate) {
    if (entityClass == null) {
      entityClass = getEntityClass();
    } else {
      setEntityClass(entityClass);
    }
    Assert.notNull(entityClass, "entityClass can not be null");
    this.sqlSelect.setStringValue(
        TableInfoHelper.getTableInfo(entityClass).chooseSelect(predicate));
    return typedThis;
  }

  @Override
  public String getSqlSelect() {
    return this.sqlSelect.getStringValue();
  }

  @Override
  public BlahLambdaQueryWrapper<T> emptySafeIn(
      boolean condition, SFunction<T, ?> column, Collection<?> coll) {

    if (coll != null && coll.size() > 0) {
      return in(condition, column, coll);
    }

    return apply("1!=1");
  }

  @Override
  public void clear() {
    super.clear();
    this.sqlSelect.toNull();
  }
}
