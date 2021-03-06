package com.db.herviz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.db.herviz.entity.Office;

/**
 * @Author: Weiqi Chen
 * @Date: 2022/4/18 19:19
 */
public interface OfficeService extends IService<Office> {
    Page<Office> getOfficeList(String keywords, Integer page, Integer limit);
}
