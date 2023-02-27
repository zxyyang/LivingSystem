package com.atguigu.service;

import java.util.List;
import java.util.Map;

/**
 * Copyright: Copyright (C) 2022, Inc. All rights reserved.
 *
 * @author: zixuan.yang
 * @since: 2023/2/27 14:46
 */
public interface DictService {

    List<Map<String,Object>> findZnodes(Long id);
}
