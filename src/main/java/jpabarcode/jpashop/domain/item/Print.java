package jpabarcode.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("P")
@Getter
@Setter
public class Print extends Item {

    private String etc;
}
