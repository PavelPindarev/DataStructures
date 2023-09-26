package interfaces;

import java.util.List;

public interface Repository {
    void add(Entity entity);
    Entity getById(int id);
    List<Entity> getByParentId(int id);
    List<Entity> getAll();
    Repository copy();
    List<Entity> getAllByType(String type);
    Entity peekMostRecent();
    Entity pollMostRecent();
    int size();
}
