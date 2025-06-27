package com.furniture.service;

import com.furniture.entity.Supplier;

import java.util.List;

public interface SupplierService {
    Supplier findSuppliersById(int id);
    List<Supplier> findAllSuppliers();
    void addSupplier(Supplier supplier);
    void updateSupplier(Supplier supplier);
    void delete(int id);
}
