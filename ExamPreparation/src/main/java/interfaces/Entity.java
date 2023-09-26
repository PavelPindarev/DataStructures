package interfaces;

import model.BaseEntity;

import java.util.List;

public interface Entity extends Comparable<Entity> {
    int getId();
    int getParentId();
    void setId(int id);
    void setParentId(Integer parentId);

    BaseEntity.Status getStatus();
    void setStatus(BaseEntity.Status status);
    void addChild(Entity child);
    List<Entity> getChildren();
}
