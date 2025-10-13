package com.suarez.service;

import com.suarez.model.Supplier;

public interface SupplierService {
    Supplier getSupplierById(int id);
    Supplier[] getAllSuppliers();
    Supplier addSupplier(Supplier supplier);
    Supplier updateSupplier(Supplier supplier);
    void deleteSupplier(int id);
}
