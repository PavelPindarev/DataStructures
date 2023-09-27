package core;

import interfaces.Buffer;
import interfaces.Entity;
import model.BaseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Loader implements Buffer {

    private List<Entity> data;

    public Loader() {
        this.data = new ArrayList<>();
    }

    @Override
    public void add(Entity entity) {
        this.data.add(entity);
    }

    @Override
    public Entity extract(int id) {
        Entity resultEntity = null;
        for (Entity entity : this.data) {
            if (entity.getId() == id) {
                resultEntity = entity;
                break;
            }
        }
        if (resultEntity != null) {
            this.data.remove(resultEntity);
        }
        return resultEntity;
    }

    @Override
    public Entity find(Entity entity) {
        return this.data.contains(entity) ? entity : null;
    }

    @Override
    public boolean contains(Entity entity) {
        return this.data.contains(entity);
    }

    @Override
    public int entitiesCount() {
        return this.data.size();
    }

    @Override
    public void replace(Entity oldEntity, Entity newEntity) {
        int index = this.data.indexOf(oldEntity);
        if (index == -1) {
            throw new IllegalStateException("Entity not found");
        }
        this.data.set(index, newEntity);
    }

    @Override
    public void swap(Entity first, Entity second) {
        int firstIndex = this.data.indexOf(first);
        int secondIndex = this.data.indexOf(second);
        if (firstIndex == -1 || secondIndex == -1) {
            throw new IllegalStateException("Entities not found");
        }
        Collections.swap(this.data, firstIndex, secondIndex);
    }

    @Override
    public void clear() {
        this.data.clear();
    }

    @Override
    public Entity[] toArray() {
        Entity[] array = new Entity[this.data.size()];
        for (int i = 0; i < this.data.size(); i++) {
            array[i] = this.data.get(i);
        }
        return array;
    }

    @Override
    public List<Entity> retainAllFromTo(BaseEntity.Status lowerBound, BaseEntity.Status upperBound) {
        List<Entity> result = new ArrayList<>();
        for (Entity datum : this.data) {
            if (statusInBounds(datum.getStatus(), lowerBound, upperBound)) {
                result.add(datum);
            }
        }
        return result;
    }

    private boolean statusInBounds(BaseEntity.Status status, BaseEntity.Status lowerBound, BaseEntity.Status upperBound) {
        return status.compareTo(lowerBound) >= 0 && status.compareTo(upperBound) <= 0;
    }

    @Override
    public List<Entity> getAll() {
        return this.data;
    }

    @Override
    public void updateAll(BaseEntity.Status oldStatus, BaseEntity.Status newStatus) {
        for (Entity datum : this.data) {
            if (datum.getStatus().compareTo(oldStatus) == 0) {
                datum.setStatus(newStatus);
            }
        }
    }

    @Override
    public void removeSold() {
        this.data.removeIf(current ->
                current.getStatus().equals(BaseEntity.Status.SOLD));
    }

    @Override
    public Iterator<Entity> iterator() {
        return new Iterator<Entity>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index + 1 <= data.size();
            }

            @Override
            public Entity next() {
                return data.get(index++);
            }
        };
    }
}
