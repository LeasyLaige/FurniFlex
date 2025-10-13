package com.suarez.serviceimpl;

import com.suarez.entity.SupplierData;
import com.suarez.model.Supplier;
import com.suarez.repository.SupplierDataRepository;
import com.suarez.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {
    Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);

    @Autowired
    SupplierDataRepository supplierDataRepository;


    @Override
    public Supplier getSupplierById(int id) {
        logger.info("Getting supplier with id: " + id);
        Supplier supplier = null;
        Optional<SupplierData> supplierData = supplierDataRepository.findById(id);

            if (supplierData.isPresent()) {
                supplier = new Supplier();
                supplier.setId(supplierData.get().getId());
                supplier.setName(supplierData.get().getName());
                supplier.setContactInfo(supplierData.get().getContactInfo());
                supplier.setAddress(supplierData.get().getAddress());
                logger.info("Successfully found supplier with id: " + id);
            } else {
                logger.warn("Supplier with id: " + id + " not found");
            }

        return null;
    }

    @Override
    public Supplier[] getAllSuppliers() {
        List<SupplierData> suppliersData = new ArrayList<>();
        List<Supplier> suppliers = new ArrayList<>();

        supplierDataRepository.findAll().forEach(suppliersData::add);
        Iterator<SupplierData> it = suppliersData.iterator();

        while (it.hasNext()) {
            SupplierData supplierData = it.next();
            Supplier supplier = new Supplier();
            supplier.setId(supplierData.getId());
            supplier.setName(supplierData.getName());
            supplier.setContactInfo(supplierData.getContactInfo());
            supplier.setAddress(supplierData.getAddress());
            suppliers.add(supplier);
        }

        Supplier[] array = new Supplier[suppliers.size()];
        for (int i = 0; i < suppliers.size(); i++) {
            array[i] = suppliers.get(i);
        }

        return array;
    }

    @Override
    public Supplier addSupplier(Supplier supplier) {
        logger.info(" add:Input " + supplier.toString());
        SupplierData supplierData = new SupplierData();
        supplierData.setName(supplier.getName());
        supplierData.setContactInfo(supplier.getContactInfo());
        supplierData.setAddress(supplier.getAddress());
        supplierData = supplierDataRepository.save(supplierData);

        logger.info(" add:Output " + supplierData.toString());
        Supplier newSupplier = new Supplier();
        newSupplier.setId(supplierData.getId());
        newSupplier.setName(supplierData.getName());
        newSupplier.setContactInfo(supplierData.getContactInfo());
        newSupplier.setAddress(supplierData.getAddress());

        return newSupplier;
    }

    @Override
    public Supplier updateSupplier(Supplier supplier) {
        Supplier updatedSupplier = null;
        int id = supplier.getId();
        Optional<SupplierData> optional = supplierDataRepository.findById(id);

        if (optional.isPresent()) {
            SupplierData originalSupplierData = new SupplierData();
            originalSupplierData.setName(supplier.getName());
            originalSupplierData.setContactInfo(supplier.getContactInfo());
            originalSupplierData.setAddress(supplier.getAddress());

            SupplierData supplierData = supplierDataRepository.save(originalSupplierData);

            updatedSupplier = new Supplier();
            updatedSupplier.setId(supplierData.getId());
            updatedSupplier.setName(supplierData.getName());
            updatedSupplier.setContactInfo(supplierData.getContactInfo());
            updatedSupplier.setAddress(supplierData.getAddress());
        } else {
            logger.error("Supplier record with id: " + id + " does not exist");
        }

        return updatedSupplier;
    }

    @Override
    public void deleteSupplier(int id) {
        Supplier supplier = null;
        logger.info(" Input >> " + id);
        Optional<SupplierData> optional = supplierDataRepository.findById(id);

        if (optional.isPresent()) {
            SupplierData supplierData = optional.get();
            supplierDataRepository.delete(supplierData);
            logger.info(" Supplier with id: " + id + " has been deleted");
        } else {
            logger.error("Supplier record with id: " + id + " does not exist");
        }
    }
}
