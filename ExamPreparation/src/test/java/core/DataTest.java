package core;

import interfaces.Entity;
import interfaces.Repository;
import model.BaseEntity;
import model.Invoice;
import model.StoreClient;
import model.User;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class DataTest {
    private Repository data;
    private int parentId;
    private Entity savedEntity;
    private int[] statusesCount;

    @Before
    public void setUp() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.data = new Data();
        Constructor<?>[] constructors = {getConstructor(Invoice.class), getConstructor(StoreClient.class), getConstructor(User.class)};
        Random random = new Random();
        BaseEntity.Status[] statuses = BaseEntity.Status.values();
        this.statusesCount = new int[statuses.length];
        List<Integer> parentIds = new ArrayList<>();
        parentIds.add(0);
        for (int i = 0; i < 25; i++) {
            Constructor<?> constructor = constructors[random.nextInt(constructors.length)];
            int nextInt = random.nextInt(i + 1);
            Entity entity = (Entity) constructor.newInstance(i, parentIds.get(nextInt));
            parentIds.add(i);
            if (entity.getId() == 13) {
                this.parentId = entity.getParentId();
                savedEntity = entity;
            }
            int enumIndex = random.nextInt(statuses.length);
            entity.setStatus(statuses[enumIndex]);
            statusesCount[enumIndex]++;
            this.data.add(entity);
        }
    }

    private Constructor<?> getConstructor(Class<?> clazz) throws NoSuchMethodException {
        return clazz.getDeclaredConstructor(int.class, int.class);
    }

    @Test
    public void add() {
        assertEquals(25, this.data.size());
        this.data.add(new Invoice(25, 24));
        assertEquals(26, this.data.size());
    }

    @Test
    public void getById() {
        Entity byId = this.data.getById(13);
        assertEquals(byId.getId(), 13);
        assertEquals(parentId, byId.getParentId());
    }

    @Test
    public void getByParentId() {
        List<Entity> byParentId = this.data.getByParentId(parentId);
        assertTrue(byParentId.stream().anyMatch(e -> e.getId() == 13));
        for (Entity entity : byParentId) {
            assertEquals(parentId, entity.getParentId());
        }
    }

    @Test
    public void getAll() {
        List<Entity> entities = this.data.getAll();
        entities.sort(Comparator.comparing(Entity::getId));
        assertEquals(25, entities.size());
        for (int i = 0; i < entities.size(); i++) {
            assertEquals(i, entities.get(i).getId());
        }
    }

    @Test
    public void copy() {
        Repository copy = this.data.copy();
        List<Entity> dataAll = this.data.getAll();
        List<Entity> copyAll = copy.getAll();
        dataAll.sort(Comparator.comparingInt(Entity::getId));
        copyAll.sort(Comparator.comparingInt(Entity::getId));
        assertEquals(dataAll.size(), copyAll.size());

        for (int i = 0; i < dataAll.size(); i++) {
            assertEquals(dataAll.get(i), copyAll.get(i));
        }
    }

    @Test
    public void getAllByType() {
        String type = Invoice.class.getSimpleName();
        ArrayList<Entity> entities = this.data.getAll().stream()
                .filter(entity -> entity.getClass().getSimpleName().equals(type))
                .collect(Collectors.toCollection(ArrayList::new));

        List<Entity> allByType = this.data.getAllByType(type);

        assertEquals(entities.size(), allByType.size());

        for (Entity entity : allByType) {
            assertEquals(type, entity.getClass().getSimpleName());
        }
    }

    @Test
    public void peekMostRecent() {
        Entity entity = this.data.peekMostRecent();
        assertEquals(25, this.data.size());
        assertEquals(24, entity.getId());
    }

    @Test
    public void pollMostRecent() {
        Entity entity = this.data.pollMostRecent();
        assertEquals(24, this.data.size());
        assertEquals(24, entity.getId());
        assertEquals(23, this.data.peekMostRecent().getId());
    }

    @Test
    public void getIdWhenNull() {
        for (int i = 0; i < 10; i++) {
            this.data.pollMostRecent();
        }
        assertEquals(15, this.data.size());
    }
}