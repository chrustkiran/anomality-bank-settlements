package com.pickme.anomality.dao;

import com.pickme.anomality.entity.Response;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ResponseRepository extends CrudRepository<Response,Integer> {

    @Query("SELECT * FROM bank_settlement")
    public Iterable<Response> findAll();

    @Query("SELECT * from bank_settlement where transection_id = ?0 allow filtering")
     public Response findById(int transection_id);

    @Query("INSERT INTO bank_settlement (transection_id, created_time, status) VALUES (?0,?1,?2)")       //THIS HAS TO BE CHANGED
    void insertResponse(int transection_id , long created_time , String status);

    @Query("SELECT COUNT(id) FROM bank_settlement WHERE bank=2 AND status=3 AND updated_datetime >= ?0 allow filtering")
    int findCountUpdatedFail(long time_interval);


    @Query("SELECT COUNT(id) FROM bank_settlement WHERE bank=2 AND status=2 AND updated_datetime >= ?0 allow filtering")
    int findCountRecievedFail(long time_interval);

    @Query("SELECT COUNT(id) FROM bank_settlement WHERE bank=2 AND updated_datetime >= ?0 allow filtering")
    int findCountinSpecifiedTime(long time_interval);


}