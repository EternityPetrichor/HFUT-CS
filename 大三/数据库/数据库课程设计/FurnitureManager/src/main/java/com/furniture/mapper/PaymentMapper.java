package com.furniture.mapper;

import com.furniture.entity.Payment;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PaymentMapper {
    @Insert("INSERT INTO payments (sale_id, payment_date, amount, payment_method) " +
            "VALUES (#{payment.saleId}, #{payment.paymentDate}, #{payment.amount}, #{payment.paymentMethod})")
    @Options(useGeneratedKeys = true, keyProperty = "payment.paymentId")
    void addPayment(@Param("payment") Payment payment);

    @Select("SELECT * FROM payments WHERE payment_id = #{paymentId}")
    Payment getPaymentById(int paymentId);

    @Select("SELECT * FROM payments")
    List<Payment> getAllPayments();

    @Update("UPDATE payments SET " +
            "sale_id = #{payment.saleId}, " +
            "payment_date = #{payment.paymentDate}, " +
            "amount = #{payment.amount}, " +
            "payment_method = #{payment.paymentMethod} " +
            "WHERE payment_id = #{payment.paymentId}")
    void updatePayment(@Param("payment") Payment payment);

    @Delete("DELETE FROM payments WHERE payment_id = #{paymentId}")
    void deletePayment(int paymentId);
}
