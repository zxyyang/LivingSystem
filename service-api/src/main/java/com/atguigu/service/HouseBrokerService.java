package com.atguigu.service;

import com.atguigu.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerService extends BaseService<HouseBroker> {

    List<HouseBroker> findListByHouseId(Long houseId);
}