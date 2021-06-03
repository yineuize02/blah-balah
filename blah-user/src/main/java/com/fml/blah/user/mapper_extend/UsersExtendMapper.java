package com.fml.blah.user.mapper_extend;

import com.fml.blah.user.entity.Users;
import java.util.List;

public interface UsersExtendMapper {

  Integer batchInsertOrUpdate(List<Users> records);
}
