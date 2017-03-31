package ua.org.javatraining.andrii_tkachenko.data.repository;

import org.springframework.data.repository.CrudRepository;
import ua.org.javatraining.andrii_tkachenko.data.model.attribute.AttributeAssociation;

/**
 * Created by tkaczenko on 31.03.17.
 */
public interface AttributeAssociationRepository extends CrudRepository<AttributeAssociation, Integer> {
}
