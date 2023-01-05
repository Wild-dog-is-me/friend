package org.dog.server.service.impl;

import org.dog.server.entity.Dynamic;
import org.dog.server.mapper.DynamicMapper;
import org.dog.server.service.IDynamicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 动态 服务实现类
 * </p>
 *
 * @author Odin
 * @since 2023-01-04
 */
@Service
public class DynamicServiceImpl extends ServiceImpl<DynamicMapper, Dynamic> implements IDynamicService {

}
