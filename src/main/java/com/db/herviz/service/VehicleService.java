package com.db.herviz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.db.herviz.entity.Vehicle;

import java.util.List;

/**
 * @Author: Chen Weiqi
 * @Date: 2022/4/18 16:39
 */
public interface VehicleService extends IService<Vehicle> {

    List<Vehicle> getCarList(Long loc);

    Page<Vehicle> getVehicleList(String keywords, Integer page, Integer limit);
}
