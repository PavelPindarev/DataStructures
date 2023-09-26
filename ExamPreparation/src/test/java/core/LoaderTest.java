package core;

import interfaces.Buffer;
import interfaces.Entity;
import model.BaseEntity;
import model.Invoice;
import model.StoreClient;
import model.User;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class LoaderTest {

    private Buffer buffer;
    private Entity savedEntity;
    private int[] statusesCount;

    @Before
    public void setUp() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.buffer = new Loader();
        Constructor<?>[] constructors = {getConstructor(Invoice.class), getConstructor(StoreClient.class), getConstructor(User.class)};
        Random random = new Random();
        int boundIndex = random.nextInt(25);
        BaseEntity.Status[] statuses = BaseEntity.Status.values();
        this.statusesCount = new int[statuses.length];
        for (int i = 0; i < 25; i++) {
            Constructor<?> constructor = constructors[random.nextInt(constructors.length)];
            Entity entity = (Entity) constructor.newInstance(i, i + 13);
            if (i == boundIndex) {
                this.savedEntity = entity;
            }
            int enumIndex = random.nextInt(statuses.length);
            entity.setStatus(statuses[enumIndex]);
            statusesCount[enumIndex]++;
            this.buffer.add(entity);
        }
    }

    private Constructor<?> getConstructor(Class<?> clazz) throws NoSuchMethodException {
        return clazz.getDeclaredConstructor(int.class, int.class);
    }

    @Test
    public void add() {
        assertEquals(25, this.buffer.entitiesCount());
    }

    @Test
    public void extract() {
        Entity entity = this.buffer.extract(1);
        assertNotNull(entity);
        assertEquals(1, entity.getId());
    }

    @Test
    public void find() {
        Entity entity = this.buffer.find(this.savedEntity);
        assertNotNull(entity);
        assertEquals(this.savedEntity.getId(), entity.getId());
    }

    @Test
    public void isAdded() {
        assertTrue(this.buffer.contains(this.savedEntity));
    }

    @Test
    public void entitiesCount() {
        assertEquals(0, new Loader().entitiesCount());
        assertEquals(25, this.buffer.entitiesCount());
        this.buffer.extract(1);
        this.buffer.extract(2);
        this.buffer.extract(3);
        assertEquals(25 - 3, this.buffer.entitiesCount());
    }

    @Test
    public void replace() {
        this.buffer.replace(savedEntity, new Invoice(1000, 999));
        Entity entity = this.buffer.extract(1000);
        assertNotNull(entity);
    }

    @Test
    public void clear() {
        assertEquals(25, this.buffer.entitiesCount());
        this.buffer.clear();
        assertEquals(0, this.buffer.entitiesCount());
    }

    @Test
    public void toArray() {
        Entity[] entities = this.buffer.toArray();
        assertEquals(Entity[].class, entities.getClass());
        assertEquals(entities.length, this.buffer.entitiesCount());
    }

    @Test
    public void retainAll() {
        int totalCount = statusesCount[BaseEntity.Status.RESERVED.ordinal()]
                + statusesCount[BaseEntity.Status.PENDING_FUNDS.ordinal()]
                + statusesCount[BaseEntity.Status.PAYED.ordinal()];

        List<Entity> entities = this.buffer.retainAllFromTo(BaseEntity.Status.RESERVED, BaseEntity.Status.PAYED);

        assertEquals(totalCount, entities.size());
        assertFalse(entities.stream().anyMatch(e -> e.getStatus().equals(BaseEntity.Status.IN_STORE)));
        assertFalse(entities.stream().anyMatch(e -> e.getStatus().equals(BaseEntity.Status.SOLD)));
    }

    @Test
    public void updateAll() {
        Invoice invoice = new Invoice(26, 23);
        invoice.setStatus(BaseEntity.Status.IN_STORE);
        this.buffer.add(invoice);
        assertTrue(this.buffer.getAll().stream().anyMatch(e -> e.getStatus().equals(BaseEntity.Status.IN_STORE)));
        this.buffer.updateAll(BaseEntity.Status.IN_STORE, BaseEntity.Status.PAYED);
        List<Entity> entities = this.buffer.getAll();
        assertEquals(26, entities.size());
        assertFalse(entities.stream().anyMatch(e -> e.getStatus().equals(BaseEntity.Status.IN_STORE)));
    }

    @Test
    public void iterator() {
        int count = 0;
        assertEquals(25, this.buffer.entitiesCount());
        for (Entity entity : buffer) {
            count++;
        }
        assertEquals(count, this.buffer.entitiesCount());
    }

    @Test
    public void iter() {
        Loader entities = new Loader();
        List<Entity> entitiesAll = entities.retainAllFromTo(BaseEntity.Status.IN_STORE, BaseEntity.Status.SOLD);
        assertEquals(0, entitiesAll.size());
    }
}