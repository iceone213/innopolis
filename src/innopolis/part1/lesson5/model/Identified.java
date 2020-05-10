package innopolis.part1.lesson5.model;

import java.io.Serializable;

/**
 * Интерфейс идентифицируемых объектов.
 */
public interface Identified<PK extends Serializable> extends Serializable, Comparable {

    /** Возвращает идентификатор объекта */
    PK getId();

}