package innopolis.part1.lesson5.model;

import java.io.Serializable;

public interface IUUIDIdentified<UUID extends Serializable, PK extends Serializable>
        extends Identified<PK> {

    String getUuid();

}