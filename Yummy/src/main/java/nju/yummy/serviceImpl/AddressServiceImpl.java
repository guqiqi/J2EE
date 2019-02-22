package nju.yummy.serviceImpl;

import nju.yummy.service.AddressService;
import nju.yummy.vo.Location;

public class AddressServiceImpl implements AddressService {
    @Override
    public boolean judgeCanSend(Location location1, Location location2) {
        return Math.random() > 0.2;
    }
}
