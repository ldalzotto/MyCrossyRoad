package internal.environnement.manager.mapper;

/**
 * Created by ldalzotto on 27/02/2017.
 */
@FunctionalInterface
public interface IConverter<A,B> {
    public B apply(A a);
}
