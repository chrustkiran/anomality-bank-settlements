package com.pickme.anomality.dao;

import com.pickme.anomality.entity.Response;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ResponseRepository extends CrudRepository<Response,Integer> {

    @Query("SELECT * FROM bank_settlement")
    public Iterable<Response> findAll();

    @Query("SELECT * from bank_settlement where transection_id = ?0 allow filtering")
     public Response findById(int transection_id);

    @Query("INSERT INTO bank_settlement (id, bank, transaction_reference_id, payment_type, payment_type_reference_id, status, updated_datetime, created_datetime ) VALUES (?0,?1,?2,?3,?4,?5,?6,?7)")
    void insertResponse(int id , int bank , String transaction_reference_id, int payment_type , long payment_type_reference_id , int status , long updated_datetime , long created_datetime);

    @Query("SELECT COUNT(id) FROM bank_settlement WHERE bank=2 AND status=3 AND updated_datetime >= ?0 AND updated_datetime <= ?1 allow filtering")
    int findCountUpdatedFail(long specified_time , long current_time);


    @Query("SELECT COUNT(id) FROM bank_settlement WHERE bank=2 AND status=2 AND updated_datetime >= ?0 AND updated_datetime <= ?1 allow filtering")
    int findCountReceivedFail(long specified_time , long current_time);

    @Query("SELECT COUNT(id) FROM bank_settlement WHERE bank=2 AND updated_datetime >= ?0 AND updated_datetime <= ?1 allow filtering")
    int findCountinSpecifiedTime(long specified_time , long current_time);




}