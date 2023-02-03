package org.dog.server.service;

import org.dog.server.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Odin
 * @since 2023-02-03
 */
public interface IDictService extends IService<Dict> {

  List<Dict> findIcon();
}
