package nju.yummy.dao;

import nju.yummy.entity.PayRecordEntity;

import java.util.List;

public interface RecordDao {
    public boolean insertRecord(PayRecordEntity payRecordEntity);

    public List<PayRecordEntity> getAllPayRecord();

    public List<PayRecordEntity> getRecordByUserId(String userId);
}
