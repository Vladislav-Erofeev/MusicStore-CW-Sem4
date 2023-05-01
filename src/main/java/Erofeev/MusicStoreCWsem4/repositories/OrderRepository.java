package Erofeev.MusicStoreCWsem4.repositories;

import Erofeev.MusicStoreCWsem4.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOwnerId(long ownerId);
}
