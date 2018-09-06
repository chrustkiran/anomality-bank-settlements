package com.pickme.anomality.entity;


import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("bank_settlement")
public class Response {
    @PrimaryKey
    private int id;

    private String transection_reference_id;

    private long created_datetime;

    private int status;

    private int payment_type;

    private long payment_type_reference_id;

    private long updated_datetime;

    private int bank;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransection_reference_id() {
        return transection_reference_id;
    }

    public void setTransection_reference_id(String transection_reference_id) {
        this.transection_reference_id = transection_reference_id;
    }

    public long getCreated_datetime() {
        return created_datetime;
    }

    public void setCreated_datetime(long created_datetime) {
        this.created_datetime = created_datetime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(int payment_type) {
        this.payment_type = payment_type;
    }

    public long getPayment_type_reference_id() {
        return payment_type_reference_id;
    }

    public void setPayment_type_reference_id(long payment_type_reference_id) {
        this.payment_type_reference_id = payment_type_reference_id;
    }

    public long getUpdated_datetime() {
        return updated_datetime;
    }

    public void setUpdated_datetime(long updated_datetime) {
        this.updated_datetime = updated_datetime;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }
}
