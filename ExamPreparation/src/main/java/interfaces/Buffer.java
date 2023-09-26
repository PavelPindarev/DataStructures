package interfaces;

import model.BaseEntity;

import java.util.List;

public interface Buffer extends Iterable<Entity> {
    void add(Entity entity);
    Entity extract(int id);
    Entity find(Entity entity);
    boolean contains(Entity entity);
    int entitiesCount();
    void replace(Entity oldEntity, Entity newEntity);
    void swap(Entity first, Entity second);
    void clear();
    Entity[] toArray();
    List<Entity> retainAllFromTo(BaseEntity.Status lowerBound, BaseEntity.Status upperBound);
    List<Entity> getAll();
    void updateAll(BaseEntity.Status oldStatus, BaseEntity.Status newStatus);
    void removeSold();
}
