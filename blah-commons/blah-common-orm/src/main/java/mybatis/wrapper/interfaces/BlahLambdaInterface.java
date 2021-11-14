package mybatis.wrapper.interfaces;

import java.io.Serializable;
import java.util.Collection;

public interface BlahLambdaInterface<Children, R> extends Serializable {

  Children emptySafeIn(boolean condition, R column, Collection<?> coll);
}
