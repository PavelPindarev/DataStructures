package core;

import interfaces.Entity;
import interfaces.Repository;

import java.util.*;
import java.util.stream.Collectors;

public class Data implements Repository {
    private ArrayDeque<Entity> data;

    public Data() {
        this.data = new ArrayDeque<>();
    }

    public Data(Data other) {
        this.data = other.data;
    }

    @Override
    public void add(Entity entity) {
        this.data.add(entity);
    }

    @Override
    public Entity getById(int id) {
        return this.data.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Entity> getByParentId(int id) {
        List<Entity> children = new ArrayList<>();
        for (Entity datum : data) {
            if (datum.getParentId() == id) {
                children.add(datum);
            }
        }
        return children;
    }

    @Override
    public List<Entity> getAll() {
        return new ArrayList<>(this.data);
    }

    @Override
    public Repository copy() {
        return new Data(this);
    }

    @Override
    public List<Entity> getAllByType(String type) {
        List<Entity> result = new ArrayList<>();
        for (Entity datum : data) {
            if (datum.getClass().getSimpleName().equals(type)) {
                result.add(datum);
            }
        }
        return result;
    }

    @Override
    public Entity peekMostRecent() {
        return this.data.peekLast();
    }

    @Override
    public Entity pollMostRecent() {
        return this.data.pollLast();
    }

    @Override
    public int size() {
        return this.data.size();
    }
}
