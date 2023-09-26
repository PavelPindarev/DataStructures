package core;

import interfaces.Entity;
import interfaces.Repository;

import java.util.List;

public class Data implements Repository {

    @Override
    public void add(Entity entity) {

    }

    @Override
    public Entity getById(int id) {
        return null;
    }

    @Override
    public List<Entity> getByParentId(int id) {
        return null;
    }

    @Override
    public List<Entity> getAll() {
        return null;
    }

    @Override
    public Repository copy() {
        return null;
    }

    @Override
    public List<Entity> getAllByType(String type) {
        return null;
    }

    @Override
    public Entity peekMostRecent() {
        return null;
    }

    @Override
    public Entity pollMostRecent() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
