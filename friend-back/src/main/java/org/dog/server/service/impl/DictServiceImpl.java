package org.dog.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dog.server.entity.Dict;
import org.dog.server.mapper.DictMapper;
import org.dog.server.service.IDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Odin
 * @since 2023-02-03
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

  @Resource
  private DictMapper dictMapper;

  @Override
  @Cacheable(value = "findIcons")
  public List<Dict> findIcon() {
    return list(new QueryWrapper<Dict>().eq("type", "icon"));
  }
}
