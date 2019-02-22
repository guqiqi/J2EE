package nju.yummy.service;

import nju.yummy.vo.Location;

public interface AddressService {
    /**
     * 判断能否送达
     * @param location1 第一个地址
     * @param location2 第二个地址
     * @return 结果
     */
    public boolean judgeCanSend(Location location1, Location location2);
}
