package INTEnvironnementManager.mapper.interfaceMapper;

/**
 * Created by ldalzotto on 27/02/2017.
 */
public interface IConverter<A,B> {
    public B apply(A a);
}
