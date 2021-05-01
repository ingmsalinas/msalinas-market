package com.msalinas.market.persistence;

import com.msalinas.market.domain.Purchase;
import com.msalinas.market.domain.repository.PurchaseRepository;
import com.msalinas.market.persistence.crud.CompraCrudRepository;
import com.msalinas.market.persistence.entity.Compra;
import com.msalinas.market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {
    @Autowired
    private CompraCrudRepository compraCrudRepository;

    @Autowired
    private PurchaseMapper mapper;

    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByIdCliente(clientId)
                .map(compras -> mapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = mapper.toCompra(purchase);
        compra.getProductos().forEach(comprasProducto -> comprasProducto.setCompra(compra));

        return mapper.toPurchase(compraCrudRepository.save(compra));
    }
}
