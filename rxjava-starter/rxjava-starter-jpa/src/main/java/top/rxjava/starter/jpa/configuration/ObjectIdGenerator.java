package top.rxjava.starter.jpa.configuration;

import org.bson.types.ObjectId;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class ObjectIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return getId();
    }

    /**
     * 该方法需要是线程安全的
     */
    public String getId() {
        synchronized (ObjectIdGenerator.class) {
            return new ObjectId().toString();
        }
    }
}
