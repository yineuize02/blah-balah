package com.fml.blah.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fml.blah.user.entity.Users;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper 接口
 *
 * @author y
 * @since 2021-05-28
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

  Integer batchUpdate(List<Users> records);
}
