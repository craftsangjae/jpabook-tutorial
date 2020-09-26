package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.DeliveryStatus;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class DeliveryRepository {
    private final EntityManager em;

    public Long save(Delivery delivery) {
        if (delivery.getId() == null){
            em.persist(delivery);
            return delivery.getId();
        } else {
            return em.merge(delivery).getId();
        }
    }

    public Long createByMemberAddress(Member member){
        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.READY);
        delivery.setAddress(member.getAddress());
        return save(delivery);
    }

    public Delivery findOne(Long deliveryId) {
        return em.find(Delivery.class, deliveryId);
    }
}
