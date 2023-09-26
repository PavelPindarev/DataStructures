package core;

import interfaces.Buffer;
import interfaces.Entity;
import model.BaseEntity;

import java.util.Iterator;
import java.util.List;

public class Loader implements Buffer {

    @Override
    public void add(Entity entity) {

    }

    @Override
    public Entity extract(int id) {
        return null;
    }

    @Override
    public Entity find(Entity entity) {
        return null;
    }

    @Override
    public boolean contains(Entity entity) {
        return false;
    }

    @Override
    public int entitiesCount() {
        return 0;
    }

    @Override
    public void replace(Entity oldEntity, Entity newEntity) {

    }

    @Override
    public void swap(Entity first, Entity second) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Entity[] toArray() {
        return new Entity[0];
    }

    @Override
    public List<Entity> retainAllFromTo(BaseEntity.Status lowerBound, BaseEntity.Status upperBound) {
        return null;
    }

    @Override
    public List<Entity> getAll() {
        return null;
    }

    @Override
    public void updateAll(BaseEntity.Status oldStatus, BaseEntity.Status newStatus) {

    }

    @Override
    public void removeSold() {

    }

    @Override
    public Iterator<Entity> iterator() {
        return null;
    }
}
